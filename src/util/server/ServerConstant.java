package util.server;

import bitzero.server.config.ConfigHandle;

import bitzero.util.common.business.CommonHandle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.json.JSONException;
import org.json.JSONObject;

public class ServerConstant {
    public static final int HEIGHT_MAP = 40;
    public static final int WEIGHT_MAP = 40;
    
    public static JSONObject configInitGame ;
    public static JSONObject configArmyCamp ;
    public static JSONObject configBarrack ;
    public static JSONObject configBuilderHut ;
    public static JSONObject configLaboratory ;
    public static JSONObject configResource ;
    public static JSONObject configStorage ;
    public static JSONObject configTownHall;
    public static JSONObject configTroop;
    public static JSONObject configTroopBase;
    public static JSONObject config;
    
    public static final short SUCCESS = 1;
    public static final short ERROR = 0;
    
    public static final String PLAYER_INFO = "player_info";
    public static final String PLAYER_TRANSIENT = "player_transient";
    public static final String MACHINE_TRANSIENT = "machine_transient";

    public static final boolean IS_CHEAT = (ConfigHandle.instance().getLong("isCheat") == 1);
    //public static final boolean IS_USE_SECOND_DATACONTROLLER = (ConfigHandle.instance().getLong("useSecondDataController") == 1);
    public static final boolean IS_PURCHASE = (ConfigHandle.instance().getLong("isPurchase") == 1);
    public static final boolean IS_METRICLOG = (ConfigHandle.instance().getLong("isMetriclog") == 1);
    public static final boolean ZME_ENABLE = (ConfigHandle.instance().getLong("zme_enable") == 1);

    public static final String GAME_DATA_KEY_PREFIX = ConfigHandle.instance().get("game_data_key_prefix").trim();
    public static final String USER_INFO_KEY = ConfigHandle.instance().get("user_info_key").trim();
    public static final int FARM_ID_COUNT_FROM = Integer.valueOf(ConfigHandle.instance().get("farm_id_count_from"));
    public static final String FARM_ID_KEY = ConfigHandle.instance().get("farm_id_key").trim();
    public static final String LAST_SNAPSHOT_KEY = ConfigHandle.instance().get("last_snapshot_key").trim();
    public static final String SEPERATOR = ConfigHandle.instance().get("key_name_seperator").trim();
    public static final String SERVER_ID = ConfigHandle.instance().get("serverId").trim();
    public static final boolean ENABLE_PAYMENT = (ConfigHandle.instance().getLong("enable_payment") == 1);
    public static final boolean ENABLE_ADMIN_PROMO = (ConfigHandle.instance().getLong("enable_admin_promo") == 1);
    public static final String CLIENT_VER = ConfigHandle.instance().get("clientVer").trim();
    public static final boolean DEV_ENVIRONMENT = (ConfigHandle.instance().getLong("devEnvironment") == 1);
    public static final String GG_STORE = ConfigHandle.instance().get("gg_store_url").trim();
    public static final String SS_STORE = ConfigHandle.instance().get("ss_store_url").trim();
    
    public static final int CUSTOM_LOGIN = ConfigHandle.instance().getLong("custom_login").intValue();
    
    public static void readConfigArmyCamp(){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>aaaaaaaaaaaaaaaaaaaaaaa");
        String path = System.getProperty("user.dir")+"/conf/Config_json/";
        StringBuffer contents = new StringBuffer();
        
        try {
            File file = new File(path+"ArmyCamp.json");
            Reader r = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader reader = new BufferedReader(r);
            String text = null;
            
            while ((text = reader.readLine()) != null){
                contents.append(text).append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            CommonHandle.writeErrLog(e);
        }
        
        try {
            ServerConstant.configArmyCamp = new JSONObject(contents.toString());
            
            Writer w = new OutputStreamWriter(new FileOutputStream(path+"configArmyCamp.txt"),"UTF-8");
            BufferedWriter fout = new BufferedWriter(w);
            fout.write(ServerConstant.configArmyCamp.toString());
            fout.close();
            
        } catch (Exception e){
            CommonHandle.writeErrLog(e);
        }
    }
    
    public static void readConfigBarrack(){
        String path = System.getProperty("user.dir")+"/conf/Config_json/";
        StringBuffer contents = new StringBuffer();
        
        try {
            File file = new File(path+"Barrack.json");
            Reader r = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader reader = new BufferedReader(r);
            String text = null;
            
            while ((text = reader.readLine()) != null){
                contents.append(text).append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            CommonHandle.writeErrLog(e);
        }
        
        try {
            ServerConstant.configBarrack = new JSONObject(contents.toString());
            
            Writer w = new OutputStreamWriter(new FileOutputStream(path+"configBarrack.txt"),"UTF-8");
            BufferedWriter fout = new BufferedWriter(w);
            fout.write(ServerConstant.configBarrack.toString());
            fout.close();
            
        } catch (Exception e){
            CommonHandle.writeErrLog(e);
        }
    }
    
    public static void readConfigBuilder(){
        String path = System.getProperty("user.dir")+"/conf/Config_json/";
        StringBuffer contents = new StringBuffer();
        
        try {
            File file = new File(path+"BuilderHut.json");
            Reader r = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader reader = new BufferedReader(r);
            String text = null;
            
            while ((text = reader.readLine()) != null){
                contents.append(text).append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            CommonHandle.writeErrLog(e);
        }
        
        try {
            ServerConstant.configBuilderHut = new JSONObject(contents.toString());
            
            Writer w = new OutputStreamWriter(new FileOutputStream(path+"configBuilderHut.txt"),"UTF-8");
            BufferedWriter fout = new BufferedWriter(w);
            fout.write(ServerConstant.configBuilderHut.toString());
            fout.close();
            
        } catch (Exception e){
            CommonHandle.writeErrLog(e);
        }
    }
    
    public static void readConfigLaboratory(){
        String path = System.getProperty("user.dir")+"/conf/Config_json/";
        StringBuffer contents = new StringBuffer();
        
        try {
            File file = new File(path+"Laboratory.json");
            Reader r = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader reader = new BufferedReader(r);
            String text = null;
            
            while ((text = reader.readLine()) != null){
                contents.append(text).append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            CommonHandle.writeErrLog(e);
        }
        
        try {
            ServerConstant.configLaboratory = new JSONObject(contents.toString());
            
            Writer w = new OutputStreamWriter(new FileOutputStream(path+"configLaboratory.txt"),"UTF-8");
            BufferedWriter fout = new BufferedWriter(w);
            fout.write(ServerConstant.configLaboratory.toString());
            fout.close();
            
        } catch (Exception e){
            CommonHandle.writeErrLog(e);
        }
    }
    
    public static void readConfigResource(){
        String path = System.getProperty("user.dir")+"/conf/Config_json/";
        StringBuffer contents = new StringBuffer();
        
        try {
            File file = new File(path+"Resource.json");
            Reader r = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader reader = new BufferedReader(r);
            String text = null;
            
            while ((text = reader.readLine()) != null){
                contents.append(text).append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            CommonHandle.writeErrLog(e);
        }
        
        try {
            ServerConstant.configResource = new JSONObject(contents.toString());
            
            Writer w = new OutputStreamWriter(new FileOutputStream(path+"configResource.txt"),"UTF-8");
            BufferedWriter fout = new BufferedWriter(w);
            fout.write(ServerConstant.configResource.toString());
            fout.close();
            
        } catch (Exception e){
            CommonHandle.writeErrLog(e);
        }
    }
    
    public static void readConfigStorage(){
        String path = System.getProperty("user.dir")+"/conf/Config_json/";
        StringBuffer contents = new StringBuffer();
        
        try {
            File file = new File(path+"Storage.json");
            Reader r = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader reader = new BufferedReader(r);
            String text = null;
            
            while ((text = reader.readLine()) != null){
                contents.append(text).append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            CommonHandle.writeErrLog(e);
        }
        
        try {
            ServerConstant.configStorage = new JSONObject(contents.toString());
            
            Writer w = new OutputStreamWriter(new FileOutputStream(path+"configStorage.txt"),"UTF-8");
            BufferedWriter fout = new BufferedWriter(w);
            fout.write(ServerConstant.configStorage.toString());
            fout.close();
            
        } catch (Exception e){
            CommonHandle.writeErrLog(e);
        }
    }
    public static void readConfigTownHall(){
        String path = System.getProperty("user.dir")+"/conf/Config_json/";
        StringBuffer contents = new StringBuffer();
        
        try {
            File file = new File(path+"TownHall.json");
            Reader r = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader reader = new BufferedReader(r);
            String text = null;
            
            while ((text = reader.readLine()) != null){
                contents.append(text).append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            CommonHandle.writeErrLog(e);
        }
        
        try {
            ServerConstant.configTownHall = new JSONObject(contents.toString());
            
            Writer w = new OutputStreamWriter(new FileOutputStream(path+"configTownHall.txt"),"UTF-8");
            BufferedWriter fout = new BufferedWriter(w);
            fout.write(ServerConstant.configTownHall.toString());
            fout.close();
            
        } catch (Exception e){
            CommonHandle.writeErrLog(e);
        }
    }
    
    public static void readConfigTroop(){
        String path = System.getProperty("user.dir")+"/conf/Config_json/";
        StringBuffer contents = new StringBuffer();
        
        try {
            File file = new File(path+"TownHall.json");
            Reader r = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader reader = new BufferedReader(r);
            String text = null;
            
            while ((text = reader.readLine()) != null){
                contents.append(text).append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            CommonHandle.writeErrLog(e);
        }
        
        try {
            ServerConstant.configTroop = new JSONObject(contents.toString());
            
            Writer w = new OutputStreamWriter(new FileOutputStream(path+"configTroop.txt"),"UTF-8");
            BufferedWriter fout = new BufferedWriter(w);
            fout.write(ServerConstant.configTroop.toString());
            fout.close();
            
        } catch (Exception e){
            CommonHandle.writeErrLog(e);
        }
    }
    
    public static void readConfigTroopBase(){
        String path = System.getProperty("user.dir")+"/conf/Config_json/";
        StringBuffer contents = new StringBuffer();
        
        try {
            File file = new File(path+"TroopBase.json");
            Reader r = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader reader = new BufferedReader(r);
            String text = null;
            
            while ((text = reader.readLine()) != null){
                contents.append(text).append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            CommonHandle.writeErrLog(e);
        }
        
        try {
            ServerConstant.configTroopBase = new JSONObject(contents.toString());
            
            Writer w = new OutputStreamWriter(new FileOutputStream(path+"configTroopBase.txt"),"UTF-8");
            BufferedWriter fout = new BufferedWriter(w);
            fout.write(ServerConstant.configTroopBase.toString());
            fout.close();
            
        } catch (Exception e){
            CommonHandle.writeErrLog(e);
        }
    }
    public static void readConfig(){
        readConfigArmyCamp();
        readConfigBarrack();
        readConfigBuilder();
        readConfigLaboratory();
        readConfigResource();
        readConfigStorage();
        readConfigTownHall();
        readConfigTroopBase();
        
        
        String path = System.getProperty("user.dir")+"/conf/Config_json/";
        ServerConstant.config = new JSONObject();
        
        try {
            ServerConstant.config.put("AMC_1", configArmyCamp.getJSONObject("AMC_1"));
            ServerConstant.config.put("BAR_1", configBarrack.getJSONObject("BAR_1"));
            ServerConstant.config.put("BDH_1", configBuilderHut.getJSONObject("BDH_1"));
            ServerConstant.config.put("LAB_1", configLaboratory.getJSONObject("LAB_1"));
            ServerConstant.config.put("RES_1", configResource.getJSONObject("RES_1"));
            ServerConstant.config.put("STO_1", configStorage.getJSONObject("STO_1"));
            ServerConstant.config.put("TOW_1", configTownHall.getJSONObject("TOW_1"));
//            //ServerConstant.config.put("TOW_1", configArmyCamp.getJSONObject("TOW_1"));
            System.out.println(">>>>>>>>>b = " + config.toString());
        } catch (JSONException e) {
        }
        try {
            
            
            Writer w = new OutputStreamWriter(new FileOutputStream(path+"config.txt"),"UTF-8");
            BufferedWriter fout = new BufferedWriter(w);
            fout.write(ServerConstant.config.toString());
            fout.close();
            
        } catch (Exception e){
            CommonHandle.writeErrLog(e);
        }

    }
}
