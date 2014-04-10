package com.dealchecker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dealchecker.dao.LocationDao;
import com.dealchecker.model.Location;

@Repository
public interface LocationRepository extends LocationDao, CrudRepository<Location, Integer> {
}
