/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xgen.userflower.clean;


import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.xgen.userflower.UserFlower;
import java.util.Date;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;



/**
 *
 * @author x19047xx
 */
public class CleanManager {
    
    UserFlower plg;
    CleaningRegionSaver saver;
    int delAmount = 0;
    
    public CleanManager(int delAmount_ ,UserFlower plg_){
        
        plg = plg_;
        saver = new CleaningRegionSaver(plg);
        delAmount = delAmount_;
        
    }
    
    
    
    public void register(List<ProtectedRegion> WGRegions, String world){
        
        
        //get time now
        Date timenow = new Date();
        long entryTime = timenow.getTime();
        
        
        //add to saver
        for(ProtectedRegion region : WGRegions){
            
            CleaningRegion cleaningRegion = new CleaningRegion(region, world,entryTime);
            
            saver.add(cleaningRegion);
            
        }
        
        saver.save();
        
    }
    
    
    
    public void execute(){
        
        List<Block> blocksToDelete;
        
        // for FadingRegions
        List<CleaningRegion> areaList = saver.getCleaningRegions();
        for(CleaningRegion clean_area : areaList){
            
            blocksToDelete = clean_area.getBlocks();
            
            
            
            if(blocksToDelete.isEmpty()){
                //this clean_area is no longer needed
                saver.remove(clean_area);
                
            }else{
                // Perform Cleaning : delete some blocks in clean_area
                int delCount = 0;
                for(Block target : blocksToDelete){

                    // logs " UserFlower breaked this block "
                    CoreProtectAccessor co = new CoreProtectAccessor();
                    Material original = target.getType();
                    co.api.logRemoval("--UserFlower--", target.getLocation(), original,null);

                    //remove the block in the world
                    target.setType(Material.AIR);
                    
                    
                    delCount++;
                    if(delCount >= delAmount){
                        //Stop removing blocks
                        break;
                    }
                }
                
            } 
            
        }
        saver.save();
        
    }
    
    
}
