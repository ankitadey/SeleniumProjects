package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reusable.Reusable;
import xpath.xpath;


	public class SellCarExcelReading extends Reusable {

	

		//Reusable obj=new Reusable();
		public WebDriver driver;
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
		
		
		int count;
		int countnextitems;
		String name;
		String newName;
		File src;
		XSSFWorkbook wb;
		XSSFSheet Sheet1;
		String cellValue,cellValuenumber;
		int rowcount=0;
		int colcount=0;
		WebElement inlineErr;
		String ErrMsg;
		String heading;
		Row row1;
		Cell cell1;
		FileInputStream fis;
		XSSFWorkbook workbook;
		Boolean ispresent;
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
		        
		        driver.findElement(By.xpath(xpath.Xpath_Sell_car)).click();
	    		System.out.println("Click On Sell Car Tab");
	    		Thread.sleep(2000);
		}
		
        @Test(priority=0,dataProvider="testdata")
    	public void sellmenuclick(String ManfactYear,String MakeModel,String Variant,String RegNo,String KMS,String ValMessage,String indexno,String printmsg) throws InterruptedException, IOException
    	{
			        
        	        Thread.sleep(3000);
		    
        	        driver.findElement(By.cssSelector(".SumoSelect.sumo_reg_year")).click();
		    		Thread.sleep(3000);
		    		driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[1]")).clear();
		    		driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[1]")).sendKeys(ManfactYear);
		    		driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[1]")).sendKeys(Keys.ARROW_DOWN);
		    		driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[1]")).sendKeys(Keys.ENTER);
		    		
		    		if(!ManfactYear.equalsIgnoreCase("Manufacture year"))
			        {
		    			driver.findElement(By.cssSelector(".SumoSelect.sumo_model_id")).click();
		    			driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[2]")).clear();
				        driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[2]")).sendKeys(MakeModel);
				        driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[2]")).sendKeys(Keys.ARROW_DOWN);
			    		driver.findElement(By.xpath("(//p[@class='CaptionCont SelectBox search']/input)[2]")).sendKeys(Keys.ENTER);	 
			         
				      
			        }
		    		
		    		if(!MakeModel.equalsIgnoreCase("Make Model"))
		    		{
		    			 driver.findElement(By.id("variant_id")).click();
					        
					     selectFromDropdown("//select[@class='form-control effect-place']",Variant);
					     
		    		}
		    		
		    		
		    		driver.findElement(By.xpath("//input[@name='reg_number']")).click();
		    		driver.findElement(By.xpath("//input[@name='reg_number']")).clear();
		    	    driver.findElement(By.xpath("//input[@name='reg_number']")).sendKeys(RegNo);
		    	    Thread.sleep(2000);
		    	    
		    	    driver.findElement(By.xpath("//input[@name='kilo_driven']")).click();
		    	    driver.findElement(By.xpath("//input[@name='kilo_driven']")).clear();
			        driver.findElement(By.xpath("//input[@name='kilo_driven']")).sendKeys(KMS);
			        Thread.sleep(2000);
			        
			        driver.findElement(By.id("sell_next")).click();
			        Thread.sleep(3000);
			        
			        System.out.println("Next button clicked");
			        
			        String Xpath_error_message_start = "(//div[text()='*Required'])[";
			        		

			        String Xpath_error_message_end = "]";
			        
			        String Xpath_errmsg=Xpath_error_message_start+indexno+Xpath_error_message_end;
			        System.out.println(Xpath_errmsg);
			     
			        try
			        {
			          Boolean ispresent =   driver.findElements(By.xpath(Xpath_errmsg)).size()>0;
			          if(ispresent)
			          {
			           //doing something.
			        	  Thread.sleep(3000);
			        	  
			        	  ErrMsg=driver.findElement(By.xpath(Xpath_errmsg)).getText();
			        	  Thread.sleep(3000);
			        	  
			        	  Assert.assertEquals(ErrMsg, ValMessage);
					        
					      System.out.println(printmsg);
					      
			          }
			         
			        }
			        catch(Exception e)
			        {
			        	System.out.println("Object not found");
			          e.printStackTrace();
			          
			          
			        }
			        
		        Thread.sleep(2000);
	   }
        
        @Test(priority=1)
        public void personaldetailspagevalidate() throws IOException, InterruptedException
        {
        	
        		
        		file = new File(System.getProperty("user.dir")+"\\src\\Data\\data.properties");
 		        //Declare a properties object
 		        prop = new Properties();
 		        fileInput=new FileInputStream(file);
 		        prop.load(fileInput);
 		        
        		Name=prop.getProperty("sellname");
		        
		        driver.findElement(By.id("name")).sendKeys(Name);
		        
		        System.out.println("Name Entered");
		        
                Mobile=prop.getProperty("sellmobile");
                
		        driver.findElement(By.id("sell_mobile")).sendKeys(Mobile);
		        
		        System.out.println("MobileNumber Entered");
		        
		        Thread.sleep(3000);
		        
		        if(Mobile.equalsIgnoreCase("")||Mobile.isEmpty())
		        {
		        	driver.findElement(By.xpath("//input[@id='sell_next']")).click();
		        	String Xpath_errmsg="//label[@class='sell-mobile']";
		        	System.out.println(driver.findElement(By.xpath(Xpath_errmsg)).getText());
			       
		        }
		        else
		        {
		        	
		        	String handle = driver.getWindowHandles().toArray()[0].toString();
			        driver.switchTo().window(handle);
			        JavascriptExecutor jse = (JavascriptExecutor) driver;
			        jse.executeScript("document.getElementById('otp_verify_otp').value = '123456';");
			        driver.findElement(By.id("otp_verify_otp")).sendKeys(Keys.SPACE);
			        driver.findElement(By.id("verify_otp_btn")).click();
			        Thread.sleep(2000);
	        		driver.findElement(By.xpath("//*[@id='otp_verify_box']/button/i")).click();
	        		Thread.sleep(2000);
		        	
		        }
        	
  			
        }
        
      //This reads data from excel having heading and provides data to test case
    	@DataProvider(name="testdata")
    	public Object[][] ReadVariant() throws IOException
    	  {
    			return readExcelData("hondatestdata.xlsx",0,"D:\\");
    			 
    	  }
    	
        
       /* @AfterClass
    	public void closeBrowser()
    	{
    		driver.quit();
    	}*/
        
}
