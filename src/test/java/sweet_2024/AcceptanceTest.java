package sweet_2024;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "Features" ,
        plugin = {"html:target/cucumber/Report.html"},
        monochrome = true ,
        snippets = SnippetType.CAMELCASE,
        glue = "sweet_2024")
public class AcceptanceTest {
}
