package com.dealchecker.model;

public enum BoardBasis {
	ANY("ANY","Any"),
	BED_AND_BREAKFAST("BB","Bed & Breakfast"),
	FULL_BOARD("FB", "Full Board"),
	HALF_BOARD("HB", "Half Board"),
	SELF_CATERING("SC", "Self Catering"),
	ALL_INCLUSIVE("AI", "All Inclusive"),
	ROOM_ONLY("RO", "Room Only"),
	UNKNOWN("UNKNOWN", "Unknown Board Type");
	
	private final String code;
	private final String displayName;
	
	private BoardBasis(String code, String displayName) {
		this.code = code;
		this.displayName = displayName;
	}

	public String getCode() {
		return code;
	}

	public String getDisplayName() {
		return displayName;
	}
}
