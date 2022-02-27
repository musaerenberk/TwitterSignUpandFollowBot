package Pages;
import Abstract.MainFunction;
import Type.DataDefauts;
import Type.DataElements;
import com.github.javafaker.Faker;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.util.Iterator;
import java.util.Set;

public class TwitterSignUpAndFollow extends MainFunction {

    int PasswordFinish;int PasswordStart;String password;
    public void Run() {
        Actions actionEnter = new Actions(driver);
        Actions actionDown = new Actions(driver);
        Actions actionTab = new Actions(driver);
        driver.get(DataDefauts.TemporaryMailSite); //Temporary mail receiving page opens
        Sleep();
        xpath(DataElements.MailCopyOperation).click(); //Temporary email copying done
        driver.get(DataDefauts.TwitterLoginAdress); //Twitter login page opens
        xpath(DataElements.SignUpButton).click();  //User presses the register button
        xpath(DataElements.PhoneOrEmailRegister).click();  //User presses register button by phone or e-mail
        Faker faker = new Faker(); //Faker is used to generate random names
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        cssSelector(DataElements.NameLabel).sendKeys(firstName+" "+lastName); //User fills the name field
        xpath(DataElements.UseEmailButton).click();
        WebElement l = cssSelector(DataElements.EmailLabel);
        l.sendKeys(Keys.CONTROL+"V"); //fills the email field
        xpath(DataElements.Day).click();
        for (int i=0; i<4;i++){
            actionDown.sendKeys(Keys.DOWN).perform();
        }
        actionEnter.sendKeys(Keys.ENTER).perform();
        xpath(DataElements.Month).click();
        for (int i=0; i<4;i++){
            actionDown.sendKeys(Keys.DOWN).perform();
        }
        actionEnter.sendKeys(Keys.ENTER).perform();
        xpath(DataElements.Year).click();
        for (int i=0; i<30;i++){
            actionDown.sendKeys(Keys.DOWN).perform();
        }
        actionEnter.sendKeys(Keys.ENTER).perform();
        xpath(DataElements.NextButton).click();  //press forward button
        xpath(DataElements.NextButton).click(); //press forward button
        xpath(DataElements.KeyAlmaSayfasinaGit).click(); //Press to go to the verification code page
        String homePage2 = driver.getWindowHandle();
        JavascriptExecutor js3 = (JavascriptExecutor) driver;
        js3.executeScript("window.open()"); //new tab opens and our goal is to get the verification code from the temporary mail receiving site
        Set<String> windows2 = driver.getWindowHandles();
        Sleep();
        Iterator iterator2 = windows2.iterator();
        String currentWindowId2;
        while (iterator2.hasNext()) {
            currentWindowId2 = iterator2.next().toString();
            if (!currentWindowId2.equals(homePage2)) {
                driver.switchTo().window(currentWindowId2);
                driver.get(DataDefauts.TemporaryMailSite);//Temporary mail receiving page opens
                Sleep();
                xpath(DataElements.verifyTwitterCom).click(); //go to mail detail
                Sleep();
                String Pagesource =driver.getPageSource();  //the source of the page is pulled
                PasswordStart = Pagesource.indexOf("onay kodun ");
                PasswordFinish = PasswordStart+17;
                password = Pagesource.substring(PasswordStart,PasswordFinish);
                password = password.replaceAll("onay kodun ", ""); //has a verification code
                System.out.println(password);
                driver.close(); //new tab is closed
                Sleep();
            }
        }
        driver.switchTo().window(homePage2);
        xpath(DataElements.confirmationCode).sendKeys(password); //verification code is written
        xpath(DataElements.NextButton).click();
        xpath(DataElements.Password).sendKeys("Aa13579Q!"); //password is written
        actionTab.sendKeys(Keys.TAB).perform();
        actionTab.sendKeys(Keys.TAB).perform();
        Sleep();
        actionEnter.sendKeys(Keys.ENTER).perform();
        Sleep();
        xpath(DataElements.FornowLate).click();    //late for now
        xpath(DataElements.FornowLate).click();    //late for now
        xpath(DataElements.Late).click();            //printed late
        xpath(DataElements.FornowLate).click();    //late for now
        driver.navigate().refresh();                //refresh
        Sleep();
        xpath(DataElements.Laststep).click();
        driver.get("https://twitter.com/twitter"); //Go to the account page to follow
        Sleep();
        xpath(DataElements.AccountInfoLabel).click();
        for (int i=0; i<3;i++){
            actionTab.sendKeys(Keys.TAB).perform();
        }
        actionEnter.sendKeys(Keys.ENTER).perform(); //Follow and press.
        Sleep();



    }
}
