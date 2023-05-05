package NguyenThiHien_2019606119;

import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test_Function_nopCommerce {

	String[] ketqua = new String[21];
	int t = 0, pa = 0, fa = 0, nu = 0;
	
	WebDriver driver;
	Select select;
	String firstName, lastName,emailAddress, month, emailAddressAuto, companyName, password, cfpassword, country, city, address1, address2, sdt, fax, cardHolderName, cardNumber, expireYear, cardCode;
	Integer day, year, slg, zipCode;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() throws IOException {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();

		emailAddressAuto = "nguyenhien" + generateRandomNumber() + "@gmail.com";
		
		File file = new File("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow rowmail01=sheet.getRow(3);
		XSSFRow rowmail02=sheet.getRow(4);
		rowmail01.createCell(14).setCellValue(emailAddressAuto);
		rowmail02.createCell(14).setCellValue(emailAddressAuto);
		XSSFSheet sheet1 = wb.getSheetAt(1);
		XSSFRow rowmail11=sheet1.getRow(4);
		XSSFRow rowmail12=sheet1.getRow(5);
		XSSFRow rowmail13=sheet1.getRow(6);
		rowmail11.createCell(9).setCellValue(emailAddressAuto);
		rowmail12.createCell(9).setCellValue(emailAddressAuto);
		rowmail13.createCell(9).setCellValue(emailAddressAuto);
		
		FileOutputStream outputStream = new FileOutputStream("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		wb.write(outputStream);
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://demo.nopcommerce.com/");
	}

	@Test
	public void TC_01_Register_Empty_Data() throws IOException {
		driver.findElement(By.className("ico-register")).click();
		driver.findElement(By.id("register-button")).click();

		WebElement CF1 = driver.findElement(By.id("FirstName-error"));
		WebElement CF2 = driver.findElement(By.id("LastName-error"));
		WebElement CF3 = driver.findElement(By.id("Email-error"));
		WebElement CF4 = driver.findElement(By.id("Password-error"));
		WebElement CF5 = driver.findElement(By.id("ConfirmPassword-error"));

		Assert.assertEquals(CF1.getText(), "First name is required.");
		Assert.assertEquals(CF2.getText(), "Last name is required.");
		Assert.assertEquals(CF3.getText(), "Email is required.");
		Assert.assertEquals(CF4.getText(), "Password is required.");
		Assert.assertEquals(CF5.getText(), "Password is required.");
		
		if (CF1.isDisplayed() && CF2.isDisplayed() && CF3.isDisplayed() && CF4.isDisplayed() && CF5.isDisplayed()) {
			
			ketqua[t] = "Pass";
			t++;			
		} else {
			ketqua[t] = "Failed";
			t++;			
		}		
	}

	@Test
	public void TC_02_Register_Invalid_Email() throws IOException {
		driver.findElement(By.className("ico-register")).click();
		
		File file = new File("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow rowinvalid = sheet.getRow(2);
		XSSFCell cell9 = rowinvalid.getCell(9);
		XSSFCell cell10 = rowinvalid.getCell(10);
		XSSFCell cell11 = rowinvalid.getCell(11);
		day = (int) cell11.getNumericCellValue();
		String d= String.valueOf(day);	
		XSSFCell cell12 = rowinvalid.getCell(12);
		XSSFCell cell13 = rowinvalid.getCell(13);
		year = (int) cell13.getNumericCellValue();
		String y = String.valueOf(year);	
		XSSFCell cell14 = rowinvalid.getCell(14);		
		XSSFCell cell15 = rowinvalid.getCell(15);		
		XSSFCell cell16 = rowinvalid.getCell(16);		
		XSSFCell cell17 = rowinvalid.getCell(17);
		// Action
		driver.findElement(By.id("gender-male")).click();
		driver.findElement(By.id("FirstName")).sendKeys(cell9.getStringCellValue());
		driver.findElement(By.id("LastName")).sendKeys(cell10.getStringCellValue());
		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		select.selectByVisibleText(d);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), d);

		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		select.selectByVisibleText(cell12.getStringCellValue());
		Assert.assertEquals(select.getFirstSelectedOption().getText(), cell12.getStringCellValue());

		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		select.selectByVisibleText(y);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), y);
		
		driver.findElement(By.id("Email")).sendKeys(cell14.getStringCellValue());
		driver.findElement(By.id("Company")).sendKeys(cell15.getStringCellValue());
		driver.findElement(By.id("Password")).sendKeys(cell16.getStringCellValue());
		driver.findElement(By.id("ConfirmPassword")).sendKeys(cell17.getStringCellValue());
		driver.findElement(By.id("register-button")).click();

		Assert.assertEquals(driver.findElement(By.xpath("//li[text() ='Wrong email' ]")).getText(), "Wrong email");
		
		if (driver.findElement(By.xpath("//li[text() ='Wrong email' ]")).isDisplayed()) {			
			ketqua[t] = "Pass";
			t++;			
		} else {			
			ketqua[t] = "Failed";
			t++;			
		}				
	}

	@Test
	public void TC_03_Register_Valid_Information() throws IOException {

		driver.findElement(By.className("ico-register")).click();
		
		File file = new File("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow rowinvalid = sheet.getRow(3);
		XSSFCell cell9 = rowinvalid.getCell(9);
		XSSFCell cell10 = rowinvalid.getCell(10);
		XSSFCell cell11 = rowinvalid.getCell(11);
		day = (int) cell11.getNumericCellValue();
		String d= String.valueOf(day);		
		XSSFCell cell12 = rowinvalid.getCell(12);
		XSSFCell cell13 = rowinvalid.getCell(13);
		year = (int) cell13.getNumericCellValue();
		String y = String.valueOf(year);		
		XSSFCell cell15 = rowinvalid.getCell(15);
		XSSFCell cell16 = rowinvalid.getCell(16);
		XSSFCell cell17 = rowinvalid.getCell(17);
		
		driver.findElement(By.id("gender-female")).click();
		driver.findElement(By.id("FirstName")).sendKeys(cell9.getStringCellValue());
		driver.findElement(By.id("LastName")).sendKeys(cell10.getStringCellValue());

		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		select.selectByVisibleText(d);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), d);

		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		select.selectByVisibleText(cell12.getStringCellValue());
		Assert.assertEquals(select.getFirstSelectedOption().getText(), cell12.getStringCellValue());

		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		select.selectByVisibleText(y);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), y);

		driver.findElement(By.id("Email")).sendKeys(emailAddressAuto);
		driver.findElement(By.id("Company")).sendKeys(cell15.getStringCellValue());
		driver.findElement(By.id("Password")).sendKeys(cell16.getStringCellValue());
		driver.findElement(By.id("ConfirmPassword")).sendKeys(cell17.getStringCellValue());
		driver.findElement(By.id("register-button")).click();
		// vertify - output data
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='page registration-result-page']/div[2]/div[1]")).getText(), "Your registration completed");
		
		if (driver.findElement(By.xpath("//div[@class='page registration-result-page']/div[2]/div[1]")).isDisplayed()) {		
			ketqua[t] = "Pass";
			t++;			
		} else {			
			ketqua[t] = "Failed";
			t++;			
		}		
	}

	@Test
	public void TC_04_Register_Email_Already_Exists() throws IOException {
		driver.findElement(By.className("ico-register")).click();
		File file = new File("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow rowinvalid = sheet.getRow(4);
		XSSFCell cell9 = rowinvalid.getCell(9);
		XSSFCell cell10 = rowinvalid.getCell(10);
		XSSFCell cell11 = rowinvalid.getCell(11);
		day = (int) cell11.getNumericCellValue();
		String d= String.valueOf(day);
		
		XSSFCell cell12 = rowinvalid.getCell(12);
		XSSFCell cell13 = rowinvalid.getCell(13);
		year = (int) cell13.getNumericCellValue();
		String y = String.valueOf(year);
		
		XSSFCell cell15 = rowinvalid.getCell(15);
		XSSFCell cell16 = rowinvalid.getCell(16);
		XSSFCell cell17 = rowinvalid.getCell(17);
		
		driver.findElement(By.id("gender-female")).click();
		driver.findElement(By.id("FirstName")).sendKeys(cell9.getStringCellValue());
		driver.findElement(By.id("LastName")).sendKeys(cell10.getStringCellValue());

		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		select.selectByVisibleText(d);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), d);

		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		select.selectByVisibleText(cell12.getStringCellValue());
		Assert.assertEquals(select.getFirstSelectedOption().getText(), cell12.getStringCellValue());

		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		select.selectByVisibleText(y);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), y);

		driver.findElement(By.id("Email")).sendKeys(emailAddressAuto);
		driver.findElement(By.id("Company")).sendKeys(cell15.getStringCellValue());
		driver.findElement(By.id("Password")).sendKeys(cell16.getStringCellValue());
		driver.findElement(By.id("ConfirmPassword")).sendKeys(cell17.getStringCellValue());
		driver.findElement(By.id("register-button")).click();
		// vertify - output data
		Assert.assertEquals(
				driver.findElement(By.xpath("//li[text() ='The specified email already exists' ]")).getText(),
				"The specified email already exists");
		
		if (driver.findElement(By.xpath("//li[text() ='The specified email already exists' ]")).isDisplayed()) {			
			ketqua[t] = "Pass";
			t++;			
		} else {			
			ketqua[t] = "Failed";
			t++;			
		}		
	}

	@Test
	public void TC_05_Register_Password_Less_Than_6_Chars() throws IOException {
		driver.findElement(By.className("ico-register")).click();
		
		File file = new File("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow rowinvalid = sheet.getRow(5);
		XSSFCell cell9 = rowinvalid.getCell(9);
		XSSFCell cell10 = rowinvalid.getCell(10);		
		XSSFCell cell11 = rowinvalid.getCell(11);
		day = (int) cell11.getNumericCellValue();
		String d= String.valueOf(day);
		
		XSSFCell cell12 = rowinvalid.getCell(12);		
		XSSFCell cell13 = rowinvalid.getCell(13);
		year = (int) cell13.getNumericCellValue();
		String y = String.valueOf(year);
		
		XSSFCell cell14 = rowinvalid.getCell(14);		
		XSSFCell cell15 = rowinvalid.getCell(15);		
		XSSFCell cell16 = rowinvalid.getCell(16);		
		XSSFCell cell17 = rowinvalid.getCell(17);
		
		driver.findElement(By.id("gender-female")).click();
		driver.findElement(By.id("FirstName")).sendKeys(cell9.getStringCellValue());
		driver.findElement(By.id("LastName")).sendKeys(cell10.getStringCellValue());

		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		select.selectByVisibleText(d);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), d);

		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		select.selectByVisibleText(cell12.getStringCellValue());
		Assert.assertEquals(select.getFirstSelectedOption().getText(), cell12.getStringCellValue());

		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		select.selectByVisibleText(y);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), y);

		driver.findElement(By.id("Email")).sendKeys(cell14.getStringCellValue());
		driver.findElement(By.id("Company")).sendKeys(cell15.getStringCellValue());
		driver.findElement(By.id("Password")).sendKeys(cell16.getStringCellValue());
		driver.findElement(By.id("ConfirmPassword")).sendKeys(cell17.getStringCellValue());
		driver.findElement(By.id("register-button")).click();

		WebElement TC51 = driver.findElement(By.xpath("//p[text() ='Password must meet the following rules: ' ]"));
		WebElement TC52 = driver.findElement(By.xpath("//li[text() ='must have at least 6 characters' ]"));

		Assert.assertEquals(TC51.getText(), "Password must meet the following rules:");
		Assert.assertEquals(TC52.getText(), "must have at least 6 characters");
		
		if (TC51.isDisplayed() && TC52.isDisplayed()) {		
			ketqua[t] = "Pass";
			t++;		
		} else {		
			ketqua[t] = "Failed";
			t++;			
		}		
	}

	@Test
	public void TC_06_Register_Incorrect_Confirm_Password() throws IOException {
		driver.findElement(By.className("ico-register")).click();
		
		File file = new File("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow rowinvalid = sheet.getRow(6);
		XSSFCell cell9 = rowinvalid.getCell(9);		
		XSSFCell cell10 = rowinvalid.getCell(10);
		XSSFCell cell11 = rowinvalid.getCell(11);
		day = (int) cell11.getNumericCellValue();
		String d= String.valueOf(day);
		
		XSSFCell cell12 = rowinvalid.getCell(12);		
		XSSFCell cell13 = rowinvalid.getCell(13);
		year = (int) cell13.getNumericCellValue();
		String y = String.valueOf(year);
	
		XSSFCell cell14 = rowinvalid.getCell(14);		
		XSSFCell cell15 = rowinvalid.getCell(15);		
		XSSFCell cell16 = rowinvalid.getCell(16);		
		XSSFCell cell17 = rowinvalid.getCell(17);
		
		driver.findElement(By.id("gender-female")).click();
		driver.findElement(By.id("FirstName")).sendKeys(cell9.getStringCellValue());
		driver.findElement(By.id("LastName")).sendKeys(cell10.getStringCellValue());

		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		select.selectByVisibleText(d);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), d);

		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		select.selectByVisibleText(cell12.getStringCellValue());
		Assert.assertEquals(select.getFirstSelectedOption().getText(), cell12.getStringCellValue());

		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		select.selectByVisibleText(y);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), y);

		driver.findElement(By.id("Email")).sendKeys(cell14.getStringCellValue());
		driver.findElement(By.id("Company")).sendKeys(cell15.getStringCellValue());
		driver.findElement(By.id("Password")).sendKeys(cell16.getStringCellValue());
		driver.findElement(By.id("ConfirmPassword")).sendKeys(cell17.getStringCellValue());
		driver.findElement(By.id("register-button")).click();

		// vertify - output data
		Assert.assertEquals(driver.findElement(By.id("ConfirmPassword-error")).getText(),
				"The password and confirmation password do not match.");
		
		if (driver.findElement(By.id("ConfirmPassword-error")).isDisplayed()) {		
			ketqua[t] = "Pass";
			t++;			
		} else {			
			ketqua[t] = "Failed";
			t++;			
		}		
	}

	@Test
	public void TC_07_Login_Empty_Data() throws IOException {
		driver.findElement(By.className("ico-login")).click();

		driver.findElement(By.xpath("//div[@class= 'form-fields']/following-sibling::div/button[1]")).click();

		Assert.assertEquals(driver.findElement(By.id("Email-error")).getText(), "Please enter your email");

		if (driver.findElement(By.id("Email-error")).isDisplayed()) {			
			ketqua[t] = "Pass";
			t++;			
		} else {			
			ketqua[t] = "Failed";
			t++;			
		}
	}

	@Test
	public void TC_08_Login_Invalid_Email() throws IOException {
		driver.findElement(By.className("ico-login")).click();
		
		File file = new File("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet1 = wb.getSheetAt(1);
		XSSFRow rowinvalid = sheet1.getRow(2);
		XSSFCell cell9 = rowinvalid.getCell(9);
		XSSFCell cell10 = rowinvalid.getCell(10);

		driver.findElement(By.id("Email")).sendKeys(cell9.getStringCellValue());
		driver.findElement(By.id("Password")).sendKeys(cell10.getStringCellValue());

		driver.findElement(By.xpath("//div[@class= 'form-fields']/following-sibling::div/button[1]")).click();

		Assert.assertEquals(driver.findElement(By.id("Email-error")).getText(), "Wrong email");

		if (driver.findElement(By.id("Email-error")).isDisplayed()) {			
			ketqua[t] = "Pass";
			t++;			
		} else {		
			ketqua[t] = "Failed";
			t++;			
		}		
	}

	@Test
	public void TC_09_Login_Email_Unregistered() throws IOException {
		driver.findElement(By.className("ico-login")).click();

		File file = new File("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet1 = wb.getSheetAt(1);
		XSSFRow rowinvalid = sheet1.getRow(3);
		XSSFCell cell9 = rowinvalid.getCell(9);
		XSSFCell cell10 = rowinvalid.getCell(10);
		
		driver.findElement(By.id("Email")).sendKeys(cell9.getStringCellValue());
		driver.findElement(By.id("Password")).sendKeys(cell10.getStringCellValue());

		driver.findElement(By.xpath("//div[@class= 'form-fields']/following-sibling::div/button[1]")).click();

		Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(), 'Login was unsuccessful')]")).getText(),
				"Login was unsuccessful. Please correct the errors and try again.\nNo customer account found");

		if (driver.findElement(By.xpath("//div[contains(text(), 'Login was unsuccessful')]")).isDisplayed()) {			
			ketqua[t] = "Pass";
			t++;			
		} else {		
			ketqua[t] = "Failed";
			t++;			
		}
	}

	@Test
	public void TC_10_Login_Email_Registered_Empty_Password() throws IOException {
		driver.findElement(By.className("ico-login")).click();
		
		File file = new File("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet1 = wb.getSheetAt(1);
		XSSFRow rowinvalid = sheet1.getRow(4);
		XSSFCell cell9 = rowinvalid.getCell(9);
		
		driver.findElement(By.id("Email")).sendKeys(cell9.getStringCellValue());

		driver.findElement(By.xpath("//div[@class= 'form-fields']/following-sibling::div/button[1]")).click();

		Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(), 'Login was unsuccessful')]")).getText(),
				"Login was unsuccessful. Please correct the errors and try again.\nThe credentials provided are incorrect");
	
		if (driver.findElement(By.xpath("//div[contains(text(), 'Login was unsuccessful')]")).isDisplayed()) {		
			ketqua[t] = "Pass";
			t++;			
		} else {			
			ketqua[t] = "Failed";
			t++;			
		}
	}

	@Test
	public void TC_11_Login_Email_Registered_Incorrect_Password() throws IOException {
		driver.findElement(By.className("ico-login")).click();
		
		File file = new File("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet1 = wb.getSheetAt(1);
		XSSFRow rowinvalid = sheet1.getRow(5);
		XSSFCell cell9 = rowinvalid.getCell(9);
		XSSFCell cell10 = rowinvalid.getCell(10);

		driver.findElement(By.id("Email")).sendKeys(cell9.getStringCellValue());
		driver.findElement(By.id("Password")).sendKeys(cell10.getStringCellValue());

		driver.findElement(By.xpath("//div[@class= 'form-fields']/following-sibling::div/button[1]")).click();

		Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(), 'Login was unsuccessful')]")).getText(),
				"Login was unsuccessful. Please correct the errors and try again.\nThe credentials provided are incorrect");

		if (driver.findElement(By.xpath("//div[contains(text(), 'Login was unsuccessful')]")).isDisplayed()) {			
			ketqua[t] = "Pass";
			t++;			
		} else {			
			ketqua[t] = "Failed";
			t++;			
		}		
	}

	@Test
	public void TC_12_Login_Email_Registered_Correct_Password() throws IOException {
		driver.findElement(By.className("ico-login")).click();
		
		File file = new File("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet1 = wb.getSheetAt(1);
		XSSFRow rowinvalid = sheet1.getRow(6);
		XSSFCell cell9 = rowinvalid.getCell(9);
		XSSFCell cell10 = rowinvalid.getCell(10);

		driver.findElement(By.id("Email")).sendKeys(cell9.getStringCellValue());
		driver.findElement(By.id("Password")).sendKeys(cell10.getStringCellValue());

		driver.findElement(By.xpath("//div[@class= 'form-fields']/following-sibling::div/button[1]")).click();

		Assert.assertEquals(driver.findElement(By.className("ico-logout")).getText(), "Log out");
		
		if (driver.findElement(By.className("ico-logout")).isEnabled()) {		
			ketqua[t] = "Pass";
			t++;			
		} else {			
			ketqua[t] = "Failed";
			t++;			
		}	
	}

	@Test
	public void TC_13_Wishlist_Empty_Data() throws IOException {
		driver.findElement(By.className("wishlist-label")).click();

		Assert.assertEquals(driver.findElement(By.className("no-data")).getText(), "The wishlist is empty!");
		
		if (driver.findElement(By.className("no-data")).isDisplayed()) {			
			ketqua[t] = "Pass";
			t++;			
		} else {			
			ketqua[t] = "Failed";
			t++;			
		}		
	}

	@Test
	public void TC_14_Add_Product_To_Wishlist() throws IOException {
		driver.get("https://demo.nopcommerce.com/");

		driver.findElement(By.cssSelector("img[alt='Picture of HTC One M8 Android L 5.0 Lollipop']")).click();

		driver.findElement(By.id("add-to-wishlist-button-18")).click();

		driver.findElement(By.className("wishlist-label")).click();

//		System.out.println(driver.findElement(By.xpath("//p[@class = 'content']")).getText());

		WebElement TC141 = driver.findElement(By.xpath("//span[text()='M8_HTC_5L']"));
		WebElement TC142 = driver.findElement(By.xpath("//a[text()='HTC One M8 Android L 5.0 Lollipop']"));
		Assert.assertEquals(driver.getCurrentUrl(), "https://demo.nopcommerce.com/wishlist");
		Assert.assertEquals(TC141.getText(), "M8_HTC_5L");
		Assert.assertEquals(TC142.getText(), "HTC One M8 Android L 5.0 Lollipop");
		
		if (TC141.isDisplayed() && TC142.isDisplayed()) {		
			ketqua[t] = "Pass";
			t++;			
		} else {			
			ketqua[t] = "Failed";
			t++;			
		}	
	}

	@Test
	public void TC_15_Add_Product_Form_Wishlist_To_Cart() throws IOException {
		driver.findElement(By.xpath("//input[@name='addtocart']")).click();
		driver.findElement(By.xpath("//button[@class='button-2 wishlist-add-to-cart-button']")).click();

		WebElement TC151 = driver.findElement(By.xpath("//span[text()='M8_HTC_5L']"));
		WebElement TC152 = driver.findElement(By.xpath("//td[@class = 'product']/a[1]"));
		Assert.assertEquals(driver.getCurrentUrl(), "https://demo.nopcommerce.com/cart");
		Assert.assertEquals(TC151.getText(), "M8_HTC_5L");
		Assert.assertEquals(TC152.getText(), "HTC One M8 Android L 5.0 Lollipop");
	
		if (TC151.isDisplayed() && TC152.isDisplayed()) {		
			ketqua[t] = "Pass";
			t++;			
		} else {			
			ketqua[t] = "Failed";
			t++;			
		}	
	}

	@Test
	public void TC_16_Remove_Product_From_Wishlist() throws IOException {
		driver.get("https://demo.nopcommerce.com/");

		driver.findElement(By.xpath("//div[@class= 'product-grid home-page-product-grid']/div[2]/div[3]/div[1]/div[1]")).click();

		driver.findElement(By.id("add-to-wishlist-button-18")).click();

		driver.findElement(By.className("wishlist-label")).click();

		driver.findElement(By.xpath("//button[@class = 'remove-btn']")).click();

		Assert.assertEquals(driver.findElement(By.className("no-data")).getText(), "The wishlist is empty!");

		if (driver.findElement(By.className("no-data")).isDisplayed()) {			
			ketqua[t] = "Pass";
			t++;			
		} else {			
			ketqua[t] = "Failed";
			t++;			
		}		
	}

	@Test
	public void TC_17_Add_Product_To_Cart() throws IOException {
		driver.get("https://demo.nopcommerce.com/");
		driver.findElement(By.xpath("//div[@class= 'product-grid home-page-product-grid']/div[2]/div[3]/div[1]/div[1]")).click();

		driver.findElement(By.id("add-to-cart-button-18")).click();
		driver.findElement(By.className("cart-label")).click();

		WebElement TC171 = driver.findElement(By.xpath("//h1[text()='Shopping cart']"));
		WebElement TC172 = driver.findElement(By.xpath("//span[text()='M8_HTC_5L']"));
		Assert.assertEquals(TC171.getText(), "Shopping cart");
		Assert.assertEquals(TC172.getText(), "M8_HTC_5L");

		if (TC171.isDisplayed() && TC172.isDisplayed()) {			
			ketqua[t] = "Pass";
			t++;			
		} else {		
			ketqua[t] = "Failed";
			t++;			
		}		
	}

	@Test
	public void TC_18_Update_Shopping_Cart() throws IOException {
		driver.findElement(By.xpath("//td[@class='quantity']/input")).click();
		driver.findElement(By.xpath("//td[@class='quantity']/input")).clear();
		
		File file = new File("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet3 = wb.getSheetAt(3);
		XSSFRow rowinvalid = sheet3.getRow(2);
		
		XSSFCell cell10 = rowinvalid.getCell(10);
		slg = (int) cell10.getNumericCellValue();
		String qty= String.valueOf(slg);
		
		driver.findElement(By.xpath("//td[@class='quantity']/input")).sendKeys(qty);
		driver.findElement(By.id("updatecart")).click();

		Assert.assertEquals(driver.findElement(By.xpath("//td[@class='subtotal']/span")).getText(), "$1,225.00");

		if (driver.findElement(By.xpath("//td[@class='subtotal']/span")).isDisplayed()) {			
			ketqua[t] = "Pass";
			t++;			
		} else {			
			ketqua[t] = "Failed";
			t++;			
		}		
	}

	@Test
	public void TC_19_Remove_From_Cart() throws IOException {
		driver.findElement(By.className("remove-btn")).click();

		Assert.assertEquals(driver.findElement(By.className("no-data")).getText(), "Your Shopping Cart is empty!");

		if (driver.findElement(By.className("no-data")).isDisplayed()) {			
			ketqua[t] = "Pass";
			t++;			
		} else {			
			ketqua[t] = "Failed";
			t++;		
		}
	}

	@Test
	public void TC_20_Order_Payment_Method_By_Cheque() throws IOException {
		driver.get("https://demo.nopcommerce.com/");
		File file = new File("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet3 = wb.getSheetAt(3);
		XSSFRow rowinvalid = sheet3.getRow(4);
		XSSFCell cell11 = rowinvalid.getCell(11);
		XSSFCell cell12 = rowinvalid.getCell(12);
		XSSFCell cell13 = rowinvalid.getCell(13);
		XSSFCell cell14 = rowinvalid.getCell(14);
		XSSFCell cell15 = rowinvalid.getCell(15);
		zipCode = (int)cell15.getNumericCellValue();
		String zip = String.valueOf(zipCode);
		XSSFCell cell16 = rowinvalid.getCell(16);
		XSSFCell cell17 = rowinvalid.getCell(17);
	
		driver.findElement(By.cssSelector("img[alt='Picture of HTC One M8 Android L 5.0 Lollipop']")).click();

		driver.findElement(By.id("add-to-cart-button-18")).click();
		driver.findElement(By.className("cart-label")).click();

		driver.findElement(By.id("termsofservice")).click();
		driver.findElement(By.id("checkout")).click();

		driver.findElement(By.id("ShipToSameAddress")).click();

		select = new Select(driver.findElement(By.xpath("//label[text()='Country:']/following-sibling::select")));
		select.selectByVisibleText(cell11.getStringCellValue());
		Assert.assertEquals(select.getFirstSelectedOption().getText(), cell11.getStringCellValue());

		driver.findElement(By.xpath("//label[text()='City:']/following-sibling::input")).sendKeys(cell12.getStringCellValue());
		driver.findElement(By.xpath("//label[text()='Address 1:']/following-sibling::input")).sendKeys(cell13.getStringCellValue());
		driver.findElement(By.xpath("//label[text()='Address 2:']/following-sibling::input")).sendKeys(cell14.getStringCellValue());
		driver.findElement(By.xpath("//label[text()='Zip / postal code:']/following-sibling::input"))
				.sendKeys(zip);
		driver.findElement(By.xpath("//label[text()='Phone number:']/following-sibling::input")).sendKeys(cell16.getStringCellValue());
		driver.findElement(By.xpath("//label[text()='Fax number:']/following-sibling::input")).sendKeys(cell17.getStringCellValue());
		driver.findElement(By.xpath("//div[@id='billing-buttons-container']/button[4]")).click();

		driver.findElement(By.xpath("//div[@id='shipping-buttons-container']/button[4]")).click();

		driver.findElement(By.xpath("//div[@id='shipping-method-buttons-container']/button")).click();
		driver.findElement(By.id("paymentmethod_0")).click();

		driver.findElement(By.xpath("//div[@id='payment-method-buttons-container']/button")).click();

		driver.findElement(By.xpath("//div[@id='payment-info-buttons-container']/button")).click();

		driver.findElement(By.xpath("//div[@id='confirm-order-buttons-container']/button")).click();

		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='section order-completed']/div[1]/strong")).getText(), "Your order has been successfully processed!");
	
		if (driver.findElement(By.xpath("//div[@class='section order-completed']/div[1]/strong")).isDisplayed()) {	
			ketqua[t] = "Pass";
			t++;			
		} else {
			ketqua[t] = "Failed";
			t++;		
		}
	}

	@Test
	public void TC_21_Order_Payment_Method_By_Card() throws IOException {
		sleepInSecond(5);
		driver.get("https://demo.nopcommerce.com/");
		
		File file = new File("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet3 = wb.getSheetAt(3);
		XSSFRow rowinvalid = sheet3.getRow(5);
		XSSFCell cell18 = rowinvalid.getCell(18);
		
		XSSFCell cell19 = rowinvalid.getCell(19);
		XSSFCell cell20 = rowinvalid.getCell(20);
		XSSFCell cell21 = rowinvalid.getCell(21);
		
		driver.findElement(By.cssSelector("img[alt='Picture of HTC One M8 Android L 5.0 Lollipop']")).click();

		driver.findElement(By.id("add-to-cart-button-18")).click();

		driver.findElement(By.className("cart-label")).click();

		driver.findElement(By.id("termsofservice")).click();
		sleepInSecond(5);
		driver.findElement(By.id("checkout")).click();
		sleepInSecond(5);
		driver.findElement(By.id("ShipToSameAddress")).click();
		
		driver.findElement(By.xpath("//div[@id = 'billing-buttons-container']/button[text()= 'Continue']")).click();

		driver.findElement(By.xpath("//div[@id='shipping-buttons-container']/button[4]")).click();

		driver.findElement(By.xpath("//div[@id='shipping-method-buttons-container']/button")).click();
		driver.findElement(By.id("paymentmethod_1")).click();
		driver.findElement(By.xpath("//div[@id='payment-method-buttons-container']/button")).click();
		sleepInSecond(5);
		driver.findElement(By.id("CardholderName")).sendKeys(cell18.getStringCellValue());
		sleepInSecond(5);
		driver.findElement(By.id("CardNumber")).sendKeys(cell19.getStringCellValue());
		select = new Select(driver.findElement(By.id("ExpireYear")));
		select.selectByVisibleText(cell20.getStringCellValue());
		Assert.assertEquals(select.getFirstSelectedOption().getText(), cell20.getStringCellValue());
		driver.findElement(By.id("CardCode")).sendKeys(cell21.getStringCellValue());

		driver.findElement(By.xpath("//div[@id='payment-info-buttons-container']/button")).click();
		sleepInSecond(5);

		driver.findElement(By.xpath("//div[@id='confirm-order-buttons-container']/button")).click();

		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='section order-completed']/div[1]/strong")).getText(), "Your order has been successfully processed!");

		if (driver.findElement(By.xpath("//div[@class='section order-completed']/div[1]/strong")).isDisplayed()) {		
			ketqua[t] = "Pass";
			t++;		
		} else {		
			ketqua[t] = "Failed";
			t++;		
		}
	}

	@AfterClass
	public void afterClass() throws IOException {
		File file = new File("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheetAt(4);
		XSSFRow rowsum = sheet.getRow(10);
		XSSFRow rowsum1 = sheet.getRow(6);
		XSSFRow rowsum2 = sheet.getRow(7);
		XSSFRow rowsum3 = sheet.getRow(8);
		XSSFRow rowsum4 = sheet.getRow(9);
		XSSFRow row5 = sheet.getRow(12);
		XSSFRow row6 = sheet.getRow(13);

		for (int i = 0; i < 21; i++) {
			if (ketqua[i] == "Pass") {
				pa++;	
			} else if (ketqua[i] == "Failed") {
				fa++;
			} else {
				nu++;
			}
		}
		rowsum.createCell(6).setCellValue(pa);
		rowsum.createCell(7).setCellValue(fa);
		rowsum.createCell(8).setCellValue(nu);
		int m1=0,m2=0,m3=0;
		for (int i = 0; i < 6; i++) {
			if (ketqua[i] == "Pass") {
				m1++;	
			} else if (ketqua[i] == "Failed") {
				m2++;
			} else {
				m3++;
			}
		}
		rowsum1.createCell(6).setCellValue(m1);
		rowsum1.createCell(7).setCellValue(m2);
		rowsum1.createCell(8).setCellValue(m3);
		int m4=0,m5=0,m6=0;
		for (int i = 6; i < 12; i++) {
			if (ketqua[i] == "Pass") {
				m4++;	
			} else if (ketqua[i] == "Failed") {
				m5++;
			} else {
				m6++;
			}
		}
		rowsum2.createCell(6).setCellValue(m4);
		rowsum2.createCell(7).setCellValue(m5);
		rowsum2.createCell(8).setCellValue(m6);
		int m7=0,m8=0,m9=0;
		for (int i = 12; i < 16; i++) {
			if (ketqua[i] == "Pass") {
				m7++;	
			} else if (ketqua[i] == "Failed") {
				m8++;
			} else {
				m9++;
			}
		}
		rowsum3.createCell(6).setCellValue(m7);
		rowsum3.createCell(7).setCellValue(m8);
		rowsum3.createCell(8).setCellValue(m9);
		int m10=0,m11=0,m12=0;
		for (int i = 16; i < 21; i++) {
			System.out.println(ketqua[i]);
			if (ketqua[i] == "Pass") {
				m10++;	
			} else if (ketqua[i] == "Failed") {
				m11++;
			} else {
				m12++;
			}
		}
		rowsum4.createCell(6).setCellValue(m10);
		rowsum4.createCell(7).setCellValue(m11);
		rowsum4.createCell(8).setCellValue(m12);
		row5.createCell(6).setCellFormula("(G11+H11)*100/J11");
		row6.createCell(6).setCellFormula("(G11/J11)*100");
		FileOutputStream outputStream = new FileOutputStream("E:\\DO_AN\\Test_Case_nopCommerce.xls");
		wb.write(outputStream);
		wb.close();
		driver.quit();
	}

	public int generateRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}