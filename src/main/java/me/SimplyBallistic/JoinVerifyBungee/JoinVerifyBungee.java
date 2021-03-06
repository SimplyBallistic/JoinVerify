package me.SimplyBallistic.JoinVerifyBungee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class JoinVerifyBungee extends Plugin implements Listener{
	public static JoinVerifyBungee instance;
	public static boolean verifyAll;
	public BungeePlayersFile file;
	private File configFile;
	public Configuration config;
	
	@Override
	public void onEnable() {
		getLogger().info("Staring...");
		
		if(getProxy().getPluginManager().getPlugin("BungeeBridgeS")==null){
			getLogger().severe("Bungee Bridge isn't installed! Shutting Down...");
			return;
		}
		instance=this;
		getProxy().getPluginManager().registerListener(this, new Listeners());
		if(!getDataFolder().exists())
		getDataFolder().mkdir();
		file=new BungeePlayersFile();
		initConfig();
	
		verifyAll=config.getBoolean("verify-all");
		
		
	}
	private void initConfig(){
		String fileName="bungee-config.yml";
		configFile=new File(getDataFolder(), fileName);
		if(!configFile.exists()){
			try {
				getLogger().info("config Yaml not found! Loading default...");
				InputStream stream=getResourceAsStream(fileName);
				Files.copy(stream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				stream.close();
			} catch (IOException e) {
				getLogger().info("Failed in creating config file! "+e.getMessage());
			}
			finally{}
			
		}
			try {
				config=ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
				getLogger().info("config file successfully loaded!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	}
	public void reload(){
		initConfig();
	}
	
}
