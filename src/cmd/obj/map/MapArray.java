package cmd.obj.map;

import java.util.Arrays;

import java.util.List;

import model.MapInfo;

import org.json.JSONException;
import org.json.JSONObject;

import util.server.ServerConstant;

public class MapArray {    
    public int[][] arr = new int[50][50];

    public MapArray() {
        for (int[] row: arr){
            Arrays.fill(row, -1);
        }
            
        System.out.println("Chay ngay di, truoc khi, Moi dieu dan toi te hon");
    }
    public boolean addBuilding(MapInfo mapinfo, int id, int x, int y){
        //System.out.println(id+" "+mapinfo.listBuilding.get(id).type+" "+x + " "+y);
        //if (id ==0 ){return false;}
        System.out.println("id, x, y = "+mapinfo.listBuilding.get(id).type+ " "+mapinfo.listBuilding.get(id).id);
        if (!(hasId(id, mapinfo.listBuilding)) ) { //neu id chua ton tai
            return false;
        } else {
        }
        
        int height = getHeight((mapinfo.listBuilding.get(id)));
        int weight = getWeight((mapinfo.listBuilding.get(id)));
        
        if (!checkPos(x,y,height,weight)){
            return false;
        }
        
        
        System.out.println("*****addbuilding in map array**************************");
        
        
        for(int i=x;i<x+weight;i++){
            for (int j = y;j<y+height;j++){
                this.arr[i][j] = id;
            }
        }
        return true;
    }

   

    private boolean checkPos(int x, int y, int height, int weight) {
        if (x<0 || y<0 ) {return false;};
        if (x+weight >=ServerConstant.WEIGHT_MAP || y+height>=ServerConstant.HEIGHT_MAP){return false;}
        System.out.println("*****check new pos***** = "+ x +" +"+y+" "+height+" "+weight);
        for(int i=x;i<x+height;i++){
            for(int j=y;j<y+weight;j++){
                if (this.arr[i][j]!= -1 ){ //neu da co nha noi ay
                    System.out.println("*****SAI ROI");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkRes(int id) {
        return false;
    }
    private int getHeight(Building building){
        try {
            return ServerConstant.config.getJSONObject(building.type).getJSONObject(Integer.toString(building.level)).getInt("height");
        } catch (JSONException e) {
            return -1;
        }
        
    }
    private int getWeight(Building building){
        //System.out.println("type = " + building.type );
        
        JSONObject config = ServerConstant.config;

        try {
            return config.getJSONObject(building.type).getJSONObject(Integer.toString(building.level)).getInt("height");
        } catch (JSONException e) {
            return -1;
        }
        
    }

    public boolean moveBuilding(MapInfo mapinfo, int id, int x, int y) {
        System.out.println("move Building"+ id +" "+x+" "+y);
        if (!hasId(id,mapinfo.listBuilding)){ // neu id chua ton tai
            return false;            
        }
        int height = getHeight((mapinfo.listBuilding.get(id)));
        int weight = getWeight((mapinfo.listBuilding.get(id)));
        
        System.out.println("move Building2"+ id +" "+x+" "+y);
        if (!checkPos(x,y,height,weight)){ //
            System.out.println("SAI VI TRI DA CO NHA O");
            return false;
        }
        System.out.println("move Building3"+ id +" "+x+" "+y);
//        if (!checkRes(id)){
//            return false;
//        }
        System.out.println("move Building4"+ id +" "+x+" "+y);
        
        int x_cur = mapinfo.listBuilding.get(id).posX;
        int y_cur = mapinfo.listBuilding.get(id).posY;
        //thay doi vi tri tren MapInfo-listBuilding
        mapinfo.listBuilding.get(id).setPos(x,y);
        
        // xoa vi tri cu tren maparray
        for(int i=x_cur;i<x_cur+weight;i++){
            for (int j=y_cur;j<y_cur+height;j++){
                this.arr[i][j] = -1;
            }
        }
        // them vi tri moi tren maparray
        for(int i=x;i<x+weight;i++){
            for (int j = y;j<y+height;j++){
                this.arr[i][j] = id;
            }
        }
        return true;
    }

    private boolean hasId(int id, List<Building> listbuilding) {
        if (listbuilding.isEmpty()) return false;
        for(Building building:listbuilding){
            if (building.getId()==id) return true;
        }
        return false;
    }
}


