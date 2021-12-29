package me.alen_alex.utils;

import de.leonhard.storage.Config;
import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ConfigSettings;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.util.function.Consumer;

public final class FileUtils{
    private final JavaPlugin plugin;

    public FileUtils(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Get a file from the plugins resource folder as input stream
     * @param name of the file to be fetched
     * @return InputStream of the resource requested
     * @see InputStream
     */
    public InputStream getResourceAsStream(@NotNull String name){
        return plugin.getResource(name);
    }

    /**
     * Generate the plugins master folder if it doesn't exist
     * Usually located at /server-root/plugins/plugin_name
     */
    public void generateParentFolder(){
        if(plugin.getDataFolder().exists())
            return;
        plugin.getDataFolder().mkdirs();
    }

    /**
     * Create a plugin configuration using {@link Config}.
     * NOTE: This will also set the current plugin version to the config
     * @return A new Config instance
     * @see Config
     */
    public Config createConfiguration(){
        generateParentFolder();
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        Config createConfig = LightningBuilder
                .fromFile(configFile)
                .addInputStream(plugin.getResource("config.yml"))
                .setDataType(DataType.SORTED)
                .setConfigSettings(ConfigSettings.PRESERVE_COMMENTS)
                .setReloadSettings(ReloadSettings.MANUALLY)
                .createConfig();
        Config config = new Config("config.yml",plugin.getDataFolder().getPath());
        config.set("version",plugin.getDescription().getVersion());
        return config;
    }

    /**
     * Generate any folder under the parent folder
     * @param folderName Name of the folder that needs to be generated
     */
    public void generateFolder(String folderName){
        File file = new File(plugin.getDataFolder()+File.separator+folderName);
        if(!file.exists())
            file.mkdirs();
    }

    /**
     * Creates an empty {@link Yaml} file under the parent folder
     * @param fileName The name of the file that needed to be generated
     * @return A YAML file
     * @see Yaml
     */
    public Yaml createYAMLFile(@NotNull String fileName){
        return new Yaml(fileName,plugin.getDataFolder().getPath());
    }

    /**
     * Creates an empty {@link Yaml} file under the specified folderName
     * @param fileName The name of the file that needed to be generated
     * @param folderName The name of the folder underwhich the file needed to be generated
     * @return A YAML file under the specified folderName
     */
    public Yaml createYAMLFile(@NotNull String fileName,@NotNull String folderName){
        generateFolder(folderName);
        return new Yaml(fileName,plugin.getDataFolder().getPath()+File.separator+folderName);
    }

    /**
     * Creates a file with the provided input stream on to the parent folder
     * NOTE: The generated file will be named same as of the args provided
     * @param inputStreamName of the file to be fetched from resources
     * @return A YAML file by the provided resource
     */
    public Yaml createYAMLFileByInputStream(@NotNull String inputStreamName){
        return new Yaml(inputStreamName,plugin.getDataFolder().getPath(),getResourceAsStream(inputStreamName));
    }

    /**
     * Creates a file with the provided input stream on to the parent folder with the provided filename
     * @param inputStreamName of the file to be fetched from resources
     * @param fileName The name that should be provided for the file
     * @return A YAML file by the provided resource and name
     */
    public Yaml createYAMLFileByInputStream(@NotNull String inputStreamName, @NotNull String fileName){
        return new Yaml(fileName,plugin.getDataFolder().getPath(),getResourceAsStream(inputStreamName));
    }

    /**
     * Creates a file with the provided input stream on to the parent folder with the provided filename and provided path
     * @param inputStreamName of the file to be fetched from resources
     * @param fileName The name that should be provided for the file
     * @param folderName The name of the folder that the file should be created
     * @return A YAML file by the provided resource and name
     */
    public Yaml createYAMLFileByInputStream(@NotNull String inputStreamName, @NotNull String fileName,@NotNull String folderName){
        return new Yaml(fileName,plugin.getDataFolder().getPath()+File.separator+folderName,getResourceAsStream(inputStreamName));
    }

    /**
     * Performs a specified {@link Consumer} task if the file exist, If not then will do nothing
     * @param fileName of the file to be exected.
     * @param toPerform the consumer operation.
     * @see Consumer
     */
    public void executeIfExists(@NotNull String fileName, Consumer<File> toPerform){
        File file = new File(plugin.getDataFolder(),fileName);
        if(file.exists()){
            toPerform.accept(file);
        }
    }

    /**
     * Checks whether the given file exists and executes {@link Consumer} if the operation in async method
     * @param fileName of the file to be exected.
     * @param toPerform the consumer operation.
     * @see Consumer
     */
    public void executeAsyncIfExists(@NotNull String fileName, Consumer<File> toPerform){
        File file = new File(plugin.getDataFolder(),fileName);
        if(file.exists()){
            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                @Override
                public void run() {
                    toPerform.accept(file);
                }
            });
        }
    }

    /**
     * Deletes the file if it exists
     * @param fileName name of the file
     * @deprecated in favour of {@link FileUtils#executeIfExists(String, Consumer)} (String, Consumer)
     */
    @Deprecated
    public void deleteFile(String fileName) {
        File file = new File(plugin.getDataFolder().getPath(), fileName);
        if (file.exists())
            file.delete();
    }

    /**
     * Deletes the file if it exists
     * @param fileName name of the file
     * @param folderName name of the folder
     * @deprecated in favour of {@link FileUtils#executeIfExists(String, Consumer)} (String, Consumer)
     */
    @Deprecated
    public void deleteFile(String fileName,String folderName) {
        File file = new File(plugin.getDataFolder().getPath() + File.separator + folderName, fileName);
        if (file.exists())
            file.delete();
    }
}
