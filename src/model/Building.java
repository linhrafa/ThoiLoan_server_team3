package model;

import org.json.JSONException;

import org.json.JSONObject;

import util.database.DataHandler;

import util.database.DataModel;

import util.server.ServerConstant;
import util.server.ServerUtil;

public class Building{
    public int id;
    public String status = "complete"; //free, building, upgrade
    public int timebuild =0;
    public String type;
    public int level;
    public int posX;
    public int posY;
    
    public Building() {
        super();
    }
    public Building(int _id,String _type, int _level_building, int _posX, int _posY, String _status ) {
        super();
        this.id = _id;
        this.type = _type;
        this.level = _level_building;
        this.posX = _posX;
        this.posY = _posY;
        this.status = _status;
    }
    
    public int getId(){
        return this.id;
    }

    public void setPos(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    long getTimeConLai() {
        
        
        return 0L;
        
    }

    void setStatus(String string) {
        this.status = string;
    }
}

