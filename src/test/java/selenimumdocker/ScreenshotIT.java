package selenimumdocker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.inject.Inject;
import java.io.File;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

// #1 you use this annotation to tell JUnit to run the class using your custom runner
@RunWith(SeleniumRunner.class)
public class ScreenshotIT {

    // #2 this is our primitve injection at work
    @Inject
    private WebDriver driver;

    @Inject
    private DesiredCapabilities desiredCapabilities;

    @Test
    public void getMyBrowserInfo() throws Exception {

        driver.get("http://mybrowserinfo.com");

        // #3 using assertThat often give better information when a test fails
        assertThat(driver.findElement(By.tagName("h1")).getText(),
                containsString("Your IP Address is"));

        // #4 take the screen shot, this code is pretty rough and ready, but could easily be refactored
        assert ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE)
                .renameTo(
                        // #5 create a file name based on the URL and the browser's name, you may 
                        //   want to change this to make it suitable for your purposes
                        new File(driver.getCurrentUrl().replaceAll("[^a-z]", "") + "-"
                                + desiredCapabilities.getBrowserName() + ".png"));
    }
}
