package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import reusable.Reusable;
import xpath.xpath;

public class ExchangeCars extends Reusable {
	File file=null;
	FileInputStream fileInput=null;
	String cityselected;
	Properties prop;
	String YourCity;
	String Manufactureyear;
	String Makemodel;
	String Variant,Registration,KiloMeterDriven;
	String PersonalName,PersonalMobile;
	
	
@BeforeClass
public void invokedriver() throws IOException, InterruptedException
{
	/*************************Invoke the browser ******************/
	driver =invokeBrowser();
	
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
	public void exchangeclick() throws InterruptedException
    {
    	driver.findElement(By.xpath(xpath.XPATH_EXCHANGE_CAR)).click();
		System.out.println("Redirected to Exchange Page");
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//ul[@class='nav nav-tabs web_tab']/li[2]/a")).click();
		Thread.sleep(2000);
		if(driver.getCurrentUrl().contains("exchange-used-car"))
		{
			System.out.println("Exchange link clicked");
		}
		driver.findElement(By.xpath("//*[@id='b0FtUWlSVXMrUVB3Rkx5S1hEM3JXQT09']")).click();
		System.out.println("Click On CarName ");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='sell_continue']")).click();
		System.out.println("Click On Continue");
		Thread.sleep(2000);
		//driver.close();
    }
    
    @Test(priority = 1)
    public void exchangeCarDetail() throws IOException, InterruptedException
    {
    	/*file = new File("C:\\Users\\Ankita Dey\\eclipse-workspace\\HondaProject\\src\\Data\\data.properties");
        //Declare a properties object
        prop = new Properties();
        fileInput=new FileInputStream(file);
        prop.load(fileInput);*/
    	
    	prop=getProperties();
        
        Manufactureyear=prop.getProperty("ExManufactureyear");
        driver.findElement(By.xpath(xpath.Xpath_Reg_Year)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[1]")).sendKeys(Manufactureyear);
		driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[1]")).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[1]")).sendKeys(Keys.ENTER);
		System.out.println("Select ManufactureYear");
		
		Makemodel=prop.getProperty("ExMakemodel");
        driver.findElement(By.xpath(xpath.Xpath_MakeModel)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[2]")).sendKeys(Makemodel);
		driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[2]")).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[2]")).sendKeys(Keys.ENTER);
		System.out.println("Select MakeModel");
    	
		Variant=prop.getProperty("ExVariant");
		driver.findElement(By.xpath(xpath.Xpath_Variant)).click();
		Thread.sleep(2000);
		selectFromDropdown(xpath.Xpath_Variant,Variant);
		System.out.println("Select Variant");
		
		Registration=prop.getProperty("ExRegistrationNumber");
		driver.findElement(By.xpath(xpath.Xpath_Registration)).sendKeys(Registration);;
		System.out.println("Enter Registration");
		
		KiloMeterDriven=prop.getProperty("ExKiloMeterDriven");
		driver.findElement(By.xpath(xpath.Xpath_KiloMeterDriven)).sendKeys(KiloMeterDriven);
		System.out.println("Enter KiloMeter Driven");
		
		driver.findElement(By.xpath(xpath.Xpath_EXCHANGE_NEXTBUTTON)).click();
		System.out.println("Click On Next");
		Thread.sleep(2000);
		
    }
    @Test(priority = 2)
    public void exchangePersonalDetails() throws IOException, InterruptedException
    {
    	/*file = new File("C:\\Users\\Ankita Dey\\eclipse-workspace\\HondaProject\\src\\Data\\data.properties");
        //Declare a properties object
        prop = new Properties();
        fileInput=new FileInputStream(file);
        prop.load(fileInput);*/
    	
    	prop=getProperties();
        
        PersonalName=prop.getProperty("ExName");
        driver.findElement(By.xpath(xpath.Xpath_EXCHANGE_NAME)).sendKeys(PersonalName);
        System.out.println("Name Entered");
        Thread.sleep(2000);
        
        PersonalMobile=prop.getProperty("ExMobileNumber");
        driver.findElement(By.xpath(xpath.Xpath_EXCHANGE_MOBILE)).sendKeys(PersonalMobile);
        System.out.println("MobileNumber Entered");
    }
    
    @AfterClass
	public void closeBrowser()
	{
		driver.quit();
	}
	
}

