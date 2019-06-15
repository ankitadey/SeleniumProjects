package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import reusable.Reusable;
import xpath.xpath;


	public class SellCar extends Reusable {

	

		//Reusable obj=new Reusable();
		WebDriver driver;
		int totcount=0;
		int totrowcount = 0;
		int totrowcountnew=0;
		File file=null;
		FileInputStream fileInput=null;
		String cityselected;
		ArrayList<String> list;
		List <WebElement> options;
		WebElement checkElement = null;
		List<WebElement> rbkms;
		Properties prop;
		WebElement SearchCityId;
		String Manufactureyear;
		String YourCity,Makemodel,Varient,RegNo,Kms,Name,Mobile;
		//invoke the driver and load the browser
		@BeforeClass
		public void invokeDriver() throws IOException, InterruptedException
		{
			/*************************Invoke the browser ******************/
			   driver=invokeBrowser();
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
    	public void sellmenuclick() throws InterruptedException, IOException
    	{
        	
        	       prop=getProperties();
		        	//file = new File("C:\\Users\\Ankita Dey\\eclipse-workspace\\HondaProject\\src\\Data\\data.properties");
			        //Declare a properties object
			        //prop = new Properties();
			       // fileInput=new FileInputStream(file);
			       // prop.load(fileInput);
			        Manufactureyear=prop.getProperty("Manufactureyear");
			        
		    		driver.findElement(By.xpath(xpath.Xpath_Sell_car)).click();
		    		System.out.println("Click On Sell Car Tab");
		    		Thread.sleep(2000);
		    		
		    		driver.findElement(By.xpath(xpath.Xpath_Reg_Year)).click();
		    		Thread.sleep(2000);
		    		
		    		driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[1]")).sendKeys(Manufactureyear);
		    		driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[1]")).sendKeys(Keys.ARROW_DOWN);
		    		driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[1]")).sendKeys(Keys.ENTER);
		    		
		    		Makemodel=prop.getProperty("Makemodel");
		    		
			        driver.findElement(By.cssSelector(".SumoSelect.sumo_model_id")).click();
			        driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[2]")).sendKeys(Makemodel);
			        driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[2]")).sendKeys(Keys.ARROW_DOWN);
		    		driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[2]")).sendKeys(Keys.ENTER);
		    		
		    		Varient=prop.getProperty("Variant");
		    		
			        driver.findElement(By.id("variant_id")).click();
			        
			        selectFromDropdown("//select[@class='form-control effect-place']",Varient);
			        
			        RegNo=prop.getProperty("RegNo");
			        
			        driver.findElement(By.id("registrationnumber")).sendKeys(RegNo);
			        
			        Kms=prop.getProperty("KmDriven");
			        
			        driver.findElement(By.id("kilodriven")).sendKeys(Kms);
			        
			        driver.findElement(By.id("sell_next")).click();
			        
			        System.out.println("Next button clicked");
			        
			        Thread.sleep(2000);
			        
			        Name=prop.getProperty("sellname");
			        
			        driver.findElement(By.id("name")).sendKeys(Name);
			        
			        System.out.println("Name Entered");
			        
                    Mobile=prop.getProperty("sellmobile");
			        
			        driver.findElement(By.id("sell_mobile")).sendKeys(Mobile);
			        
			        System.out.println("MobileNumber Entered");
			        
			        Thread.sleep(3000);
			        
			        String handle = driver.getWindowHandles().toArray()[0].toString();
			        driver.switchTo().window(handle);
			        JavascriptExecutor jse = (JavascriptExecutor) driver;
			        jse.executeScript("document.getElementById('otp_verify_otp').value = '123456';");
			        driver.findElement(By.id("otp_verify_otp")).sendKeys(Keys.SPACE);
			        driver.findElement(By.id("verify_otp_btn")).click();
			        Thread.sleep(2000);
	        		driver.findElement(By.xpath("//*[@id='otp_verify_box']/button/i")).click();
	        		
			        			   
			   
		}
        
       /* @AfterClass
    	public void closeBrowser()
    	{
    		driver.quit();
    	}*/
        
}
