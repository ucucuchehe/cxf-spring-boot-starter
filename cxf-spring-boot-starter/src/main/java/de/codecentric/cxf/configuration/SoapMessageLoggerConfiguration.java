package de.codecentric.cxf.configuration;

import org.apache.cxf.ext.logging.LoggingFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

/**
 * Logging of SoapMessages to e.g. Console. To activate, set property soap.messages.logging=true.
 *
 * Extraction of SoapMessages, so they can be further processed, e.g. via Logstash to push to elasticsearch.
 * Activate with property soap.messages.extract=true.
 * 
 * @author Jonas Hecht
 */
@Configuration
@ConditionalOnProperty(name = "endpoint.autoinit", matchIfMissing = true)
public class SoapMessageLoggerConfiguration {

  @Bean
  public LoggingFeature loggingFeature() {
    LoggingFeature loggingFeature = new LoggingFeature();
    loggingFeature.setPrettyLogging(true);
    loggingFeature.setVerbose(true);
    loggingFeature.addBinaryContentMediaTypes(MediaType.APPLICATION_PDF_VALUE);
    return loggingFeature;
  }

}
