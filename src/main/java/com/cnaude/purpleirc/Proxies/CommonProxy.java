package com.cnaude.purpleirc.Proxies;

import com.cnaude.purpleirc.CommandHandlers;
import com.cnaude.purpleirc.IRCCommand;
import com.cnaude.purpleirc.PurpleBot;
import com.cnaude.purpleirc.PurpleIRC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 *
 * @author cnaude
 */
public abstract class CommonProxy {
    
    CommandHandlers commandHandlers;
    PurpleIRC plugin;
        
    public abstract void init(PurpleIRC plugin);
    
    public abstract void broadcastToGame(final String message, final String permission);

    public abstract EntityPlayer getPlayerExact(String name);

    public abstract EntityPlayer getPlayer(String name);
    
    public abstract String tokenizedTopic(String topic);
    
    public abstract String getServerMotd();
    
    public abstract String getMCPlayers(PurpleBot ircBot, String channelName);
    
    public abstract void executeCommand(IRCCommand ircCommand);
    
    public abstract World getEntityWorld();
    
    public abstract CommandHandlers getCommandHandlers();
    
}
