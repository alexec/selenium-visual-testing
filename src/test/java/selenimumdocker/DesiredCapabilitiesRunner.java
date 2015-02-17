package selenimumdocker;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.inject.Inject;
import java.lang.reflect.Field;

class DesiredCapabilitiesRunner extends BlockJUnit4ClassRunner {
    private final DesiredCapabilities desiredCapabilities;
    private final WebDriverSupplier webDriverSupplier;

    DesiredCapabilitiesRunner(DesiredCapabilities desiredCapabilities, Class<?> klass,
                              WebDriverSupplier webDriverSupplier) throws InitializationError {
        super(klass);
        this.desiredCapabilities = desiredCapabilities;
        this.webDriverSupplier = webDriverSupplier;
    }

    private static void inject(Object target, Object bean) {
        // #1 a very primitive form of injection, we simple find any field annotated
        //    with @Inject and if the target has the same class, set it using
        //    standard Java Reflection
        for (Field field : target.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Inject.class) != null &&
                    field.getType().isAssignableFrom(bean.getClass())) {
                try {
                    field.setAccessible(true);
                    field.set(target, bean);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    protected Statement withBefores(FrameworkMethod method, Object target, Statement statement) {
        // #2 inject the web driver and the capabilities into the test class
        inject(target, webDriverSupplier.get(desiredCapabilities));
        inject(target, desiredCapabilities);
        return super.withBefores(method, target, statement);
    }

    @Override
    protected String getName() {
        // #3 to make sure that the class has a clean name in the IDE
        return String.format("%s [%s]", super.getName(), desiredCapabilities);
    }

    @Override
    protected String testName(final FrameworkMethod method) {
        // #4 as above, make sure that the class is clearly named in the IDE
        return String.format("%s [%s]", method.getName(), desiredCapabilities);
    }
}
