/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xgen.userflower;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.xgen.userflower.clean.Clean;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;
/**
 *
 * @author x19047xx
 */
public class CommandExecute implements CommandExecutor{
    
    private final UserFlower plg;
    private AutoRunConfig autoRunConfig = null;
    private BukkitTask autoRunner = null;
    
    
    public CommandExecute(UserFlower plg_){
        plg = plg_;
    }

    public CommandExecute(UserFlower plg_, AutoRunConfig autoRunConfig_, BukkitTask repeatTask_){
        plg = plg_;
        autoRunConfig = autoRunConfig_;
        autoRunner = repeatTask_;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        
        
        if (cmd.getName().equalsIgnoreCase("ufclean")){
            Player p = (Player)sender;
            
            if(!p.hasPermission("userflower.clean")){
                p.sendMessage("< ??? > お前に ufcleanコマンド を使う資格はない。");
                return false;
            }
            if(args.length < 2){
                p.sendMessage(ChatColor.BLUE+"引数が足りません");
                return false;
            }
            if(args.length > 2){
                p.sendMessage(ChatColor.BLUE+"引数が多すぎます");
                return false;
            }
            
            long border ;
            int deletenum;
            
            try{
                border = Long.parseLong(args[0]);
                deletenum = Integer.parseInt(args[1]);
            }catch(Exception e){
                p.sendMessage(ChatColor.BLUE+"引数は整数値を入力してください");
                return false;
            }
            
            new Clean(plg,border,deletenum).run();
            
            p.sendMessage(ChatColor.BLUE+"クリーンコマンド発動");
            return true;
            
        }else if(cmd.getName().equalsIgnoreCase("ufauto")){
            
            Player p = (Player)sender;
            
            if(!p.hasPermission("userflower.auto")){
                p.sendMessage("< ??? > 貴様にufautoコマンドは使わせんぞ！！");
                return false;
            }
            if(args.length < 4){
                p.sendMessage(ChatColor.BLUE+"引数が足りません");
                return false;
            }
            if(args.length > 4){
                p.sendMessage(ChatColor.BLUE+"引数が多すぎます");
                return false;
            }
            
            boolean enable;
            long border ;
            int deletenum ;
            long interval ;
            
            try{
                enable = Boolean.parseBoolean(args[0]);
                border = Long.parseLong(args[1]);
                deletenum = Integer.parseInt(args[2]);
                interval = Long.parseLong(args[3]);
            }catch(Exception e){
                p.sendMessage(ChatColor.BLUE+"/auto [true/false] [long] [int] [long]");
                return false;
            }
            
            autoRunConfig.set(enable, border, deletenum, interval);
            if(autoRunner != null)autoRunner.cancel();
            if(autoRunConfig.enable == true){
                autoRunner = new Clean(plg, autoRunConfig.border,autoRunConfig.deleteAmount).runTaskTimer(plg, 0, autoRunConfig.interval);
            }
            
            p.sendMessage(ChatColor.BLUE+"自動実行を"+autoRunConfig.enable+"に設定");
            return true;
        }
        
        return false;
    }
    
}
