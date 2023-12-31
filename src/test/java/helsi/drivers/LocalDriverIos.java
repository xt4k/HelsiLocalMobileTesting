package helsi.drivers;

import com.codeborne.selenide.WebDriverProvider;
import helsi.config.Configs;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;


public class LocalDriverIos implements WebDriverProvider {

    public static URL getAppiumServerUrl() {
        try {
            return new URL(Configs.mobileDriver.serverUrl());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WebDriver createDriver(Capabilities capabilities) {
        File app = getApp();

        UiAutomator2Options options = new UiAutomator2Options();
        options.merge(capabilities);
        options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2); //todo Deprecated
        options.setPlatformName(Configs.mobileDriver.platformName());
        options.setDeviceName(Configs.mobileDriver.deviceName());
        options.setPlatformVersion(Configs.mobileDriver.platformVersion());
        options.setApp(app.getAbsolutePath());
        options.setLocale(Configs.mobileDriver.locale());
        options.setLanguage(Configs.mobileDriver.language());
        options.setAppPackage(Configs.mobileDriver.appPackage());
        options.setAppActivity(Configs.mobileDriver.appActivity());
        options.setCapability("autoAcceptAlerts", "true"); //to accept all alerts for iOS apps
        options.setCapability("autoDismissAlerts", "true"); //to dismiss all alerts for iOS apps


        return new IOSDriver(getAppiumServerUrl(), options);
    }

    private File getApp() {
        String appPath = Configs.mobileDriver.appPath();
        String appUrl = Configs.mobileDriver.appUrl();

        File app = new File(appPath);
        if (!app.exists()) {
            try (InputStream in = new URL(appUrl).openStream()) {
                copyInputStreamToFile(in, app);
            } catch (IOException e) {
                throw new AssertionError("Failed to download apk", e);
            }
        }
        return app;
    }
}