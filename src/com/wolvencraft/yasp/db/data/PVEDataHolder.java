package com.wolvencraft.yasp.db.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.wolvencraft.yasp.db.data.normal.TotalPVEEntry;

public class PVEDataHolder {
	
	private List<TotalPVEEntry> data;
	
	public PVEDataHolder() {
		data = new ArrayList<TotalPVEEntry>();
	}
	
	public void add(TotalPVEEntry newData) {
		data.add(newData);
	}
	
	public List<TotalPVEEntry> get() {
		List<TotalPVEEntry> temp = new ArrayList<TotalPVEEntry>();
		for(TotalPVEEntry value : data) temp.add(value);
		return temp;
	}
	
	public TotalPVEEntry get(EntityType creatureType, ItemStack weapon) {
		for(TotalPVEEntry entry : data) {
			if(entry.equals(creatureType, weapon)) return entry;
		}
		TotalPVEEntry entry = new TotalPVEEntry(creatureType, weapon);
		data.add(entry);
		return entry;
	}
	
	public void clear() {
		data.clear();
	}
	
	public void sync(int killerId) {
		for(TotalPVEEntry entry : data) entry.pushData(killerId);
	}
	
}
