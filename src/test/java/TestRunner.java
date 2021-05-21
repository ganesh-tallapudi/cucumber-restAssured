import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features",
                 //glue = "src/test/java/stepDefinitions",
                 //tags="@Before or @SmokeTest",
                 plugin = {"pretty","html:target/Test_Reports.html"},
                 monochrome = true)


public class TestRunner {


}
