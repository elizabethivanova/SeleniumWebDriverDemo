import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class HerokuAppHomework {
    WebDriver driver;
    Actions actions;
    Alert alert;

    @BeforeMethod
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        actions = new Actions(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(7));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
    @Test
    public void testAddRemoveElement(){
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");
        WebElement addElementButton = driver.findElement(By.cssSelector(".example>button"));
        Assert.assertTrue(addElementButton.isDisplayed());

        By listElements = By.cssSelector("#elements>button");
        List<WebElement> listDeletes = driver.findElements(listElements);
        Assert.assertEquals(listDeletes.size(), 0);

        int expectedElements = 0;
        Random ran = new Random();
        int x = ran.nextInt(6) + 5;

        for (int i = 1; i <= x; i++) {
            addElementButton.click();
            expectedElements++;
            listDeletes = driver.findElements(listElements);
            Assert.assertEquals(listDeletes.size(), expectedElements);
        }

        for (int i = 1; i <= x; i++) {
            WebElement deleteButton = driver.findElement(By.xpath("//div[@id='elements']/button[1]"));
            deleteButton.click();
            expectedElements--;
            listDeletes = driver.findElements(listElements);
            Assert.assertEquals(listDeletes.size(), expectedElements);
        }
    }
    @Test
    public void testBasicAuth(){
        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");
        WebElement congratulationsText = driver.findElement(By.xpath("//*[contains(text(), 'Congratulations!')]"));
        Assert.assertTrue(congratulationsText.isDisplayed());
    }
    @Test
    public void testChallengingDom(){
        driver.get("https://the-internet.herokuapp.com/challenging_dom");
        List<WebElement> buttons = driver.findElements(By.cssSelector(".large-2.columns>a"));
        Assert.assertEquals(buttons.size(), 3);
        WebElement canvas = driver.findElement(By.cssSelector("#canvas"));
        Assert.assertTrue(canvas.isDisplayed());
        WebElement table = driver.findElement(By.cssSelector(".large-10.columns>table"));
        Assert.assertTrue(table.isDisplayed());
    }
    @Test
    public void testCheckboxes(){
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        WebElement checkboxOne = driver.findElement(By.xpath("//form[@id='checkboxes']/input[1]"));
        WebElement checkboxTwo = driver.findElement(By.xpath("//form[@id='checkboxes']/input[2]"));
        Assert.assertFalse(checkboxOne.isSelected());
        Assert.assertTrue(checkboxTwo.isSelected());
        checkboxOne.click();
        Assert.assertTrue(checkboxOne.isSelected());
        Assert.assertTrue(checkboxTwo.isSelected());
        checkboxTwo.click();
        Assert.assertTrue(checkboxOne.isSelected());
        Assert.assertFalse(checkboxTwo.isSelected());
        checkboxOne.click();
        Assert.assertFalse(checkboxOne.isSelected());
        Assert.assertFalse(checkboxTwo.isSelected());

    }
    @Test
    public void testContextMenu(){
        driver.get("https://the-internet.herokuapp.com/context_menu");
        WebElement box = driver.findElement(By.cssSelector("#hot-spot"));
        Assert.assertTrue(box.isDisplayed());
        actions.contextClick(box).perform();

        alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(), "You selected a context menu");
        alert.accept();
    }
    public void checkFourElements(){
        List<WebElement> elementsList = driver.findElements(By.xpath("//ul/li"));
        Assert.assertEquals(elementsList.get(0).getText(), "Home");
        Assert.assertEquals(elementsList.get(1).getText(), "About");
        Assert.assertEquals(elementsList.get(2).getText(), "Contact Us");
        Assert.assertEquals(elementsList.get(3).getText(), "Portfolio");
    }
    public void checkFiveElements(){
        checkFourElements();
        List<WebElement> elementsList = driver.findElements(By.xpath("//ul/li"));
        Assert.assertEquals(elementsList.get(4).getText(), "Gallery");
    }
    @Test(invocationCount = 10)
    public void testDisappearingElements(){
        driver.get("https://the-internet.herokuapp.com/disappearing_elements");
        List<WebElement> elementsList = driver.findElements(By.xpath("//ul/li"));
        if(elementsList.size() == 4){
            checkFourElements();
        }else if(elementsList.size() == 5){
            checkFiveElements();
        }
    }
    @Test
    public void testDragAndDrop() throws InterruptedException {
        driver.get("https://jqueryui.com/droppable/");
        WebElement iframe = driver.findElement(By.xpath("//iframe"));
        driver.switchTo().frame(iframe);
        WebElement draggable = driver.findElement(By.xpath("//*[@id='draggable']"));
        WebElement droppable = driver.findElement(By.xpath("//*[@id='droppable']"));
        WebElement textBox = driver.findElement(By.xpath("//*[@id='droppable']/p"));
        Assert.assertEquals(textBox.getText(), "Drop here");
        //Thread.sleep(2000);
        actions.dragAndDrop(draggable, droppable).perform();
        textBox = driver.findElement(By.xpath("//*[@id='droppable']/p"));
        Assert.assertEquals(textBox.getText(), "Dropped!");

    }
}
