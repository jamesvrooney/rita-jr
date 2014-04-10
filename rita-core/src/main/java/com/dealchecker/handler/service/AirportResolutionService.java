package com.dealchecker.handler.service;

import com.dealchecker.model.Location;

public interface AirportResolutionService {
	
	Location getByCode(String airportCode) throws MissingLocationException;
	
	void refresh();
}
