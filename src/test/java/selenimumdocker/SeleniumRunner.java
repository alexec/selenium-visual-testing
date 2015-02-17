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

    private static final List<DesiredCapabilities> DESIRED_CAPABILITIES =
            asList(DesiredCapabilities.chrome(), DesiredCapabilities.firefox());

    public SeleniumRunner(Class<?> klass) throws InitializationError {
        super(klass, createRunners(klass));
    }

    private static List<Runner> createRunners(Class<?> klass) throws InitializationError {
        List<Runner> runners = new ArrayList<Runner>();
        for (DesiredCapabilities desiredCapabilities : DESIRED_CAPABILITIES) {
            runners.add(new DesiredCapabilitiesRunner(desiredCapabilities, klass, WEB_DRIVER_SUPPLIER));
        }
        return runners;
    }
}
