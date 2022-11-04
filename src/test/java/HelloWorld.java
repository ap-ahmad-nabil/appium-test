import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class HelloWorld {
    public AndroidDriver driver;
    public WebDriverWait wait;

    //Elements By
    By loginBtn = By.id("com.ada.astrapayupdate:id/btnLogin");
    By phoneNumberForm = By.id("com.ada.astrapayupdate:id/input_phone_number");
    By continueButton = By.id("com.ada.astrapayupdate:id/btnContinue");
    By pinForm = By.id("com.ada.astrapayupdate:id/password");
    By tooltip = By.id("com.ada.astrapayupdate:id/tv_custom_heading");

    By allowWhenUsingBy = By.id("com.android.packageinstaller:id/permission_allow_button");

    @BeforeMethod
    public void setup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "Pixel 4 API 28");
        caps.setCapability("udid", "emulator-5554"); //DeviceId from "adb devices" command
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "9.0");
        caps.setCapability("skipUnlock", "true");
        caps.setCapability("appPackage", "com.ada.astrapayupdate");
        caps.setCapability("appActivity", "com.astrapay.MainActivity");
        caps.setCapability("noReset", "false");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        wait = new WebDriverWait(driver, Duration.ofMillis(100000));
    }

    @Test
    public void basicTest() {

        //Allow location permission
        if (wait.until(ExpectedConditions.visibilityOfElementLocated(allowWhenUsingBy)).isDisplayed()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(allowWhenUsingBy)).click();
        }

        //click Login Button
        if (wait.until(ExpectedConditions.visibilityOfElementLocated(loginBtn)).isDisplayed()) {
            driver.findElement(loginBtn).click();
        }

        //Insert phone number
        wait.until(ExpectedConditions.visibilityOfElementLocated(phoneNumberForm)).sendKeys("89506974203");;

        //Click continue button after input phone number
        driver.findElement(continueButton).click();
        //input pin
        wait.until(ExpectedConditions.visibilityOfElementLocated(pinForm)).sendKeys("111111");

        //Do a simple assertion
        //validate tooltip
        String toolBarTitleStr = wait.until(ExpectedConditions.visibilityOfElementLocated(tooltip)).getText();
        Assert.assertTrue(toolBarTitleStr.toLowerCase().contains("selamat datang di astrapay"));
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
