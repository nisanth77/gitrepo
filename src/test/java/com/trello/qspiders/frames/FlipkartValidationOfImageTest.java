package com.trello.qspiders.frames;

import java.io.File;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class FlipkartValidationOfImageTest {
WebDriver driver;

@Test

public void optionVerificationTest() throws Throwable {
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
	driver.get("https://www.flipkart.com/");
	Reporter.log("pass");
	driver.findElement(By.xpath("//button[text()='✕']")).click();
	
	List<WebElement> optionsImage = driver.findElements(By.xpath("//*[@class='eFQ30H']"));
	for (WebElement optionImage : optionsImage) {
		//File image = optionImage.getScreenshotAs(OutputType.FILE);
		File image = optionImage.findElement(By.tagName("img")).getScreenshotAs(OutputType.FILE);
		
		File sourceFile = new File("./flipkartscreenshot/"+optionImage.findElement(By.className("xtXmba")).getText()+".png");
		FileUtils.copyFile(image, sourceFile);
		
	}
	driver.manage().window().minimize();
	driver.quit();
}
}

