package com.dfotiou.api.soap.service;

import java.util.List;

public interface GeolocationService {

	String findNearestPoint(double lat, double lon);

	List<String> findFrequentPoints(int threshold);

}
