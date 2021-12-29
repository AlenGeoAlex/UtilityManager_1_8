package me.alen_alex.configuration;

import de.leonhard.storage.internal.FlatFile;
import me.alen_alex.UtilityManager;
import org.apache.commons.lang3.StringUtils;
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
     * Creates a file
     * @param isConfig boolean is the file a config file.
     * @param fileName name of the file that would be used to create
     * @param isResourceName name of the resource that needed to be fetched. Can be NULL
     * @return boolean if the file has been properly created
     * @see de.leonhard.storage.Config
     */
    public boolean initFiles(boolean isConfig, @NotNull String fileName , String isResourceName){
        if(isConfig)
            this.yamlFile = utilityManager.getFileUtils().createConfiguration();
        else {
            if(StringUtils.isBlank(isResourceName))
                this.yamlFile = utilityManager.getFileUtils().createYAMLFile(fileName);
            else this.yamlFile = utilityManager.getFileUtils().createYAMLFileByInputStream(isResourceName,fileName);
        }

        return yamlFile != null;
    }

    /**
     * Creates a file
     * @param isConfig boolean is the file a config file.
     * @param fileName name of the file that would be used to create
     * @param folderName name of the folder under which the file needed to be created
     * @param isResourceName name of the resource that needed to be fetched. Can be NULL
     * @return boolean if the file has been properly created
     * @see de.leonhard.storage.Config
     */
    public boolean initFiles(boolean isConfig, @NotNull String fileName, String folderName, String isResourceName){
        if(isConfig)
            this.yamlFile = utilityManager.getFileUtils().createConfiguration();
        else {
            if(StringUtils.isBlank(isResourceName))
                this.yamlFile = utilityManager.getFileUtils().createYAMLFile(fileName);
            else this.yamlFile = utilityManager.getFileUtils().createYAMLFileByInputStream(isResourceName,fileName,folderName);
        }

        return yamlFile != null;
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
        return utilityManager.getChatUtils().colorize(yamlFile.getString(configPath));
    }

    /**
     * Colorize and get the list of message from given config path
     * @param configPath the path of the config-string
     * @return String a Colorized String
     */
    public List<String> fetchListMessage(@NotNull String configPath){
        List<String> stringList = new ArrayList<>();
        yamlFile.getStringList(configPath).forEach(s -> {
            stringList.add(utilityManager.getChatUtils().colorize(s));
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
     * Reloads the entire loaded data using {@link Configuration#loadConfig()}.
     * @return long Time taken to complete the reloading process
     */
    public long reloadFile(){
        long start = System.currentTimeMillis();
        loadConfig();
        long end = System.currentTimeMillis();
        return ((end-start));
    }

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
