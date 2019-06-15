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
//import org.testng.Assert;
import org.testng.annotations.Test;
import Data.HondaModels;
import reusable.Reusable;


public class BuyHonda extends Reusable {

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
	
	//invoke the driver and load the browser
	@BeforeClass
	public void invokeDriver() throws IOException, InterruptedException
	{
		/*************************Invoke the browser ******************/
		   driver=invokeBrowser();
		   
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
	        
	        Thread.sleep(3000);
	        cityselected=driver.findElement(By.xpath("//span[@class='input-group-addon city-popup']//span/i/parent::span")).getText();
	        Assert.assertEquals(cityselected, YourCity);
	        System.out.println("You have selected the city "+YourCity);
	        Thread.sleep(5000);
		    driver.get("http://beta.honda.gaadi.com/used-cars/");
		
	}
	
	//validating the city as selected from home page
	@Test(priority = 0)
	public void validateCity()
	{
		String script = "return document.getElementById('temp-city').getAttribute('value');";
		String city = ((JavascriptExecutor) driver).executeScript(script).toString();
		Assert.assertEquals(city, cityselected);
		System.out.println("Same city selected which is :"+city);
	}
	
	//filter selection and validate the total cars count
	@Test(priority = 1)
	public void dataSelectionHondaCar() throws IOException, InterruptedException
	{
  
	 // for honda cars
	  options= driver.findElements(By.xpath("(//div[@id='aboutus'] //input[@type='checkbox'])"));	  
	  int countarr=HondaModels.HondaCars.length;
	  if(countarr>0)
	  {
		  for(int i=0;i<countarr;i++)
		  {
			  options= driver.findElements(By.xpath("(//div[@id='aboutus'] //input[@type='checkbox'])"));
			 if(options.size()>0)
			 {
				 
				 String expectedcar=HondaModels.HondaCars[i].toString();
				 
				 ClickAllCheckboxes("(//div[@id='aboutus'] //input[@type='checkbox'])","//div[@id='aboutus']//label",expectedcar);
				 
				 
			 }
			 else
			 {
				 System.out.println("No checkboxes present for Honda Car Models");
				 break;
			 }
		  }
	  }
	  
	  // for Nonhonda Cars
	  int countarrnonhonda=HondaModels.NonHondaCars.length;
	  if(countarrnonhonda>0)
	  {
		  
		  driver.findElement(By.xpath("(//div[@class='panel panel-default'])[2]")).click();
	      Thread.sleep(2000);
		  for(int i=0;i<countarrnonhonda;i++)
		  {
			  options= driver.findElements(By.xpath("(//div[@id='nonhonda'] //input[@type='checkbox'])"));
			 if(options.size()>0)
			 {
				 
				 String expectedcar=HondaModels.NonHondaCars[i].toString();
				 
				 ClickAllCheckboxes("(//div[@id='nonhonda'] //input[@type='checkbox'])","//div[@id='nonhonda']//label",expectedcar);
				 
				 
			 }
			 else
			 {
				 System.out.println("No checkboxes present for Non Honda Car Models");
				 break;
			 }
		  }
	  }
	  
	  
	 /* file = new File("C:\\Users\\Ankita Dey\\eclipse-workspace\\HondaProject\\src\\Data\\data.properties");
      //Declare a properties object
      prop = new Properties();
      fileInput=new FileInputStream(file);
      prop.load(fileInput);*/
	  
	  prop=getProperties();
      
      // price selection
      String price=prop.getProperty("price");
      if(!price.isEmpty())
      {
    	  selectFromDropdownById("price-range",price);
      }
      
      //Kms selection
      String Kms=prop.getProperty("Kms");
      if(!Kms.isEmpty())
      {
    	  
    	  rbkms=driver.findElements(By.xpath("//div[@class='radio']//input"));
    	  if(rbkms.size()>0)
    	  {
    		  SelectRadioButton("//div[@class='radio']//input",Kms);
    	  }
    	  else
		  {
			 System.out.println("No radio buttons present for Kilometers Driven");
			 
		  }
         
      }
     
      //Model Year selection
      String YearFrom=prop.getProperty("YearFrom");
      String YearTo=prop.getProperty("YearTo");
      
      if(!YearFrom.isEmpty())
      {
    	  selectFromDropdownById("invt_year_from",YearFrom);
      }
      
      if(!YearTo.isEmpty())
      {
    	  selectFromDropdownById("invt_year_to",YearTo);
      }
      
      
      // Fuel Type Selection
      int countarrfueltype=HondaModels.FuelType.length;
      if(countarrfueltype>0)
      {
    	  for(int i=0;i<countarrfueltype;i++)
		  {
			  options= driver.findElements(By.xpath("//input[@class='search_fuel_type']"));
			 if(options.size()>0)
			 {
				 
				 String expectedfueltype=HondaModels.FuelType[i].toString();
				 
				 ClickAllCheckboxes("//input[@class='search_fuel_type']","//input[@class='search_fuel_type']/parent::label",expectedfueltype);
				 
				 
			 }
			 else
			 {
				 System.out.println("No checkboxes present for Fuel Type");
				 break;
			 }
		  }
    	  
      }
      
      //Transmission selection
      int countarrtrans=HondaModels.Transmission.length;
      if(countarrtrans>0)
      {
    	  for(int i=0;i<countarrtrans;i++)
		  {
			  options= driver.findElements(By.xpath("//input[@class='search_trans']"));
			 if(options.size()>0)
			 {
				 
				 String expectedtrans=HondaModels.Transmission[i].toString();
				 
				 ClickAllCheckboxes("//input[@class='search_trans']","//input[@class='search_trans']/parent::label",expectedtrans);
				 
				 
			 }
			 else
			 {
				 System.out.println("No checkboxes present for Transmission");
				 break;
			 }
		  }
    	  
      }
      
      // Total count match
      String actualcounttext=driver.findElement(By.className("filter-head")).getText();
      String arr[]=actualcounttext.split("\\s+");
      String actualcount=arr[0].toString();
      totcount=Integer.parseInt(actualcount);
      totrowcount=getData();
      if(totrowcount>0)
      {
    	  if(totrowcount>=12)
  		 {
      	  ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
  			Thread.sleep(5000);
  			totrowcountnew=getData();
  			
  			totcount=Integer.parseInt(actualcount);
  			Assert.assertEquals(totcount, totrowcountnew);
  	    	System.out.println("Total count is matched");
  			
  		}
        else
         {
      	 
      	   Assert.assertEquals(totcount, totrowcount);
      	   System.out.println("Total count is matched");
         }
        
      }
      else
      {
    	  System.out.println("No Records Found");
      }
     
	}
	
	
	//validating the city is getting changed or not
	@Test(priority = 2)
	public void validateCityChangeSelection() throws IOException, InterruptedException
	{
	    /*file = new File("C:\\Users\\Ankita Dey\\eclipse-workspace\\HondaProject\\src\\Data\\data.properties");
        //Declare a properties object
        prop = new Properties();
        fileInput=new FileInputStream(file);
        prop.load(fileInput);
        */
		
		prop=getProperties();
		String City=prop.getProperty("City");
        
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
	
	@Test(priority = 3)
	public void ListView()
	{
		try {
			
			driver.findElement(By.xpath("//*[@id='click_advance']/i")).click();
			System.out.println("Listview click is working");
		}
		  
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
	
		
	}
	
	@Test(priority = 4)
	public void SortBy() throws IOException, InterruptedException
	{
		/*file = new File("C:\\Users\\Ankita Dey\\eclipse-workspace\\HondaProject\\src\\Data\\data.properties");
        //Declare a properties object
        prop = new Properties();
        fileInput=new FileInputStream(file);
        prop.load(fileInput);*/
		
		prop=getProperties();
		
		String sortByText=prop.getProperty("SortBy");
	
	      WebElement dropdown=driver.findElement(By.id("orderByText"));
	      dropdown.click();
	      selectFromLiDropdown(dropdown,sortByText);
	    
	}
	
	@AfterClass
	public void closeBrowser()
	{
		driver.quit();
	}
	

}
