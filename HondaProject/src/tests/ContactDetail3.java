package tests;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
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

public class ContactDetail3 extends Reusable{
	
	int count;
	int countnextitems;
	String name;
	String newName;
	File file=null;
	FileInputStream fileInput=null;
	WebElement SearchId;
	String cityselected;
	Properties prop;
	File src;
	XSSFWorkbook wb;
	XSSFSheet Sheet1;
	String cellValue,cellValuenumber;
	int rowcount=0;
	int colcount=0;
	WebElement inlineErr;
	String ErrMsg;
	
	@BeforeClass
	public void invokeDriver() throws IOException, InterruptedException
	{
		/*************************Invoke the browser ******************/
		   driver=invokeBrowser();
		
		/*************************Invoke the browser ******************/
		   
		  // select your city
		   
		    file = new File("C:\\Users\\Ankita Dey\\eclipse-workspace\\HondaProject\\src\\Data\\Config.properties");
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
	        
	        cityselected=driver.findElement(By.xpath("//span[@class='input-group-addon city-popup']//span/i/parent::span")).getText();
	        Assert.assertEquals(cityselected, YourCity);
	        System.out.println("You have selected the city "+YourCity);
	        Thread.sleep(5000);
	        driver.get("http://beta.honda.gaadi.com/used-cars/");
	}
	
	@Test(dataProvider="testdata")
	public void contactDetail(String name,String phone,String valmessage) throws IOException, InterruptedException
	{
		
			driver.findElement(By.xpath(xpath.XPATH_CONTACT_DETAIL)).click();
	   	    Thread.sleep(2000);
	   	    driver.findElement(By.xpath("//input[@id='cust_name']")).click();
	   	    driver.findElement(By.xpath("//input[@id='cust_name']")).sendKeys(name);
	   	    driver.findElement(By.xpath("//input[@id='mobile']")).click();
		    driver.findElement(By.xpath("//input[@id='mobile']")).sendKeys(phone);
		    Thread.sleep(2000);
			WebElement btn=driver.findElement(By.xpath("//input[@id='submit_enquiry']"));
			btn.click();
			Thread.sleep(3000);
		            
			boolean errsize=driver.findElements(By.cssSelector(".error-message")).size()>0;
			int counterr=driver.findElements(By.cssSelector(".error-message")).size();
			if(errsize)
			{
				if(counterr>1)
				{
					ErrMsg="Validations are incorrect for both Name and Phone";
				}
				else
				{
					if(valmessage.contains("Name")||valmessage.contains("Mobile")||valmessage.contains("Contact"))
					{
						inlineErr=driver.findElement(By.xpath("(//div[@class='error-message'])[1]"));
					}
					
					ErrMsg=inlineErr.getText();
					Thread.sleep(3000);
					Assert.assertEquals(ErrMsg,valmessage);
				}
			
			    System.out.println(ErrMsg);
			    Thread.sleep(3000);
			    driver.findElement(By.xpath("(//i[@class='icon-ic_close'])[2]")).click();
				Thread.sleep(2000);
			}
			else
			{
				Assert.assertTrue(true);
				
				Thread.sleep(3000);
				
				driver.findElement(By.xpath("//i[@class='icon-ic_close close-popup']")).click();
				Thread.sleep(2000);
			}
			
			driver.navigate().refresh();
			Thread.sleep(3000);
		
		
	       
	}
	
	//This reads data from excel and provides data to test case
	@DataProvider(name="testdata")
	public Object[][] ReadVariant() throws IOException
	  {
		    src=new File("D:\\testdata1.xlsx");
		    FileInputStream fis = new FileInputStream(src);
		    wb=new XSSFWorkbook(fis);
		    Sheet1=wb.getSheetAt(0);    
	        int RowNum =Sheet1.getPhysicalNumberOfRows();// count number of Rows //7
	        int ColNum=Sheet1.getRow(0).getPhysicalNumberOfCells();//3
	        Object Data[][]= new Object[RowNum][ColNum+1]; // pass my  count data in array
	         
	            for(int i=0; i<RowNum; i++) //Loop work for Rows
	            {  
	                Row row= Sheet1.getRow(i);
	                 
	                for (int j=0; j<ColNum+1; j++) //Loop work for colNum
	                {
	                    if(row==null)
	                    {
	                    	Data[i][j]= "";
	                    	
	                    } 
	                    else
	                    {
	                        Cell cell= row.getCell(j);
	                        if(cell==null)
	                        {
	                        	//DataFormatter hdf = new DataFormatter();
	                           // String value=hdf.formatCellValue(cell);
	                            Data[i][j]= "";//if it get Null value it pass no data 
	                        }
	                        	
	                        else
	                        {
	                        	
	                        	DataFormatter hdf = new DataFormatter();
	                            String value=hdf.formatCellValue(cell);
	                            Data[i][j]=value; //This formatter get my all values as string i.e integer, float all type data value
	                        }
	                    }
	                }
	            }
	 
	        return Data;
	    }
	
	
}

	
	
	
	
	
	
	
	
