package cmd.obj.map;

import util.database.DataHandler;

import util.database.DataModel;

import util.server.ServerUtil;

public class Building {
    public int id;
    public String status = "free"; //free, building, upgrade
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

    void setPos(int x,int y) {
        this.posX = x;
        this.posY = y;
    }
}
