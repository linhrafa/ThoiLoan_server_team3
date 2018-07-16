package cmd.receive.user;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;

import bitzero.util.common.business.CommonHandle;

import java.nio.ByteBuffer;

//import service.DemoHandler.DemoDirection;

public class RequestUserInfo extends BaseCmd {
//    public String username;
//    public String password;
    public RequestUserInfo(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }
    public void unpackData(){
        ByteBuffer bf = makeBuffer();
        try {
//            username = readString(bf);
//            password = readString(bf);
        }catch (Exception e) {
            CommonHandle.writeErrLog(e);}
    }
}
