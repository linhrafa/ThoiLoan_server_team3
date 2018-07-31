package service;

import bitzero.server.core.BZEventParam;
import bitzero.server.core.BZEventType;
import bitzero.server.core.IBZEvent;
import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.data.DataCmd;

import cmd.CmdDefine;

import cmd.obj.map.MapArray;

import cmd.obj.map.Obs;

import cmd.receive.map.RequestUpgradeConstruction;
import cmd.receive.map.RequestAddConstruction;
import cmd.receive.map.RequestCancleConstruction;
import cmd.receive.map.RequestFinishTimeConstruction;
import cmd.receive.map.RequestGetServerTime;
import cmd.receive.map.RequestMapInfo;

//import cmd.send.demo.ResponseMove;

import cmd.receive.map.RequestMoveConstruction;

import cmd.receive.map.RequestQuickFinish;

import cmd.receive.map.RequestRemoveObs;

import cmd.send.demo.ResponseRequestAddConstruction;
import cmd.send.demo.ResponseRequestCancleConstruction;
import cmd.send.demo.ResponseRequestFinishTimeConstruction;
import cmd.send.demo.ResponseRequestMapInfo;
import cmd.send.demo.ResponseRequestMoveConstruction;
import cmd.send.demo.ResponseRequestQuickFinish;
import cmd.send.demo.ResponseRequestRemoveObs;
import cmd.send.demo.ResponseRequestServerTime;
import cmd.send.demo.ResponseRequestUpgradeConstruction;
import cmd.send.demo.ResponseRequestUserInfo;

import java.awt.Point;

import java.util.List;

import model.Building;
import model.MapInfo;
import model.ZPUserInfo;

import org.apache.commons.lang.exception.ExceptionUtils;

import org.json.JSONException;

import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.server.ServerConstant;

public class 
MapInfoHandler extends BaseClientRequestHandler {
    
    public static short MAPINFO_MULTI_IDS = 2000;
    public static short add_house_id = 1;
    public static short upgrade_house_id = 2;
    private final Logger logger = LoggerFactory.getLogger("MapInfoHandler");
    private final Logger logger_move = LoggerFactory.getLogger("Move_construction");
    
    public MapInfoHandler() {
        super();
    }
    
    public void init() {
        getParentExtension().addEventListener(BZEventType.PRIVATE_MESSAGE, this);        
    }

    @Override
    public void handleClientRequest(User user, DataCmd dataCmd) {
        try {
            switch (dataCmd.getId()) {
                case CmdDefine.GET_MAP_INFO:                
                    RequestMapInfo map = new RequestMapInfo(dataCmd);
                    processMapInfo(user);
                    break;
                case CmdDefine.MOVE_CONSTRUCTION:
                    System.out.println("Receive MOVE REQUEST");
                    RequestMoveConstruction move_construction = new RequestMoveConstruction(dataCmd);
                    processMoveConstruction(user,move_construction);
                    break;
                case CmdDefine.ADD_CONSTRUCTION:
                    System.out.println("Receive MAP REQUEST");
                    RequestAddConstruction add_construction = new RequestAddConstruction(dataCmd);
                    processAddConstruction(user,add_construction);
                    break;
                case CmdDefine.UPGRADE_CONSTRUCTION:
                    System.out.println("Receive MAP UPGRADE");
                    RequestUpgradeConstruction upgrade_construction = new RequestUpgradeConstruction(dataCmd);
                    processUpgradeConstruction(user,upgrade_construction);
                    break;
                case CmdDefine.FINISH_TIME_CONSTRUCTION:
                    System.out.println("FINISH_TIME_CONSTRUCTION");
                    RequestFinishTimeConstruction finish_time = new RequestFinishTimeConstruction(dataCmd);
                    processFinishTimeConstruction(user,finish_time);
                    break;
                case CmdDefine.CANCLE_CONSTRUCTION:
                    logger.info("CANCLE_CONSTRUCTION");
                    RequestCancleConstruction cancle_construction = new RequestCancleConstruction(dataCmd);
                    processRequestCancleConstruction(user,cancle_construction);
                    break;
//                case CmdDefine.REMOVE_OBS:
//                    logger.info("REMOVE_OBS");
//                    RequestRemoveObs remove_obs = new RequestRemoveObs(dataCmd);
//                    processRequestRemoveObs(user,remove_obs);
//                    break;
                
                case CmdDefine.QUICK_FINISH:
                    logger.info("QUICK_FINISH ");
                    RequestQuickFinish quick_finish = new RequestQuickFinish(dataCmd);
                    processQuickFinish(user,quick_finish);
                    break;
                case CmdDefine.GET_SERVER_TIME:
                    //System.out.println("GET_SERVER_TIME");
                    RequestGetServerTime server_time = new RequestGetServerTime(dataCmd);
                    processGetServerTime(user,server_time);
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
    
    private void processMapInfo(User user){
        try {
            System.out.println("getID map:" + user.getId() );
            MapInfo mapInfo = (MapInfo) MapInfo.getModel(user.getId(), MapInfo.class);
                if (mapInfo == null) {
                    System.out.println("mapInfo_null @#%&*^$&*&$@^$#$&^@#$&@$%@#%^@#^");
                    mapInfo = new MapInfo();
                    
                    
                    
                    mapInfo.saveModel(user.getId());
                }
//            System.out.println(">>>>>MAP ARRAY:");
            logger.info("in ra TRUOC KHI CHECK MAP map");
            mapInfo.print();
            mapInfo.checkStatus();
            logger.info("in ra SAU KHI CHECK MAP");
            mapInfo.print();
            mapInfo.saveModel(user.getId());
            send(new ResponseRequestMapInfo(mapInfo), user);
            
        } catch (Exception e) {
        }
    }
    
    private void processEventPrivateMsg(User user){
        /**
         * process event
         */
        logger.info("processEventPrivateMsg, userId = " + user.getId());
    }


    private void processMoveConstruction(User user, RequestMoveConstruction move_construction) {
        
        try {
            System.out.println("processMoveConstruction" + user.getId() );
            MapInfo mapInfo = (MapInfo) MapInfo.getModel(user.getId(), MapInfo.class);
            if (mapInfo == null) {
                //send response error
            }
            logger_move.debug("Map Info truoc khi move");
            mapInfo.print();
            MapArray mapArray = new MapArray();
            mapArray = mapInfo.getMapArray();
            //System.out.println("VI TRI CU="+mapInfo.listBuilding.get(move_construction.id).posX+" "+mapInfo.listBuilding.get(move_construction.id).posY);
            boolean check = mapArray.moveBuilding(mapInfo, move_construction.id, move_construction.posX,move_construction.posY);
            //mapInfo.saveModel(user.getId());
            if (check){
                //System.out.println("new positionnnnn = "+ mapInfo.listBuilding.toString() );
                System.out.println("VI TRI MOI="+mapInfo.listBuilding.get(move_construction.id).posX+" "+mapInfo.listBuilding.get(move_construction.id).posY);
                mapInfo.saveModel(user.getId());
                send(new ResponseRequestMoveConstruction(ServerConstant.SUCCESS), user);
            }
            else{
                System.out.println("new positionnnnn = FALSE"  );
                //mapInfo.saveModel(user.getId());
                send(new ResponseRequestMoveConstruction(ServerConstant.ERROR), user);
            }
                
                   
               } catch (Exception e) {
            }
    }

    private void processAddConstruction(User user, RequestAddConstruction add_construction) {
        try {           
            
            System.out.println("processAddConstruction" + user.getId() );
            ZPUserInfo userInfo = (ZPUserInfo) ZPUserInfo.getModel(user.getId(), ZPUserInfo.class);
           if (userInfo == null) {
               ////send response error
               send(new ResponseRequestAddConstruction(ServerConstant.ERROR), user);
            }
            MapInfo mapInfo = (MapInfo) MapInfo.getModel(user.getId(), MapInfo.class);
           if (mapInfo == null) {
               
               //send response error
               send(new ResponseRequestAddConstruction(ServerConstant.ERROR), user);
            }
            
            
            
            MapArray mapArray = new MapArray();
            mapArray = mapInfo.getMapArray();
            
            boolean checkPosition = mapArray.check_addBuilding(mapInfo, add_construction.type, add_construction.posX,add_construction.posY);
            System.out.println("checkPosition = " + checkPosition );
            
            //CHECK_RESOURCE *********************************************
            int level =1;
            if (add_construction.type.equals("BDH_1")){
                       level=userInfo.builderNumber+1;
                   }
            //System.out.println("new level = " + level );
            int check_resource = 0;
            check_resource = checkResource(userInfo,(add_construction.type),level);
               
            
            //System.out.println("check_resource coin bu vao tai nguyen khac to add building= " + check_resource );
            
            int coin = getCoin(add_construction.type,level); //coin de thuc hien thao tac voi nha
            
            System.out.println("check_resource+coin= " + (check_resource+coin) );
            System.out.println("userInfo.coin= " + userInfo.coin );
            
            if (checkPosition && (check_resource+coin<userInfo.coin)){ 
                //add building to pending
                int gold = getGold(add_construction.type,level);
                int elixir = getElixir(add_construction.type,level);
                int darkElixir = getDarkElixir(add_construction.type,level);
                
                
                
                //Xet truong hop dac biet
                if (add_construction.type.equals("BDH_1")){
                    level = userInfo.builderNumber+1;
                    if (level>5){
                        send(new ResponseRequestAddConstruction(ServerConstant.ERROR), user);
                        return;
                    }
                    mapInfo.addBuilding(add_construction.type, add_construction.posX, add_construction.posY,level, "complete");
                    userInfo.reduceUserResources(gold,elixir,darkElixir,check_resource+coin, add_construction.type, true);
                    userInfo.saveModel(user.getId());
                    mapInfo.saveModel(user.getId());
                    
                    send(new ResponseRequestAddConstruction(ServerConstant.SUCCESS), user);
                    return;
                }
                else { //neu khong phai nha BuildingHut
                
                
                
                System.out.println("so tho xay hien tai la: "+ userInfo.builderNumber);
                
                // kiem tra tho xay
                //                if (mapInfo.getBuilderNotFree()>=userInfo.builderNumber){ //neu khong co tho xay
                if (mapInfo.getBuilderNotFree()>=userInfo.builderNumber){ //neu khong co tho xay
                    
                    System.out.println("CAN GIAI PHONG THO XAY");
                    
                    //get resource cua nha
                    
                    int g_release = mapInfo.getGToReleaseBuilder();
                    System.out.println("So G de giai phong la "+ g_release);
//                    check_resource = check_resource +g;
                    if (userInfo.coin < coin+check_resource+g_release ){ //neu khong du tien mua tho xay
                        //linhrafa --Neu false
                        //tra ve false
                        send(new ResponseRequestAddConstruction(ServerConstant.ERROR), user);
                    }
                    else {
                        //giai phong 1 ngoi nha pending
                        
                        mapInfo.releaseBuilding(); 
                        
                        mapInfo.addBuilding(add_construction.type, add_construction.posX, add_construction.posY,level, "pending");
                        
                        mapInfo.print();
                        
                        mapArray = mapInfo.getMapArray();
                        
                        userInfo.reduceUserResources(gold,elixir,darkElixir,check_resource+coin+g_release, add_construction.type, true);
                        userInfo.saveModel(user.getId());
                        mapInfo.saveModel(user.getId());
//                        logger.info("in ra khi add construction");
//                        mapInfo.print();
                        send(new ResponseRequestAddConstruction(ServerConstant.SUCCESS), user);
                    }
                } 
                else { //neu da du tho xay
                    userInfo.reduceUserResources(gold,elixir,darkElixir,check_resource+coin, add_construction.type, true);
                    mapInfo.addBuilding(add_construction.type, add_construction.posX, add_construction.posY,level, "pending");
                    userInfo.saveModel(user.getId());
                    mapInfo.saveModel(user.getId());
//                    logger.info("in ra khi add construction");
//                    mapInfo.print();
                    send(new ResponseRequestAddConstruction(ServerConstant.SUCCESS), user);
                }
            }
                
                
            }
            else {
                //linhrafa --Neu false
                //tra ve false
                send(new ResponseRequestAddConstruction(ServerConstant.ERROR), user);
            }      
               } catch (Exception e) {
            }
    }
    
    private void processUpgradeConstruction(User user, RequestUpgradeConstruction upgrade_construction) {
        MapInfo mapInfo;
        try {
            ZPUserInfo userInfo = (ZPUserInfo) ZPUserInfo.getModel(user.getId(), ZPUserInfo.class);
            if (userInfo == null) {
               ////send response error
               send(new ResponseRequestUpgradeConstruction(ServerConstant.ERROR), user);
               return;
            }
            //*------------------------------------------------
            mapInfo = (MapInfo) MapInfo.getModel(user.getId(), MapInfo.class);
            if (mapInfo == null) {               
               //send response error
               send(new ResponseRequestUpgradeConstruction(ServerConstant.ERROR), user);
               return;
            }
            Building building = mapInfo.listBuilding.get(upgrade_construction.id);
            if (building.type.equals("BDH_1") || building.status.equals(ServerConstant.destroy_status)){
                    send(new ResponseRequestUpgradeConstruction(ServerConstant.ERROR), user);
                    return;
                }
            //*------------------------------------------------
//            logger.info(">>>>>>>>>>>>>in ra truoc khi upgrade>>>>>>>");
//            mapInfo.print();
        int exchange_resource = 0;
        exchange_resource = checkResource(userInfo,(building.type),building.level+1);
            
        System.out.println("check_resource chuyen doi to upgrade building= " + exchange_resource );
        int coin = getCoin(building.type,building.level+1);

        if ((exchange_resource+coin<userInfo.coin)){ 
                //add building to pending
                int gold = getGold(building.type,building.level+1);
                int elixir = getElixir(building.type,building.level+1);
                int darkElixir = getDarkElixir(building.type,building.level+1);
                
                mapInfo.print();
                
                System.out.println("so tho xay hien tai la: "+ userInfo.builderNumber);
                
                // kiem tra tho xay
                //                if (mapInfo.getBuilderNotFree()>=userInfo.builderNumber){ //neu khong co tho xay
                if (mapInfo.getBuilderNotFree()>=userInfo.builderNumber){ //neu khong co tho xay
                    
                    System.out.println("CAN GIAI PHONG THO XAY");
                    
                    //get resource cua nha
                    
                    int g_release = mapInfo.getGToReleaseBuilder();
                    System.out.println("So G de giai phong la "+ g_release);
//                    check_resource = check_resource +g;
                    if (userInfo.coin < coin+exchange_resource+g_release ){ //neu khong du tien mua tho xay
                        //linhrafa --Neu false
                        //tra ve false
                        send(new ResponseRequestAddConstruction(ServerConstant.ERROR), user);
                    }
                    else {
                        //giai phong 1 ngoi nha pending
                        
                        mapInfo.releaseBuilding(); 
                        
                        mapInfo.print();
                        
                        userInfo.reduceUserResources(gold,elixir,darkElixir,exchange_resource+coin+g_release, building.type, false);
                        mapInfo.upgradeBuilding(upgrade_construction.id);
                        
                        userInfo.saveModel(user.getId());
                        mapInfo.saveModel(user.getId());
                        logger.info(">>>>>>>>>>>>>in ra sau khi upgrade>>>>>>>");
                        mapInfo.print()
                            ;
                        send(new ResponseRequestUpgradeConstruction(ServerConstant.SUCCESS), user);
                    }
                } 
                else { //neu da du tho xay
                    userInfo.reduceUserResources(gold,elixir,darkElixir,exchange_resource+coin, building.type, false);                    
                    mapInfo.upgradeBuilding(upgrade_construction.id);
                    
                    userInfo.saveModel(user.getId());
                    mapInfo.saveModel(user.getId());
                    logger.info(">>>>>>>>>>>>>in ra sau khi upgrade>>>>>>>");
                    mapInfo.print();
                    send(new ResponseRequestUpgradeConstruction(ServerConstant.SUCCESS), user);
                }
            
                
                
            }
        else {
            //linhrafa --Neu false
            //tra ve false
            send(new ResponseRequestUpgradeConstruction(ServerConstant.ERROR), user);
            return;
        }                
            
        } catch (Exception e) {
        }
        
        
    }
    private int checkResource(ZPUserInfo user,String type, int level) {
        //Kiem tra tai nguyen co du khong
        //Neu g = 0 la du tai nguyen
        //Neu g > 0 la so G con thieu so voi cost
        System.out.println("level ="+level);
        
        int g = 0;
        int gold_bd = getGold(type,level);
        System.out.println("check Resource, gold  = "+ gold_bd);
        if (user.gold < gold_bd){
            g+=goldToG(gold_bd-user.gold);                    
        };
        
        int elixir_bd = getElixir(type,level);
        if (user.elixir < elixir_bd){
            g+=elixirToG(elixir_bd-user.elixir);                    
        };
        
        int darkElixir_bd = getDarkElixir(type,level);
        if (user.darkElixir < darkElixir_bd){
            g+=darkElixirToG(darkElixir_bd-user.darkElixir);                    
        };
        
//        int coin_bd = getCoin(type,level);
//        System.out.println("check Resource, coin_bd  = "+ coin_bd);
//        System.out.println("check Resource, user.coin  = "+ user.coin);
//        
//        if (user.coin < coin_bd){
//            g+=coin_bd-user.coin; 
//        };
    System.out.println("so coin de bu vao cac tai nguyen khac la = "+g);
        return g;
    }

    public int getGold(String type, int level){
        int g = 0;
        try {
            
            JSONObject construction = ServerConstant.config.getJSONObject(type).getJSONObject(String.valueOf(level));
            //System.out.println(">>>>>>>>>>>> construction.hitpoints = "+ type+ ":"+construction.getInt("hitpoints"));
            //Object checkObj = construction.opt("gold");   
            //if (construction.getJSONObject("gold")!= null ){ //neu nha co ton vang
                
               
                //System.out.println("user.gold = " + user.gold);
                //System.out.println("gold = " + construction.getInt("gold"));
                g = construction.getInt("gold");
            //}
        } catch (JSONException e) {
            System.out.println("getGold khong ton' tai nguyen");
        }
        return g;
    }
    
    public int getDarkElixir(String type, int level){
        int g = 0;
        try {
            JSONObject construction = ServerConstant.config.getJSONObject(type).getJSONObject(String.valueOf(level));
            g = construction.getInt("darkElixir");
//            Object checkObj = construction.opt("darkElixir");   
//            if (checkObj instanceof JSONObject){ //neu nha co ton vang
//                JSONObject Obj = (JSONObject) checkObj;
//                //System.out.println("user.gold = " + user.gold);
//                System.out.println("Obj.getInt(\"darkElixir\") = " + Obj.getInt("darkElixir"));
//                
//            }
        } catch (JSONException e) {            
            System.out.println("get darkElixir khong ton tai nguyen");
        }
        return g;
    }
    public int getElixir(String type, int level){
        int g = 0;
        try {
            JSONObject construction = ServerConstant.config.getJSONObject(type).getJSONObject(String.valueOf(level));
            g = construction.getInt("elixir");
//            Object checkObj = construction.opt("elixir");   
//            if (checkObj instanceof JSONObject){ //neu nha co ton vang
//                JSONObject Obj = (JSONObject) checkObj;
//                //System.out.println("user.gold = " + user.gold);
//                System.out.println("Obj.getInt(\"elixir\") = " + Obj.getInt("elixir"));
//                g = Obj.getInt("elixir");
//            }
        } catch (JSONException e) {
            System.out.println("getElixir khong ton tai nguyen");
        }
        return g;
    }
    public int getCoin(String type, int level){
        System.out.println("level in getCoin: "+ level);
        int g = 0;
        try {
            JSONObject construction = ServerConstant.config.getJSONObject(type).getJSONObject(String.valueOf(level));
            System.out.println("coin>>>>>>> construction.hitpoints = "+ type+ ":"+construction.getInt("hitpoints"));
            g = construction.getInt("coin");
//            Object checkObj = construction.opt("coin");   
//            if (checkObj instanceof JSONObject){ //neu nha co ton vang
//                JSONObject Obj = (JSONObject) checkObj;
//                //System.out.println("user.gold = " + user.gold);
//                System.out.println("Obj.getInt(\"coin\") = " + Obj.getInt("coin"));
//                g = Obj.getInt("coin");
//            }
            System.out.println("getCoin  ton: "+g);
        } catch (JSONException e) {
            System.out.println("getCoin khong ton tai nguyen");
        }
        return g;
    }

    private int goldToG(int gold_bd) {
        return gold_bd;
    }

    private int elixirToG(int elixir_bd) {
        return elixir_bd;
    }

    private int darkElixirToG(int darkElixir_bd) {
        return darkElixir_bd;
    }


    private void processFinishTimeConstruction(User user, RequestFinishTimeConstruction finish_time) {
        System.out.println(">>>>>>processFinishTimeConstruction");
        MapInfo mapInfo;
        try {
            mapInfo = (MapInfo) MapInfo.getModel(user.getId(), MapInfo.class);
            if (mapInfo == null) {
                //send response error
            }  
            Building building = mapInfo.listBuilding.get(finish_time.id);
            if (building.status.equals(ServerConstant.destroy_status)){
                System.out.println("Nha nay da huy");
                return;
            }
            if (building.type.equals("BDH_1")){
                System.out.println("Nha nay la nha tho xay ma` ma'");
                return;
            }
            long time_cur = System.currentTimeMillis();
            long time_da_chay = time_cur-building.timeStart;
            long time_can_chay = building.getTimeBuild(building.status);
            if ((time_da_chay > time_can_chay) ){
                if (building.status.equals("upgrade")){
                    mapInfo.listBuilding.get(finish_time.id).level ++;
                }
                mapInfo.listBuilding.get(finish_time.id).setStatus("complete");
            }
            
            mapInfo.saveModel(user.getId());
            
            send(new ResponseRequestFinishTimeConstruction(ServerConstant.SUCCESS), user);
            
            
        } catch (Exception e) {
        }
        
        
        
    }


    private void processGetServerTime(User user, RequestGetServerTime finish_time) {
        try {
            System.out.println("getID:" + user.getId() );   
            long time_cur = System.currentTimeMillis();
            send(new ResponseRequestServerTime(time_cur), user);
        } catch (Exception e) {

        }

    }

    private void processQuickFinish(User user, RequestQuickFinish quick_finish) {
        logger.info("processQuickFinish");
        MapInfo mapInfo;
        try {
            ZPUserInfo userInfo = (ZPUserInfo) ZPUserInfo.getModel(user.getId(), ZPUserInfo.class);
            if (userInfo == null) {
               ////send response error
               send(new ResponseRequestQuickFinish(ServerConstant.ERROR), user);
               return;
            }
            //*------------------------------------------------
            mapInfo = (MapInfo) MapInfo.getModel(user.getId(), MapInfo.class);
            if (mapInfo == null) {               
               //send response error
               send(new ResponseRequestQuickFinish(ServerConstant.ERROR), user);
               return;
            }
            
            logger.info("quick_finish.id: "+ quick_finish.id);
            
            Building building = mapInfo.listBuilding.get(quick_finish.id);
            
            logger.info("quick_finish.type : "+ building.type);
            logger.info("quick_finish.id: "+ quick_finish.id);
            if (building.type.equals("BDH_1") || building.status.equals(ServerConstant.destroy_status)){
                    send(new ResponseRequestQuickFinish(ServerConstant.ERROR), user);
                    return;
                }
            //*------------------------------------------------
            logger.info(">>>>>>>>>>>>>in ra truoc khi quick finish>>>>>>>");
//            
            mapInfo.print();                
            int g_release = building.getGtoQuickFinish();
            System.out.println("So G de hoan thanh nhanh la "+ g_release);
            mapInfo.print();
            
            if (userInfo.coin < g_release ){
                send(new ResponseRequestQuickFinish(ServerConstant.ERROR), user);
                return;
            }
            else {
                System.out.println("Building = "+ building.type+ ", level: "+ building.level+" , status: "+building.status);
                userInfo.reduceUserResources(0,0,0,g_release, building.type, false);
                if (building.status.equals(ServerConstant.upgrade_status)){
                    mapInfo.listBuilding.get(quick_finish.id).level ++;
                }
                mapInfo.listBuilding.get(quick_finish.id).setStatus(ServerConstant.complete_status);
                mapInfo.print();
                
                mapInfo.saveModel(user.getId());
                userInfo.saveModel(user.getId());
                send(new ResponseRequestQuickFinish(ServerConstant.SUCCESS), user);
            }
        } catch (Exception e) {
        }
    }

    private void processRequestCancleConstruction(User user, RequestCancleConstruction cancle_construction) {
        logger.info("processRequestCancleConstruction");
        MapInfo mapInfo;
        try {
            ZPUserInfo userInfo = (ZPUserInfo) ZPUserInfo.getModel(user.getId(), ZPUserInfo.class);
            if (userInfo == null) {
               ////send response error
                logger.debug("khong ton tai user");
               send(new ResponseRequestCancleConstruction(ServerConstant.ERROR), user);
               return;
            }
            //*------------------------------------------------
            mapInfo = (MapInfo) MapInfo.getModel(user.getId(), MapInfo.class);
            if (mapInfo == null) {               
               //send response error
               logger.debug("khong ton tai map");
               send(new ResponseRequestCancleConstruction(ServerConstant.ERROR), user);
               return;
            }
            
            Building building = mapInfo.listBuilding.get(cancle_construction.id);
            logger.debug(building.type+"lalalaal" + building.status+ building.id);
            logger.debug("id duoc truyen len la: "+ cancle_construction.id);
            if (building.type.equals("BDH_1") || building.status.equals(ServerConstant.destroy_status)){
                    logger.debug("nha BDH hoac la nha da huy");
                    send(new ResponseRequestCancleConstruction(ServerConstant.ERROR), user);
                    return;
                }
            int gold = building.getGtoCancle(ServerConstant.gold_resource);
            int elixir = building.getGtoCancle(ServerConstant.elixir_resource);
            int darkElixir = building.getGtoCancle(ServerConstant.darkElixir_resource);
            int coin = building.getGtoCancle(ServerConstant.coin_resource);
            
            int gold_rq = mapInfo.getRequire(ServerConstant.gold_capacity);    
            int elx_rq = mapInfo.getRequire(ServerConstant.elixir_capacity);
            int dElx_rq = mapInfo.getRequire(ServerConstant.darkElixir_capacity);
            
            userInfo.addResource(gold,elixir,darkElixir,coin,gold_rq,elx_rq,dElx_rq);
            
            if (building.status.equals(ServerConstant.upgrade_status)){
                mapInfo.listBuilding.get(cancle_construction.id).setStatus(ServerConstant.complete_status);    
            }
            else if ((building.status.equals(ServerConstant.pending_status))){
                mapInfo.listBuilding.get(cancle_construction.id).setStatus(ServerConstant.destroy_status);    
            }
            
            
            mapInfo.saveModel(user.getId());
            userInfo.saveModel(user.getId());
            send(new ResponseRequestCancleConstruction(ServerConstant.SUCCESS), user);
            
        } catch (Exception e) {
            
        }
    }

    private void processRequestRemoveObs(User user, RequestRemoveObs remove_obs) {
        logger.info("processRequestRemoveObs");
        MapInfo mapInfo;
        try {
            ZPUserInfo userInfo = (ZPUserInfo) ZPUserInfo.getModel(user.getId(), ZPUserInfo.class);
            if (userInfo == null) {
               ////send response error
                logger.debug("khong ton tai user");
               send(new ResponseRequestRemoveObs(ServerConstant.ERROR), user);
               return;
            }
            //*------------------------------------------------
            mapInfo = (MapInfo) MapInfo.getModel(user.getId(), MapInfo.class);
            if (mapInfo == null) {               
               //send response error
               logger.debug("khong ton tai map");
               send(new ResponseRequestRemoveObs(ServerConstant.ERROR), user);
               return;
            }
            
            Obs obs = mapInfo.listObs.get(remove_obs.id);
            logger.debug(obs.type+" " + obs.status+ obs.id);
            logger.debug("id obs duoc truyen len la: "+ remove_obs.id);
            if (obs.status.equals(ServerConstant.destroy_status)){                    
                    send(new ResponseRequestRemoveObs(ServerConstant.ERROR), user);
                    return;
                }
            
            int exchange_resource = 0;
            exchange_resource = checkResource(userInfo,(obs.type),1);
            int coin = getCoin(obs.type,obs.level);
            if ((exchange_resource+coin>userInfo.coin)){ 
                send(new ResponseRequestRemoveObs(ServerConstant.ERROR), user);
                return;
            }
            
            
            int gold = obs.getGtoRemove(ServerConstant.gold_resource);
            int elixir = obs.getGtoRemove(ServerConstant.elixir_resource);
            int darkElixir = obs.getGtoRemove(ServerConstant.darkElixir_resource);
//            int coin = obs.getGtoRemove(ServerConstant.coin_resource);
            
            int gold_rq = mapInfo.getRequire(ServerConstant.gold_capacity);    
            int elx_rq = mapInfo.getRequire(ServerConstant.elixir_capacity);
            int dElx_rq = mapInfo.getRequire(ServerConstant.darkElixir_capacity);
            
            
            userInfo.addResource(gold,elixir,darkElixir,coin,gold_rq,elx_rq,dElx_rq);
                      
            
            mapInfo.saveModel(user.getId());
            userInfo.saveModel(user.getId());
            send(new ResponseRequestCancleConstruction(ServerConstant.SUCCESS), user);
            
        } catch (Exception e) {
            
        }
    }
}

