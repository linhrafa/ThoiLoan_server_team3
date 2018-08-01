package cmd.send.demo;

import bitzero.server.extensions.data.BaseMsg;

import cmd.CmdDefine;


import java.awt.Point;

import java.nio.ByteBuffer;

import model.Building;
import model.MapInfo;
import cmd.obj.map.Obs;

import model.Troop;
import model.TroopInfo;

public class ResponseRequestTroopInfo extends BaseMsg {
    TroopInfo troopInfo;
    public ResponseRequestTroopInfo(TroopInfo troopInfo) {
        super(CmdDefine.GET_TROOP_INFO);
        this.troopInfo = troopInfo;
    }

    @Override
    public byte[] createData() {
        Troop troop;
        ByteBuffer bf = makeBuffer();
        int size = this.troopInfo.getSize();
        bf.putInt(size);
        for (String key : this.troopInfo.troopMap.keySet()) {
            troop = this.troopInfo.troopMap.get(key);
            putStr(bf, troop.type);
            bf.putShort(troop.isUnlock);
            bf.putShort(troop.level);
            bf.putShort(troop.population);
            putStr(bf, troop.status);
            bf.putLong(troop.timeStart);
            bf.putShort(troop.numberOnQueue);
        }
//        //Obs
//        size = mapInfo.listObs.size();
//        System.out.println("size" +size);
//        bf.putInt(size);
//        for (Obs obs : mapInfo.listObs) {
//            bf.putInt(obs.id+5000);
//            putStr(bf, obs.type);
//            bf.putInt(obs.posX);
//            bf.putInt(obs.posY);
            
//        }
        return packBuffer(bf);
    }
}
