package cmd.obj.map;

import java.util.Arrays;

import model.MapInfo;

public class MapArray {    
    public int[][] arr = new int[50][50];

    public MapArray() {
        for (int[] row: arr){
            Arrays.fill(row, 0);
        }
            
        System.out.println("Chay ngay di, truoc khi,Moi dieu dan toi te hon");
    }
    public boolean addBuilding(MapInfo mapinfo, int id, int x, int y){
        
        System.out.println("id, x, y = "+mapinfo.listBuilding.get(id).type+ " "+mapinfo.listBuilding.get(id).id);
        if (!checkId(id)){
            return false;            
        }
        if (!checkPos(x,y)){
            return false;
        }
        if (!checkRes(id)){
            return false;
        }
        int height = getHeight((mapinfo.listBuilding.get(id).type));
        int weight = getWeight((mapinfo.listBuilding.get(id).type));
        for(int i=x;i<x+weight;i++){
            for (int j = y;j<y+height;j++){
                this.arr[i][j] = id;
            }
        }
        return true;
    }

    private boolean checkId(int id) {
        return false;
    }

    private boolean checkPos(int x, int y) {
        return false;
    }

    private boolean checkRes(int id) {
        return false;
    }
    private int getHeight(String type){
        return -1;
    }
    private int getWeight(String type){
        return -1;
    }
}


