import java.io.FileInputStream;
import java.io.FileReader;
import java.time.Duration;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginPage {
	WebDriver driver;
	Sheet sheet;
	JSONObject jsonobject;

	@BeforeMethod
	public void configBeforeMethod() {

		String browserName = sheet.getRow(3).getCell(2).getStringCellValue();
		if (browserName.equals("edge")) {
			driver = new EdgeDriver();
		} else if (browserName.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (browserName.equals("firefox")) {
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
		driver.get(sheet.getRow(4).getCell(2).getStringCellValue());
	}

	@AfterMethod
	public void configAfterMethod() {
		driver.manage().window().minimize();
		driver.quit();
	}

	@BeforeClass
	public void ConfigBeforeClass() throws Throwable {
		FileInputStream fis = new FileInputStream("./src/test/resource/orangedata.xlsx");
		FileReader file = new FileReader("./src/test/resource/orangedata.json");
		Workbook workbook = WorkbookFactory.create(fis);
		sheet = workbook.getSheet("orangecrmdata");
		JSONParser jsonpaser = new JSONParser();
		jsonobject = (JSONObject) jsonpaser.parse(file);

	}

	@Test

	public void loginPage_01() {
		SoftAssert softassert = new SoftAssert();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		System.out.println("title: " + (sheet.getRow(15).getCell(4).getStringCellValue()));
		softassert.assertEquals(driver.getTitle(), sheet.getRow(15).getCell(5).getStringCellValue(),
				"Loginpage title found incorrect");
		driver.findElement(By.name((String) jsonobject.get("name")))
				.sendKeys(sheet.getRow(7).getCell(3).getStringCellValue());
		driver.findElement(By.name((String) jsonobject.get("password")))
				.sendKeys(sheet.getRow(8).getCell(3).getStringCellValue());
		driver.findElement(By.xpath((String) jsonobject.get("loginbutton"))).submit();
		wait.until(ExpectedConditions.urlContains(sheet.getRow(11).getCell(5).getStringCellValue()));
		softassert.assertEquals(driver.getCurrentUrl(), sheet.getRow(11).getCell(5).getStringCellValue(),
				"url is found incorrect");
		softassert.assertEquals(driver.getTitle(), sheet.getRow(10).getCell(5).getStringCellValue(),
				"Home page title found incorrect");
		Reporter.log("home page is displayed");
		driver.findElement(By.xpath((String) jsonobject.get("profile"))).click();
		driver.findElement(By.linkText((String) jsonobject.get("logout"))).click();
		wait.until(ExpectedConditions.titleContains(sheet.getRow(15).getCell(5).getStringCellValue()));
		softassert.assertEquals(driver.getCurrentUrl(), sheet.getRow(16).getCell(5).getStringCellValue(),
				"Url found incorrect");
		softassert.assertEquals(driver.getTitle(), sheet.getRow(15).getCell(5).getStringCellValue(),
				"Title found incorrect");
		Reporter.log("Login page is displayed");
		softassert.assertAll("the Problem in script");

	}
}
