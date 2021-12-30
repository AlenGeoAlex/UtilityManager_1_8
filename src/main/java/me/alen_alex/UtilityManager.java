package me.alen_alex;

import me.alen_alex.utils.MessageUtils;
import me.alen_alex.utils.EnumValidator;
import me.alen_alex.utils.FileUtils;
import me.alen_alex.utils.LocationUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class UtilityManager {

    private final LocationUtils locationUtils;
    private final FileUtils fileUtils;
    private final EnumValidator enumValidator;
    private final MessageUtils messageUtils;

    public UtilityManager(final JavaPlugin plugin){
        locationUtils = new LocationUtils(plugin);
        fileUtils = new FileUtils(plugin);
        enumValidator = new EnumValidator(plugin);
        messageUtils = new MessageUtils(plugin);
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

    public MessageUtils getMessageUtils() {
        return messageUtils;
    }
}
