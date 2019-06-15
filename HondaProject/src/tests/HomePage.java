package tests;

import java.io.File;
import java.io.FileInputStream;
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
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import reusable.Reusable;
import xpath.xpath;

public class HomePage extends Reusable {

	

	int count;
	int countnextitems;
	String name;
	String newName;
	File file=null;
	FileInputStream fileInput=null;
	WebElement SearchId;
	String cityselected;
	Properties prop;
	String YourCity,cust_name,cust_mobile;
	String SearchMakeModel;
	// WebDriver driver;
	
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
	
	//Verify title of the home page
	
	@Test(priority = 0)
	public void validateTitleHomePage()
	{
		
		// verifying the title of the page
		String actualTitle=driver.getTitle();
		String expectedTitle="Honda Auto Terrace | Honda’s one-stop facility for Buying, Selling and Exchange of Used Cars";
		if(actualTitle.equalsIgnoreCase(expectedTitle))
		{
		   Assert.assertEquals(actualTitle, expectedTitle);
			System.out.println("Title Matched");
		}
		else
		{
			Assert.assertFalse(true);
			//System.out.println("Title not Matched");
		}
		
	}
	
	
	@Test(priority = 1,dataProvider="getDataForSearch",alwaysRun = true)
	public void validateSearch(String CarName) throws InterruptedException
	{
		SearchId=driver.findElement(By.id("serach_used_car"));
		SearchId.click();
		SearchId.clear();
		SearchId.sendKeys(CarName);
		Thread.sleep(2000);
		SearchId.sendKeys(Keys.ARROW_DOWN);
		SearchId.sendKeys(Keys.ENTER);
		Thread.sleep(5000);
		String SearchText=driver.findElement(By.xpath("//h1[@class='filter-head']")).getText();
		if(SearchText.contains(CarName))
		{
			System.out.println("Searched Result Found "+SearchText);
			Assert.assertTrue(true);
		}
		else
		{
			System.out.println("Searched Result Not Found");
			Assert.assertFalse(true);
		}
		
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		js.executeScript("window.history.go(-1)");
		
	}
	
	//verifying the footer Know More links properly navigating or not
	@Test(priority = 2)
	public void validateFooterLinks() throws InterruptedException
	{
			
		WebElement footerDriver=driver.findElement(By.xpath(xpath.XPATH_FOOTER_LINK));
			
		String clickonlinktab=Keys.chord(Keys.CONTROL,Keys.ENTER);
			
		int countlinks=footerDriver.findElements(By.tagName("a")).size();
		//System.out.println(countlinks);
			
		if(countlinks>0)
		{
			System.out.println("Validating: "+driver.findElement(By.className("heading")).getText()+" "+"Links.");
			for(int i=0;i<countlinks;i++)
			{
				footerDriver.findElements(By.tagName("a")).get(i).sendKeys(clickonlinktab);
				Thread.sleep(3000);
					
			}
				
				// Get current window
		        String parentWinHandle = driver.getWindowHandle();
		        
		        //get all the currently opened windows and store in set
		        Set<String> winHandles=driver.getWindowHandles();
				
				for(String handle: winHandles){
					
		            if(!handle.equals(parentWinHandle))
		            {
						
					  driver.switchTo().window(handle);
					  
					  Thread.sleep(2000);
					
					  String currentURL=driver.getCurrentUrl();
					//System.out.println(currentURL);
				
						if(currentURL.contains("Check_Points"))
						{
							System.out.println("Navigated to check points page");
							Assert.assertTrue(true);
						}
						else
						{
							Assert.assertTrue(false);
						}
						
						System.out.println("Closing the new window...");
			            driver.close();
				  }
				
				}
			}
			else
			{
				System.out.println("Links do not exist in footer");
				
			}
			
			driver.quit();
			
		}
		
	
	// verifying the footer links properly navigating or not
	@Test(priority = 3)
	public void validateHondaUsedCarsByModel() throws InterruptedException, IOException
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
	        
	        String YourCity=prop.getProperty("YourCity");
	        
	        if(YourCity.contains("Bangalore"))
	        {
	        	driver.findElement(By.xpath("(//div[@class='city_img'])[1]")).click();
	        }
	        else
	        {
	        	driver.findElement(By.xpath("(//div[@class='city_img'])[2]")).click();
	        }
	        
	        /*cityselected=driver.findElement(By.xpath("//span[@class='input-group-addon city-popup']//span/i/parent::span")).getText();
	        Assert.assertEquals(cityselected, YourCity);
	        System.out.println("You have selected the city "+YourCity);
	        Thread.sleep(5000);*/
		    WebElement footerDriver=driver.findElement(By.xpath("//div[@id='cars_by_model']"));
			
			//String clickonlinktab=Keys.chord(Keys.CONTROL,Keys.ENTER);
			
			int countlinks=footerDriver.findElements(By.xpath("//div[@class='car_box']/a[1]")).size();
			//System.out.println(countlinks);//8
			
			if(countlinks>0)
			{
				for(int i=1;i<=countlinks;i++)
				{
					//WebElement footerDriver1=driver.findElement(By.xpath("//div[@id='cars_by_model']"));
					driver.findElement(By.xpath("(//div[@class='car_box']/a[1])["+i+"]")).click();
					Thread.sleep(2000);
					String carheading=driver.findElement(By.xpath("//h1[@class='filter-head']")).getText();
					Thread.sleep(2000);
					String[] data=carheading.split("\\s");
					String countcar=data[0];
					System.out.println(countcar);
					System.out.println(carheading);
					
					driver.navigate().back();
					Thread.sleep(2000);
				/*	WebElement element = driver.findElement(By.xpath("(//div[@class='car_box']/a[2])[1]"));
					((JavascriptExecutor)driver).executeScript("var mouseEvent = document.createEvent('MouseEvents');mouseEvent.initEvent('mouseover', true, true); arguments[0].dispatchEvent(mouseEvent);", element);
					System.out.println("view text:"+element.getText());*/
					//JavascriptExecutor js = (JavascriptExecutor) driver; 
					//js.executeScript("window.history.go(-1)");
					//driver.navigate().refresh();
					//Thread.sleep(3000);
					
					
				}
				
				// Get current window handle
		      /*  String parentWinHandle = driver.getWindowHandle();
		        
		        Set<String> winHandles=driver.getWindowHandles();//get all the currently opened windows and store in set
				
				for(String handle: winHandles){
					
		            if(!handle.equals(parentWinHandle))
		            {
						
					  driver.switchTo().window(handle);
					  
					  Thread.sleep(1000);
					
					  String currentURL=driver.getCurrentUrl();
					//System.out.println(currentURL);
				
						if(currentURL.contains("sell_exchange"))
						{
							System.out.println("Navigated to sell exchange page");
							Assert.assertTrue(true);
						}
						else if(currentURL.contains("car_available"))
						{
							System.out.println("Navigated to Buy Certified Cars page");
							Assert.assertTrue(true);
						}
						else if(currentURL.contains("ford_dealer"))
						{
							System.out.println("Navigated to Ford Dealers page");
							Assert.assertTrue(true);
						}
						else if(currentURL.contains("car_valuation"))
						{
							System.out.println("Navigated to car valuation page");
							Assert.assertTrue(true);
						}
						else
						{
							Assert.assertTrue(false);
						}
						
						System.out.println("Closing the new window...");
			            driver.close();
				  }
				
				}*/
			}
			else
			{
				System.out.println("Links do not exist in footer");
				
			}
			
		}
	
	// verifying the footer links properly navigating or not
	@Test(priority = 4)
	public void sellcarbuttonclick() throws InterruptedException
	{
		driver.findElement(By.xpath("//div[@class='sell-car-content']/a")).click();
		Thread.sleep(2000);
		//System.out.println(driver.findElement(By.xpath("(//div[@class='col-xs-12'])[1]/ul/li[1]/a")).getText());
		List<WebElement> elems=driver.findElements(By.xpath("(//div[@class='col-xs-12'])[1]/ul/li[1]/a"));
		int sizecount=elems.size();
		if(sizecount>0)
		{
			System.out.println("Navigated to Sell Car Page");
		}
		
		driver.navigate().back();
		
	}
	
	@DataProvider
	public Object[][] getDataForSearch() throws IOException
	{
		/*file = new File("C:\\Users\\Ankita Dey\\eclipse-workspace\\HondaProject\\src\\Data\\data.properties");
        //Declare a properties object
        prop = new Properties();
        fileInput=new FileInputStream(file);
        prop.load(fileInput);*/
		prop=getProperties();
		SearchMakeModel=prop.getProperty("HomeSearchText");
		Object[][] data=new Object[1][1];
		data[0][0]=SearchMakeModel;
		return data;
		
	}
	
	@Test(priority = 5)
	public void iamInterestedButton() throws InterruptedException
	{
		
		
		driver.findElement(By.xpath(xpath.XPATH_HOME_I_AM_INTERESTED_BUTTON)).click();
		System.out.println("Click on I Am Interested Button");
		Thread.sleep(2000); 
	}
	
	@Test(priority = 6)	
	public void enterValue() throws IOException, InterruptedException
	{
		/*file = new File("C:\\Users\\Ankita Dey\\eclipse-workspace\\HondaProject\\src\\Data\\data.properties");
        //Declare a properties object
        prop = new Properties();
        fileInput=new FileInputStream(file);
        prop.load(fileInput);*/
		
		prop=getProperties();
        
		cust_name=prop.getProperty("ExName");
	    driver.findElement(By.xpath(xpath.XPATH_HOME_I_AM_INTERESTED_POPUP_CUSTNAME)).sendKeys(cust_name);
	    
	    cust_mobile=prop.getProperty("ExMobileNumber");
		driver.findElement(By.xpath(xpath.XPATH_HOME_I_AM_INTERESTED_POPUP_CUSTMobile)).sendKeys(cust_mobile);
		
		driver.findElement(By.xpath("//input[@id='submit_enquiry']")).click();
		System.out.println("Click on Enquiry Now");
		Thread.sleep(3000);
	}
	
	@Test(priority = 7)	
	public void enterOTP() throws InterruptedException{
		
		 String handle = driver.getWindowHandles().toArray()[0].toString();
	     driver.switchTo().window(handle);
	     JavascriptExecutor jse = (JavascriptExecutor) driver;
	     jse.executeScript("document.getElementById('otp_verify_otp').value = '123456';");
	     Thread.sleep(2000);
		 driver.findElement(By.id("otp_verify_otp")).sendKeys(Keys.SPACE);
		 Thread.sleep(2000);
		 driver.findElement(By.id("verify_otp_btn")).click();
		 Thread.sleep(2000);
		 driver.findElement(By.xpath("//*[@id='otp_verify_box']/button/i")).click();
	}
	
	
	@AfterClass
	public void closeBrowser()
	{
		driver.quit();
	}
	
	
}
	




