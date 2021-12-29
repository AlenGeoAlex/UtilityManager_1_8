package me.alen_alex.utils;

import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class EnumValidator {

    private final JavaPlugin plugin;

    public EnumValidator(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Check if A enum constant is valid and return Optional depending upon the validity
     * @param name Name of the enum constant that need to be fetched
     * @param enumClass {@link Class} the enum belongs to
     * @return {@link Optional} enum based on the validity
     * @see Optional
     * @see Class
     */
    public Optional<Enum> getIfPresent(@NotNull String name, @NotNull Class enumClass){
        if(EnumUtils.isValidEnum(enumClass,name))
            return Optional.ofNullable(Enum.valueOf(enumClass,name));
        else return Optional.empty();
    }

    /**
     * Check if a {@link Material} is valid and returns an Optional value if the material is valid
     * @param material of the material that needs to be matched
     * @return {@link Optional<Material>} an optional value based on the validity
     */
    public Optional<Material> getMaterialIfPresent(@NotNull String material){
        if(EnumUtils.isValidEnum(Material.class,material))
            return Optional.ofNullable(Material.valueOf(material));
        else return Optional.empty();
    }

    /**
     * Check if a {@link Sound} is valid and returns an Optional value if the material is valid
     * @param sound of the material that needs to be matched
     * @return {@link Optional<Material>} an optional value based on the validity
     */
    public Optional<Sound> getSoundIfPresent(@NotNull String sound){
        if(EnumUtils.isValidEnum(Sound.class,sound))
            return Optional.ofNullable(Sound.valueOf(sound));
        else return Optional.empty();
    }




}
