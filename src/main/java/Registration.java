import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Registration {
    protected static WebDriver driver;//calling webdriver

    public String generateEmail(String startValue){
        String email= startValue.concat(new Date().toString().replaceAll(" ", "").replaceAll(":" ,""));
        return email;
    }
public static String randomDate(){
    DateFormat format = new SimpleDateFormat("ddMMyyHHmmss");
    return format.format(new Date());
}
    @BeforeMethod
    public void launchBrowser() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\java\\Resouces\\WebBrowser\\chromedriver.exe");//calling the chromedriver path
        driver = new ChromeDriver();//creating chrome driver object
        driver.manage().window().fullscreen();//to maximise the web page.
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);//waiting time before opening teh website
    }

    @Test
    public void registrationNopCommerce() {
        driver.get("https://demo.nopcommerce.com/");//web page url given
        driver.findElement(By.xpath("//a[@class='ico-register']")).click();//to click on register button for registration
        driver.findElement(By.id("FirstName")).sendKeys("Niketa");//registration form compulsory fields to be filled, Enter name
        driver.findElement(By.xpath("//input[@name='LastName']")).sendKeys("Parekh");//Enter surname
        driver.findElement(By.name("Email")).sendKeys(generateEmail("Niketa")+"@gmail.com");//Enter email
        driver.findElement(By.id("Password")).sendKeys("london26");//Enter password
        driver.findElement(By.xpath("//input[@name='ConfirmPassword']")).sendKeys("london26");//Enter Confirm password
        driver.findElement(By.xpath("//input[@type='submit' and @name='register-button']")).click();//click on register button
        String actualWelcomeMessage = driver.findElement(By.className("result")).getText();//storing value of acutal message in a strin variable
        String expectedMessage = "Your registration completed";//expected registered sucessfully message
        System.out.println(actualWelcomeMessage);//to print actual message
        Assert.assertEquals(actualWelcomeMessage, expectedMessage);

    }

    @Test
    public void emailafriend() throws InterruptedException {
        registrationNopCommerce();
        driver.findElement(By.xpath("//img")).click();//returning back to the home page for purchasing
        driver.findElement(By.linkText("Computers")).click();//click on computers category
        driver.findElement(By.linkText("Notebooks")).click();//click on notebook category
        driver.findElement(By.linkText("Apple MacBook Pro 13-inch")).click();//selecting preferred notebook
        driver.findElement(By.xpath("//input[@value='Email a friend']")).click();//clicking on the email a friend button to refer the product
        driver.findElement(By.className("friend-email")).sendKeys("karishma@gmail.com");//Enter friend's email address
        driver.findElement(By.xpath("//textarea")).sendKeys("Hi, this looks like a great product. Consider buying?");
        driver.findElement(By.name("send-email")).click();//Click on send button
        String expectedConfirmationMessage = "Your message has been sent.";
        String actualConfirmationMessage = driver.findElement(By.className("result")).getText();
        System.out.println(actualConfirmationMessage);
        Assert.assertEquals(actualConfirmationMessage, expectedConfirmationMessage);

    }

    //@AfterMethod
    public void teardown() {
        //to get validation report
        driver.close();//chrome browser close
    }

}


