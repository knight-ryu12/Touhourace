package org.noteusoft.mireiyu.touhouraces;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class THRPlugin
extends JavaPlugin
implements Listener
{
	private static THRPlugin instance;
	
	public static THRPlugin getInstance()
	{
		return instance;
	}
	public final Logger logger = Logger.getLogger("Minecraft");
	public static THRPlugin plugin;	
	
	public final String touhouraces = ChatColor.WHITE + "[" + ChatColor.RED + "THR" + ChatColor.WHITE + "] " + ChatColor.RESET;
	public final PluginDescriptionFile pdfFile = getDescription();
	
	public void onDisable()
	{
		this.logger.info(touhouraces + "�͖���������܂����B");
	}
	
	/*�N��*/
	public void onEnable()
	{
		PluginDescriptionFile pdfFile = getDescription();
		this.logger.info(touhouraces + pdfFile.getVersion() + " �͗L��������܂����B");
		PluginManager pm = getServer().getPluginManager();
        final Plugin sc = this.getServer().getPluginManager().getPlugin("SimpleClans");
        final Plugin cs = this.getServer().getPluginManager().getPlugin("CrackShot");
        
        if (sc != null) {
    		this.logger.info(touhouraces + pdfFile.getVersion() + "�͐�����Simpleclans�ƘA�g���܂����B");
        }
        if (cs != null) {
    		this.logger.info(touhouraces + pdfFile.getVersion() + "�͐�����CrackShot�ƘA�g���܂����B");
        }

		pm.registerEvents(this, this);
		saveDefaultConfig();
		registerSchedule();
		registerListeners();
		registerCommands();
	}
	
	public void registerSchedule()
	{
		getServer().getPluginManager().registerEvents(new org.noteusoft.mireiyu.touhouraces.Schedule.SkillSchedule(this), this);
	}
	
	public void registerListeners()
	{
		getServer().getPluginManager().registerEvents(new org.noteusoft.mireiyu.touhouraces.Listener.SkillListener(this), this);
	}
	
	public void registerCommands() 
	{
		getCommand("touhouraces").setExecutor(new org.noteusoft.mireiyu.touhouraces.command.SkillCommand(this));
		getCommand("thr").setExecutor(new org.noteusoft.mireiyu.touhouraces.command.SkillCommand(this));
	}
}  