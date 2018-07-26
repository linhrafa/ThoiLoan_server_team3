package cmd.obj.map;

import util.server.ServerConstant;

public class Obs {
    public int id;
    public String type;
    public int posX;
    public int posY;
    public String status;
    public Obs() {
        super();
    }
    public Obs(int _id,String _type,int x, int y) {
        super();
        this.id = _id;
        this.type = _type;
        this.posX = x;
        this.posY = y;
        this.status = ServerConstant.complete_status;
    }
}
