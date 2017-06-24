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

import com.cnaude.purpleirc.Proxies.CommonProxy;
import static com.cnaude.purpleirc.PurpleIRC.proxy;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

/**
 *
 * @author Chris Naude We have to implement our own ICommandSender so that we can
 * receive output from the command dispatcher.
 */
public class IRCCommandSender implements ICommandSender {

    private final PurpleIRC plugin;
    private final PurpleBot ircBot;
    private final CommonProxy proxy;
    
    private final String target;
    private final boolean ctcpResponse;
    private final String name;

    private void addMessageToQueue(String message) {
        plugin.logDebug("addMessageToQueue: " + message);
        ircBot.messageQueue.add(new IRCMessage(target,
                plugin.colorConverter.gameColorsToIrc(message), ctcpResponse));
    }

    /**
     *
     * @param ircBot
     * @param target
     * @param proxy
     * @param plugin
     * @param ctcpResponse
     * @param name
     */
    public IRCCommandSender(PurpleIRC plugin, PurpleBot ircBot, CommonProxy proxy, String target, boolean ctcpResponse, String name) {
        super();
        this.plugin = plugin;
        this.proxy = proxy;
        this.ircBot = ircBot;
        this.target = target;        
        this.ctcpResponse = ctcpResponse;
        this.name = name;
    }

    @Override
    public World getEntityWorld() {
        return proxy.getEntityWorld();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Entity getCommandSenderEntity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean sendCommandFeedback() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCommandStat(CommandResultStats.Type type, int amount) {
        plugin.logDebug("setCommandStat: " + type + " " + amount);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MinecraftServer getServer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ITextComponent getDisplayName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BlockPos getPosition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vec3d getPositionVector() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendMessage(ITextComponent component) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean canUseCommand(int permLevel, String commandName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
