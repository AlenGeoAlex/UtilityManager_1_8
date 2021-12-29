package me.alen_alex;

import me.alen_alex.utils.ChatUtils;
import me.alen_alex.utils.EnumValidator;
import me.alen_alex.utils.FileUtils;
import me.alen_alex.utils.LocationUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class UtilityManager {

    private final LocationUtils locationUtils;
    private final FileUtils fileUtils;
    private final EnumValidator enumValidator;
    private final ChatUtils chatUtils;

    public UtilityManager(final JavaPlugin plugin){
        locationUtils = new LocationUtils(plugin);
        fileUtils = new FileUtils(plugin);
        enumValidator = new EnumValidator(plugin);
        chatUtils = new ChatUtils(plugin);
    }

    public LocationUtils getLocationUtils() {
        return locationUtils;
    }

    public FileUtils getFileUtils() {
        return fileUtils;
    }

    public EnumValidator getEnumValidator() {
        return enumValidator;
    }

    public ChatUtils getChatUtils() {
        return chatUtils;
    }
}
