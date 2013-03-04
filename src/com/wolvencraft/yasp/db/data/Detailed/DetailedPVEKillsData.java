package com.wolvencraft.yasp.db.data.detailed;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.wolvencraft.yasp.db.QueryUtils;
import com.wolvencraft.yasp.db.tables.detailed.DetailedPVEKills;
import com.wolvencraft.yasp.util.Util;

public class DetailedPVEKillsData implements _DetailedData {
	
	public DetailedPVEKillsData(Location location, EntityType creatureType, ItemStack weapon, boolean playerKilled) {
		this.creatureType = creatureType;
		this.weapon = weapon;
		this.location = location;
		this.playerKilled = playerKilled;
		this.timestamp = Util.getTimestamp();
	}
	
	private EntityType creatureType;
	private ItemStack weapon;
	private Location location;
	private boolean playerKilled;
	private long timestamp;
	
	@Override
	public boolean pushData(int playerId) {
		return QueryUtils.insert(
			DetailedPVEKills.TableName.toString(),
			getValues(playerId)
		);
	}

	@Override
	public Map<String, Object> getValues(int playerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(DetailedPVEKills.PlayerID.toString(), playerId);
		map.put(DetailedPVEKills.CreatureId.toString(), creatureType.getTypeId());
		if(playerKilled) {
			map.put(DetailedPVEKills.PlayerKilled.toString(), 1);
			map.put(DetailedPVEKills.MaterialId.toString(), -1);
			map.put(DetailedPVEKills.MaterialData.toString(), 0);
		} else {
			map.put(DetailedPVEKills.PlayerKilled.toString(), 0);
			map.put(DetailedPVEKills.MaterialId.toString(), weapon.getTypeId());
			map.put(DetailedPVEKills.MaterialData.toString(), weapon.getData().getData());
		}
		map.put(DetailedPVEKills.World.toString(), location.getWorld().getName());
		map.put(DetailedPVEKills.XCoord.toString(), location.getBlockX());
		map.put(DetailedPVEKills.YCoord.toString(), location.getBlockY());
		map.put(DetailedPVEKills.ZCoord.toString(), location.getBlockZ());
		map.put(DetailedPVEKills.Timestamp.toString(), timestamp);
		return map;
	}

}
