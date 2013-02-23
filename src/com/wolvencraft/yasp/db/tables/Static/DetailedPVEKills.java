package com.wolvencraft.yasp.db.tables.Static;

public enum DetailedPVEKills implements _StaticTable {

	TableName("detailed_pve_kills"),
	
	EntryId("detailed_pve_id"),
	PlayerID("player_id"),
	CreatureId("creature_id"),
	MaterialId("material_id"),
	PlayerKilled("player_killed"),
	World("world"),
	XCoord("x"),
	YCoord("y"),
	ZCoord("z"),
	Timestamp("time");
	
	DetailedPVEKills (String columnName) {
		this.columnName = columnName;
	}
	
	private String columnName;
	
	@Override
	public String toString() { return columnName; }
}