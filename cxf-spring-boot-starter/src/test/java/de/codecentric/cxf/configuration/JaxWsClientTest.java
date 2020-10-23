package de.codecentric.cxf.configuration;


import de.codecentric.cxf.common.BootStarterCxfException;
import de.codecentric.namespace.weatherservice.WeatherException;
import de.codecentric.namespace.weatherservice.WeatherService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


@SpringBootTest(classes = JaxWsClientTestApplication.class)
public class JaxWsClientTest {
	
	@Autowired
	private WeatherService weatherServiceClient;

	@Value(value="classpath:requests/GetCityForecastByZIPTest.xml")
	private Resource GetCityForecastByZIPTestXml;
	
	@Test
	public void is_JaxWsClient_correctly_booted_without_automatic_Endpoint_initialization() throws WeatherException, BootStarterCxfException, IOException {
		Assertions.assertThat(weatherServiceClient).isNotNull();
	}
}
