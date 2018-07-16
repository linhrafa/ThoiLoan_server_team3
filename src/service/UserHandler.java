package service;

import bitzero.server.core.BZEventParam;
import bitzero.server.core.BZEventType;
import bitzero.server.core.IBZEvent;
import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.data.DataCmd;

import cmd.CmdDefine;

import cmd.receive.user.RequestUserInfo;

import cmd.send.demo.ResponseRequestUserInfo;

import extension.FresherExtension;

import model.ZPUserInfo;

import org.apache.commons.lang.exception.ExceptionUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserHandler extends BaseClientRequestHandler {
    public static short USER_MULTI_IDS = 1000;
    private final Logger logger = LoggerFactory.getLogger("UserHandler");
    
    public UserHandler() {
        super();
    }

    public void init() {
        getExtension().addEventListener(BZEventType.USER_DISCONNECT, this);
        getExtension().addEventListener(BZEventType.USER_RECONNECTION_SUCCESS, this);
    }

    private FresherExtension getExtension() {
        return (FresherExtension) getParentExtension();
    }

    public void handleServerEvent(IBZEvent ibzevent) {
        if (ibzevent.getType() == BZEventType.USER_DISCONNECT)
            this.userDisconnect((User) ibzevent.getParameter(BZEventParam.USER));
    }

    public void handleClientRequest(User user, DataCmd dataCmd) {
        try {
            switch (dataCmd.getId()) {
            case CmdDefine.GET_USER_INFO:
                RequestUserInfo reqInfo = new RequestUserInfo(dataCmd);            
//                System.out.println("username : "+reqInfo.username);
//                System.out.println("pass : "+reqInfo.password);
//                getUserInfo(user,reqInfo.username,reqInfo.password);
                getUserInfo(user);
                break;
            }
        } catch (Exception e) {
            logger.warn("USERHANDLER EXCEPTION " + e.getMessage());
            logger.warn(ExceptionUtils.getStackTrace(e));
        }

    }

    private void getUserInfo(User user) {
        try {
            System.out.println("getID:" + user.getId() );
            ZPUserInfo userInfo = (ZPUserInfo) ZPUserInfo.getModel(user.getId(), ZPUserInfo.class);
            if (userInfo == null) {
                System.out.println("userInfo_null @#%&*^$&*&$@^$#$&^@#$&@$%@#%^@#^");
                userInfo = new ZPUserInfo(user.getId(), user.getName());
                userInfo.saveModel(user.getId());
            }
            send(new ResponseRequestUserInfo(userInfo), user);
        } catch (Exception e) {

        }

    }

    private void userDisconnect(User user) {
        // log user disconnect
    }

}
