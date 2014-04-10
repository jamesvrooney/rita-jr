package com.dealchecker.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dealchecker.handler.DummyHandler;
import com.dealchecker.handler.SupplierHandler;
import com.dealchecker.handler.ebookers.EbookersHolidayHandler;
import com.dealchecker.handler.ebookers.EbookersHolidays;
import com.dealchecker.handler.service.AirportResolutionService;
import com.dealchecker.handler.service.HandlerService;
import com.dealchecker.handler.service.impl.CachedAirportResolutionService;
import com.dealchecker.handler.service.impl.HandlerServiceImpl;
import com.dealchecker.model.Deal;
import com.dealchecker.model.Location;
import com.dealchecker.repository.LocationRepository;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

@Configuration
public class HandlerConfig {
	
	@Autowired
	private LocationRepository locationRepository;
	
	private Map<Integer, SupplierHandler> subscribedHandlers() {
		ImmutableMap<Integer, SupplierHandler> handlerMap = ImmutableMap.<Integer,SupplierHandler>builder()
				.put(0, DummyHandler.thatReturns(Lists.newArrayList(new Deal()))) // FIXME this is just for testing
				.put(EbookersHolidays.getSupplierId(), new EbookersHolidayHandler(cachedAirportResolutionService()))
				.build();
		return handlerMap;
	}

	@Bean
	public HandlerService handlerService() {
		HandlerServiceImpl handlerService = new HandlerServiceImpl(subscribedHandlers());
		return handlerService;
	}

	@Bean
	public AirportResolutionService cachedAirportResolutionService() {
		// TODO: externalize this please
		Cache<String, Location> cache = CacheBuilder.newBuilder().maximumSize(100).build();
		
		AirportResolutionService ars = new CachedAirportResolutionService(locationRepository, cache);
		return ars;
	}
}
