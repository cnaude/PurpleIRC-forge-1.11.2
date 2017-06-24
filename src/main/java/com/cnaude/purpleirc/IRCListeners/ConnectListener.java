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
package com.cnaude.purpleirc.IRCListeners;

import com.cnaude.purpleirc.PurpleBot;
import com.cnaude.purpleirc.PurpleIRC;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;

/**
 *
 * @author cnaude
 */
public class ConnectListener extends ListenerAdapter {

    PurpleIRC plugin;
    PurpleBot ircBot;

    /**
     *
     * @param plugin
     * @param ircBot
     */
    public ConnectListener(PurpleIRC plugin, PurpleBot ircBot) {
        this.plugin = plugin;
        this.ircBot = ircBot;
    }

    /**
     *
     * @param event
     */
    @Override
    public void onConnect(ConnectEvent event) {
        plugin.logDebug("ON CONNECT 0");
        PircBotX bot = event.getBot();
        plugin.logDebug("ON CONNECT 1");
        if (bot.getUserBot().getNick().isEmpty()) {
            plugin.logDebug("ON CONNECT 2");
            plugin.logDebug("Connected but bot nick is blank!");
            plugin.logDebug("ON CONNECT 3");
        } else {
            plugin.logDebug("ON CONNECT 4");
            ircBot.broadcastIRCConnect(ircBot.botNick);
            plugin.logDebug("ON CONNECT 5");
            if (ircBot.sendRawMessageOnConnect) {
                plugin.logDebug("ON CONNECT 6");
                plugin.logDebug("Sending raw message to server");
                plugin.logDebug("ON CONNECT 7");
                ircBot.asyncRawlineNow(ircBot.rawMessage);
                plugin.logDebug("ON CONNECT 8");
            }
        }
        plugin.logDebug("ON CONNECT 9");
        ircBot.setConnected(true);
        plugin.logDebug("ON CONNECT 10");
        ircBot.autoJoinChannels();
        plugin.logDebug("ON CONNECT 11");
    }
}
