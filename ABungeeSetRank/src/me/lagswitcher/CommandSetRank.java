package me.lagswitcher;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ProxyReloadEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class CommandSetRank
  extends Command
{
  private Main plugin = Main.getPlugin();
  
  public CommandSetRank()
  {
    super("setrank");
  }
  
  @SuppressWarnings("deprecation")
public void execute(CommandSender sender, String[] strings)
  {
    if ((sender instanceof ProxiedPlayer))
    {
      ProxiedPlayer p = (ProxiedPlayer)sender;
      if (p.hasPermission("bungeecord.command.setrank"))
      {
        if (strings.length == 2)
        {
          ProxiedPlayer playerq = this.plugin.getProxy().getPlayer(strings[0]);
          
          String rango = strings[1];
          if (playerq != null)
          {
            playerq.removeGroups((String[])playerq.getGroups().toArray(new String[playerq.getGroups().size()]));
            
            playerq.addGroups(new String[] { rango });
            File file = new File(this.plugin.getDataFolder().getParentFile().getParent(), "config.yml");
            try
            {
              net.md_5.bungee.config.Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
              for (String quitar : playerq.getGroups()) {
                config.getStringList("groups." + playerq.getName()).remove(quitar);
              }
              playerq.disconnect("Bungee Rank set to " + rango);
              
              config.getStringList("groups." + playerq.getName()).add(rango);
              config.set("groups." + playerq.getName(), Arrays.asList(new String[] { rango }));
              ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
              BungeeCord.getInstance().config.load();
              BungeeCord.getInstance().stopListeners();
              BungeeCord.getInstance().startListeners();
              BungeeCord.getInstance().getPluginManager().callEvent(new ProxyReloadEvent(sender));
            }
            catch (IOException ex)
            {
              ex.printStackTrace();
            }
            String msg = ChatColor.translateAlternateColorCodes('&', this.plugin.success).replace("%player%", playerq.getDisplayName()).replace("%rank%", rango);
            
            this.plugin.getLogger().info(msg);
            p.sendMessage(new ComponentBuilder(msg).create());
          }
          else
          {
            p.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', this.plugin.notOnline)).create());
          }
        }
        else
        {
          p.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', this.plugin.invArgs)).create());
        }
      }
      else {
        p.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', this.plugin.perms)).create());
      }
    }
  }
}
