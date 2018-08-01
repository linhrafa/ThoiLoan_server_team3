package service;

import bitzero.server.core.BZEventParam;
import bitzero.server.core.BZEventType;
import bitzero.server.core.IBZEvent;
import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.data.DataCmd;

import cmd.CmdDefine;

import cmd.receive.map.RequestAddConstruction;
import cmd.receive.map.RequestCancleConstruction;
import cmd.receive.map.RequestFinishTimeConstruction;
import cmd.receive.map.RequestGetServerTime;
import cmd.receive.map.RequestMapInfo;
import cmd.receive.map.RequestMoveConstruction;
import cmd.receive.map.RequestQuickFinish;
import cmd.receive.map.RequestUpgradeConstruction;
import cmd.receive.troop.RequestResearchTroop;
import cmd.receive.troop.RequestTroopInfo;
import cmd.receive.user.RequestAddResource;
import cmd.receive.user.RequestUserInfo;

import cmd.send.demo.ResponseRequestAddResource;
import cmd.send.demo.ResponseRequestMapInfo;
import cmd.send.demo.ResponseRequestResearchTroop;
import cmd.send.demo.ResponseRequestTroopInfo;
import cmd.send.demo.ResponseRequestUserInfo;

import extension.FresherExtension;

import model.MapInfo;
import model.TroopInfo;
import model.ZPUserInfo;

import org.apache.commons.lang.exception.ExceptionUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.server.ServerConstant;

public class TroopHandle extends BaseClientRequestHandler {
    public static short TROOP_MULTI_IDS = 4000;
    private final Logger logger = LoggerFactory.getLogger("TroopHandle");
    public TroopHandle() {
        super();
    }
    public void init() {
        getParentExtension().addEventListener(BZEventType.PRIVATE_MESSAGE, this);        
    }
    @Override
    public void handleClientRequest(User user, DataCmd dataCmd) {
        try {
            switch (dataCmd.getId()) {
                case CmdDefine.GET_TROOP_INFO:
                    RequestTroopInfo packet1 = new RequestTroopInfo(dataCmd);
                    processTroopInfo(user, packet1);
                    break;
                case CmdDefine.RESEARCH_TROOP:
                    RequestResearchTroop packet2 = new RequestResearchTroop(dataCmd);
                    processResearchTroop(user, packet2);
                    break;
            }
        } catch (Exception e) {
            logger.warn("DEMO HANDLER EXCEPTION " + e.getMessage());
            logger.warn(ExceptionUtils.getStackTrace(e));
        }

    }
    
    public void handleServerEvent(IBZEvent ibzevent) {        
        if (ibzevent.getType() == BZEventType.PRIVATE_MESSAGE) {
            this.processEventPrivateMsg((User)ibzevent.getParameter(BZEventParam.USER));
        }
    }
    private void processEventPrivateMsg(User user){
        /**
         * process event
         */
        logger.info("processEventPrivateMsg, userId = " + user.getId());
    }

    private void processTroopInfo(User user, RequestTroopInfo troop) {
        try {
            System.out.println("get troop info, userId: " + user.getId());
            TroopInfo troopInfo = (TroopInfo) TroopInfo.getModel(user.getId(), TroopInfo.class);
            if (troopInfo == null) {
                System.out.println("==> troopInfo null");
                troopInfo = new TroopInfo();
                troopInfo.saveModel(user.getId());
            }
            send(new ResponseRequestTroopInfo(troopInfo), user);
        } catch (Exception e) {
            
        }
    }

    private void processResearchTroop(User user, RequestResearchTroop troop) {
        try {
            System.out.println("research troop: id " + user.getId() + " & type: " + troop.type);
            send(new ResponseRequestResearchTroop(ServerConstant.SUCCESS), user);
        } catch (Exception e) {
            send(new ResponseRequestResearchTroop(ServerConstant.ERROR), user);
        }
    }
}
