package com.trello.qspiders.frames;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Test;


public class HandlingIndividualFramesTest {
	WebDriver driver;
@Test
	public void individualFramesTest() throws InterruptedException {
	String browserName="chrome";
	
	if (browserName.equals("edge")) {
		driver= new EdgeDriver();
	}
	else if (browserName.equals("chrome")) {
		
		ChromeOptions chromeOptions = new ChromeOptions();
		  chromeOptions.addArguments("--no-sandbox");
		  chromeOptions.addArguments("--disable-dev-shm-usage");
		driver= new ChromeDriver(chromeOptions);
	}
	else if (browserName.equals("firefox")) {
		driver= new FirefoxDriver();
	}
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	driver.get("https://www.selenium.dev/selenium/docs/api/java/index.html?overview-summary.html");
	driver.switchTo().frame("packageListFrame");
	driver.findElement(By.xpath("//li/a[text()='org.openqa.selenium.chromium']")).click();
	driver.switchTo().defaultContent().switchTo().frame("packageFrame");
	driver.findElement(By.xpath("//*[text()='HasCdp']")).click();
	Thread.sleep(2000);
	Reporter.log("pass");
	driver.manage().window().minimize();
	driver.quit();
	
}
}
