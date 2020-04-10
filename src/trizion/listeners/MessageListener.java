package trizion.listeners;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import trizion.mysql.Mysql;

public class MessageListener implements Listener{
	@EventHandler
	public void onChatMessage(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String dateString = Mysql.getString(player.getUniqueId(), "mutes", "UNTIL");
		if(dateString != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			try {
				Date date = formatter.parse(dateString);
				if(event.getMessage().charAt(0) != '/' && date.after(new Date())) {
					event.setCancelled(true);
					event.getPlayer().sendMessage("§cYou can not send messages due to being muted!");
				}
				return;
			} catch (ParseException e) {
				e.printStackTrace();
				return;
			}
		}
		return;

	}

}
