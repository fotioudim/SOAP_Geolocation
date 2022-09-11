package com.dfotiou.api.soap.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.dfotiou.api.soap.LocationKDTree;
import com.dfotiou.api.soap.Point;

@SpringBootTest
class SoapServiceTests {
	
	private static final List<Point> points = new ArrayList<Point>(3);
	
	/**
	 * Test the methods feeding data from <a href="https://simplemaps.com/data/gr-cities">Cities coordinates</a>
	 */
	@BeforeEach
	public void init() throws Exception {
		Point athens = new Point();
		athens.setName("Athens");
		athens.setLatitude(37.9794);
		athens.setLongitude(23.7161);

		points.add(athens);

		Point thessaloníki = new Point();
		thessaloníki.setName("Thessaloníki");
		thessaloníki.setLatitude(40.6333);
		thessaloníki.setLongitude(22.9500);

		points.add(thessaloníki);

		Point patra = new Point();
		patra.setName("Pátra");
		patra.setLatitude(38.2500);
		patra.setLongitude(21.7333);

		points.add(patra);
   }
	
	
	@Test
	void getNearestPoint() {
		LocationKDTree pointTree = new LocationKDTree(points);
		
		Point nearestPoint = pointTree.findNearest(37.9500, 23.7000); // Piraeus coordinates
		assertEquals("Athens", nearestPoint.getName(), "Closest city to Piraeus is Athens.");
		
		nearestPoint = pointTree.findNearest(41.0833, 23.5500); // Sérres coordinates
		assertEquals("Thessaloníki", nearestPoint.getName(), "Closest city to Sérres is Thessaloníki.");
		
		nearestPoint = pointTree.findNearest(38.3917, 21.8275); // Náfpaktos coordinates
		assertEquals("Pátra", nearestPoint.getName(), "Closest city to Náfpaktos is Pátra.");
	}
}
