package model;

import bitzero.util.common.business.CommonHandle;
import bitzero.util.socialcontroller.bean.UserInfo;

import cmd.obj.map.Army;
import cmd.obj.map.Building;
import cmd.obj.map.Obs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Random;

import org.json.JSONObject;

import util.database.DataModel;

import util.server.ServerConstant;

public class MapInfo extends DataModel{
    
    //public long id;
   
    public String lastUpdate;
    
    //public String apk = "";
    public int size_building = 0;
    public List<Building> listBuilding = new ArrayList<Building>();

    public int size_army = 0;
    public List<Army> listArmy = new ArrayList<Army>();
    
    public int size_obs = 0;
    public List<Obs> listObs = new ArrayList<Obs>();
    
    public MapInfo() {
            super();
            this.InitJsonData();
        }
    private void InitJsonData() {
        int number_obs =5;
        List<Integer> list_obs = new ArrayList<Integer>();
        String path = "E:/workspace/Fresher_demo/conf/";
        StringBuffer contents = new StringBuffer();
        
        try {
            File file = new File(path+"init.json");
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
            ServerConstant.configInitGame = new JSONObject(contents.toString());
            
            
            JSONObject map = ServerConstant.configInitGame.getJSONObject("map");
            JSONObject player = ServerConstant.configInitGame.getJSONObject("player");
            JSONObject obs = ServerConstant.configInitGame.getJSONObject("obs");
            //du lieu nha   
            Iterator<?> keys = map.keys();
            while (keys.hasNext()){
                String key = (String) keys.next();
                JSONObject house_type = (JSONObject) map.get(key);
//                
//                System.out.println("typehouse "+ key);    
//                System.out.println("posX "+ house_type.getInt("posX")); 
//                System.out.println("posy "+ house_type.getInt("posY")); 
                
                addBuilding(key,house_type.getInt("posX"), house_type.getInt("posY"));
            }
            
            System.out.println("listbuilding "+ this.listBuilding.size()+ " and size = "+this.size_building); 
            
            //du lieu co cay hoa la
                        
            for(int i=0;i<number_obs;i++){                
                while (true){
                    int id_obs = new Random().nextInt(57)+1; 
                    
                    if (check(id_obs,list_obs)){      
                        list_obs.add(id_obs);
                        
                        break;
                    }
                }
            }
            for(int i:list_obs){
                
                String num = Integer.toString(i);
                System.out.println("num =" +num);
                JSONObject obs_type = (JSONObject) obs.get(num);
                System.out.println("obs_type =" +obs_type);
                Obs _obs = new Obs(this.size_obs,obs_type.getString("type"),obs_type.getInt("posX"), obs_type.getInt("posY"));
                this.listObs.add(_obs);
                this.size_obs++;
                
            }
            
            
            
            Writer w = new OutputStreamWriter(new FileOutputStream(path+"configInitGame.txt"),"UTF-8");
            BufferedWriter fout = new BufferedWriter(w);
            fout.write(ServerConstant.configInitGame.toString());
            fout.close();
            
        } catch (Exception e){
            CommonHandle.writeErrLog(e);
        }
    }
    public void printValues() {
//        System.out.printf("id=%s|name=%s|ver=%s|ctnver=%s|lastUpdate=%s|pId=%s|des=%s|publisher=%s|type=%s|category=%s|icon_large=%s|icon_medium=%s|icon_small=%s|apk=%s|size=%s\n", new Object[] {
//                          id, name, ver, contentversion, lastUpdate, pId, des, publisher, type, category, icon_large,
//                          icon_medium, icon_small, apk, size
//        });
//        System.out.printf("lastUpdate=%s|size=%s\n|listBuilding=%", new Object[] {
//                          id, name, ver, contentversion, lastUpdate, pId, des, publisher, type, category, icon_large,
//                          icon_medium, icon_small, apk, size
// });
    }

    public String toString() {
//        return String.format("id=%s|name=%s|ver=%s|ctnver=%s|lastUpdate=%s|pId=%s|des=%s|publisher=%s|type=%s|category=%s|icon_large=%s|icon_medium=%s|icon_small=%s|apk=%s|size=%s\n", new Object[] {
//                             id, name, ver, contentversion, lastUpdate, pId, des, publisher, type, category, icon_large,
//                             icon_medium, icon_small, apk, size
//    });
//    }
           return "";                      
}
    public void addBuilding(String type, int posX, int posY) throws Exception {
        Building building = new Building(this.size_building,type,1,posX,posY,"free");                
        this.size_building++;
        this.listBuilding.add(building);        
    }

    private boolean check(int id,List<Integer> list){
        if (list.size()==0) return true;
        for(int i:list){
//            System.out.println("id="+id);
//            System.out.println("i="+i);
//            System.out.println("list.get(i)="+list.get(i));
            if (id==i){
                return false;
            }
        }
        return true;
    }

    
}
