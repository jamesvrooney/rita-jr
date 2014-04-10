package com.dealchecker.handler.service.impl

import com.dealchecker.handler.service.AirportResolutionService
import com.dealchecker.model.Location
import com.google.common.base.Preconditions;

final class AirportResolutionServices {
	
	static AirportResolutionService newFromMap(Map<String,Location> map) {
		Preconditions.checkNotNull(map)
		new InMemoryAiportResolutionServiceImpl(map)
	}

	private AirportResolutionServices() {}
}
