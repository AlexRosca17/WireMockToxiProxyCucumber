package tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        features = "src/test/resources/WireMockToxiProxy.feature",
        glue = "tests.steps",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class Runner extends AbstractTestNGCucumberTests {}
