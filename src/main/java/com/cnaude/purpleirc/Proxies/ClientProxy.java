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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 *
 * @author cnaude
 */
public class ClientProxy extends CommonProxy {

    private final FMLClientHandler fmlClientInstance = FMLClientHandler.instance();

    @Override
    public void init(PurpleIRC plugin) {
        commandHandlers = new CommandHandlers(plugin, this);
        ClientCommandHandler.instance.registerCommand(commandHandlers);
    }

    @Override
    public void broadcastToGame(String message, String permission) {
        fmlClientInstance.getClient().player.sendMessage(new TextComponentString(message));
    }

    @Override
    public EntityPlayer getPlayerExact(String name) {
        return fmlClientInstance.getWorldClient().getPlayerEntityByName(name);
    }

    @Override
    public EntityPlayer getPlayer(String name) {
        return fmlClientInstance.getWorldClient().getPlayerEntityByName(name);
    }

    @Override
    public String tokenizedTopic(String topic) {
        return plugin.colorConverter
                .gameColorsToIrc(topic.replace("%MOTD%", fmlClientInstance.getServer().getMOTD()));
    }

    @Override
    public String getServerMotd() {
        return "MOTD: " + fmlClientInstance.getServer().getMOTD();
    }

    @Override
    public String getMCPlayers(PurpleBot ircBot, String channelName) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            return "";
        }
        Map<String, String> playerList = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (EntityPlayerMP ep : fmlClientInstance.getServer().getPlayerList().getPlayers()) {
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
                .replace("%MAX%", Integer.toString(fmlClientInstance.getServer().getMaxPlayers()))
                .replace("%PLAYERS%", pList);
        plugin.logDebug("L: " + msg);
        return plugin.colorConverter.gameColorsToIrc(msg);
    }

    @Override
    public void executeCommand(IRCCommand ircCommand) {
        fmlClientInstance.getServer().getCommandManager().executeCommand(ircCommand.getIRCCommandSender(), ircCommand.getGameCommand());
    }

    @Override
    public World getEntityWorld() {
        return fmlClientInstance.getWorldClient();
    }

    @SubscribeEvent
    public void worldJoined(ClientConnectedToServerEvent event) {
        plugin.startBots();
    }

    @Override
    public CommandHandlers getCommandHandlers() {
        return commandHandlers;
    }

}
