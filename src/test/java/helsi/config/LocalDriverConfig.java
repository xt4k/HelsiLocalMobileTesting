package helsi.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/drivers/local.properties")
public interface LocalDriverConfig extends Config {

    @Key("platform.name")
    String platformName();

    @Key("platform.version")
    String platformVersion();

    @Key("device.name")
    String deviceName();

    @Key("locale")
    String locale();

    @Key("language")
    String language();
    String appUrl();
    String appPath();

    @Key("server.url")
    String serverUrl();

    @Key("app.package")
    String appPackage();

    @Key("app.activity")
    String appActivity();
    @Key("timeout")
    String timeout();

    @Key("mobile.video.storage")
    String getMobileVideoStorage();

}
