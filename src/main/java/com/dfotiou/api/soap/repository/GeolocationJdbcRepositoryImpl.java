package com.dfotiou.api.soap.repository;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.dfotiou.api.soap.LocationKDTree;
import com.dfotiou.api.soap.Point;

@Repository("geolocationRepository")
public class GeolocationJdbcRepositoryImpl implements GeolocationRepository {
	
	Logger logger = LoggerFactory.getLogger(GeolocationRepository.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Cacheable(value = "points")
	public LocationKDTree findAllPoints() {
		logger.info("Fetch all points via {}", GeolocationRepository.class.getSimpleName());
		
		List<Point> points = jdbcTemplate.query("SELECT name, ST_X(point) as latitude, ST_Y(point) as longitude FROM Point", new BeanPropertyRowMapper<Point>(Point.class));
		Assert.notNull(points, "The points must not be null");
		
		LocationKDTree pointTree = new LocationKDTree(points);
		return pointTree;
	}
	
	public void updateCounter(String name) {
		logger.info("Update counter of {} via {}", name, GeolocationRepository.class.getSimpleName());
		jdbcTemplate.update("UPDATE Point SET counter = counter+1 WHERE name = ?", name);
		return;
	}
	
	public List<String> getCounters(int threshold) {
		logger.info("Get counters via {}", GeolocationRepository.class.getSimpleName());
		
		return jdbcTemplate.queryForList(String.format("SELECT name FROM Point WHERE counter>%x", threshold), String.class);
	}
}
