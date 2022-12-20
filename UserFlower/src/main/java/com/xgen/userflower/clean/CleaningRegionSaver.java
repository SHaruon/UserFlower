/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xgen.userflower.clean;

import com.xgen.userflower.UserFlower;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;




/**
 *
 * @author x19047xx
 */
public final class CleaningRegionSaver{
    
    private String file_name = "fadingRegions.yml";
    private File file;
    private FileConfiguration config ;
    private UserFlower plg;
   
    
    public CleaningRegionSaver(UserFlower plg_){
        plg = plg_;
        
        if(!plg.getDataFolder().exists())plg.getDataFolder().mkdir();
        file = new File(plg.getDataFolder(),file_name);
        
        if(!file.exists())try {
            file.createNewFile();
        } catch (IOException ex) {
            
        }
       
        config = YamlConfiguration.loadConfiguration(file);
    }
    
    
    
    public void add(CleaningRegion rg){
        
        //if !contains name
        Set<String> StringList = config.getKeys(false);
        if(!StringList.contains(rg.name)){
            
            config.set(rg.name+".minPoint.x",rg.minPoint.x);
            config.set(rg.name+".minPoint.y",rg.minPoint.y);
            config.set(rg.name+".minPoint.z",rg.minPoint.z);
            
            config.set(rg.name+".maxPoint.x",rg.maxPoint.x);
            config.set(rg.name+".maxPoint.y",rg.maxPoint.y);
            config.set(rg.name+".maxPoint.z",rg.maxPoint.z);
            
            config.set(rg.name+".scope_world",rg.scope_world);
            config.set(rg.name+".entryTime", rg.entryTime);
        }
        
    }
    
    public List<CleaningRegion> getCleaningRegions(){
        
        List<CleaningRegion> result = new ArrayList<>();
        
        Set<String> StringList = config.getKeys(false);
        
        
        for(String rgname : StringList){
            
            CleaningRegion rg = new CleaningRegion(config,rgname);
            
            result.add(rg);
            
        }
        
        
        return result;
    }
    
    public void remove(CleaningRegion rg){
        config.set(rg.name,null);
    }
    
    
    public void save(){
        try {
            config.save(file);
        } catch (IOException ex) {
           
        }
    }
    
    
}
