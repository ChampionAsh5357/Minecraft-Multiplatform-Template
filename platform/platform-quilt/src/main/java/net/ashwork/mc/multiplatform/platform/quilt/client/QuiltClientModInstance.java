/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.quilt.client;

import net.ashwork.mc.multiplatform.client.ClientModInstance;
import net.ashwork.mc.multiplatform.platform.core.client.ClientModLoaderPlatform;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

/**
 * The client mod instance for the Quilt Mod Loader.
 */
public final class QuiltClientModInstance implements ClientModInitializer {

    @Override
    public void onInitializeClient(final ModContainer mod) {
        // Set platform
        var platform = new QuiltClientLoaderPlatform();
        ClientModLoaderPlatform.Reference.set(platform);

        // Initialize instances
        ClientModInstance.create(platform);
    }
}
