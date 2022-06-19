/*
 * Copyright (c) ChampionAsh5357
 * SPDX-License-Identifier: MIT
 */

package net.ashwork.mc.multiplatform.platform.forge.client;

import net.ashwork.mc.multiplatform.client.ClientModInstance;
import net.ashwork.mc.multiplatform.platform.core.client.ClientModLoaderPlatform;

/**
 * The client mod instance for the Forge Mod Loader.
 */
public final class ForgeClientModInstance {

    /**
     * Default constructor.
     */
    public ForgeClientModInstance() {
        // Set platform
        var platform = new ForgeClientLoaderPlatform();
        ClientModLoaderPlatform.Reference.set(platform);

        // Initialize instances
        ClientModInstance.create(platform);
    }
}
