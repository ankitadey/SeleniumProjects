package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
//import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import reusable.Reusable;
import xpath.xpath;

public class events extends Reusable {
	
	File file=null;
	FileInputStream fileInput=null;
	WebElement SearchCityId;
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
	
	@Test(priority = 0)
	public void eventsmenuclick() throws InterruptedException
	{
		driver.findElement(By.xpath(xpath.XPATH_EVENTS)).click();
		System.out.println("Redirected to Events Page");
		Thread.sleep(2000);
	}
	
	@Test(priority = 1)
	public void verifycityselection() throws IOException, InterruptedException
	{
		/*file = new File("C:\\Users\\Ankita Dey\\eclipse-workspace\\HondaProject\\src\\Data\\data.properties");
        //Declare a properties object
        prop = new Properties();
        fileInput=new FileInputStream(file);
        prop.load(fileInput);*/
		
		prop=getProperties();
        String City=prop.getProperty("CityEvents");
        
        SearchCityId=driver.findElement(By.id("srch_city"));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("document.getElementById('srch_city').click();");
        js.executeScript("arguments[0].value ='';", SearchCityId);
        Thread.sleep(2000);
        js.executeScript("arguments[0].value ='"+City+"';", SearchCityId);
        //SearchCityId.click();
        //SearchCityId.clear();
        Thread.sleep(2000);
		SearchCityId.sendKeys(Keys.ARROW_DOWN);
		SearchCityId.sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		System.out.println("New City Selected : "+City);
	}
	
	@Test(priority = 2)
	public void verifyeventsdatapresent()
	{
	    String text=driver.findElement(By.xpath("//div[@class='col-1']/h3")).getText();
	    if(text.equalsIgnoreCase("There are no events in your city."))
	    {
	    	System.out.println("No events present for this city");
	    	Assert.assertFalse(false);
	    }
	}
	
	@AfterClass
	public void closeBrowser()
	{
		driver.quit();
	}
}	
