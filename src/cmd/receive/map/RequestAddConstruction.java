package cmd.receive.map;

import bitzero.server.entities.User;
import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;

import bitzero.util.common.business.CommonHandle;

import java.nio.ByteBuffer;




public class RequestAddConstruction extends BaseCmd{
    public String type;
    public int posX;
    public int posY;  
    
    public RequestAddConstruction(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }
    
    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        try {
            this.type = readString(bf);
            System.out.println("REQUEST FROM CLIENT: research type: " + this.type );         
        }catch (Exception e) {
            CommonHandle.writeErrLog(e);}
    }
}


