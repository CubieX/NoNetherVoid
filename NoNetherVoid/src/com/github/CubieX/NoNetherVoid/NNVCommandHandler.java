package com.github.CubieX.NoNetherVoid;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NNVCommandHandler implements CommandExecutor
{
   private NoNetherVoid plugin = null;
   private NNVConfigHandler cHandler = null;

   public NNVCommandHandler(NoNetherVoid plugin, NNVConfigHandler cHandler) 
   {
      this.plugin = plugin;
      this.cHandler = cHandler;      
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
   {
      Player player = null;
      
      if (sender instanceof Player) 
      {
         player = (Player) sender;
      }
            
      if (cmd.getName().equalsIgnoreCase("nnv"))
      { // If the player typed /el then do the following... (can be run from console also)
         if (args.length == 0)
         { //no arguments, so help will be displayed
            return false;
         }
         if (args.length==1)
         {
            if (args[0].equalsIgnoreCase("version")) // argument 0 is given and correct
            {            
               sender.sendMessage(ChatColor.YELLOW + "This server is running " + plugin.getDescription().getName() + " version " + plugin.getDescription().getVersion());
               return true;
            }    
            if (args[0].equalsIgnoreCase("reload")) // argument 0 is given and correct
            {            
               if(sender.hasPermission("nonethervoid.admin"))
               {                        
                  cHandler.reloadConfig(sender);
                  return true;
               }
               else
               {
                  sender.sendMessage(ChatColor.RED + "You do not have sufficient permission to reload " + plugin.getDescription().getName() + "!");
               }
            }
         }
         else
         {
            sender.sendMessage(ChatColor.YELLOW + "Invalid argument count.");
         }                

      }         
      return false; // if false is returned, the help for the command stated in the plugin.yml will be displayed to the player
   }
}
