package com.bgsoftware.superiorskyblock.commands.admin;

import com.bgsoftware.superiorskyblock.Locale;
import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.bgsoftware.superiorskyblock.commands.ISuperiorCommand;
import com.bgsoftware.superiorskyblock.wrappers.player.SSuperiorPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CmdAdminSpawn implements ISuperiorCommand {

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("spawn");
    }

    @Override
    public String getPermission() {
        return "superior.admin.spawn";
    }

    @Override
    public String getUsage(java.util.Locale locale) {
        return "admin spawn [player-name]";
    }

    @Override
    public String getDescription(java.util.Locale locale) {
        return Locale.COMMAND_DESCRIPTION_ADMIN_SPAWN.getMessage(locale);
    }

    @Override
    public int getMinArgs() {
        return 2;
    }

    @Override
    public int getMaxArgs() {
        return 3;
    }

    @Override
    public boolean canBeExecutedByConsole() {
        return true;
    }

    @Override
    public void execute(SuperiorSkyblockPlugin plugin, CommandSender sender, String[] args) {
        SuperiorPlayer targetPlayer = null;

        if (!(sender instanceof Player) && args.length == 2) {
            sender.sendMessage(ChatColor.RED + "You must specify a player to teleport.");
            return;
        }

        else if(args.length == 3){
            targetPlayer = SSuperiorPlayer.of(args[2]);
            if(!targetPlayer.isOnline())
                targetPlayer = null;
        }

        else if(sender instanceof Player){
            targetPlayer = SSuperiorPlayer.of((Player) sender);
        }

        if(targetPlayer == null){
            Locale.INVALID_PLAYER.send(sender, args[2]);
            return;
        }

        targetPlayer.teleport(plugin.getGrid().getSpawnIsland());

        Locale.SPAWN_TELEPORT_SUCCESS.send(sender, targetPlayer.getName());
    }

    @Override
    public List<String> tabComplete(SuperiorSkyblockPlugin plugin, CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}