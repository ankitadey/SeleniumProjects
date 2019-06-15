package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
//import java.util.List;
import java.util.Set;
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

public class newcars extends Reusable {
	
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
	public void newcarsmenuclick() throws InterruptedException
	{
		driver.findElement(By.xpath(xpath.XPATH_NEW_CARS)).click();
		System.out.println("New Cars Menu clicked");
		Thread.sleep(2000);
		// Get current window
        String parentWinHandle = driver.getWindowHandle();
        
        //get all the currently opened windows and store in set
        Set<String> winHandles=driver.getWindowHandles();
		
		for(String handle: winHandles){
		
			if(!handle.equals(parentWinHandle))
			{
				driver.switchTo().window(handle);
				Thread.sleep(2000);
				String HondaText=driver.findElement(By.xpath(xpath.XPATH_HONDA_CAR_LOGO)).getAttribute("alt");
				if(HondaText.contains("Honda"))
				{
					System.out.println("Redirected to Honda website");
				}
				else
				{
					System.out.println("Redirection problem");
					Assert.assertFalse(true);
				}
				
				System.out.println("Closing the new window...");
	            driver.close();
			}
			
		}
		
	}
	
	@AfterClass
	public void closeAllBrowser()
	{
		driver.quit();
	}
	
}	
