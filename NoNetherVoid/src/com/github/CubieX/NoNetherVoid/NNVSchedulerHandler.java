package com.github.CubieX.NoNetherVoid;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class NNVSchedulerHandler
{
   private NoNetherVoid plugin = null;

   public NNVSchedulerHandler(NoNetherVoid plugin)
   {
      this.plugin = plugin;
   }

   public void startNetherRoofAccessCheckScheduler_SynchRepeated()
   {      
      plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable()
      {
         public void run()
         {
            try
            {
               Player[] currOnlinePlayers = plugin.getServer().getOnlinePlayers();

               for(Player currPlayer : currOnlinePlayers)
               {
                  if(currPlayer.getLocation().getWorld().getEnvironment().equals(World.Environment.NETHER))
                  {
                     if(currPlayer.getLocation().getY() >= 128)
                     {
                        if(!currPlayer.hasPermission("nonethervoid.bypass"))
                        {
                           if(!currPlayer.isDead())
                           {
                              currPlayer.sendMessage(ChatColor.RED + "You are NOT allowed to enter the roof of the nether!");
                              currPlayer.setHealth(0);
                           }
                        }
                     }
                  }
               }
            }
            catch(Exception ex)
            {
               NoNetherVoid.log.severe(ex.getMessage()); // something went wrong.
            }
         }
      }, 20*5L, 20*1L); // 5 sec delay, 1 sec period        
   }  
}
