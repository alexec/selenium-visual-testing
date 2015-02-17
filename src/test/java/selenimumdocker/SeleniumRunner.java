package selenimumdocker;

import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class SeleniumRunner extends Suite {
    private static final WebDriverSupplier WEB_DRIVER_SUPPLIER = new WebDriverSupplier();

    // #1 a list of the different browser configurations we want
    private static final List<DesiredCapabilities> DESIRED_CAPABILITIES =
            asList(DesiredCapabilities.chrome(), DesiredCapabilities.firefox());

    public SeleniumRunner(Class<?> klass) throws InitializationError {
        super(klass, createRunners(klass));
    }

    // #2 this method creates one runner per configuration
    private static List<Runner> createRunners(Class<?> klass) throws InitializationError {
        List<Runner> runners = new ArrayList<Runner>();
        for (DesiredCapabilities desiredCapabilities : DESIRED_CAPABILITIES) {
            // #3 the actual running of the test is delegate to a DesiredCapabilitiesRunner
            //    telling it the capabilities we need
            runners.add(new DesiredCapabilitiesRunner(desiredCapabilities, klass, WEB_DRIVER_SUPPLIER));
        }
        return runners;
    }
}
