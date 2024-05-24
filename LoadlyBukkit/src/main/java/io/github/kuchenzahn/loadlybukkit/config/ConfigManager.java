package io.github.kuchenzahn.loadlybukkit.config;

import io.github.kuchenzahn.loadlybukkit.LoadlyBukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    public void checkConfig(){
        for(ConfigParam param : ConfigParam.values()){
            if(!ConfigManager.getConfig().contains(param.getPath())){
                ConfigManager.getConfig().set(param.getPath(), param.getDefaultValue());
            }
        }

        save();
    }

    public void reloadConfig(){
        LoadlyBukkit.getInstance().reloadConfig();
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
        SERVER_PORT("loadly.port", 2244),
        AUTOMATIC_JOIN_MESSAGE("automatic-join-message.enabled", true),
        AUTOMATIC_JOIN_MESSAGE_DISPLAY("automatic-join-message.display", "ACTIONBAR"),
        AUTOMATIC_JOIN_MESSAGE_TEXT("automatic-join-message.text", "§6Welcome to the server %s!"),
        AUTOMATIC_JOIN_MESSAGE_PRIORITY("automatic-join-message.priority", 1),
        AUTOMATIC_JOIN_MESSAGE_TYPE("automatic-join-message.type", "INFO"),
        MESSAGE_COMMAND_PLAYER_REQUIRED("message.command.player-required", "§cYou must be a player to execute this command!");

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
