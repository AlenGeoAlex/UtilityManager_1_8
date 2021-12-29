package me.alen_alex.utils;

import net.minecraft.server.v1_8_R3.StructureBoundingBox;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;

public final class LocationUtils {

    private final JavaPlugin plugin;
    private static final String DELIMITER = "/";

    public LocationUtils(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Serialize the give location to {@link String} format
     * @param location Location the location which need to be serialized
     * @return String  A Serialized location as a string
     */
    public String toString(@NotNull Location location){
        StringJoiner stringJoiner = new StringJoiner(DELIMITER);
        stringJoiner.add(location.getWorld().getName());
        stringJoiner.add(String.valueOf(location.getBlockX()));
        stringJoiner.add(String.valueOf(location.getBlockY()));
        stringJoiner.add(String.valueOf(location.getBlockZ()));
        stringJoiner.add(String.valueOf(((int) location.getYaw())));
        stringJoiner.add(String.valueOf(((int) location.getPitch())));

        return stringJoiner.toString();
    }

    /**
     * Serialize the current location of the {@link Player}
     * @param player The player whose location want to be serialized
     * @return String A Serialized location as a string
     */
    public String toString(@NotNull Player player){
        return this.toString(player.getLocation());
    }

    /**
     * Serialize the location of a given {@link Block}
     * @param block The block on which the location needs to be serialized
     * @return String A Serialized location as a string
     */
    public String toString(@NotNull Block block){
        return this.toString(block.getLocation());
    }

    /**
     * Serialize the location of a given {@link Player} with the respective provided {@link BlockFace}
     * @param player The player whose location want to be serialized
     * @param relativeBlockFace The facing direction of the block from a player location
     * @return String A Serialized location as a string
     * @see BlockFace
     */
    public String toString(@NotNull Player player, @NotNull BlockFace relativeBlockFace){
        return this.toString(player.getLocation().getBlock().getRelative(relativeBlockFace));
    }

    /**
     * deserialize a {@link Location} that has been serialized using {@link LocationUtils#toString(Location)} or its corresponding methods.
     * @param locationString The location of the string that needs to be deserialized
     * @param parseExact Whether to parse the yaw and pitch too
     * @throws IllegalArgumentException if the provided locationString is empty or null
     * @throws NullPointerException if the world cannot be located while deserializing
     * @return Location a deserialized location
     * @see Location
     */
    public Location parseLocation(@NotNull String locationString, boolean parseExact){
        if(StringUtils.isBlank(locationString))
            throw new IllegalArgumentException("The provided locationString is empty/blank in LocationUtils#parseLocation");

        String[] locationArgs = locationString.split(DELIMITER);
        World world = plugin.getServer().getWorld(locationArgs[0]);
        if(world == null)
            throw new NullPointerException("The provided world cannot be found on the server to parse the location. Is the world loaded?");
        if(parseExact)
            return new Location(world,Double.parseDouble(locationArgs[1]),Double.parseDouble(locationArgs[2]),Double.parseDouble(locationArgs[3]),Long.parseLong(locationArgs[4]),Long.parseLong(locationArgs[5]));
        else return new Location(world,Double.parseDouble(locationArgs[1]),Double.parseDouble(locationArgs[2]),Double.parseDouble(locationArgs[3]));
    }

    /**
     * deserialize a {@link Location} that has been serialized using {@link LocationUtils#toString(Location)} or its corresponding methods.
     * NOTE: The deserialized Location will be an exact location with corresponding pitch and yaw
     * @param locationString The location of the string that needs to be deserialized
     * @throws IllegalArgumentException if the provided locationString is empty or null
     * @throws NullPointerException if the world cannot be located while deserializing
     * @return Location a deserialized location
     * @see Location
     */
    public Location parseLocation(@NotNull String locationString){
        return parseLocation(locationString,true);
    }

    /**
     * Returns an optional parameter if the world with provided name exist
     * @param name The name of the world.
     * @return An optional world if it exists.
     * @see Optional
     */
    public Optional<World> getIfWorldExist(@NotNull String name){
        return Optional.ofNullable(plugin.getServer().getWorld(name));
    }

    /**
     * Checks whether 2 of the given locations are similar.
     * This can be used to compare 2 given location without considering pitch and yaw.
     * For eg: Comparing a location of Block.
     * @param locationA The first location that needs to be compared
     * @param locationB The Second location that needs to be compared
     * @return boolean  Whether the two of the locations are similar
     */
    public boolean isSimilar(@NotNull Location locationA, @NotNull Location locationB){
        return locationA.getWorld().getName().equals(locationB.getWorld().getName()) && locationA.getBlockX() == locationB.getBlockX() && locationA.getBlockY() == locationB.getBlockY() && locationA.getBlockZ() == locationB.getBlockZ();
    }

    /**
     * Checks whether 2 of the given locations are exact to each other.
     * This can be used to compare 2 given location considering pitch and yaw.
     * NOTE: This takes both the yaw and pitch into consideration when comparing
     * @param locationA The first location that needs to be compared
     * @param locationB The Second location that needs to be compared
     * @return boolean  Whether the two of the locations are similar
     */
    public boolean isExact(@NotNull Location locationA, @NotNull Location locationB){
        return locationA.getWorld().getName().equals(locationB.getWorld().getName()) && locationA.getBlockX() == locationB.getBlockX() && locationA.getBlockY() == locationB.getBlockY() && locationA.getBlockZ() == locationB.getBlockZ() && locationA.getPitch() == locationB.getPitch() && locationA.getYaw() == locationB.getYaw();
    }

    /**
     * Gets the material of a given location
     * @param location of which the material needs to be known
     * @return Material of the given block
     */
    public Material getMaterialAt(@NotNull Location location){
        return location.getBlock().getType();
    }

    /**
     * Matches a material at an asked location
     * @param location of the material to be compared
     * @param material which needs to be matched
     * @return boolean whether both of them are equal
     */
    public boolean isMaterialSimilar(@NotNull Location location,Material material){
        return location.getBlock().getType() == material;
    }

    /**
     * Checks whether the given block at the location provided is a type of {@link Material#AIR}
     * @param location of which needs to be checked
     * @return boolean whether the given material is air or not
     */
    public boolean isAir(@NotNull Location location){
        return this.isMaterialSimilar(location,Material.AIR);
    }

    /**
     * Checks whether the given block at the location provided is a liquid
     * @param location which needs to be checked
     * @return boolean whether the given location is a liquid
     */
    public boolean isLiquid(@NotNull Location location){
        return location.getBlock().isLiquid();
    }

    /**
     * Get blocks in a square region that satisfies a given condition
     * @param center of the Location. The Blocks will be collected from this point upto its radius
     * @param radius of the square that needed to be checked
     * @param predicate conditions that needed to be checked
     * @return {@link Iterator<Block>} gets a block Iterator.
     */
    public Iterator<Block> getBlocksInRadiusSquare(final Location center, final int radius, final Predicate<Block> predicate) {
        final List<Block> blocks = new ArrayList<>();
        for (int x = center.getBlockX() - radius; x <= center.getBlockX() + radius; x++) {
            for (int y = center.getBlockY() - radius; y <= center.getBlockY() + radius; y++) {
                for (int z = center.getBlockZ() - radius; z <= center.getBlockZ() + radius; z++) {
                    final Block block = Objects.requireNonNull(center.getWorld()).getBlockAt(x, y, z);
                    if (predicate.test(block)) {
                        blocks.add(block);
                    }
                }
            }
        }
        return blocks.iterator();
    }

    /**
     * Get all blocks in a square region.
     * @param center of the Location. The Blocks will be collected from this point upto its radius
     * @param radius of the square that needed to be checked
     * @return {@link Iterator<Block>} gets a block Iterator.
     */
    public Iterator<Block> getBlocksInRadiusSquare(final Location center, final int radius) {
        final List<Block> blocks = new ArrayList<>();
        for (int x = center.getBlockX() - radius; x <= center.getBlockX() + radius; x++) {
            for (int y = center.getBlockY() - radius; y <= center.getBlockY() + radius; y++) {
                for (int z = center.getBlockZ() - radius; z <= center.getBlockZ() + radius; z++) {
                    final Block block = Objects.requireNonNull(center.getWorld()).getBlockAt(x, y, z);
                        blocks.add(block);

                }
            }
        }
        return blocks.iterator();
    }

    /**
     * Get blocks in a circle region that satisfies a given condition
     * @param center of the Location. The Blocks will be collected from this point upto its radius
     * @param radius of the circle that needed to be checked
     * @param predicate conditions that needed to be checked
     * @return {@link Iterator<Block>} gets a block Iterator.
     */
    public Iterator<Block> getBlocksInRadiusCircle(final Location center, final int radius, final Predicate<Block> predicate) {
        final List<Block> blocks = new ArrayList<>();
        final World world = center.getWorld();
        for (int x = center.getBlockX() - radius; x <= center.getBlockX() + radius; x++) {
            for (int y = center.getBlockY() - radius; y <= center.getBlockY() + radius; y++) {
                for (int z = center.getBlockZ() - radius; z <= center.getBlockZ() + radius; z++) {
                    final Location location = new Location(world, x, y, z);
                    final double distanceSquared = location.distanceSquared(center);
                    if (distanceSquared <= radius * radius) {
                        final Block block = location.getBlock();
                        if (predicate.test(block)) {
                            blocks.add(block);
                        }
                    }
                }
            }
        }
        return blocks.iterator();
    }

    /**
     * Get all blocks in a circle region.
     * @param center of the Location. The Blocks will be collected from this point upto its radius
     * @param radius of the circle that needed to be checked
     * @return {@link Iterator<Block>} gets a block Iterator.
     */
    public Iterator<Block> getBlocksInRadiusCircle(final Location center, final int radius) {
        final List<Block> blocks = new ArrayList<>();
        final World world = center.getWorld();
        for (int x = center.getBlockX() - radius; x <= center.getBlockX() + radius; x++) {
            for (int y = center.getBlockY() - radius; y <= center.getBlockY() + radius; y++) {
                for (int z = center.getBlockZ() - radius; z <= center.getBlockZ() + radius; z++) {
                    final Location location = new Location(world, x, y, z);
                    final double distanceSquared = location.distanceSquared(center);
                    if (distanceSquared <= radius * radius) {
                        final Block block = location.getBlock();
                            blocks.add(block);

                    }
                }
            }
        }
        return blocks.iterator();
    }





}
