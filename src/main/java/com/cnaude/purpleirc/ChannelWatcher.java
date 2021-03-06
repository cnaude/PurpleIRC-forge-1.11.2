/*
 * Copyright (C) 2014 cnaude
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.cnaude.purpleirc;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Chris Naude This thread checks each for users and updates the
 * internal lists.
 */
public class ChannelWatcher {

    private final PurpleIRC plugin;
    Timer timer = new Timer();

    /**
     *
     * @param plugin
     */
    public ChannelWatcher(final PurpleIRC plugin) {
        this.plugin = plugin;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (PurpleBot ircBot : plugin.ircBots.values()) {
                    ircBot.updateNickList();
                }
            }
        }, plugin.ircChannelCheckInterval, plugin.ircChannelCheckInterval);
    }

    /**
     *
     */
    public void cancel() {
        timer.cancel();
    }
}
