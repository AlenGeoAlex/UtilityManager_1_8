package me.alen_alex.utils;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class ChatUtils {

    private final JavaPlugin plugin;
    private String pluginPrefix;

    public ChatUtils(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public ChatUtils(JavaPlugin plugin, String pluginPrefix) {
        this.plugin = plugin;
        this.pluginPrefix = pluginPrefix;
    }

    public String getPluginPrefix() {
        return pluginPrefix;
    }

    public void setPluginPrefix(String pluginPrefix) {
        this.pluginPrefix = pluginPrefix;
    }

    public String colorize(@NotNull String message){
        return ChatColor.translateAlternateColorCodes('&',message);
    }

}
