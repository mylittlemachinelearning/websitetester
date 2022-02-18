package websitetester;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AutomatedSignUpTester {
	// define some  constants here, we want all the configuration in one place
	private static final String SITE_BASE_URL = "https://www.twinspires.com/";
	private static final String SITE_SIGN_UP_BUTTON_CLASS = "join-menu-item";
	private static final String SITE_REGISTER_FNAME = "fname";
	private static final String SITE_REGISTER_LNAME = "lname";
	private static final String SITE_REGISTER_EMAIL = "email";
	private static final String SITE_REGISTRATION_NEXT_BUTTON_CLASS = "reg-next--btn";
	private static final String SITE_REGISTRATION_FORM_BUILDER_ID = "form-builder-firstname-label";
	private static final String SITE_REGISTER_STEP_2_CLASS = "div.step-num.is-current";
	private static final String REGISTER_STEP_LABEL_1_COMPLETE = "2";
	private static final String REGISTER_STEP_DIV_CLASS_OFFER = "offer-image";
	
	// the Webdriver that does all the work
	ChromeDriver driver;
	
	// setting up our instance 
    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }
    
    // we want a fresh browser for each test
    @BeforeEach
    void setupTest() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("enable-automation"); 
		driver = new ChromeDriver(options);
    }
    
    // when the test is done, let's clean up our open browsers
    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testSignUp() {
    	// define some loading time timouts
    	java.time.Duration timeout = java.time.Duration.ofMillis(30000);
    	WebDriverWait wait = new WebDriverWait(driver, timeout);
    	// load the page 
        driver.get(SITE_BASE_URL);
        // make sure it's loaded so we can start
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(SITE_SIGN_UP_BUTTON_CLASS)));
        // click the join button
        driver.findElement(By.className(SITE_SIGN_UP_BUTTON_CLASS)).click();
        // wait until join page is loaded    
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SITE_REGISTRATION_FORM_BUILDER_ID)));
        // fill the fields
        driver.findElement(By.name(SITE_REGISTER_FNAME)).sendKeys("Hans");
        driver.findElement(By.name(SITE_REGISTER_LNAME)).sendKeys("Wiener");
        driver.findElement(By.name(SITE_REGISTER_EMAIL)).sendKeys("hans.wiener@gmail.com");
        // click the next button
        driver.findElement(By.className(SITE_REGISTRATION_NEXT_BUTTON_CLASS)).click();
        // wait for the next page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(SITE_REGISTER_STEP_2_CLASS)));
        // check the registration step
        String step = driver.findElement(By.cssSelector(SITE_REGISTER_STEP_2_CLASS)).getText();
        // make sure we're now in step 2
        Assertions.assertEquals(step, REGISTER_STEP_LABEL_1_COMPLETE);
    }

	
}
