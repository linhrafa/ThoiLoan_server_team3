package cmd.receive.map;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;

import bitzero.util.common.business.CommonHandle;

import java.nio.ByteBuffer;




public class RequestCancleConstruction extends BaseCmd{
    public int id;    
    
    public RequestCancleConstruction(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }
    
    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        try {
            id = readInt(bf);
           
        }catch (Exception e) {
            CommonHandle.writeErrLog(e);}
    }
}

