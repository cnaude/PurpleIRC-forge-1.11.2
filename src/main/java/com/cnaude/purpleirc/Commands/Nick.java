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
package com.cnaude.purpleirc.Commands;

import com.cnaude.purpleirc.PurpleIRC;

import com.cnaude.purpleirc.CommandSender;
import net.minecraft.util.text.TextFormatting;

/**
 *
 * @author cnaude
 */
public class Nick implements IRCCommandInterface {

    private final PurpleIRC plugin;
    private final String usage = "[bot] [nick]";
    private final String desc = "Change bot's IRC nick.";
    private final String name = "nick";
    private final String fullUsage = TextFormatting.WHITE + "Usage: " + TextFormatting.GOLD + "/irc " + name + " " + usage;

    /**
     *
     * @param plugin
     */
    public Nick(PurpleIRC plugin) {
        this.plugin = plugin;
    }

    /**
     *
     * @param sender
     * @param args
     */
    @Override
    public void dispatch(CommandSender sender, String[] args) {
        if (args.length == 3) {
            String bot = plugin.botify(args[1]);
            String nick = args[2];
            if (plugin.ircBots.containsKey(bot)) {
                plugin.ircBots.get(bot).asyncChangeNick(sender, nick);
            } else {
                sender.sendMessage(plugin.invalidBotName.replace("%BOT%", bot));
            }
        } else {
            sender.sendMessage(fullUsage);
        }
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String desc() {
        return desc;
    }

    @Override
    public String usage() {
        return usage;
    }
}