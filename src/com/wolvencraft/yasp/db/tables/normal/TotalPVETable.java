package com.wolvencraft.yasp.db.tables.normal;

import com.wolvencraft.yasp.db.tables.DBTable;

public enum TotalPVETable implements DBTable {
	
	TableName("total_pve"),
	TotalPVEId("total_pve_id"),
	PlayerId("player_id"),
	CreatureId("creature_id"),
	PlayerKilled("player_killed"),
	CreatureKilled("creature_killed");
	
	TotalPVETable(String columnName) {
		this.columnName = columnName;
	}
	
	private String columnName;
	
	@Override
	public String toString() { return columnName; }
}