package de.codecentric.cxf.autodetection;


import de.codecentric.cxf.common.BootStarterCxfException;
import de.codecentric.namespace.weatherservice.WeatherService;
import org.junit.jupiter.api.Test;

import javax.jws.WebService;
import java.util.List;

import static de.codecentric.cxf.autodetection.WebServiceAutoDetector.SEI_ANNOTATION;
import static de.codecentric.cxf.autodetection.WebServiceAutoDetector.WEB_SERVICE_CLIENT_ANNOTATION;
import static de.codecentric.cxf.autodetection.WebServiceAutoDetectorTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class WebServiceScannerTest {

    private static final String GENERATED_CLASSES_PACKAGE = "de.codecentric.namespace.weatherservice";
    private WebServiceScanner webServiceScanner = new WebServiceScanner();

    @Test public void
    is_SEI_successfully_detected_when_the_SEI_implementing_class_also_has_the_same_WebService_Annotation() throws BootStarterCxfException {

        Class weather = null;

        try {
            weather = webServiceScanner.scanForClassWithAnnotationAndIsAnInterface(WebService.class, GENERATED_CLASSES_PACKAGE);
        } catch (BootStarterCxfException exception) {
            fail("Interface should have been found!");
        }

        assertThat(weather).isEqualTo(WeatherService.class);
    }

    @Test public void
    is_Class_With_Annotation_Which_is_also_an_Interface_Successfully_detected() throws BootStarterCxfException {
        Class serviceEndpointInterface = webServiceScanner.scanForClassWithAnnotationAndIsAnInterface(SEI_ANNOTATION, GENERATED_CLASSES_PACKAGE);

        assertThat(serviceEndpointInterface).isNotNull();

        assertThat(serviceEndpointInterface.getSimpleName()).isEqualTo(WEATHER_SERVICE_ENDPOINT_INTERFACE.getSimpleName());
    }

    @Test public void
    is_Class_which_Implementes_another_Class_successfully_found() throws NoSuchFieldException, BootStarterCxfException {
        Class weatherServiceEndpointImpl = webServiceScanner.scanForClassWhichImplementsAndPickFirst(WEATHER_SERVICE_ENDPOINT_INTERFACE, "de.codecentric.cxf");

        assertThat(weatherServiceEndpointImpl).isNotNull();
        assertThat(weatherServiceEndpointImpl.getSimpleName()).isEqualTo(WEATHER_SEI_IMPLEMENTING_CLASS.getSimpleName());
    }

    @Test public void
    is_Class_with_Annotation_successfully_found() throws BootStarterCxfException {
        Class webServiceClient = webServiceScanner.scanForClassWithAnnotationAndPickTheFirstOneFound(WEB_SERVICE_CLIENT_ANNOTATION, GENERATED_CLASSES_PACKAGE);

        assertThat(webServiceClient).isNotNull();
        assertThat(webServiceClient.getSimpleName()).isEqualTo(WEATHER_WEBSERVICE_CLIENT.getSimpleName());
    }

    @Test public void
    picks_the_interface_from_two_classes_where_one_is_an_interface_and_the_other_not() throws BootStarterCxfException, ClassNotFoundException {
        List<String> twoWebServices = generateListWithSeiAndSeiImplNameWithBothWebServiceAnnotation();

        Class interfaze = webServiceScanner.justPickTheClassThatIsAnInterface(twoWebServices);

        assertThat(interfaze).isNotNull();
        assertThat(interfaze).isInterface();
    }

}
