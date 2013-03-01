package com.wolvencraft.yasp.util;

import java.sql.Timestamp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Util {
 		
	/**
	 * Replaces the color codes with colors
	 * @param msg String to be parsed
	 * @return Parsed string
	 */
	public static String parseChatColors(String str) {
		if(str == null) return "";
		
		for(ChatColor color : ChatColor.values()) {
			str = str.replaceAll("&" + color.getChar(), color + "");
		}
		
		return str;
	}
	
	/**
	 * Returns the current time in a timestamp form
	 * @deprecated
	 * @return Current timestamp
	 */
	public static Timestamp getCurrentTime() {
		java.util.Date date= new java.util.Date();
		return new Timestamp(date.getTime());
	}
	
	/**
	 * Returns the current time in Unix timestamp form
	 * @return Current time
	 */
	public static long getTimestamp() {
		Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
		return timestamp.getTime() / 1000;
	}
	
	/**
	 * Checks if the player is exempt from the statistics
	 * @param player Player in question
	 * @return <b>true</b> if the player's stats should not be registered, <b>false</b> otherwise
	 */
	public static boolean isExempt(Player player) {
		return !(player.hasPermission("statistician.ignore") || player.hasPermission("stats.ignore"));
	}
	
	/**
	 * Checks if all players in the list are exempt from the statistics
	 * @param players Players to check
	 * @return <b>true</b> if the players' stats should not be registered, <b>false</b> otherwise
	 */
	public static boolean isExempt(Player... players) {
		for(Player player : players) {
			if(!(player.hasPermission("statistician.ignore") || player.hasPermission("stats.ignore"))) return false;
		}
		return true;
	}
}