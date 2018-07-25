package cmd.send.demo;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import java.nio.ByteBuffer;



public class ResponseRequestAddResource extends BaseMsg {
    short validate ;
    public ResponseRequestAddResource(short s) {
        super(CmdDefine.ADD_RESOURCE);
        this.validate = s;
    }


    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();        
        return packBuffer(bf);
    }
}
