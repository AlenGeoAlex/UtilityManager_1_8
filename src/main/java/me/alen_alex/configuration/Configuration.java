package me.alen_alex.configuration;

import de.leonhard.storage.internal.FlatFile;
import me.alen_alex.UtilityManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Configuration {

    private final UtilityManager utilityManager;
    protected FlatFile yamlFile;

    public Configuration(UtilityManager utilityManager) {
        this.utilityManager = utilityManager;
    }

    /**
     * Clears the loaded file data
     */
    public void clearConfigData(){
        this.yamlFile.getFileData().clear();
    }

    /**
     * Colorize and get the message from given config path
     * @param configPath the path of the config-string
     * @return String a Colorized String
     */
    public String fetchStringMessage(@NotNull String configPath){
        return utilityManager.getMessageUtils().colorize(yamlFile.getString(configPath));
    }

    /**
     * Colorize and get the list of message from given config path
     * @param configPath the path of the config-string
     * @return String a Colorized String
     */
    public List<String> fetchListMessage(@NotNull String configPath){
        List<String> stringList = new ArrayList<>();
        yamlFile.getStringList(configPath).forEach(s -> {
            stringList.add(utilityManager.getMessageUtils().colorize(s));
        });
        return stringList;
    }

    /**
     * Get the exact location from the provided-config path.
     * NOTE: This location would also fetch both Pitch and Yaw. Check {@link Configuration#getLocationFromConfig(String)} for simple location
     * without the pitch and yaw
     * @param configPath the path of the config-string
     * @return deserialized location
     */
    public Location getExactLocationFromConfig(@NotNull String configPath){
        return utilityManager.getLocationUtils().parseLocation(yamlFile.getString(configPath),true);
    }

    /**
     * Get the exact location from the provided-config path.
     * NOTE: This location would not fetch both Pitch and Yaw. Check {@link Configuration#getExactLocationFromConfig(String)} (String)} for exact location
     * with the pitch and yaw
     * @param configPath the path of the config-string
     * @return deserialized location
     */
    public Location getLocationFromConfig(@NotNull String configPath){
        return utilityManager.getLocationUtils().parseLocation(yamlFile.getString(configPath),false);
    }

    /**
     * Get an optional parameter if the given material is valid
     * @param configPath the path of the config-string
     * @return Optional Material
     */
    public Optional<Material> getMaterialFromConfig(@NotNull String configPath){
        return utilityManager.getEnumValidator().getMaterialIfPresent(yamlFile.getString(configPath));
    }

    /**
     * Reloads the entire loaded data using {@link Configuration#loadConfig()}. This will check for {@link Configuration#initConfig()}
     * @return long Time taken to complete the reloading process. It will return 0 if {@link Configuration#initConfig()} fails
     */
    public long reloadFile(){
        long start = System.currentTimeMillis();
        if(!initConfig())
            return 0;
        loadConfig();
        long end = System.currentTimeMillis();
        return ((end-start));
    }

    /**
     * Create necessary files and check for file updation if requires
     * @return boolean if the file is generated successfully
     */
    public abstract boolean initConfig();

    /**
     * Load all the necessary information from the config into the plugins cache
     */
    public abstract void loadConfig();

    /**
     * Get the version of the config.
     * @return
     */
    public abstract String getConfigVersion();

    /**
     * Get the version of the plugin.
     * @return
     */
    public abstract String getPluginVersion();



}
