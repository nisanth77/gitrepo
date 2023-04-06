package com.trello.qspiders.frames;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TravelBackParent {
	WebDriver driver;

	@BeforeMethod
	public void configBeforeMethod() {
		String browserName = "chrome";

		if (browserName.equals("edge")) {
			driver = new EdgeDriver();
		} else if (browserName.equals("chrome")) {
			ChromeOptions chromeOptions = new ChromeOptions();
			  chromeOptions.addArguments("--no-sandbox");
			  chromeOptions.addArguments("--disable-dev-shm-usage");
			driver= new ChromeDriver(chromeOptions);
		} else if (browserName.equals("firefox")) {
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();

	}

	@AfterTest
	public void configAfterMethod() {
		driver.manage().window().minimize();
		driver.quit();
	}

	@Test
	public void travelBackToParent() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.get("https://www.w3schools.com/html/tryit.asp?filename=tryhtml_iframe_height_width");
		/*
		 * Wrong 
		 * WebElement parentFrame = driver.findElement(By.id("iframeResult"));
		 * driver.switchTo().frame(driver.findElement(By.xpath(
		 * "//iframe[@src='demo_iframe.htm']"))); String childFrameText =
		 * driver.findElement(By.tagName("h1")).getText();
		 * driver.switchTo().frame(parentFrame);
		 */

		// tranfer control to parent frame
		driver.switchTo().frame("iframeResult");
		String parentExpectedText = driver.findElement(By.tagName("p")).getText();
		Reporter.log(parentExpectedText);
		// from that parent frame we transfer control to child
		driver.switchTo().frame(0);
		String childElementtext = driver.findElement(By.tagName("h1")).getText();
		Reporter.log(childElementtext);
		// transfer control to parent frame by using parentele()
		driver.switchTo().parentFrame();
		
		Reporter.log(driver.findElement(By.tagName("p")).getText());

	}
}
