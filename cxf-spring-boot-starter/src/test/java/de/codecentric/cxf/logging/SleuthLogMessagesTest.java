package de.codecentric.cxf.logging;

import de.codecentric.cxf.TestApplication;
import de.codecentric.cxf.common.XmlUtils;
import de.codecentric.namespace.weatherservice.WeatherException;
import de.codecentric.namespace.weatherservice.WeatherService;
import de.codecentric.namespace.weatherservice.general.GetCityForecastByZIP;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest(
        classes = TestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"server.port:8087"})
public class SleuthLogMessagesTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Autowired
    private WeatherService weatherServiceClient;

    @Value(value="classpath:requests/GetCityForecastByZIPTest.xml")
    private Resource GetCityForecastByZIPTestXml;

    @Test public void
    sleuth_meta_data_should_be_print_out() throws Exception {
        callSoapService();

        String log = systemOutRule.getLog();
        Assertions.assertThat(isSleuthLogLineSomewhereIn(log)).isTrue();
    }

    private boolean isSleuthLogLineSomewhereIn(String log) {
        Pattern sleuth_log_line_pattern = Pattern.compile("(INFO \\[,\\w+,\\w+,.*: Returning a forecast.)");
        Matcher loglineMatcher = sleuth_log_line_pattern.matcher(log);
        return loglineMatcher.find();
    }

    @Disabled
    @Test public void
    call_time_should_be_print_out() throws Exception {
        callSoapService();

        String log = systemOutRule.getLog();
        Assertions.assertThat(log).contains("Call time");
    }

    private void callSoapService() throws de.codecentric.cxf.common.BootStarterCxfException, IOException, WeatherException {
        GetCityForecastByZIP getCityForecastByZIP = XmlUtils.readSoapMessageFromStreamAndUnmarshallBody2Object(
                GetCityForecastByZIPTestXml.getInputStream(), GetCityForecastByZIP.class);

        weatherServiceClient.getCityForecastByZIP(getCityForecastByZIP.getForecastRequest());
    }

}
