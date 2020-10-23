package de.codecentric.cxf.autodetection.diagnostics;


import de.codecentric.cxf.common.BootStarterCxfException;
import de.codecentric.namespace.weatherservice.WeatherService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.diagnostics.FailureAnalysis;



public class ClassesForAutodetectionMissingFailureAnalyzersTest {

    private SeiImplMissingFailureAnalyzer seiImplMissingFailureAnalyzer = new SeiImplMissingFailureAnalyzer();
    private WebServiceClientMissingFailureAnalyzer webServiceClientMissingFailureAnalyzer = new WebServiceClientMissingFailureAnalyzer();
    private SeiMissingFailureAnalyzer seiMissingFailureAnalyzer = new SeiMissingFailureAnalyzer();


    @Test
    public void
    does_SeiImplMissing_failure_analysis_contain_correct_description() throws BootStarterCxfException {

        SeiImplClassNotFoundException seiNotFoundException = SeiImplClassNotFoundException.build().setNotFoundClassName(WeatherService.class.getName());

        FailureAnalysis failureAnalysis = seiImplMissingFailureAnalyzer.analyze(seiNotFoundException);

        Assertions.assertThat(failureAnalysis.getDescription()).contains(SeiImplClassNotFoundException.MESSAGE);
    }


    @Test public void
    does_WebServiceClientMissing_failure_analysis_contain_correct_description() throws BootStarterCxfException {

        FailureAnalysis failureAnalysis = webServiceClientMissingFailureAnalyzer.analyze(new WebServiceClientNotFoundException());

        Assertions.assertThat(failureAnalysis.getDescription()).contains(WebServiceClientNotFoundException.MESSAGE);
    }

    @Test public void
    does_SeiMissing_failure_analysis_contain_correct_description() {
        FailureAnalysis failureAnalysis = seiMissingFailureAnalyzer.analyze(new SeiNotFoundException());

        Assertions.assertThat(failureAnalysis.getDescription()).contains(SeiNotFoundException.MESSAGE);
    }




}
