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

    DesiredCapabilitiesRunner(DesiredCapabilities desiredCapabilities, Class<?> klass, WebDriverSupplier webDriverSupplier) throws InitializationError {
        super(klass);
        this.desiredCapabilities = desiredCapabilities;
        this.webDriverSupplier = webDriverSupplier;
    }

    @Override
    protected Statement withBefores(FrameworkMethod method, Object target, Statement statement) {
        inject(target, webDriverSupplier.get(desiredCapabilities));
        inject(target, desiredCapabilities);
        return super.withBefores(method, target, statement);
    }

    private static void inject(Object target, Object bean) {
        for (Field field : target.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Inject.class) != null && field.getType().isAssignableFrom(bean.getClass())) {
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
    protected String getName() {
        return String.format("%s [%s]", super.getName(), desiredCapabilities);
    }

    @Override
    protected String testName(final FrameworkMethod method) {
        return String.format("%s [%s]", method.getName(), desiredCapabilities);
    }
}
