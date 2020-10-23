package de.codecentric.cxf.soaprawclient;

import de.codecentric.cxf.TestApplication;
import de.codecentric.cxf.common.BootStarterCxfException;
import de.codecentric.cxf.configuration.CxfAutoConfiguration;
import de.codecentric.namespace.weatherservice.WeatherService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes=TestApplication.class)
public class SoapRawClientTest {

    @Autowired
    private CxfAutoConfiguration cxfAutoConfiguration;

    @Value(value="classpath:requests/GetCityForecastByZIPTest.xml")
    private Resource GetCityForecastByZIPTestXml;

    @Test
    public void callSoapServiceFails() throws Exception {
        String falseSoapServiceUrl = "http://foobar:8087" + cxfAutoConfiguration.baseAndServiceEndingUrl();
        SoapRawClient soapRawClient = new SoapRawClient(falseSoapServiceUrl, WeatherService.class);
        try {
            soapRawClient.callSoapService(GetCityForecastByZIPTestXml.getInputStream());
            Assertions.fail("An expected exception was not thrown");
        } catch (BootStarterCxfException bootStarterException) {

            assertThat(bootStarterException.getMessage()).contains(SoapRawClient.ERROR_MESSAGE);

            Throwable unknownHostException = bootStarterException.getCause();
            assertThat(unknownHostException).isInstanceOf(UnknownHostException.class);
            assertThat(unknownHostException.getMessage()).contains("foobar");
        }
    }
}
