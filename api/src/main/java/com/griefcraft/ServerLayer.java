/*
 * Copyright 2011 Tyler Blair. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */

package com.griefcraft;

import com.griefcraft.player.Player;
import com.griefcraft.sql.JDBCDatabase;
import com.griefcraft.world.World;

import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;

public abstract class ServerLayer {

    /**
     * A map of all of the currently loaded players
     */
    private final Map<String, Player> players = new HashMap<String, Player>();

    /**
     * A map of all of the currently known worlds
     */
    private final Map<String, World> worlds = new HashMap<String, World>();

    /**
     * Load a player directly from the server without using any caches
     *
     * @param playerName
     */
    protected abstract Player internalGetPlayer(String playerName);

    /**
     * Load a world directly from the server without using any caches
     *
     * @param worldName
     */
    protected abstract World internalGetWorld(String worldName);

    /**
     * Get the default world
     *
     * @return
     */
    public abstract World getDefaultWorld();

    /**
     * Get a player from the server
     *
     * @param playerName
     * @return
     */
    public Player getPlayer(String playerName) {
        if (players.containsKey(playerName)) {
            return players.get(playerName);
        }

        Player player = internalGetPlayer(playerName);

        if (player != null) {
            players.put(playerName, player);
        }

        return player;
    }

    /**
     * Get a world from the server
     *
     * @param worldName
     * @return
     */
    public World getWorld(String worldName) {
        if (worlds.containsKey(worldName)) {
            return worlds.get(worldName);
        }

        World world = internalGetWorld(worldName);

        if (world != null) {
            worlds.put(worldName, world);
        }

        return world;
    }

    /**
     * Override a jdbc driver. If this returns null, the default method of resolving the driver is done
     *
     * @param driver
     * @return
     */
    public Driver overrideJDBCDriver(JDBCDatabase.Driver driver) {
        return null;
    }

}