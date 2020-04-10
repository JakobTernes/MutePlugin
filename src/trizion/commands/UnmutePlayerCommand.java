package trizion.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import trizion.mysql.Mysql;

public class UnmutePlayerCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			//put in the sufficient permissions below
			if(player.hasPermission("unmute")) {
				if(args.length == 1) {
					String playerName = args[0];
					String uuidString = Mysql.getString(playerName, "uuids", "UUID");
					if(uuidString == null) {
						player.sendMessage("Player " + args[0] + " can not be found! Check spelling!");
						return false;
					}
					UUID uuid = UUID.fromString(uuidString);
					//redundancy
					Mysql.newPlayer(uuid, player, "mutes");
					Mysql.updateString(uuid, "mutes", "UNTIL", null);
					player.sendMessage(args[0] + " has been unmuted!");	
				
				}else {
					player.sendMessage("§cPlease use /unmute [player]");	
				}
			}
		}
		
		return false;
	}

}
