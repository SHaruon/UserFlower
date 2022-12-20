/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xgen.userflower;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author x19047xx
 */
public class AutoRunConfig{
    private String file_name = "autoRunConfig.yml";
    private File file;
    private FileConfiguration config ;
    private UserFlower plg;
    
    //default
    public boolean enable=false;
    public long border=7200000;//per 2h
    public int deleteAmount=0;
    public long interval=72000;//per 1h
   
    
    public AutoRunConfig(UserFlower plg_){
        plg = plg_;
        
        if(!plg.getDataFolder().exists())plg.getDataFolder().mkdir();
        file = new File(plg.getDataFolder(),file_name);
        
        if(!file.exists())try {
            file.createNewFile();
        } catch (IOException ex) {
            
        }
        
        config = YamlConfiguration.loadConfiguration(file);
        
        //load config
        if(config.get("enable") == null){
            config.set("enable",enable);
        }else{
            enable = config.getBoolean("enable");
        }
        if(config.get("border") == null){
            config.set("border",border);
        }else{
            border =(config.getLong("border"));
        }
        if(config.get("deleteAmount") == null){
            config.set("deleteAmount",deleteAmount);
        }else{
            deleteAmount = config.getInt("deleteAmount");
        }
        if(config.get("interval") == null){
            config.set("interval",interval);
        }else{
            interval = (config.getLong("interval"));
        }
        try{
            config.save(file);
        } catch (IOException ex) {
            
        }
        
    }
    
    public void set(boolean enable_ ,long border_, int deleteAmount_, long interval_){
        enable = enable_;
        border = border_;
        deleteAmount = deleteAmount_;
        interval = interval_;
        
        config.set("enable",enable_);
        config.set("border", border_);
        config.set("deleteAmount",deleteAmount_);
        config.set("interval",interval_);
        try {
            config.save(file);
        } catch (IOException ex) {
            
        }
    }
    
    
}
