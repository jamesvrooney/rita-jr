package com.dealchecker.dao;

import com.dealchecker.model.Location;

public interface LocationDao {

	Location getLocationByCode(String code);
}
