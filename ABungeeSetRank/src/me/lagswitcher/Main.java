package me.lagswitcher;

import net.md_5.bungee.api.plugin.Plugin;

public class Main
  extends Plugin
{
  private static Main plugin;
  public String perms = "&c&lPermissions> &cYou must be rank Admin";
  public String invArgs = "&c&lRank> &cInvalid arguments";
  public String notOnline = "&c&lRank> &cPlayer is not connected";
  public String success = "&c&lRank> &cThe rank of &a%player% &cis now &a%rank%";
  
  public void onEnable()
  {
    plugin = this;
    getProxy().getPluginManager().registerCommand(this, new CommandSetRank());
    System.out.println("================================");
    System.out.println("This network is running ABungeeSetRank by BenzPlayzMC");
    System.out.println("================================");
  }
  
  public static Main getPlugin()
  {
    return plugin;
  }
}
