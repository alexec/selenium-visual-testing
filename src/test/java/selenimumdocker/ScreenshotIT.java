package selenimumdocker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.inject.Inject;
import java.io.File;

@RunWith(SeleniumRunner.class)
public class ScreenshotIT {

    @Inject
    private WebDriver driver;

    @Inject
    private DesiredCapabilities desiredCapabilities;

    @Test
    public void getMyBrowserInfo() throws Exception {
        driver.get("http://mybrowserinfo.com");
        assert ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE)
                .renameTo(new File(
                        driver.getCurrentUrl().replaceAll("[^a-z]", "") + "-" + desiredCapabilities.getBrowserName() + ".png"));
    }
}
