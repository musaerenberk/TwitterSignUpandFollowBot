package Abstract;

import Utils.PageUtility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class Abstract extends PageUtility implements IRetryAnalyzer  {

    public static WebDriver driver;
    public static ExtentReports extent;
    public static ExtentTest logger;
    public String browserName = System.getProperty("browserName");
    public String deviceName = System.getProperty("deviceName");
    public final Properties configProp = new Properties();
    public String baseURL = "https://www.google.com/";

    public static int waitDuration = 10;
    private int count = 0;
    private static int maxTry = 3;
    public static String failedMessage;
    public Logger log= LogManager.getLogger(getClass().getName());
    public InputStream inputStream;

    @BeforeClass
    public void setUp()  {

        ChromeOptions options = new ChromeOptions();

        //Comment lines can be removed if you want chrome to run in the background
/*
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("--disable-dev-shm-usage");

*/
        if (browserName == "chrome") {
            System.setProperty("webdriver.chrome.driver", "C:/Users/meren/OneDrive/Belgeler/Selenium/ChromeDriver/chromedriver.exe");
            driver.manage().window().maximize();
            driver.get(baseURL);
            MDC.put("BrowserName", "Chrome");
            extent = new ExtentReports(
                    System.getProperty("user.dir") + "/test-output/ExtentReport.html", true);
            extent.addSystemInfo("Host Name", "twitter")
                    .addSystemInfo("twitter", "twitter")
                    .addSystemInfo("twitter", "twitter");
            extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
            inputStream = getClass().getClassLoader().getResourceAsStream("browserElement.properties");
            log.info("BROWSER AÇILDI");

        } else if (browserName == "responsive") {
            try {
                Map<String, String> mobileEmulation = new HashMap<String, String>();
                mobileEmulation.put("deviceName", deviceName);
                Map<String, Object> chromeOptions = new HashMap<String, Object>();
                chromeOptions.put("mobileEmulation", mobileEmulation);
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                System.setProperty("webdriver.chrome.driver", "C:/Users/meren/OneDrive/Belgeler/Selenium/ChromeDriver/chromedriver.exe");
                driver.manage().window().maximize();
                driver.get(baseURL);
                MDC.put("BrowserName", "Responsive");
                MDC.put("DeviceName", deviceName);
                extent = new ExtentReports(
                        System.getProperty("user.dir") + "/test-output/ExtentReport.html", true);
                extent.addSystemInfo("Host Name", "twitterBotFollow")
                        .addSystemInfo("twitterBotFollow", "twitterBotFollow")
                        .addSystemInfo("User Name", "Berk Karadeli");
                extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
                inputStream = getClass().getClassLoader().getResourceAsStream("responsiveElement.properties");
                // configProp.load(inputStream);
                //new InputStreamReader(inputStream, "UTF-8");
            } catch (NoClassDefFoundError e) {
                System.out.println(e.getMessage());
            }
            log.info("BROWSER AÇILDI");
        } else {
            System.setProperty("webdriver.chrome.driver", "C:/Users/meren/OneDrive/Belgeler/Selenium/ChromeDriver/chromedriver.exe");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.get(baseURL);
            MDC.put("BrowserName", "Chrome");
            extent = new ExtentReports(
                    System.getProperty("user.dir") + "/test-output/ExtentReport.html", true);
            extent.addSystemInfo("Host Name", "twitter")
                    .addSystemInfo("Environment", "local")
                    .addSystemInfo("User Name", "Berk Karadeli");
            extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
        }

    }
    //Find İnvestTest Result
    @AfterMethod
    public void testResult(ITestResult result) {
        if (result.isSuccess() == true) {
            log.info("PASSED");
            String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";
            System.setProperty(ESCAPE_PROPERTY, "false");
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        }
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
            logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
        }
        else if (result.getStatus() == ITestResult.SKIP) {
            logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
        }
        else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(LogStatus.PASS, "Test Case Passed is " + result.getName());
        }

        else {
            log.error("FAILED");
            failedMessage = result.getThrowable().toString();
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";
            System.setProperty(ESCAPE_PROPERTY, "false");
            Reporter.setCurrentTestResult(result);
            Reporter.log("<img style='width:99%;height:99%;' src=" + new File("screenshot/" + result.getName() + ".png").getAbsolutePath() + ">");
        }

        extent.endTest(logger);
    }
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (count < maxTry) {
                count++;
                result.setStatus(ITestResult.FAILURE);
                log.info("Test Fail");
                return true;
            } else {
                result.setStatus(ITestResult.FAILURE);
                log.info("Test Fail");
            }
        } else {
            result.setStatus(ITestResult.SUCCESS);
            log.info("Test Başarılı");
        }
        return false;
    }
    //Close Driver
    @AfterClass
    public void teardown() throws Exception {

        extent.flush();
        extent.close();
        driver.quit();
        log.info("Browser kapatıldı");


    }
}
