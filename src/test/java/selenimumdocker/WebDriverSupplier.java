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
    private DesiredCapabilities desiredCapabilities;

    public WebDriver get(DesiredCapabilities desiredCapabilities) {
        // #1 if the driver we already has does not match what we want, close it
        if (!desiredCapabilities.equals(this.desiredCapabilities)) {
            close();
        }
        if (driver == null) {
            try {
                // #2 create a new driver, note that we augment it 
                //    so that it can create screen shots
                this.driver = new Augmenter().augment(new RemoteWebDriver(
                        // #3 this is the URL of out grid, change this to 
                        //    point to your if you're not using docker
                        new URL("http://localhost:4444/wd/hub"),
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
            // #4 close the driver down when we've finished with it to free up resources
            driver.close();
            driver = null;
        }
    }
}
