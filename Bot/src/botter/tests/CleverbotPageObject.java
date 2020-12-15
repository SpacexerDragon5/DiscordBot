package botter.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CleverbotPageObject {

    private final WebDriver driver;
    private String path = "C:\\Users\\Iss38bcT\\chromedriver.exe";
    public CleverbotPageObject(WebDriver driver) {
        this.driver = driver;
        goToCleverbotPage();
    }

    public void goToCleverbotPage() {
        driver.get("http://www.cleverbot.com");
        waitForPageToLoad();
    }

    public String askAndGetResponse(String question) {
        writeQuestion(question, driver);
        return getResponse(driver);
    }

    private String getResponse(WebDriver driver) {
        while (!isFinishedResponding()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        List<WebElement> webElements = driver.findElements(By.className("bot"));
        return webElements.get(webElements.size() - 1).getText();
    }

    private void writeQuestion(String question, WebDriver driver) {
        WebElement element = driver.findElement(By.className("stimulus"));
        element.sendKeys(question);
        clickSayItButton(driver);
        waitForPageToLoad();
    }

    private void clickSayItButton(WebDriver driver) {
        WebElement e = driver.findElement(By.className("sayitbutton"));
        e.click();
    }

    private boolean isFinishedResponding() {
        try {
            WebElement e = driver.findElement(By.id("snipTextIcon"));
            return e != null && e.isDisplayed();
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    private Boolean waitForPageToLoad() {

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //keep executing the given JS till it returns "true", when page is fully loaded and ready
  try {
	wait.wait(10000);
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
        return true;
    }

    public void close() {
        driver.close();
    }
}