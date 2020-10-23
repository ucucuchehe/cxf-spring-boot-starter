package de.codecentric.cxf.endpoint;


import de.codecentric.cxf.TestApplication;
import de.codecentric.cxf.TestServiceEndpoint;
import de.codecentric.cxf.common.BootStarterCxfException;
import de.codecentric.cxf.common.XmlUtils;
import de.codecentric.namespace.weatherservice.WeatherException;
import de.codecentric.namespace.weatherservice.WeatherService;
import de.codecentric.namespace.weatherservice.general.ForecastReturn;
import de.codecentric.namespace.weatherservice.general.GetCityForecastByZIP;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(
		classes = TestApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
		properties = { "server.port:8087" }
)
public class WeatherServiceEndpointSystemTest {
	
	@Autowired
	private WeatherService weatherServiceClient;

	@Autowired
	private TestServiceEndpoint testServiceEndpoint;

	@Value(value="classpath:requests/GetCityForecastByZIPTest.xml")
	private Resource GetCityForecastByZIPTestXml;
	
	@Test
	public void isEndpointCorrectlyAutoDetectedAndConfigured() throws WeatherException, BootStarterCxfException, IOException {
		// Given
		GetCityForecastByZIP getCityForecastByZIP = XmlUtils.readSoapMessageFromStreamAndUnmarshallBody2Object(
				GetCityForecastByZIPTestXml.getInputStream(), GetCityForecastByZIP.class);
		
		// When
		ForecastReturn forecastReturn = weatherServiceClient.getCityForecastByZIP(getCityForecastByZIP.getForecastRequest());
		
		// Then
		assertThat(forecastReturn).isNotNull();
		assertThat(forecastReturn.getCity()).isEqualTo("Weimar");
		assertThat(forecastReturn.getForecastResult().getForecast().get(0).getProbabilityOfPrecipiation().getDaytime()).isEqualTo("22%");
	}

	@Test
	public void isEndpointCorrectlyAutowired() {
		assertThat(testServiceEndpoint.getInjectedBean()).isNotNull();
	}
}
