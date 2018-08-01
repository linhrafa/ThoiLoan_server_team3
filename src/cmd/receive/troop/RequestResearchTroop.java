package cmd.receive.troop;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;

import bitzero.util.common.business.CommonHandle;

import java.nio.ByteBuffer;


public class RequestResearchTroop extends BaseCmd {
    public String type;

    public RequestResearchTroop(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }

    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        try {
            type = readString(bf);
        } catch (Exception e) {
            CommonHandle.writeErrLog(e);
        }
    }
}
