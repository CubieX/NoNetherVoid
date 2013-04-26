package com.github.CubieX.NoNetherVoid;

import org.bukkit.event.Listener;


public class NNVEntityListener implements Listener
{
   private NoNetherVoid plugin = null;

   public NNVEntityListener(NoNetherVoid plugin)
   {        
      this.plugin = plugin;

      //plugin.getServer().getPluginManager().registerEvents(this, plugin);
   }

}
