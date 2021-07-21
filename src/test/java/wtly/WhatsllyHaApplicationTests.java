package wtly;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import wtly.boundries.PingResponse;

@SpringBootTest
class WhatsllyHaApplicationTests {

	private RestTemplate restTemplate;
	@Autowired
	private TestsUtils utils;

	@PostConstruct
	public void init() {
		restTemplate = new RestTemplate();
	}

	// GIVEN all the servers up, THEN I ping to all of them, THEN I get success =
	// true
	@Test
	void test_all_servers_up() {
		List<String> urls = this.utils.getAllOkUrls();
		urls.stream().forEach(url -> {
			PingResponse response = makeGETHttpCall(url);
			assertThat(response.isSuccess()).isEqualTo(true);
		});
	}

	// GIVEN the servers is up, THEN I send numberOfCalls http requests, THEN the
	// server still handles
	@Test
	void test_overload_servers() {
		List<String> urls = this.utils.getAllOkUrls();
		urls.stream().forEach(url -> {
			for (int i = 0; i < this.utils.getNumberOfCalls(); i++) {
				PingResponse response = makeGETHttpCall(url);
				assertThat(response.isSuccess()).isEqualTo(true);
			}
		});
	}

	// GIVEN the servers is up, THEN I send GET http requests with wrong end URL,
	// THEN the server return 404 Not Found
	@Test
	void test_404_NotFound_for_wrong_URL() {
		List<String> urls = this.utils.getAllBadUrls();
		urls.stream().forEach(url -> {
			assertThrows(HttpStatusCodeException.class, () -> makeGETHttpCall(url));
		});
	}

	// GIVEN the servers is up, THEN I send GET http requests to all servers,
	// THEN I get the same response with the relevant values
	@Test
	void test_get_properly_response_from_all_servers() {
		List<String> urls = this.utils.getAllOkUrls();
		IntStream.range(0, urls.size()).forEach(i -> {
			PingResponse actualResponse = makeGETHttpCall(urls.get(i));
			PingResponse expectedResponse = this.utils.getOkResponseWithoutHost(this.utils.getServersNames().get(i));
			assertThat(actualResponse).isEqualTo(expectedResponse);

		});

	}

	private PingResponse makeGETHttpCall(String url) {
		PingResponse response = this.restTemplate.getForObject(url, PingResponse.class);
		return response;
	}
}
