package de.codecentric.soap.configuration;

import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.interceptor.AbstractLoggingInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import de.codecentric.cxf.common.BootStarterCxfException;
import de.codecentric.cxf.configuration.CxfAutoConfiguration;
import de.codecentric.cxf.soaprawclient.SoapRawClient;
import de.codecentric.namespace.weatherservice.WeatherService;
import org.springframework.http.MediaType;

@Configuration
@PropertySource("classpath:dev-test.properties")
public class CxfBootSimpleTestConfiguration {

    @Value("${webservice.client.port}")
    private String clientPort;
    
    @Value("${webservice.client.host}")
    private String clientHost;
    
    // Mind the "static"
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    /*
     * CXF JaxWs Client
     */
    @Bean
    public WeatherService weatherServiceClient() {
        JaxWsProxyFactoryBean jaxWsFactory = new JaxWsProxyFactoryBean();
        jaxWsFactory.setServiceClass(WeatherService.class);
        jaxWsFactory.setAddress(buildUrl());
        jaxWsFactory.getFeatures().add(loggingFeature());
        return (WeatherService) jaxWsFactory.create();
    }

    @Bean
    public LoggingFeature loggingFeature() {
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        loggingFeature.addBinaryContentMediaTypes(MediaType.APPLICATION_PDF_VALUE);
        return loggingFeature;
    }

    /*
     * SoapRawClient for XmlErrorTests
     */    
    @Bean
    public SoapRawClient soapRawClient() throws BootStarterCxfException {
        System.out.println(buildUrl());
        return new SoapRawClient(buildUrl(), WeatherService.class);
    }
    
    public String buildUrl() {
        // return something like http://localhost:8084/soap-api/WeatherSoapService
        return "http://" + clientHost + ":" + clientPort + cxfAutoConfiguration.baseAndServiceEndingUrl();
    }
    
    @Autowired
    private CxfAutoConfiguration cxfAutoConfiguration;
}
