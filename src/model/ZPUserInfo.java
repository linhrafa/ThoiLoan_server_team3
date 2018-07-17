package model;

import bitzero.util.common.business.CommonHandle;




import java.awt.Point;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;

import java.io.UnsupportedEncodingException;

import java.io.Writer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import util.database.DataModel;

import util.server.ServerConstant;


public class ZPUserInfo extends DataModel {
    // Zing me
    public int id;
    public String name;
    public long exp = 0L;
    //public int level = 0;
    public int gold = 0;
    public int coin = 1000;
    public int elixir = 0;
    public int darkElixir = 0; 
    public int builderNumber ;


    
    public Point position;    
    

    public ZPUserInfo(int _id, String _name) {
        super();
        id = _id;
        name = _name;        
        InitJsonData();        
    }
    
    public String toString() {
        return String.format("%s|%s", new Object[] { id, name });
    }
    
    private void InitJsonData() {
        String path = System.getProperty("user.dir")+"/conf/";
        StringBuffer contents = new StringBuffer();
        
        try {
            File file = new File(path+"init.json");
            Reader r = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader reader = new BufferedReader(r);
            String text = null;
            
            while ((text = reader.readLine()) != null){
                contents.append(text).append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            CommonHandle.writeErrLog(e);
        }
        
        try {
            ServerConstant.configInitGame = new JSONObject(contents.toString());
                   
            JSONObject player = ServerConstant.configInitGame.getJSONObject("player");
            JSONObject obs = ServerConstant.configInitGame.getJSONObject("obs");
            //System.out.println(tow1.getInt("posX"));    
            this.gold = player.getInt("gold");
            System.out.println(this.gold); 
            this.coin = player.getInt("coin");
            System.out.println(this.coin); 
            this.elixir = player.getInt("elixir");
            System.out.println(this.elixir); 
            this.darkElixir = player.getInt("darkElixir");
            System.out.println(this.darkElixir); 
            this.builderNumber = player.getInt("builderNumber"); 
            System.out.println(this.builderNumber);             
            
            
            
            Writer w = new OutputStreamWriter(new FileOutputStream(path+"configplayerInitGame.txt"),"UTF-8");
            BufferedWriter fout = new BufferedWriter(w);
            fout.write(ServerConstant.configInitGame.toString());
            fout.close();
            
        } catch (Exception e){
            CommonHandle.writeErrLog(e);
        }
    }

    
    
}
