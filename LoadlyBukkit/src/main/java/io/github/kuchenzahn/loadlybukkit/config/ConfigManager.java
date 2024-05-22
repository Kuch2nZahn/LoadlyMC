package io.github.kuchenzahn.loadlybukkit.config;

import io.github.kuchenzahn.loadlybukkit.LoadlyBukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;

public class ConfigManager {

    public void checkConfig(){
        for(ConfigParam param : ConfigParam.values()){
            if(!ConfigManager.getConfig().contains(param.getPath())){
                ConfigManager.getConfig().set(param.getPath(), param.getDefaultValue());
            }
        }

        save();
    }

    private void saveConfig(){
        LoadlyBukkit.getInstance().saveConfig();
    }

    public static Object get(ConfigParam param){
        return ConfigManager.getConfig().get(param.getPath());
    }

    public static void set(ConfigParam param, Object value){
        ConfigManager.getConfig().set(param.getPath(), value);
    }

    public static void save(){
        ConfigManager.getConfig().options().copyDefaults(true);
        ConfigManager.getInstance().saveConfig();
    }

    public static void reload(){
        LoadlyBukkit.getInstance().reloadConfig();
    }

    public static ConfigManager getInstance(){
        return LoadlyBukkit.getInstance().getConfigManager();
    }

    private static FileConfiguration getConfig(){
        return LoadlyBukkit.getInstance().getConfig();
    }

    public enum ConfigParam{
        PREFIX("loadly.prefix", "§8[§6Loadly§8] §7"),
        SERVER_HOST("loadly.host", "localhost"),
        SERVER_PORT("loadly.port", 2244);

        private final String path;
        private final Object defaultValue;

        ConfigParam(String path, Object defaultValue){
            this.path = path;
            this.defaultValue = defaultValue;
        }

        public String getPath(){
            return path;
        }

        public Object getDefaultValue(){
            return defaultValue;
        }

        public static ConfigParam getByPath(String path){
            for(ConfigParam param : values()){
                if(param.getPath().equals(path)){
                    return param;
                }
            }
            return null;
        }

        public static ConfigParam getByDefaultValue(Object defaultValue){
            for(ConfigParam param : values()){
                if(param.getDefaultValue().equals(defaultValue)){
                    return param;
                }
            }
            return null;
        }
    }
}
