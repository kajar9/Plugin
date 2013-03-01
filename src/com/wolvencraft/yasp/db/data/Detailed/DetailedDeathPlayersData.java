package com.wolvencraft.yasp.db.data.detailed;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.wolvencraft.yasp.db.QueryUtils;
import com.wolvencraft.yasp.db.tables.detailed.DetailedDeathPlayers;
import com.wolvencraft.yasp.util.Util;

public class DetailedDeathPlayersData implements _DetailedData {
	
	public DetailedDeathPlayersData(Player player, int playerId, DamageCause deathCause) {
		this.playerId = playerId;
		this.deathCause = deathCause.name();
		this.location = player.getLocation();
		this.timestamp = Util.getTimestamp();
	}
	
	private int playerId;
	private String deathCause;
	private Location location;
	private long timestamp;

	@Override
	public boolean pushData() {
		return QueryUtils.insert(
			DetailedDeathPlayers.TableName.toString(),
			getValues()
		);
	}

	@Override
	public Map<String, Object> getValues() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(DetailedDeathPlayers.PlayerId.toString(), playerId);
		map.put(DetailedDeathPlayers.Cause.toString(), deathCause);
		map.put(DetailedDeathPlayers.World.toString(), location.getWorld().getName());
		map.put(DetailedDeathPlayers.XCoord.toString(), location.getBlockX());
		map.put(DetailedDeathPlayers.YCoord.toString(), location.getBlockY());
		map.put(DetailedDeathPlayers.ZCoord.toString(), location.getBlockZ());
		map.put(DetailedDeathPlayers.Timestamp.toString(), timestamp);
		return map;
	}

}
