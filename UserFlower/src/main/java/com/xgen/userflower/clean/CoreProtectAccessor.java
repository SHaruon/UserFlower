/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xgen.userflower.clean;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author x19047xx
 */
public final class CoreProtectAccessor{
    
    public CoreProtectAPI api;
    
    public CoreProtectAccessor(){
        
        api = getCoreProtect();
        
    }
    
    private CoreProtectAPI getCoreProtect() {

    Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

    // Check that CoreProtect is loaded
    if (plugin == null || !(plugin instanceof CoreProtect)) {
        return null;
    }

    // Check that the API is enabled
    CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
    if (CoreProtect.isEnabled() == false) {
        return null;
    }

    // Check that a compatible version of the API is loaded
    if (CoreProtect.APIVersion() < 9) {
        return null;
    }

    return CoreProtect;
}
    
}
