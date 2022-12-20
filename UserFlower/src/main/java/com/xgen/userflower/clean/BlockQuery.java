/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xgen.userflower.clean;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.coreprotect.CoreProtectAPI.ParseResult;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 *
 * @author x19047xx
 */
public class BlockQuery {
    
    public Location location;
    public boolean hasRegion;
    public boolean isBlock = true;
    public long lastPlacedTime;
    private CoreProtectAccessor co = new CoreProtectAccessor();
    
    
    public BlockQuery(String world, Point loc){
        
        location = new Location(Bukkit.getServer().getWorld(world),loc.x,loc.y,loc.z);
        
        hasRegion = checkRegion();
        
        lastPlacedTime = getBlockPlacedTime();
   
    }
    
    public Block getBlock(){
        Block block = location.getBlock();
        return block;
    }
    
    private boolean checkRegion(){
        
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        com.sk89q.worldedit.util.Location point = new com.sk89q.worldedit.util.Location(BukkitAdapter.adapt(location.getWorld()),location.getX(),location.getY(),location.getZ());
        ApplicableRegionSet set = query.getApplicableRegions(point);

        return set.size() >= 1;
    }

    private long getBlockPlacedTime(){
        
        long time = 0;
        
        Block block = location.getBlock();
        
        List<Material> Exeption = new ArrayList<>(Arrays.asList(
                Material.AIR,
                Material.CAVE_AIR
        ));
        
        
        
        if(Exeption.contains(block.getType())){
            
            isBlock = false;
            
        }else{
                    
            List<String[]> lookup = co.api.blockLookup(block, 31536000);

            for(String[] value : lookup){
                ParseResult result = co.api.parseResult(value);

                String action = result.getActionString();

                //get when placed
                if("place".equals(action)){
                    time = result.getTimestamp();
                    break;
                }

            }
        }

        
        return time;
    }
    
}
