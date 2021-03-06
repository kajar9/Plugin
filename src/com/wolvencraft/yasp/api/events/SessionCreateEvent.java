/*
 * SessionCreateEvent.java
 * 
 * Statistics
 * Copyright (C) 2013 bitWolfy <http://www.wolvencraft.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package com.wolvencraft.yasp.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.wolvencraft.yasp.session.DataSession;

/**
 * Called when a new player session is being created
 * @author bitWolfy
 *
 */
public class SessionCreateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String playerName;
    
    public SessionCreateEvent(String playerName) {
        this.playerName = playerName;
    }
    
    /**
     * Returns the player name
     * @return Player name
     */
    public String getName() {
        return playerName;
    }
    
    /**
     * Returns the player's statistics
     * @return Player's totals
     */
    public DataSession getSession() {
        return new DataSession(playerName);
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
