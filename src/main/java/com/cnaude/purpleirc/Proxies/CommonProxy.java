package com.cnaude.purpleirc.Proxies;

import com.cnaude.purpleirc.CommandHandlers;
import com.cnaude.purpleirc.GameListeners.GamePlayerChatListener;
import com.cnaude.purpleirc.GameListeners.GamePlayerDeathListener;
import com.cnaude.purpleirc.GameListeners.GamePlayerJoinListener;
import com.cnaude.purpleirc.GameListeners.GamePlayerPlayerAchievementAwardedListener;
import com.cnaude.purpleirc.GameListeners.GamePlayerQuitListener;
import com.cnaude.purpleirc.GameListeners.GameServerCommandListener;
import com.cnaude.purpleirc.IRCCommand;
import com.cnaude.purpleirc.PurpleBot;
import com.cnaude.purpleirc.PurpleIRC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 *
 * @author cnaude
 */
public abstract class CommonProxy {

    CommandHandlers commandHandlers;
    PurpleIRC plugin;

    public void preInit(FMLPreInitializationEvent event, PurpleIRC plugin) {
        this.plugin = plugin;
    }

    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new GamePlayerDeathListener(plugin));
        MinecraftForge.EVENT_BUS.register(new GameServerCommandListener(plugin));
        MinecraftForge.EVENT_BUS.register(new GamePlayerChatListener(plugin));

        MinecraftForge.EVENT_BUS.register(new GamePlayerJoinListener(plugin));
        MinecraftForge.EVENT_BUS.register(new GamePlayerQuitListener(plugin));
        MinecraftForge.EVENT_BUS.register(new GamePlayerPlayerAchievementAwardedListener(plugin));
    }

    public abstract void broadcastToGame(final String message, final String permission);

    public abstract EntityPlayer getPlayerExact(String name);

    public abstract EntityPlayer getPlayer(String name);

    public abstract String tokenizedTopic(String topic);

    public abstract String getServerMotd();

    public abstract String getMCPlayers(PurpleBot ircBot, String channelName);

    public abstract void executeCommand(IRCCommand ircCommand);

    public abstract World getEntityWorld();

    public abstract CommandHandlers getCommandHandlers();
    
    public abstract MinecraftServer getServer();

}
