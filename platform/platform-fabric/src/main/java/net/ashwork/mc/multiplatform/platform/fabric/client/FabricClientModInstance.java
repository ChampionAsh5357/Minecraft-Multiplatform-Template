/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.fabric.client;

import net.ashwork.mc.multiplatform.client.ClientModInstance;
import net.ashwork.mc.multiplatform.platform.core.client.ClientModLoaderPlatform;
import net.fabricmc.api.ClientModInitializer;

/**
 * The client mod instance for the Fabric Mod Loader.
 */
public final class FabricClientModInstance implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Set platform
        var platform = new FabricClientLoaderPlatform();
        ClientModLoaderPlatform.Reference.set(platform);

        // Initialize instances
        ClientModInstance.create(platform);
    }
}
