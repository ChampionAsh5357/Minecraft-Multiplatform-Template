/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.quilt;

import net.ashwork.mc.multiplatform.ModInstance;
import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

/**
 * The mod instance for the Quilt Mod Loader.
 */
public final class QuiltModInstance implements ModInitializer {

    @Override
    public void onInitialize(final ModContainer mod) {
        // Set platform
        var platform = new QuiltLoaderPlatform(mod);
        ModLoaderPlatform.Reference.set(platform);

        // Initialize instances
        ModInstance.create(platform);
    }
}
