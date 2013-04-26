package com.github.CubieX.NoNetherVoid;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class NoNetherVoid extends JavaPlugin
{
   public static final Logger log = Bukkit.getServer().getLogger();
   static final String logPrefix = "[NoNetherVoid] "; // Prefix to go in front of all log entries
   private NoNetherVoid plugin = null;
   private NNVCommandHandler comHandler = null;
   private NNVConfigHandler cHandler = null;
   private NNVEntityListener eListener = null;
   private NNVSchedulerHandler schedHandler = null;

   static boolean debug = false;

   //*************************************************
   static String usedConfigVersion = "1"; // Update this every time the config file version changes, so the plugin knows, if there is a suiting config present
   //*************************************************

   @Override
   public void onEnable()
   {
      this.plugin = this;
      cHandler = new NNVConfigHandler(this);

      if(!checkConfigFileVersion())
      {
         log.severe(logPrefix + "Outdated or corrupted config file(s). Please delete your config files."); 
         log.severe(logPrefix + "will generate a new config for you.");
         log.severe(logPrefix + "will be disabled now. Config file is outdated or corrupted.");
         getServer().getPluginManager().disablePlugin(this);
         return;
      }

      if (!hookToPermissionSystem())
      {
         log.severe(logPrefix + "- Disabled due to no superperms compatible permission system found!");
         getServer().getPluginManager().disablePlugin(this);
         return;
      }

      eListener = new NNVEntityListener(this);      
      comHandler = new NNVCommandHandler(this, cHandler);      
      getCommand("nnv").setExecutor(comHandler);

      schedHandler = new NNVSchedulerHandler(this);

      readConfigValues();

      schedHandler.startNetherRoofAccessCheckScheduler_SynchRepeated();

      log.info(this.getDescription().getName() + " version " + getDescription().getVersion() + " is enabled!");
   }

   private boolean checkConfigFileVersion()
   {      
      boolean configOK = false;     

      if(cHandler.getConfig().isSet("config_version"))
      {
         String configVersion = cHandler.getConfig().getString("config_version");

         if(configVersion.equals(usedConfigVersion))
         {
            configOK = true;
         }
      }

      return (configOK);
   }

   private boolean hookToPermissionSystem()
   {
      if ((getServer().getPluginManager().getPlugin("PermissionsEx") == null) &&
            (getServer().getPluginManager().getPlugin("bPermissions") == null) &&
            (getServer().getPluginManager().getPlugin("zPermissions") == null) &&
            (getServer().getPluginManager().getPlugin("PermissionsBukkit") == null))
      {
         return false;
      }
      else
      {
         return true;
      }
   }

   public void readConfigValues()
   {
      debug = cHandler.getConfig().getBoolean("debug");
   }

   @Override
   public void onDisable()
   {      
      cHandler = null;
      eListener = null;
      comHandler = null;
      schedHandler = null;
      log.info(this.getDescription().getName() + " version " + getDescription().getVersion() + " is disabled!");
   }   
}


