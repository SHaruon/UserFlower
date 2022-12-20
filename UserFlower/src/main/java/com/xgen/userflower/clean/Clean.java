/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
    
    /*###########################################################
                    Thanks to edu.self.startux

     We customized LastLog plugin to access its private classes
    ############################################################*/
    
package com.xgen.userflower.clean;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.xgen.userflower.UserFlower;
import static org.bukkit.Bukkit.getPluginManager;
import edu.self.startux.lastLog.LastLogPlugin;
import edu.self.startux.lastLog.PlayerList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author x19047xx
 */
public class Clean extends BukkitRunnable{
    
    public long border_time = 0;
    public int deletenum = 10;
    private String scope_world;
    private World world;
    UserFlower plg;
    
    public Clean(UserFlower plg_,long border_, int deletenum_){
        
        border_time = border_;
        
        deletenum = deletenum_;
        
        scope_world = "world";
        world = Bukkit.getServer().getWorld(scope_world);
        
        plg = plg_;
        
    }
    
    @Override
    public void run(){
        main();
    }

    /*----------------------------------------------
       Main function of UserFlower.Clean  
    ----------------------------------------------*/
    public void main(){
               
        List<UUID> inactivePlayers = getInactivePlayers(border_time);
        
        List<ProtectedRegion> inactiveRegions = getInactiveRegions(inactivePlayers);
        
        // set cleaning area by region
        CleanManager cleaner = new CleanManager(deletenum,plg);
        cleaner.register(inactiveRegions, scope_world);
   
        // Delete inactive regions
        //!! Note that regionCleaner only cleans no region area. Delete inactive regions first.
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(world));
        for(ProtectedRegion target : inactiveRegions){
            regions.removeRegion(target.getId());
        }
        
        //perform Cleaning
        cleaner.execute();
    }
    
    
    
    private List<UUID> getInactivePlayers(long border_time_){
        
        
        List<UUID> InactivePlayers = new ArrayList<>();
        
        //Call Already-loaded LastLog plugin
        LastLogPlugin lastlog = (LastLogPlugin)(getPluginManager().getPlugin("LastLog"));
        if(lastlog == null) return InactivePlayers;
        
        
        //Get lastlogin time of all player in the server
        PlayerList all_player = lastlog.getPlayerList(true);
        for (int i=0 ; i < all_player.getLength() ; i++){
            
            //Get each Player's Name and LastLogTime
            UUID player_name = all_player.getEntry(i).uuid;
            long lastlog_time = all_player.getEntry(i).time;
            
           
            long now = (new Date()).getTime();
            
            //Judge if player is inactive, by its lastlogtime
            if(now - border_time_ > lastlog_time){
                InactivePlayers.add(player_name);
            } 
        }
        
 
        return InactivePlayers;
    }
    
    
    
    private List<ProtectedRegion> getInactiveRegions(List<UUID> inactive_players){
        
        List<ProtectedRegion> result = new ArrayList<>();
        
        // Get All Region in the world
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        Map<String, ProtectedRegion> all_region = container.get(BukkitAdapter.adapt(world)).getRegions();
        
        
        // Search Inactive Region and save dying area
        all_region.forEach((name,region) -> {
            
            if(name.equals("__global__"))return;
            
            Set<UUID> owners = region.getOwners().getUniqueIds();
            
            
            // if just One of owners inactive, their region will be treated as "Inactive Region"
            for (int p=0 ; p < inactive_players.size() ; p++ ){
                if(owners.contains(inactive_players.get(p))){
                    result.add(region);
                }
            }
           
        });
        
        return result;
    }
    
    
    

   
    
    
    
}
