package selenimumdocker;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.Closeable;
import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverSupplier implements Closeable {

    private WebDriver driver;
    private DesiredCapabilities desiredCapabilities;;

    public WebDriver get(DesiredCapabilities desiredCapabilities) {
        if (!desiredCapabilities.equals(this.desiredCapabilities)) {
            close();
        }
        if (driver == null) {
            try {
                this.driver = new Augmenter().augment(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),
                        desiredCapabilities));
                this.desiredCapabilities = desiredCapabilities;
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        return driver;
    }

    public void close() {
        if (driver != null ) {
            driver.close();
            driver = null;
        }
    }
}
