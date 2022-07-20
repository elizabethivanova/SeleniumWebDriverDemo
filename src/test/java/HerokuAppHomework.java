import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
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
    WebDriverWait wait;

    @BeforeMethod
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        actions = new Actions(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(7));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
    public void testDragAndDrop(){
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
    @Test
    public void testScroll() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/floating_menu");
        WebElement menu = driver.findElement(By.xpath("//*[@id='menu']"));
        Assert.assertTrue(menu.isDisplayed());

        actions.scrollByAmount(0, 3000).perform();
        Assert.assertTrue(menu.isDisplayed());
        actions.scrollByAmount(0, -2000).perform();
        Assert.assertTrue(menu.isDisplayed());
    }
    @Test
    public void testHover(){
        driver.get("https://the-internet.herokuapp.com/hovers");
        for (int i = 1; i <= 3; i++) {
            WebElement user = driver.findElement(By.xpath("//div[@class='figure']" + "[" + i + "]"));
            actions.moveToElement(user).perform();
            WebElement userCheck = driver.findElement(By.xpath("//div[@class='figcaption']/h5[contains(text(), 'name: user" + i + "')]"));
            Assert.assertTrue(userCheck.isDisplayed());
        }
    }
    @Test
    public void testDropDown(){
        driver.get("https://the-internet.herokuapp.com/dropdown");
        WebElement dropDown = driver.findElement(By.xpath("//select[@id='dropdown']"));
        WebElement startOption = driver.findElement(By.xpath("//select[@id='dropdown']/option[contains(text(), 'Please select an option')]"));
        Assert.assertEquals(startOption.getText(), "Please select an option");
        WebElement optionOne = driver.findElement(By.xpath("//select[@id='dropdown']/option[@value=1]"));
        WebElement optionTwo = driver.findElement(By.xpath("//select[@id='dropdown']/option[@value=2]"));
        dropDown.click();
        optionOne.click();
        Assert.assertEquals(optionOne.getText(), "Option 1");
        optionTwo.click();
        Assert.assertEquals(optionTwo.getText(), "Option 2");

    }
    @Test
    public void toDoDynamicContent(){
        driver.get("https://the-internet.herokuapp.com/dynamic_content");
        WebElement clickHereLink = driver.findElement(By.xpath("//a[contains(text(), 'click')]"));
        List<WebElement> text = driver.findElements(By.xpath("//*[@class='large-10 columns']"));
        //WebElement firstText = driver.findElement();
        WebElement firstAvatar = driver.findElement(By.xpath("//*[@src='/img/avatars/Original-Facebook-Geek-Profile-Avatar-5.jpg']"));
        WebElement secondAvatar = driver.findElement(By.xpath("//*[@src='/img/avatars/Original-Facebook-Geek-Profile-Avatar-6.jpg']"));
        //WebElement dynamicAvatar = driver.findElement(By.xpath("//*[@src='/img/avatars/Original-Facebook-Geek-Profile-Avatar-7.jpg']"));
        String firstText = text.get(0).getText();
        String secondText = text.get(1).getText();
        String dynamicText = text.get(2).getText();

        for (int i = 1; i <= 3; i++) {
            clickHereLink.click();
            Assert.assertEquals(firstText, "Accusantium eius ut architecto neque vel voluptatem vel nam eos minus ullam dolores voluptates enim sed voluptatem rerum qui sapiente nesciunt aspernatur et accusamus laboriosam culpa tenetur hic aut placeat error autem qui sunt.");
            Assert.assertEquals(secondText, "Omnis fugiat porro vero quas tempora quis eveniet ab officia cupiditate culpa repellat debitis itaque possimus odit dolorum et iste quibusdam quis dicta autem sint vel quo vel consequuntur dolorem nihil neque sunt aperiam blanditiis.");
            Assert.assertNotEquals(dynamicText, "Natus error cumque nemo harum ipsa nihil minus temporibus quia autem nisi quidem asperiores amet maxime voluptatibus aut autem est blanditiis voluptas facilis distinctio sit iure et architecto aut ut veritatis.");
            Assert.assertEquals(firstAvatar.getText(), "/img/avatars/Original-Facebook-Geek-Profile-Avatar-5.jpg");
            Assert.assertEquals(secondAvatar.getText(), "/img/avatars/Original-Facebook-Geek-Profile-Avatar-6.jpg");
        }
    }
    @Test
    public void dynamicControls(){
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
        WebElement removeButton = driver.findElement(By.xpath("//button[@autocomplete='off' and contains(text(), 'Remove')]"));
        WebElement checkbox = driver.findElement(By.xpath("//*[@id='checkbox']"));
        Assert.assertTrue(checkbox.isDisplayed());
        By loadingBarBy = By.xpath("//*[@id='loading']");
        removeButton.click();
        WebElement loadingBar = driver.findElement(loadingBarBy);
        wait.until(ExpectedConditions.invisibilityOf(loadingBar));
        WebElement message = driver.findElement(By.xpath("//*[@id='message']"));
        Assert.assertTrue(message.isDisplayed());
    }
    @Test
    public void dynamicLoading(){
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
        WebElement startBtn = driver.findElement(By.xpath("//div[@id='start']/button"));
        By loadingBarBy = By.xpath("//*[@id='loading']");
        startBtn.click();
        WebElement loadingBar = driver.findElement(loadingBarBy);
        wait.until(ExpectedConditions.invisibilityOf(loadingBar));
        WebElement finishTxt = driver.findElement(By.xpath("//*[@id='finish']"));
        Assert.assertTrue(finishTxt.isDisplayed());

    }
    @Test
    public void multipleWindows(){
        driver.get("https://the-internet.herokuapp.com/windows");
        String parentWindow = driver.getWindowHandle();
        String newWindow = "https://the-internet.herokuapp.com/windows/new";
        WebElement clickHereLink = driver.findElement(By.xpath("//a[@href='/windows/new']"));
        clickHereLink.click();
        //driver.switchTo().window(newWindow);
        By txtNewWindowBy = By.xpath("//div[@class='example']");
        WebElement txtNewWindow = driver.findElement(txtNewWindowBy);
        wait.until(ExpectedConditions.visibilityOf(txtNewWindow));

        Assert.assertTrue(txtNewWindow.isDisplayed());
    }
}
