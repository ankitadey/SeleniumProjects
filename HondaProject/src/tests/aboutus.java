package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
//import java.util.List;
import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import reusable.Reusable;
import xpath.xpath;

public class aboutus extends Reusable {
	
	
	File file=null;
	FileInputStream fileInput=null;
	WebElement SearchId;
	String cityselected;
	Properties prop;
	String YourCity;
	
	@BeforeClass
	public void invokeDriver() throws IOException, InterruptedException
	{
		/*************************Invoke the browser ******************/
		    driver=invokeBrowser();
		
		/*************************Invoke the browser ******************/
		   
		  // select your city
		   
		    file = new File(System.getProperty("user.dir")+"\\src\\Data\\Config.properties");
	        //Declare a properties object
	        prop = new Properties();
	        fileInput=new FileInputStream(file);
	        prop.load(fileInput);
	        
	        YourCity=prop.getProperty("YourCity");
	        
	        if(YourCity.contains("Bangalore"))
	        {
	        	driver.findElement(By.xpath("(//div[@class='city_img'])[1]")).click();
	        }
	        else
	        {
	        	driver.findElement(By.xpath("(//div[@class='city_img'])[2]")).click();
	        }
	        
	        cityselected=driver.findElement(By.xpath("//span[@class='input-group-addon city-popup']//span/i/parent::span")).getText();
	        Assert.assertEquals(cityselected, YourCity);
	        System.out.println("You have selected the city "+YourCity);
	        Thread.sleep(5000);
	}
	
	@Test
	public void aboutusmenuclick() throws InterruptedException
	{
		driver.findElement(By.xpath(xpath.XPATH_ABOUT_US)).click();
		System.out.println("Redirected to About Us Page");
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//ul[@class='nav nav-tabs']/li[1]/a")).click();
		Thread.sleep(2000);
		if(driver.getCurrentUrl().contains("about-honda-auto-terrace"))
		{
		
			System.out.println("About Us link clicked");
			Assert.assertTrue(true);
		}
		else
		{
			System.out.println("About Us link not clicked");
			Assert.assertFalse(true);
		}
		
		driver.findElement(By.xpath("//ul[@class='nav nav-tabs']/li[2]/a")).click();
		Thread.sleep(2000);
		if(driver.getCurrentUrl().contains("contact-us"))
		{
			System.out.println("Contact Us link clicked");
			Assert.assertTrue(true);
		}
		else
		{
			System.out.println("Contact Us link not clicked");
			Assert.assertFalse(true);
		}
		
		//driver.close();
	}
	
	
	@AfterClass
	public void closeBrowser()
	{
		driver.quit();
	}
}	
