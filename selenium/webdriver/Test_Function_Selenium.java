package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Test_Function_Selenium {
	WebDriver driver;
	Select select;
	String firstName, lastName, day, month, year, emailAddress, companyName, password;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {

		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();

		firstName = "Nguyen";
		lastName = "Hien";
		day = "28";
		month = "August";
		year = "2001";
		emailAddress = "nguyenhien" + generateRandomNumber() + "@gmail.com";
		companyName = "Auto Test Selenium";
		password = "123456";

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://demo.nopcommerce.com/");

	}

/*	@Test
	public void TC_01_Register_Empty_Data() {
		driver.findElement(By.className("ico-register")).click();
		// Action

		driver.findElement(By.id("register-button")).click();

		// Verify (Actual data = Expected Data)

		Assert.assertEquals(driver.findElement(By.id("FirstName-error")).getText(), "First name is required.");
		Assert.assertEquals(driver.findElement(By.id("LastName-error")).getText(), "Last name is required.");
		Assert.assertEquals(driver.findElement(By.id("Email-error")).getText(), "Email is required.");
		Assert.assertEquals(driver.findElement(By.id("Password-error")).getText(), "Password is required.");
		Assert.assertEquals(driver.findElement(By.id("ConfirmPassword-error")).getText(), "Password is required.");
	}

	@Test
	public void TC_02_Register_Invalid_Email() {
		driver.findElement(By.className("ico-register")).click();
		// Action
		driver.findElement(By.id("gender-male")).click();
		driver.findElement(By.id("FirstName")).sendKeys(firstName);
		driver.findElement(By.id("LastName")).sendKeys(lastName);
		driver.findElement(By.id("Email")).sendKeys("123@123");
		driver.findElement(By.id("Company")).sendKeys(companyName);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
		driver.findElement(By.id("register-button")).click();

		// Verify (Actual data = Expected Data)

		Assert.assertEquals(driver.findElement(By.xpath("//li[text() ='Wrong email' ]")).getText(), "Wrong email");
	}
*/
	@Test
	public void TC_03_Register_Valid_Information() {
		driver.findElement(By.className("ico-register")).click();
		driver.findElement(By.id("gender-female")).click();
		driver.findElement(By.id("FirstName")).sendKeys(firstName);
		driver.findElement(By.id("LastName")).sendKeys(lastName);

		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		select.selectByVisibleText(day);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), day);

		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		select.selectByVisibleText(month);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);

		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		select.selectByVisibleText(year);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);

		driver.findElement(By.id("Email")).sendKeys(emailAddress);
		driver.findElement(By.id("Company")).sendKeys(companyName);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
		driver.findElement(By.id("register-button")).click();

		// vertify - output data
		Assert.assertEquals(driver.findElement(By.className("result")).getText(), "Your registration completed");

	}
/*
	@Test
	public void TC_04_Register_Email_Already_Exists() {
		driver.findElement(By.className("ico-register")).click();
		driver.findElement(By.id("gender-female")).click();
		driver.findElement(By.id("FirstName")).sendKeys(firstName);
		driver.findElement(By.id("LastName")).sendKeys(lastName);

		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		select.selectByVisibleText(day);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), day);

		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		select.selectByVisibleText(month);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);

		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		select.selectByVisibleText(year);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);

		driver.findElement(By.id("Email")).sendKeys(emailAddress);
		driver.findElement(By.id("Company")).sendKeys(companyName);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
		driver.findElement(By.id("register-button")).click();

		// vertify - output data
		Assert.assertEquals(
				driver.findElement(By.xpath("//li[text() ='The specified email already exists' ]")).getText(),
				"The specified email already exists");
	}

	@Test
	public void TC_05_Register_Password_Less_Than_6_Chars() {
		driver.findElement(By.className("ico-register")).click();
		driver.findElement(By.id("gender-female")).click();
		driver.findElement(By.id("FirstName")).sendKeys(firstName);
		driver.findElement(By.id("LastName")).sendKeys(lastName);

		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		select.selectByVisibleText(day);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), day);

		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		select.selectByVisibleText(month);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);

		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		select.selectByVisibleText(year);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);

		driver.findElement(By.id("Email")).sendKeys(emailAddress);
		driver.findElement(By.id("Company")).sendKeys(companyName);
		driver.findElement(By.id("Password")).sendKeys("12345");
		driver.findElement(By.id("ConfirmPassword")).sendKeys("12345");
		driver.findElement(By.id("register-button")).click();

		Assert.assertEquals(driver.findElement(By.xpath("//p[text() ='Password must meet the following rules: ' ]")).getText(), "Password must meet the following rules:");
		Assert.assertEquals(driver.findElement(By.xpath("//li[text() ='must have at least 6 characters' ]")).getText(), "must have at least 6 characters");
	}

	@Test
	public void TC_06_Register_Incorrect_Confirm_Password() {
		driver.findElement(By.className("ico-register")).click();
		driver.findElement(By.id("gender-female")).click();
		driver.findElement(By.id("FirstName")).sendKeys(firstName);
		driver.findElement(By.id("LastName")).sendKeys(lastName);

		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		select.selectByVisibleText(day);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), day);

		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		select.selectByVisibleText(month);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);

		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		select.selectByVisibleText(year);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);

		driver.findElement(By.id("Email")).sendKeys(emailAddress);
		driver.findElement(By.id("Company")).sendKeys(companyName);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("ConfirmPassword")).sendKeys("1234567");
		driver.findElement(By.id("register-button")).click();

		// vertify - output data
		Assert.assertEquals(driver.findElement(By.id("ConfirmPassword-error")).getText(), "The password and confirmation password do not match.");
	}
	
	@Test
	public void TC_07_Login_Empty_Data() {
		driver.findElement(By.className("ico-login")).click();
		
//		driver.findElement(By.id("Email")).sendKeys("");
//		driver.findElement(By.id("Password")).sendKeys("");
		
		
		driver.findElement(By.xpath("//div[@class= 'form-fields']/following-sibling::div/button[1]")).click();
		
		Assert.assertEquals(driver.findElement(By.id("Email-error")).getText(), "Please enter your email");
		
	}

	@Test
	public void TC_08_Login_Invalid_Email() {
		driver.findElement(By.className("ico-login")).click();
		
		driver.findElement(By.id("Email")).sendKeys("jfhdbgsjhbs@");
		driver.findElement(By.id("Password")).sendKeys(password);

		driver.findElement(By.xpath("//div[@class= 'form-fields']/following-sibling::div/button[1]")).click();
		
		Assert.assertEquals(driver.findElement(By.id("Email-error")).getText(), "Wrong email");
		
	}

	@Test
	public void TC_09_Login_Email_Unregistered() {
		driver.findElement(By.className("ico-login")).click();
		
		driver.findElement(By.id("Email")).sendKeys("heinhiennfhcdsj@gmail.com");
		driver.findElement(By.id("Password")).sendKeys(password);
		
		driver.findElement(By.xpath("//div[@class= 'form-fields']/following-sibling::div/button[1]")).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(), 'Login was unsuccessful')]")).getText(), "Login was unsuccessful. Please correct the errors and try again.\nNo customer account found");

//		driver.findElement(By.xpath("//div[contains(text(), 'Login was unsuccessful')]")).getText(); 
		
//		System.out.println(driver.findElement(By.xpath("//div[contains(text(), 'Login was unsuccessful')]")).getText());
	}

	@Test
	public void TC_10_Login_Email_Registered_Empty_Password() {
		driver.findElement(By.className("ico-login")).click();
		
		driver.findElement(By.id("Email")).sendKeys(emailAddress);
		driver.findElement(By.id("Password")).sendKeys("");
		
		driver.findElement(By.xpath("//div[@class= 'form-fields']/following-sibling::div/button[1]")).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(), 'Login was unsuccessful')]")).getText(), "Login was unsuccessful. Please correct the errors and try again.\nThe credentials provided are incorrect");

	}

	@Test
	public void TC_11_Login_Email_Registered_Incorrect_Password() {
		driver.findElement(By.className("ico-login")).click();
		
		driver.findElement(By.id("Email")).sendKeys(emailAddress);
		driver.findElement(By.id("Password")).sendKeys("654732tr572");
		
		driver.findElement(By.xpath("//div[@class= 'form-fields']/following-sibling::div/button[1]")).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(), 'Login was unsuccessful')]")).getText(), "Login was unsuccessful. Please correct the errors and try again.\nThe credentials provided are incorrect");

	}
*/
	@Test
	public void TC_12_Login_Email_Registered_Correct_Password() {
		driver.findElement(By.className("ico-login")).click();
		
		driver.findElement(By.id("Email")).sendKeys(emailAddress);
		driver.findElement(By.id("Password")).sendKeys(password);
		
		driver.findElement(By.xpath("//div[@class= 'form-fields']/following-sibling::div/button[1]")).click();
		
		Assert.assertEquals(driver.getCurrentUrl(), "https://demo.nopcommerce.com/");

	}

	@Test
	public void TC_13_Wishlist_Empty_Data() {
		driver.findElement(By.className("wishlist-label")).click();
		
		Assert.assertEquals(driver.findElement(By.className("no-data")).getText(), "The wishlist is empty!");
		
	}
	
	@Test
	public void TC_14_Add_Product_To_Wishlist() {
		driver.get("https://demo.nopcommerce.com/");
		
		driver.findElement(By.cssSelector("img[alt='Picture of HTC One M8 Android L 5.0 Lollipop']")).findElement(By.xpath("//a[text()= 'HTC One M8 Android L 5.0 Lollipop']")).click();
		
		driver.findElement(By.id("add-to-wishlist-button-18")).click();
		
		driver.findElement(By.className("wishlist-label")).click();
		
//		System.out.println(driver.findElement(By.xpath("//p[@class = 'content']")).getText());
		Assert.assertEquals(driver.getCurrentUrl(), "https://demo.nopcommerce.com/wishlist");
		Assert.assertEquals(driver.findElement(By.xpath("//span[text()='M8_HTC_5L']")).getText(), "M8_HTC_5L");
		Assert.assertEquals(driver.findElement(By.xpath("//a[text()='HTC One M8 Android L 5.0 Lollipop']")).getText(), "HTC One M8 Android L 5.0 Lollipop");
		
	}
	
	@Test
	public void TC_15_Add_Product_Form_Wishlist_To_Cart() {
//		driver.get("https://demo.nopcommerce.com/wishlist");
		driver.findElement(By.xpath("//input[@name='addtocart']")).click();
		driver.findElement(By.xpath("//button[@class='button-2 wishlist-add-to-cart-button']")).click();
	
		
		Assert.assertEquals(driver.getCurrentUrl(), "https://demo.nopcommerce.com/cart");
		Assert.assertEquals(driver.findElement(By.xpath("//span[text()='M8_HTC_5L']")).getText(), "M8_HTC_5L");
		Assert.assertEquals(driver.findElement(By.xpath("//td[@class = 'product']/a[1]")).getText(), "HTC One M8 Android L 5.0 Lollipop");
//		System.out.println(driver.findElement(By.xpath("//td[@class = 'product']/a[1]")).getText());
		
	}

	@Test
	public void TC_16_Remove_Product_From_Wishlist() {
		driver.get("https://demo.nopcommerce.com/");
		
		driver.findElement(By.cssSelector("img[alt='Picture of HTC One M8 Android L 5.0 Lollipop']")).findElement(By.xpath("//a[text()= 'HTC One M8 Android L 5.0 Lollipop']")).click();
		
		driver.findElement(By.id("add-to-wishlist-button-18")).click();
		
		driver.findElement(By.className("wishlist-label")).click();
		
		driver.findElement(By.className("//button[@class = 'remove-btn']")).click();
		
		Assert.assertEquals(driver.findElement(By.className("no-data")).getText(), "The wishlist is empty!");
	
	}
	
	@Test
	public void TC_17_() {
		
	}
	
	@Test
	public void TC_18_() {
		
	}
	
	@Test
	public void TC_19_() {
		
	}
	
	@Test
	public void TC_20_() {
		
	}
	
	@Test
	public void TC_21_() {
		
	}
	
	@AfterClass
	public void afterClass() {
//		driver.quit();
	}

	public int generateRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}

}