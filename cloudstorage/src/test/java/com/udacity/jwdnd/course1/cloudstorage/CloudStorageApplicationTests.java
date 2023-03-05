package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-button")));
		WebElement buttonSignUp = driver.findElement(By.id("submit-button"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-button")));
		WebElement loginButton = driver.findElement(By.id("submit-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		WebElement login = driver.findElement(By.id("login-link"));
		login.click();
		// Check if we have been redirected to the log in page.
		assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void Test_Logout() {

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		doMockSignUp("logout", "Test", "logout", "12663");
		doLogIn("logout", "12663");

		WebElement logout = driver.findElement(By.id("logoutBTN"));
		logout.click();

		driver.get("http://localhost:" + this.port + "/home");

		Assertions.assertNotEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
	}

	//  Test for Note Creation, Viewing, Editing, and Deletion
	@Test
	public void Test_Note() {

		doMockSignUp("Note", "Test", "Note", "123456");
		doLogIn("Note", "123456");

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement nav = driver.findElement(By.id("nav-notes-tab"));
		nav.click();
		//adding note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AddNoteBTN")));
		WebElement add = driver.findElement(By.id("AddNoteBTN"));
		add.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title")));
		WebElement title = driver.findElement(By.id("title"));
		title.sendKeys("Yousef");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description")));
		WebElement description = driver.findElement(By.id("description"));
		description.sendKeys("Alanazi");


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteSBMT")));
		WebElement submit = driver.findElement(By.id("noteSBMT"));
		submit.click();

		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Note unsaved");
		}

		WebElement home = driver.findElement(By.id("return"));
		home.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement nav2 = driver.findElement(By.id("nav-notes-tab"));
		nav2.click();
		//editing note
		try {
			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ntEd")));
			WebElement edit = driver.findElement(By.id("ntEd"));
			edit.click();
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			System.out.println("edit");
		}

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title")));
		WebElement title2 = driver.findElement(By.id("title"));
		title2.sendKeys("Y");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description")));
		WebElement description2 = driver.findElement(By.id("description"));
		description2.sendKeys("A");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteSBMT")));
		WebElement submit2 = driver.findElement(By.id("noteSBMT"));
		submit2.click();

		WebElement home2 = driver.findElement(By.id("return"));
		home2.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement nav3 = driver.findElement(By.id("nav-notes-tab"));
		nav3.click();
		//deleting note.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ntRemove")));
		WebElement remove = driver.findElement(By.id("ntRemove"));
		remove.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		WebElement success = driver.findElement(By.id("success"));
		String expected = success.getText();
		System.out.println(expected);

		Assertions.assertEquals(expected, "Success");
	}

	// Tests for Credential Creation, Viewing, Editing, and Deletion.
	@Test
	public void Test_Credential(){

		doMockSignUp("Credential", "Test", "Credential", "123456");
		doLogIn("Credential", "123456");

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement nav = driver.findElement(By.id("nav-credentials-tab"));
		nav.click();

		//adding credential
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AddCr")));
		WebElement add = driver.findElement(By.id("AddCr"));
		add.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement url = driver.findElement(By.id("credential-url"));
		url.sendKeys("https://learn.udacity.com/");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement username = driver.findElement(By.id("credential-username"));
		username.sendKeys("Yousef");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement password = driver.findElement(By.id("credential-password"));
		password.sendKeys("Alanazi501");


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveCredential")));
		WebElement submit = driver.findElement(By.id("btnSaveCredential"));
		submit.click();

		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Credential Error");
		}

		WebElement home = driver.findElement(By.id("return"));
		home.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement nav2 = driver.findElement(By.id("nav-credentials-tab"));
		nav2.click();

		// editing credential
		try {
			webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("EditeCr")));
			WebElement edit = driver.findElement(By.id("EditeCr"));
			edit.click();
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			System.out.println("edit ");
		}


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement url2 = driver.findElement(By.id("credential-url"));
		url2.sendKeys("https://learn.udacity.com/");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement username2 = driver.findElement(By.id("credential-username"));
		username2.sendKeys("Y");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement password2 = driver.findElement(By.id("credential-password"));
		password2.sendKeys("A");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveCredential")));
		WebElement submit2 = driver.findElement(By.id("btnSaveCredential"));
		submit2.click();


		WebElement home2 = driver.findElement(By.id("return"));
		home2.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement nav3 = driver.findElement(By.id("nav-credentials-tab"));
		nav3.click();

		//deleting credential.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("DeleteCr")));
		WebElement remove = driver.findElement(By.id("DeleteCr"));
		remove.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		WebElement success = driver.findElement(By.id("success"));
		String expected = success.getText();
		System.out.println(expected);

		assertEquals(expected, "Success");

	}


}
