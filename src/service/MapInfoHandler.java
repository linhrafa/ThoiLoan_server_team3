package service;

import bitzero.server.core.BZEventParam;
import bitzero.server.core.BZEventType;
import bitzero.server.core.IBZEvent;
import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.data.DataCmd;

import cmd.CmdDefine;

import cmd.obj.map.MapArray;

import cmd.receive.map.RequestAddConstruction;
import cmd.receive.map.RequestMapInfo;

//import cmd.send.demo.ResponseMove;

import cmd.receive.map.RequestMoveConstruction;

import cmd.send.demo.ResponseRequestAddConstruction;
import cmd.send.demo.ResponseRequestMapInfo;
import cmd.send.demo.ResponseRequestMoveConstruction;
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
    private final Logger logger = LoggerFactory.getLogger("MapInfoHandler");
    
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
            MapArray mapArray = new MapArray();
            mapArray = mapInfo.getMapArray();
            
            System.out.println("LALALALALALALALALALA");
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
            System.out.println("LOG_ADDBUILDING: type+" + add_construction.type );
            boolean validate_add_construction = true;
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
            
            int check_resource = 0;
            check_resource = checkResource(userInfo,(add_construction.type));
            System.out.println("check_resource = " + check_resource );
            
            if (checkPosition && (check_resource==0)){
                //add building to pending
                mapInfo.addBuilding(add_construction.type, add_construction.posX, add_construction.posY);
                mapArray = mapInfo.getMapArray();
                //get resource cua nha
                int gold = getGold(add_construction.type);
                int elixir = getElixir(add_construction.type);
                int darkElixir = getDarkElixir(add_construction.type);
                int coin = getCoin(add_construction.type);
                
                
                // kiem tra tho xay
//                if (mapInfo.getBuilderNotFree()>=userInfo.builderNumber){ //neu khong co tho xay
                if (mapInfo.getBuilderNotFree()>=0){ //neu khong co tho xay

                    int g = mapInfo.getGToReleaseBuilder();
                    check_resource = check_resource +g;
                    if (userInfo.coin < check_resource ){ //neu khong du tien mua tho xay
                        //linhrafa --Neu false
                        //tra ve false
                        send(new ResponseRequestAddConstruction(ServerConstant.ERROR), user);
                    }
                    else {
                        //giai phong 1 ngoi nha pending
                        mapInfo.releaseBuilding(); 
                        userInfo.reduceUserResources(gold,elixir,darkElixir,check_resource, add_construction.type, true);
                        userInfo.saveModel(user.getId());
                        mapInfo.saveModel(user.getId());
                        
                        send(new ResponseRequestAddConstruction(ServerConstant.SUCCESS), user);
                    }
                } else { //neu da du tho xay
                    userInfo.reduceUserResources(gold,elixir,darkElixir,check_resource, add_construction.type, true);
                    
                    userInfo.saveModel(user.getId());
                    mapInfo.saveModel(user.getId());
                    
                    send(new ResponseRequestAddConstruction(ServerConstant.SUCCESS), user);
                }
                
            }
            else {
                //linhrafa --Neu false
                //tra ve false
                send(new ResponseRequestAddConstruction(ServerConstant.ERROR), user);
            }
            
//            if (checkPosition){     //check vi tri           
//                check_resource = checkResource(userInfo, mapInfo.listBuilding.get(add_construction.id));
//                if (check_resource>0){ //neu so G can bo sung lon hon 0
//                    if(add_construction.is_use_G_buy_resource){ //neu nguoi dung dong y tra G
////                        check_builder = checkBuilder(); //dem so tho xay dang ranh
////                        if (check_builder<=0){ //neu khong du tho xay
////                            if (add_construction.is_use_G_release_builder){ //dong y tra tien giai phong tho xay
////                                validate_add_construction = true;   
////                                int G_toReleaseBuilder = mapInfo.getGToReleaseBuilder(); //chu y' ko release building trong nay
////                                if (user.payResource(G_toReleaseBuilder)){ //neu du tien de tra  thi` tra tien va tra ve true
////                                    mapInfo.releaseBuilding();
////                                }
////                                else {
////                                    validate_add_construction = false;
////                                }
////                            }
////                            else {
////                                validate_add_construction = false;    
////                            }
////                            
////                        } else {
////                            
////                        }
//                        
//                        
//                    }
//                    else {
//                        validate_add_construction = false;
//                        
//                    }
//                }
//            }
//            else {
//                validate_add_construction = false;
//            }
            
            
                
                   
               } catch (Exception e) {
            }
    }
    
    private int checkResource(ZPUserInfo user,String type) {
        //Kiem tra tai nguyen co du khong
        //Neu g = 0 la du tai nguyen
        //Neu g > 0 la so G con thieu so voi cost
        int g = 0;
        int gold_bd = getGold(type);
        System.out.println("check Resource, gold  = "+ gold_bd);
        if (user.gold < gold_bd){
            g+=goldToG(gold_bd-user.gold);                    
        };
        
        int elixir_bd = getElixir(type);
        if (user.elixir < elixir_bd){
            g+=elixirToG(elixir_bd-user.elixir);                    
        };
        
        int darkElixir_bd = getDarkElixir(type);
        if (user.darkElixir < darkElixir_bd){
            g+=darkElixirToG(darkElixir_bd-user.darkElixir);                    
        };
        
        int coin_bd = getCoin(type);
        System.out.println("check Resource, coin_bd  = "+ coin_bd);
        System.out.println("check Resource, user.coin  = "+ user.coin);
        
        if (user.coin < coin_bd){
            g+=coin_bd-user.coin; 
        };
    System.out.println("so coin can them la = "+g);
        return g;
    }

    public int getGold(String type){
        int g = 0;
        try {
            
            JSONObject construction = ServerConstant.config.getJSONObject(type).getJSONObject("1");
            System.out.println(">>>>>>>>>>>> construction.hitpoints = "+ type+ ":"+construction.getInt("hitpoints"));
            //Object checkObj = construction.opt("gold");   
            //if (construction.getJSONObject("gold")!= null ){ //neu nha co ton vang
                
               
                //System.out.println("user.gold = " + user.gold);
                System.out.println("gold = " + construction.getInt("gold"));
                g = construction.getInt("gold");
            //}
        } catch (JSONException e) {
            System.out.println("getGold khong ton' tai nguyen");
        }
        return g;
    }
    
    public int getDarkElixir(String type){
        int g = 0;
        try {
            JSONObject construction = ServerConstant.config.getJSONObject(type).getJSONObject("1");
            g = construction.getInt("darkElixir");
//            Object checkObj = construction.opt("darkElixir");   
//            if (checkObj instanceof JSONObject){ //neu nha co ton vang
//                JSONObject Obj = (JSONObject) checkObj;
//                //System.out.println("user.gold = " + user.gold);
//                System.out.println("Obj.getInt(\"darkElixir\") = " + Obj.getInt("darkElixir"));
//                
//            }
        } catch (JSONException e) {            
            System.out.println("darkElixir khong ton tai nguyen");
        }
        return g;
    }
    public int getElixir(String type){
        int g = 0;
        try {
            JSONObject construction = ServerConstant.config.getJSONObject(type).getJSONObject("1");
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
    public int getCoin(String type){
        int g = 0;
        try {
            JSONObject construction = ServerConstant.config.getJSONObject(type).getJSONObject("1");
            //System.out.println("coin>>>>>>> construction.hitpoints = "+ type+ ":"+construction.getInt("hitpoints"));
            g = construction.getInt("coin");
//            Object checkObj = construction.opt("coin");   
//            if (checkObj instanceof JSONObject){ //neu nha co ton vang
//                JSONObject Obj = (JSONObject) checkObj;
//                //System.out.println("user.gold = " + user.gold);
//                System.out.println("Obj.getInt(\"coin\") = " + Obj.getInt("coin"));
//                g = Obj.getInt("coin");
//            }
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
}

