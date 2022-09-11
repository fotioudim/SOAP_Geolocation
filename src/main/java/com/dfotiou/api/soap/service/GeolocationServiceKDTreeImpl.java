package com.dfotiou.api.soap.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dfotiou.api.soap.model.LocationKDTree;
import com.dfotiou.api.soap.repository.GeolocationRepository;

@Service("geolocationServiceKDTree")
public class GeolocationServiceKDTreeImpl implements GeolocationService{

	@Autowired 
	private GeolocationRepository geolocationRepository;
	
	/**
	 * Nearest neighbor search (NNS) is the optimization problem of finding the point in a given set that 
	 * is closest to a given point. Using Linear search is the simplest solution to the NNS problem but has a running time of O(dN), 
	 * where d is the dimensionality. Another more efficient way is to use space-partitioning methods, such as kd-tree average complexity is O(log N) 
	 * in the case of randomly distributed points, worst case complexity is O(kN^(1-1/k)).
	 * @param d
	 * @param e
	 * @return
	 */
	@Override
	public String findNearestPoint(double lat, double lon) {
		LocationKDTree points = geolocationRepository.findAllPoints();
		String nearestPoint = points.findNearest(lat, lon).getName();
		geolocationRepository.updateCounter(nearestPoint);
		return nearestPoint;
	}

	@Override
	public List<String> findFrequentPoints(int threshold) {
		return geolocationRepository.getCounters(threshold);
	}

}
