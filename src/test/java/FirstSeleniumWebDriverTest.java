import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class FirstSeleniumWebDriverTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(7));
    }
    @Test
    public void testLogin(){
        driver.get("http://training.skillo-bg.com/");
        WebElement loginButton = driver.findElement(By.cssSelector("#nav-link-login"));
        loginButton.click();
        WebElement usernameField = driver.findElement(By.cssSelector("#defaultLoginFormUsername"));
        usernameField.sendKeys("elizabethtest");
        WebElement passwordFiled = driver.findElement(By.cssSelector("#defaultLoginFormPassword"));
        passwordFiled.sendKeys("password");
        WebElement signInButton = driver.findElement(By.cssSelector("#sign-in-button"));
        signInButton.click();
        WebElement logoutButton = driver.findElement(By.cssSelector(".fas.fa-sign-out-alt.fa-lg"));
        WebElement profileLink = driver.findElement(By.cssSelector("#nav-link-profile"));
        WebElement newPostLink = driver.findElement(By.cssSelector("#nav-link-new-post"));
        Assert.assertTrue(logoutButton.isDisplayed());
        Assert.assertTrue(profileLink.isDisplayed());
        Assert.assertTrue(newPostLink.isDisplayed());
    }
    @AfterMethod
    public void tearDown(){
        driver.close();
    }
}
