package trizion.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Mysql {
	private static String host = "localhost";
	private static String port = "3306";
	private static String database = "serverdb";
	private static String username = "root";
	private static String password = "mysqlpwd123";
	public static Connection con;
	
	public static boolean isConnected() {
		if(con == null) return false;
		else return true;
	}	
	
	public static void connect() {
		if(!isConnected()) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
				System.out.println("[MySQL] connection successfully established!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void disconnect() {
		if(isConnected()) {
			try {
				con.close();
				System.out.println("[MySQL] connection successfully closed!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
//-----------------END OF SETUP----------------------//
	
	public static boolean playerOnTable(UUID uuid, String table) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
			ps.setString(1, uuid.toString());
			ResultSet r = ps.executeQuery();
			//player found
			if(r.next()) return true;	
		}catch(SQLException e ){
			e.printStackTrace();
		}
		return false;	
	}
	
	public static void newPlayer(final UUID uuid, Player player, String table) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
			ps.setString(1, uuid.toString());
			ResultSet r = ps.executeQuery();
			r.next();
			if(playerOnTable(uuid, table) != true) {
				PreparedStatement insertion = con.prepareStatement("INSERT INTO " + table + " (UUID,NAME) VALUES (?,?)");
				insertion.setString(1, uuid.toString());
				insertion.setString(2, player.getName());
				insertion.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateInt(UUID uuid, String valueTable, String valueName, int value) {
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE " + valueTable + " SET " + valueName + "=? WHERE UUID=?");
			ps.setInt(1, value);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static void updateString(UUID uuid, String valueTable, String valueName, String string) {
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE " + valueTable + " SET " + valueName + "=? WHERE UUID=?");
			ps.setString(1, string);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static int getInt(UUID uuid, String valueTable, String valueName) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM " + valueTable + " WHERE UUID=?");
			ps.setString(1, uuid.toString());
			ResultSet r = ps.executeQuery();
			if(r.next()) return r.getInt(valueName);
			else return 0;
		}catch(SQLException e ){
			e.printStackTrace();
		}	
		return 0;	
	}
	
	public static String getString(UUID uuid, String valueTable, String valueName) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM " + valueTable + " WHERE UUID=?");
			ps.setString(1, uuid.toString());
			ResultSet r = ps.executeQuery();
			if(r.next()) return r.getString(valueName);
			else return null;
		}catch(SQLException e ){
			e.printStackTrace();
		}	
		return null;	
	}
	public static String getString(String playerName, String valueTable, String valueName) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM " + valueTable + " WHERE NAME=?");
			ps.setString(1, playerName);
			ResultSet r = ps.executeQuery();
			if(r.next()) return r.getString(valueName);
			else return null;
		}catch(SQLException e ){
			e.printStackTrace();
		}	
		return null;	
	}
}