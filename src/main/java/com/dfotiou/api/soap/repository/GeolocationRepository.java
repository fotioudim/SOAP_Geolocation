package com.dfotiou.api.soap.repository;

import java.util.List;

import com.dfotiou.api.soap.model.LocationKDTree;

public interface GeolocationRepository {

	LocationKDTree findAllPoints();

	void updateCounter(String name);

	List<String> getCounters(int threshold);

}
