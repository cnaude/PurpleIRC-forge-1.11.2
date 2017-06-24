package com.cnaude.purpleirc.Proxies;

import com.cnaude.purpleirc.CommandHandlers;
import com.cnaude.purpleirc.IRCCommand;
import com.cnaude.purpleirc.PurpleBot;
import com.cnaude.purpleirc.PurpleIRC;
import com.google.common.base.Joiner;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.command.CommandHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.server.FMLServerHandler;

/**
 *
 * @author cnaude
 */
public class ServerProxy extends CommonProxy {

    final FMLServerHandler fmlServerInstance = FMLServerHandler.instance();
    
    @Override
    public void init(PurpleIRC plugin) {
        commandHandlers = new CommandHandlers(plugin, this);
        registerCommands((CommandHandler) fmlServerInstance.getServer().commandManager, commandHandlers);
        plugin.startBots();
    }

    @Override
    public void broadcastToGame(final String message, final String permission) {
        for (EntityPlayerMP ep : fmlServerInstance.getServer().getPlayerList().getPlayers()) {
            ep.sendMessage(new TextComponentTranslation(message));
        }
    }

    @Override
    public EntityPlayer getPlayerExact(String name) {
        return (EntityPlayer) fmlServerInstance.getServer().getEntityWorld().getPlayerEntityByName(name);
    }

    @Override
    public EntityPlayerMP getPlayer(String name) {
        for (EntityPlayerMP ep : fmlServerInstance.getServer().getPlayerList().getPlayers()) {
            if (ep.getDisplayNameString().equalsIgnoreCase(name)) {
                return ep;
            }
        }
        return null;
    }

    @Override
    public String tokenizedTopic(String topic) {
        return plugin.colorConverter
                .gameColorsToIrc(topic.replace("%MOTD%", fmlServerInstance.getServer().getMOTD()));
    }

    @Override
    public String getServerMotd() {
        return "MOTD: " + fmlServerInstance.getServer().getMOTD();
    }

    /**
     *
     * @param ircBot
     * @param channelName
     * @return
     */
    @Override
    public String getMCPlayers(PurpleBot ircBot, String channelName) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            return "";
        }
        Map<String, String> playerList = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (EntityPlayerMP ep : fmlServerInstance.getServer().getPlayerList().getPlayers()) {
            String pName = plugin.tokenizer.playerTokenizer(ep, plugin.listPlayer);
            playerList.put(ep.getName(), pName);
        }

        String pList;
        if (!plugin.listSortByName) {
            // sort as before
            ArrayList<String> tmp = new ArrayList<>(playerList.values());
            Collections.sort(tmp, Collator.getInstance());
            pList = Joiner.on(plugin.listSeparator).join(tmp);
        } else {
            // sort without nick prefixes
            pList = Joiner.on(plugin.listSeparator).join(playerList.values());
        }

        String msg = plugin.listFormat
                .replace("%COUNT%", Integer.toString(playerList.size()))
                .replace("%MAX%", Integer.toString(fmlServerInstance.getServer().getMaxPlayers()))
                .replace("%PLAYERS%", pList);
        plugin.logDebug("L: " + msg);
        return plugin.colorConverter.gameColorsToIrc(msg);
    }

    @Override
    public void executeCommand(IRCCommand ircCommand) {
        fmlServerInstance.getServer().getCommandManager().executeCommand(ircCommand.getIRCCommandSender(), ircCommand.getGameCommand());
    }

    @Override
    public World getEntityWorld() {
        return fmlServerInstance.getServer().getEntityWorld();
    }

    public void registerCommands(CommandHandler handler, CommandHandlers handlers) {
        handler.registerCommand(handlers);
    }
    
    
    @Override
    public CommandHandlers getCommandHandlers() {
        return commandHandlers;
    }

}
