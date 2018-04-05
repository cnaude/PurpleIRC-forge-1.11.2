package com.cnaude.purpleirc;

import com.cnaude.purpleirc.Proxies.CommonProxy;
import com.cnaude.purpleirc.Utilities.ChatColor;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import org.dynmap.permissions.PermissionsHandler;

/**
 *
 * @author cnaude
 */
public class CommandSender {

    ICommandSender sender;
    PurpleIRC plugin;
    CommonProxy proxy;

    /**
     *
     * @param sender
     * @param plugin
     * @param proxy
     */
    public CommandSender(ICommandSender sender, PurpleIRC plugin, CommonProxy proxy) {
        this.sender = sender;
        this.plugin = plugin;
        this.proxy = proxy;
    }

    public void sendMessage(String message) {
        if (sender instanceof EntityPlayer) {
            sender.sendMessage(new TextComponentTranslation(message));
        } else {
            sender.sendMessage(new TextComponentTranslation(ChatColor.stripColor(message)));
        }

    }

    public boolean hasPermission(String permission) {
        if (sender instanceof EntityPlayer && plugin.dynmapHook != null) {
            PermissionsHandler ph = PermissionsHandler.getHandler();
            if (ph != null) {
                return ph.hasPermission(sender.getCommandSenderEntity().getName(), permission);
            }
        }
        return true;
    }

    public EntityPlayer getPlayer() {
        return proxy.getPlayer(sender.getName());
    }

    public boolean isPlayer() {
        return sender instanceof EntityPlayerMP;
    }
}
