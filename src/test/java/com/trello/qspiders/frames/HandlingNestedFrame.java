package com.trello.qspiders.frames;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HandlingNestedFrame {
	WebDriver driver;
	@BeforeMethod
	public void configBeforeMethod() {
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
	
}
@AfterTest
	public void configAfterMethod() {
		driver.manage().window().minimize();
		driver.quit();
	}
@Test
public void nestedFrames() throws Throwable {
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	driver.get("https://demo.automationtesting.in/Frames.html");
	driver.findElement(By.linkText("Iframe with in an Iframe")).click();
	WebElement parentFrame = driver.findElement(By.xpath("//div[@id='Multiple']/iframe"));
	driver.switchTo().frame(parentFrame);
	driver.switchTo().frame(driver.findElement(By.xpath("//h5[text()='Nested iFrames']/../iframe")));
	driver.findElement(By.xpath("//input[@type='text']")).sendKeys("hello");
	Thread.sleep(3000);
	
}
}
