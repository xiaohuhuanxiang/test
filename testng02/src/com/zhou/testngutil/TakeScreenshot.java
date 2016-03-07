package com.zhou.testngutil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;



public class TakeScreenshot {

	public WebDriver driver;

	public TakeScreenshot() {
		 String chromdriver="E:\\java\\chromedriver.exe";
	     System.setProperty("webdriver.chrome.driver", chromdriver);


		this.driver = new ChromeDriver();
		driver.get("http://m1.fun.tv/");
	}

	private void takeScreenshotBase(String screenPath) {
		try {
			//自动截图
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(screenPath));
		} catch (IOException e) {
			System.out.println("Screen shot error: " + screenPath);
		}
	}

	public void takeScreenshot(String screenName) {
		
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
//		String mDateTime = formatter.format(new Date());
//		
//		
//		String screenName = mDateTime + ".jpg";
		File dir = new File("test-output/snapshot");
		if (!dir.exists())
			dir.mkdirs();
		String screenPath = dir.getAbsolutePath() + "/" + screenName;
		this.takeScreenshotBase(screenPath);
		System.out.println(screenPath);
	}

}
