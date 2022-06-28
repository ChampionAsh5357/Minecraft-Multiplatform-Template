/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.forge.client;

import net.ashwork.mc.multiplatform.client.ClientModInstance;
import net.ashwork.mc.multiplatform.platform.core.client.ClientModLoaderPlatform;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * The client mod instance for the Forge Mod Loader.
 */
public final class ForgeClientModInstance {

    /**
     * Default constructor.
     *
     * @param modBus the event bus for mod initialization
     */
    public ForgeClientModInstance(IEventBus modBus) {
        // Set platform
        var platform = new ForgeClientLoaderPlatform(modBus);
        ClientModLoaderPlatform.Reference.set(platform);

        // Initialize instances
        ClientModInstance.create(platform);
    }
}
