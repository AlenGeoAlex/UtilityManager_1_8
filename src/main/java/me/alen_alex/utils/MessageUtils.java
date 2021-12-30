package me.alen_alex.utils;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MessageUtils {

    private final JavaPlugin plugin;
    private String pluginPrefix;
    private boolean hasPrefix;

    public MessageUtils(JavaPlugin plugin) {
        this.plugin = plugin;
        this.pluginPrefix = null;
        hasPrefix = false;
    }

    public MessageUtils(JavaPlugin plugin, String pluginPrefix) {
        this.plugin = plugin;
        this.pluginPrefix = pluginPrefix;
        hasPrefix = true;
    }

    /**
     * Gets the plugin prefix
     * @return the configured plugin prefix. Can also return null, if nothing is set
     */
    @Nullable
    public String getPluginPrefix() {
        return pluginPrefix;
    }

    /**
     * Sets the plugin prefix
     * @param pluginPrefix String of what should be the prefix of chat messages
     */
    public void setPluginPrefix(String pluginPrefix) {
        this.pluginPrefix = pluginPrefix;
        if(StringUtils.isBlank(pluginPrefix))
            hasPrefix = false;
        else hasPrefix = true;
    }

    /**
     * Colorize and returns the string with the legacy Color codes
     * NOTE: If the message is identified as blank message by {@link StringUtils#isBlank(CharSequence)}, it will simply
     * return null
     * @param message Message to translate the color codes
     * @return String Translated messages.
     */
    @Nullable
    public String colorize(String message){
        if(StringUtils.isBlank(message))
            return null;
        return ChatColor.translateAlternateColorCodes('&',message);
    }

    /**
     * The message will be stripped of any color codes if present
     * NOTE: If the message is identified as blank message by {@link StringUtils#isBlank(CharSequence)}, it will simply
     * return null
     * @param message String to be striped of color codes
     * @return String which is stripped of color codes
     */
    @Nullable
    public String stripColorCodes(@NotNull String message){
        if(StringUtils.isBlank(message))
            return null;
        return ChatColor.stripColor(message);
    }

    /**
     * Format the message with plugin prefix.
     * @param message
     * @return
     */
    public String formatMessage(String message){
        if(StringUtils.isBlank(message))
            return "";

        if(hasPrefix) {
            return pluginPrefix + " " + message;
        }else return message;
    }

    /**
     * Send a message to a player without again calling {@link MessageUtils#colorize(String)} since those messages can
     * already cache the colorized string
     * NOTE: If the message is identified as blank message by {@link StringUtils#isBlank(CharSequence)}, it will simply
     * skip the message from sending to the player
     * @param player to send the message
     * @param message what to send as the message
     */
    public void sendColorCachedMessage(Player player, String message){
        if(StringUtils.isBlank(message))
            return;
        player.sendMessage(formatMessage(message));
    }

    /**
     * Send a message to a command sender without again calling {@link MessageUtils#colorize(String)} since those messages can
     * already cache the colorized string
     * NOTE: If the message is identified as blank message by {@link StringUtils#isBlank(CharSequence)}, it will simply
     * skip the message from sending to the player
     * @param sender to send the message
     * @param message what to send as the message
     * @see org.bukkit.command.CommandSender
     */
    public void sendColorCachedMessage(CommandSender sender,String message){
        if(StringUtils.isBlank(message))
            return;
        sender.sendMessage(formatMessage(message));
    }

    /**
     * Send a message to a player after colorizing a done proper formatting
     * NOTE: If the message is identified as blank message by {@link StringUtils#isBlank(CharSequence)}, it will simply
     * skip the message from sending to the player
     * @param player to send the message
     * @param message what to send as the message
     */
    public void sendMessage(Player player,String message){
        if(StringUtils.isBlank(message))
            return;
        player.sendMessage(colorize(formatMessage(message)));
    }

    /**
     * Send a message to a player after colorizing a done proper formatting
     * NOTE: If the message is identified as blank message by {@link StringUtils#isBlank(CharSequence)}, it will simply
     * skip the message from sending to the player
     * @param sender to send the message
     * @param message what to send as the message
     * @see org.bukkit.command.CommandSender
     */
    public void sendMessage(CommandSender sender,String message){
        if(StringUtils.isBlank(message))
            return;
        sender.sendMessage(colorize(formatMessage(message)));
    }

}
