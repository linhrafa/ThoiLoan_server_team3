package cmd.obj.map;

import java.util.Arrays;

import model.MapInfo;

public class MapArray {    
    private int[][] arr = new int[50][50];

    public MapArray() {
        Arrays.fill(arr, 0);
    }
    public boolean addBuilding(int id, int x, int y){
        if (!checkId(id)){
            return false;            
        }
        if (!checkPos(x,y)){
            return false;
        }
        if (!checkRes(id)){
            return false;
        }
        int height = getHeight((MapInfo.listBuilding.get(id).type));
        int weight = getWeight((MapInfo.listBuilding.get(id).type));
        for(int i=x;i<x+weight;i++){
            for (int j = y;j<y+height;j++){
                
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
    private int getHeight()
}


