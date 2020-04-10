package trizion.commands;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import trizion.mysql.Mysql;

public class MutePlayerCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			//put in the sufficient permissions below
			if(player.hasPermission("mute")) {
				if(args.length == 2) {
					String playerName = args[0];
					String uuidString = Mysql.getString(playerName, "uuids", "UUID");
					if(uuidString == null) {
						player.sendMessage("Player " + args[0] + " can not be found! Check spelling!");
						return false;
					}
					UUID uuid = UUID.fromString(uuidString);
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(new Date());
					String time = args[1];
					char timeUnit = time.charAt(time.length() - 1);
					
					switch(timeUnit){
					case 's':
						int sAmount = Integer.parseInt(time.replace("s", ""));
						calendar.add(Calendar.SECOND, sAmount);
					break;
					case 'm':
						int mAmount = Integer.parseInt(time.replace("m", ""));
						calendar.add(Calendar.MINUTE, mAmount);
					break;
					case 'h':
						int hAmount = Integer.parseInt(time.replace("h", ""));
						calendar.add(Calendar.HOUR, hAmount);
					break;
					case 'd':
						int dAmount = Integer.parseInt(time.replace("d", ""));
						calendar.add(Calendar.HOUR, dAmount * 24);
					break;
					default:
						player.sendMessage("§cInvalid duration! use [int][s/m/h/d]!");
						return false;
					
					}
					Date date = calendar.getTime();
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
					String dateString = formatter.format(date);
					
					if(!Mysql.playerOnTable(uuid, "mutes")) {
						Mysql.newPlayer(uuid, player, "mutes");
					}				
					Mysql.updateString(uuid, "mutes", "UNTIL", dateString);
					Bukkit.broadcastMessage("§c" + args[0] + " has been muted by " + player.getName());
				}else {
					player.sendMessage("§cPlease use /mute [player] [duration]!");

				}
			}else {
				player.sendMessage("§cYou are not permitted to do that!");
			}
		}
		
		return false;
	}

}
