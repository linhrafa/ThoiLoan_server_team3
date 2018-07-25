package cmd.receive.user;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;

import bitzero.util.common.business.CommonHandle;

import java.nio.ByteBuffer;

//import service.DemoHandler.DemoDirection;

public class RequestAddResource extends BaseCmd {
    public int gold, elixir, darkElixir,coin;
    public RequestAddResource(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }
    public void unpackData(){
        ByteBuffer bf = makeBuffer();
        try {
            gold = readInt(bf);
            elixir = readInt(bf);
            darkElixir = readInt(bf);
            coin = readInt(bf);
        }catch (Exception e) {
            CommonHandle.writeErrLog(e);
        }
    }
}
