package de.codecentric.cxf.logging;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CxfLoggingSoapActionUtilTest {

    @Test
    public void extractSoapActionMethodCorrectly() {
        // Given
        String exampleSoapHttpHeader = "{accept-encoding=[gzip,deflate], connection=[Keep-Alive], Content-Length=[5121], content-type=[text/xml;charset=UTF-8], host=[localhost:8095], SOAPAction=[\"urn:getWeatherInformation\"], user-agent=[Apache-HttpClient/4.1.1 (java 1.5)]}";
        
        // When
        String methodNameExtracted = CxfLoggingSoapActionUtil.extractSoapMethodNameFromHttpHeader(exampleSoapHttpHeader);
        
        // Then
        Assertions.assertThat(methodNameExtracted).isEqualTo("getWeatherInformation");
    }
}
