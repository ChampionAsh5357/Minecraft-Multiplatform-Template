/*
 * Copyright (c) ChampionAsh5357
 * SPDX-License-Identifier: MIT
 */

package net.ashwork.mc.multiplatform;

import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;

/**
 * The mod instance called whenever a platform is initialized.
 */
public final class ModInstance {

    /**
     * The identifier of the mod instance.
     */
    public static final String ID = "multiplatform";

    /**
     * Creates a mod instance for the given platform.
     *
     * @param platform the platform instance.
     */
    public static void create(final ModLoaderPlatform platform) {
        // Entrypoint, begin code here
    }
}
