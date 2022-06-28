package net.ashwork.mc.multiplatform.platform.core.manager;

import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.core.property.BlockProperties;
import net.ashwork.mc.multiplatform.platform.core.property.FoodPropertiesBuilder;
import net.ashwork.mc.multiplatform.platform.core.util.ObjectReference;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Function;

/**
 * A manager for creating property data.
 */
public interface PropertyPlatformManager {

    /**
     * Gets the current platform instance.
     *
     * @return the platform instance
     * @throws NullPointerException if the platform has not been initialized
     */
    static PropertyPlatformManager get() {
        return ModLoaderPlatform.get().data();
    }

    /**
     * Creates a builder for food properties.
     *
     * @return a builder for food properties
     */
    FoodPropertiesBuilder food();

    /**
     * Creates the properties for a block. Assumes all states of the block has
     * the same color pulled from the material.
     *
     * @param material the material the block is made out of
     */
    default BlockProperties block(Material material) {
        return this.block(material, material.getColor());
    }

    /**
     * Creates the properties for a block. Assumes all states of the block has
     * the same color pulled from a dye.
     *
     * @param material the material the block is made out of
     * @param color the color of the block when dyed
     * @return the properties of a block
     */
    default BlockProperties block(Material material, DyeColor color) {
        return this.block(material, color.getMaterialColor());
    }

    /**
     * Creates the properties for a block. Assumes all states of the block has
     * the same color.
     *
     * @param material the material the block is made out of
     * @param color the color of a block
     * @return the properties of a block
     */
    default BlockProperties block(Material material, MaterialColor color) {
        return this.block(material, state -> color);
    }

    /**
     * Creates the properties for a block.
     *
     * @param material the material the block is made out of
     * @param color a function that converts a state block to a color
     * @return the properties of a block
     */
    BlockProperties block(Material material, Function<BlockState, MaterialColor> color);

    /**
     * Copies the properties from another block.
     *
     * @param block the block to copy properties from
     * @return the properties of a block
     */
    BlockProperties block(ObjectReference<Block> block);
}
