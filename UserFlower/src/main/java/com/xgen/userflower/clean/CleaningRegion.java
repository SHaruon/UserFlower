/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xgen.userflower.clean;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author x19047xx
 */
public class CleaningRegion {
    
    public String name;
    
    public String scope_world;
    
    public Point minPoint;
    
    public Point maxPoint;
    
    public long entryTime;
    
    
    
    
    //Initialize CleaningRegion data by existing region
    public CleaningRegion(ProtectedRegion rg,String world, long time){
        
        name = rg.getId();
        
        minPoint = new Point(0,0,0);
        minPoint.x = rg.getMinimumPoint().getBlockX();
        minPoint.y = rg.getMinimumPoint().getBlockY();
        minPoint.z = rg.getMinimumPoint().getBlockZ();

        maxPoint = new Point(0,0,0);
        maxPoint.x = rg.getMaximumPoint().getBlockX();
        maxPoint.y = rg.getMaximumPoint().getBlockY();
        maxPoint.z = rg.getMaximumPoint().getBlockZ();

        scope_world = world;
        entryTime = time;
    }
    
    
    //Initialize CleaningRegion data by config file
    public CleaningRegion(FileConfiguration config,String rgname){
        
        name = rgname;
        
        minPoint = new Point(0,0,0);
        minPoint.x = config.getInt(rgname+".minPoint.x");
        minPoint.y = config.getInt(rgname+".minPoint.y");
        minPoint.z = config.getInt(rgname+".minPoint.z");

        maxPoint = new Point(0,0,0);
        maxPoint.x = config.getInt(rgname+".maxPoint.x");
        maxPoint.y = config.getInt(rgname+".maxPoint.y");
        maxPoint.z = config.getInt(rgname+".maxPoint.z");

        scope_world = config.getString(rgname+".scope_world");
        entryTime = config.getLong(rgname+".entryTime");
    }
    
    
    public List<Block> getBlocks(){
        
        List<Block> result = new ArrayList<>();
        
        for(Point point : minPoint.to(maxPoint)){
      
            BlockQuery pos = new BlockQuery(scope_world,point);
            
            if( !pos.hasRegion && pos.isBlock && pos.lastPlacedTime <= entryTime){
                // if block is placed before Region-Cleaning start
                
                result.add(pos.getBlock());
                
            }
        }
            
        return result;
    }
    
    
    
}
