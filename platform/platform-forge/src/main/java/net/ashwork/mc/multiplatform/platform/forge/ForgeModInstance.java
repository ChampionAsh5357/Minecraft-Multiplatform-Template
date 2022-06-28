/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.forge;

import net.ashwork.mc.multiplatform.ModInstance;
import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;
import net.ashwork.mc.multiplatform.platform.forge.client.ForgeClientModInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * The mod instance for the Forge Mod Loader.
 */
@Mod(ModInstance.ID)
public final class ForgeModInstance {

    /**
     * Default constructor.
     */
    public ForgeModInstance() {
        // Get event buses
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Set platform
        var platform = new ForgeLoaderPlatform(modBus);
        ModLoaderPlatform.Reference.set(platform);

        // Initialize instances
        ModInstance.create(platform);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> new ForgeClientModInstance(modBus));
    }
}
