package com.dealchecker.handler.service.impl

import com.dealchecker.handler.service.AirportResolutionService
import com.dealchecker.handler.service.MissingLocationException
import com.dealchecker.handler.service.RefreshAirportsException
import com.dealchecker.model.Location

final class InMemoryAiportResolutionServiceImpl implements AirportResolutionService {
	
	final Map<String, Location> airportMap
	 
	InMemoryAiportResolutionServiceImpl(Map<String, Location> airportMap) {
		this.airportMap = airportMap	
	} 
	
	@Override
	Location getByCode(String airportCode) throws MissingLocationException {
		
		Location airportLocation =  airportMap.get(airportCode)
		if(airportLocation == null) {
			throw new MissingLocationException("Can not find the airport $airportCode in the map")
		}
		return airportLocation
	}

	@Override
	void refresh() {
		throw new UnsupportedOperationException("InMemoryAiportResolutionService can NOT refresh!")
	}

}
