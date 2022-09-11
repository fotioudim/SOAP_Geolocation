package com.dfotiou.api.soap.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.dfotiou.api.soap.geolocation.GetFrequentPointsRequest;
import com.dfotiou.api.soap.geolocation.GetFrequentPointsResponse;
import com.dfotiou.api.soap.geolocation.GetNearestPointRequest;
import com.dfotiou.api.soap.geolocation.GetNearestPointResponse;
import com.dfotiou.api.soap.service.GeolocationService;

@Endpoint
public class GeolocationEndpoint {
	private static final String NAMESPACE_URI = "http://dfotiou.com/api/soap/geolocation";

	private GeolocationService geolocationService;

	Logger logger = LoggerFactory.getLogger(GeolocationEndpoint.class);

	@Autowired
	public GeolocationEndpoint(GeolocationService geolocationService) {
		this.geolocationService = geolocationService;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getNearestPointRequest")
	@ResponsePayload
	public GetNearestPointResponse getNearestPoint(@RequestPayload GetNearestPointRequest request) {
		logger.info("Get nearest point for lat:{}, lon:{}", request.getLatitude(), request.getLongitude());

		GetNearestPointResponse response = new GetNearestPointResponse();
		response.setName(geolocationService.findNearestPoint(request.getLatitude(), request.getLongitude()));

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFrequentPointsRequest")
	@ResponsePayload
	public GetFrequentPointsResponse getFrequentPoints(@RequestPayload GetFrequentPointsRequest request) {
		logger.info("Get Points with counter larger than: {}", request.getThreshold());

		GetFrequentPointsResponse response = new GetFrequentPointsResponse();
		response.getNames().addAll(geolocationService.findFrequentPoints(request.getThreshold()));

		return response;
	}
}
