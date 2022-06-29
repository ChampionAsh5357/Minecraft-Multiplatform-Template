/*
 * Written by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multiplatform.platform.core.dist;

import net.ashwork.mc.multiplatform.platform.core.ModLoaderPlatform;

import java.util.function.Supplier;

/**
 * A distribution of the minecraft game. There are two common distributions, and though
 * much code is common between them, there are some specific pieces that are only present
 * in one or the other.
 * <ul>
 *     <li>{@link #CLIENT} is the <em>client</em> distribution, it contains
 *     the game client, and has code to render a viewport into a game world.</li>
 *     <li>{@link #DEDICATED_SERVER} is the <em>dedicated server</em> distribution,
 *     it contains a server, which can simulate the world and communicates via network.</li>
 * </ul>
 */
public enum Dist {

    /**
     * The client distribution. This is the game client players can purchase and play.
     * It contains the graphics and other rendering to present a viewport into the game world.
     */
    CLIENT,

    /**
     * The dedicated server distribution. This is the server only distribution available for
     * download. It simulates the world, and can be communicated with via a network.
     * It contains no visual elements of the game whatsoever.
     */
    DEDICATED_SERVER;

    /**
     * Runs the supplied task if the mod is currently loaded on the invoking
     * distribution.
     *
     * @param task the code to run
     */
    public void runWhenOn(Supplier<Runnable> task) {
        if (ModLoaderPlatform.get().dist() == this) {
            task.get().run();
        }
    }
}
