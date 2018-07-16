package cmd.receive.map;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;

import bitzero.util.common.business.CommonHandle;

import java.nio.ByteBuffer;




public class RequestMapInfo extends BaseCmd{
    public short direction;
    public RequestMapInfo(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }
    
    @Override
    public void unpackData() {
        
    }
}
