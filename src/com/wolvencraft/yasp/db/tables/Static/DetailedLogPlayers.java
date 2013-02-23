package com.wolvencraft.yasp.db.tables.Static;

public enum DetailedLogPlayers implements _StaticTable {
	
	TableName("detailed_players_log"),
	
	EntryId("logID"),
	PlayerId("player_id"),
	LoggedIn("logged_in"),
	LoggedOut("logged_out"),
	World("world"),
	XCoord("x"),
	YCoord("y"),
	ZCoord("z");
	
	DetailedLogPlayers(String columnName) {
		this.columnName = columnName;
	}
	
	private String columnName;
	
	@Override
	public String toString() { return columnName; }

}