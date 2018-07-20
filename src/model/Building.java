package model;

import java.sql.Time;

import org.json.JSONException;

import org.json.JSONObject;

import util.database.DataHandler;

import util.database.DataModel;

import util.server.ServerConstant;
import util.server.ServerUtil;

public class Building{
    public int id;
    public String status = "complete"; //complete, pending, upgrade
    public int timebuild =0;
    public long timeStart =0;
    public String type;
    public int level;
    public int posX;
    public int posY;
    
    public Building() {
        super();
        //this.timeStart = System.currentTimeMillis();
    }
    public Building(int _id,String _type, int _level_building, int _posX, int _posY, String _status ) {
        super();
        this.id = _id;
        this.type = _type;
        this.level = _level_building;
        this.posX = _posX;
        this.posY = _posY;
        this.timeStart = System.currentTimeMillis();        
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

    public void setStatus(String string) {
        this.status = string;
    }
    public long getTimeBuild() {
        try {
            JSONObject construction = ServerConstant.config.getJSONObject(this.type).getJSONObject(Integer.toString(this.level));
            return ( (long) construction.getInt("buildTime"));
        } catch (JSONException e){
            System.out.println("get buildTime error");
            return -1;
        }
    }

    public String getStatus() {
        return this.status;
    }
}

