/*
 * Copyright (c) ChampionAsh5357
 * SPDX-License-Identifier: MIT
 */

package net.ashwork.mc.multiplatform.platform.fabric;

import net.ashwork.mc.multiplatform.ModInstance;
import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;
import net.fabricmc.api.ModInitializer;

/**
 * The mod instance for the Fabric Mod Loader.
 */
public final class FabricModInstance implements ModInitializer {

    @Override
    public void onInitialize() {
        // Set platform
        var platform = new FabricLoaderPlatform();
        ModLoaderPlatform.Reference.set(platform);

        // Initialize instances
        ModInstance.create(platform);
    }
}
