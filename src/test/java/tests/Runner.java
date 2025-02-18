package tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;

@Listeners({AllureTestNg.class})
@CucumberOptions(
        features = "src/test/resources/",
        glue = "tests",
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber-report.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class Runner extends AbstractTestNGCucumberTests {}
