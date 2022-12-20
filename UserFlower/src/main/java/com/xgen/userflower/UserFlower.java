/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.xgen.userflower;



import com.xgen.userflower.clean.Clean;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;



/**
 *
 * @author x19047xx
 */
public class UserFlower extends JavaPlugin implements Listener{
    
    BukkitTask autoRunner;
    
    @Override
    public void  onEnable(){
        getLogger().info("==================================");
        getLogger().info("    User Flower by Xgen_Old_Man   ");
        getLogger().info("                                  ");
        getLogger().info("==================================");
        
        AutoRunConfig autoRunConfig = new AutoRunConfig(this);
        if(autoRunConfig.enable == true){
            autoRunner = new Clean(this, autoRunConfig.border,autoRunConfig.deleteAmount).runTaskTimer(this, 0, autoRunConfig.interval);
        }
        
        getCommand("ufclean").setExecutor(new CommandExecute(this));
        getCommand("ufauto").setExecutor(new CommandExecute(this,autoRunConfig,autoRunner));
    }
    
    
}
