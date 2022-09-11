package com.dfotiou.api.soap;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.dfotiou.api.soap.geolocation.GetFrequentPointsRequest;
import com.dfotiou.api.soap.geolocation.GetNearestPointRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SoapApplicationIntegrationTests {

	private Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

    @LocalServerPort private int port = 0;

    @BeforeEach
    public void init() throws Exception {
        marshaller.setPackagesToScan(ClassUtils.getPackageName(GetNearestPointRequest.class), ClassUtils.getPackageName(GetFrequentPointsRequest.class));
        marshaller.afterPropertiesSet();
    }
    
    @Test
    void whenRequestNearestPoint_thenResponseIsNotNull() {
        WebServiceTemplate ws = new WebServiceTemplate(marshaller);
        GetNearestPointRequest request = new GetNearestPointRequest();
        request.setLatitude(0);
        request.setLongitude(0);

        assertThat(ws.marshalSendAndReceive("http://localhost:" + port + "/ws", request)).isNotNull();
    }
    
    @Test
    void whenRequestFrequentPoints_thenResponseIsNotNull() {
        WebServiceTemplate ws = new WebServiceTemplate(marshaller);
        GetFrequentPointsRequest request = new GetFrequentPointsRequest();
        request.setThreshold(0);

        assertThat(ws.marshalSendAndReceive("http://localhost:" + port + "/ws", request)).isNotNull();
    }
}
