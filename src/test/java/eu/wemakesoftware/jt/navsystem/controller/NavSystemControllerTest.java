package eu.wemakesoftware.jt.navsystem.controller;

import eu.wemakesoftware.jt.navsystem.controller.model.BaseStationReports;
import eu.wemakesoftware.jt.navsystem.controller.model.DistanceReportInput;
import eu.wemakesoftware.jt.navsystem.jpa.DistanceReportRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class NavSystemControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	DistanceReportRepository distanceReportRepository;

	@Test
	public void checkUnknownMobileStationError() {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/location/1000",
				String.class)).contains("{" +
				"\"mobileId\":\"1000\",\"x\":null,\"y\":null," +
				"\"errorRadius\":null,\"errorCode\":1," +
				"\"errorDescription\":" +
				"\"Unknown mobile station UUID\"}");
	}

	@Test
	public void testReportPosting(){
		BaseStationReports request = new BaseStationReports();
		request.baseStationId = "1";
		request.reports = Arrays.asList(
			new DistanceReportInput("1",1, LocalDateTime.MAX),
			new DistanceReportInput("2",1, LocalDateTime.MAX)
		);
		this.restTemplate.postForObject("http://localhost:" + port + "/report", request, String.class);
		assertThat(distanceReportRepository.findThreeDistanceReports("1").size()).isEqualTo(3);
	}

	@Test
	public void testTriangulation(){
		String response = this.restTemplate.getForObject("http://localhost:" + port + "/location/1",
				String.class);
		assertThat(response).isEqualTo("{" +
				"\"mobileId\":\"1\"," +
				"\"x\":6.042948452725629," +
				"\"y\":6.042948452725629," +
				"\"errorRadius\":2.6103232486278545," +
				"\"errorCode\":null," +
				"\"errorDescription\":null}");
	}

}