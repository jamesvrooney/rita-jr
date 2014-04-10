package com.dealchecker.handler.service.impl;

import java.util.concurrent.Callable;

import com.dealchecker.dao.LocationDao;
import com.dealchecker.handler.service.AirportResolutionService;
import com.dealchecker.handler.service.MissingLocationException;
import com.dealchecker.model.Location;
import com.google.common.cache.Cache;

public final class CachedAirportResolutionService implements AirportResolutionService {

	private final LocationDao locationDao;
	private final Cache<String, Location> cache;
	
	public CachedAirportResolutionService(LocationDao locationDao, Cache<String, Location> cache) {
		this.locationDao = locationDao;
		this.cache = cache;
	}

	@Override
	public Location getByCode(final String airportCode)	throws MissingLocationException {
		try {
			return cache.get(airportCode, new Callable<Location>() {
				@Override
				public Location call() throws Exception {
					return locationDao.getLocationByCode(airportCode);
				}
			});
		} catch(Exception e) {
			throw new MissingLocationException("error trying to fetch airport [code: " + airportCode + "]", e.getCause());
		}
	}

	@Override
	public void refresh() {
		cache.invalidateAll();
	}

}
