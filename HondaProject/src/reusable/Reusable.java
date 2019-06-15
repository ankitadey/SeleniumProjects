package reusable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
//import org.openqa.selenium.remote.Augmenter;
//import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.TakesScreenshot;


public class Reusable {
	
	public String url=null;
	public String browser1=null;
	public String propertyFilePath=null;
	public static WebDriver driver;
	FileInputStream fileInput=null;
	File file=null;
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

    private static Properties props;
  
    
  
	/*************************Invoke the browser ******************/
	
	  public WebDriver invokeBrowser() throws IOException
	  {
		
		    file = new File(System.getProperty("user.dir")+"\\src\\Data\\Config.properties");
	        //Declare a properties object
	        Properties prop = new Properties();
	        fileInput=new FileInputStream(file);
	        prop.load(fileInput);
	        
	        browser1=prop.getProperty("browser");
	        
	        if(browser1.equalsIgnoreCase("chrome"))
			{
				propertyFilePath="D:\\Setup files\\chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", propertyFilePath);
				/*ChromeOptions opt = new ChromeOptions();
				opt.addArguments("disable-infobars");
				opt.addArguments("--start-maximized");
				opt.addArguments("--disable-extensions");*/
				driver=new ChromeDriver();
			}
			else if(browser1.equalsIgnoreCase("mozilla"))
			{
				propertyFilePath="D:\\Setup files\\Mozilla_Driver\\geckodriver.exe";
				System.setProperty("webdriver.gecko.driver", propertyFilePath);
				driver=new FirefoxDriver();
			}
			else
			{
				propertyFilePath="D:\\Setup files\\IE_Driver_64_bit\\IEDriverServer.exe";
				System.setProperty("webdriver.ie.driver", propertyFilePath);
				driver=new InternetExplorerDriver();
			}
            
	 
	        //Get properties from configuration.properties
	        url=prop.getProperty("url");
	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);// applicable for every test case
	          
	        driver.get(url);
	        
	        return driver;
	        
	  }
	  
	
	  protected static Properties getProperties() throws FileNotFoundException, IOException
	    {
	        if(props == null)
	        {
	            props = new Properties();
	            props.load(new FileInputStream(new File(System.getProperty("user.dir")+"\\src\\Data\\data.properties")));
	        }
	        return props;
	    }
	  
	  
	  public void getScreenShot(String result) throws IOException, InterruptedException
	  {
		   
		    File screenshot = ((TakesScreenshot)driver).
		                        getScreenshotAs(OutputType.FILE);
		  //FileUtils.copyFile(screenshot, new File("C:\\testScreenShot.jpg"));
		    Thread.sleep(2000);
		    String fileSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		    
		    FileHandler.copy(screenshot, new File("D:/reports/screenshot"+result+fileSuffix+".png"));
	  }
	 
	  /*************************Select values from dropdown using xpath ******************/
	
	   public void selectFromDropdown(String xPath, String data) {
		   WebElement ele = driver.findElement(By.xpath(xPath));
		   Select sell=new Select(ele);
		   sell.selectByVisibleText(data);
		   
	   }
	   
	   /*************************Select values from dropdown having li using xpath ******************/
	 
		
	   public void selectFromLiDropdown(WebElement dropdown,String data) {
		  
		  
		   List<WebElement> options=dropdown.findElements(By.xpath("//ul[@class='dropdown-menu']/li/a"));
		      for(WebElement option:options)
		      {
		    	  if(option.getText().equalsIgnoreCase(data))
		    	  {
		    		  option.click();
		    		  System.out.println("Sort By is working");
		    		  break;
		    	  }
		      }
	   }
	   
	   /*************************Select values from dropdown using id ******************/
		
	   public void selectFromDropdownById(String Id, String data) {
		   WebElement ele = driver.findElement(By.id(Id));
		   Select sell=new Select(ele);
		   sell.selectByValue(data);
		   
	   }
	   
	   /**************** Get Data from each page and return rowcount of each page 
	 * @throws InterruptedException ***********************/
	   public  int getData() throws InterruptedException
		{
			WebElement table=driver.findElement(By.xpath("//div[@id='inventory_div']"));
			List<WebElement> cars=table.findElements(By.xpath("//div[@class='col-sm-4 col-xs-4 pad-L0 pad-R0 mrg-R20 both_page_view']"));
			int rowcount=cars.size();//3
			System.out.println("Total count "+ rowcount);
			
			
			for(int i=0;i<rowcount;i++)
			{
				List<WebElement> carname=driver.findElements(By.xpath("//div[@class='col-sm-4 col-xs-4 pad-L0 pad-R0 mrg-R20 both_page_view']//h3/a"));
				System.out.println(carname.get(i).getText());
				
			}
			
			return rowcount;
					
		}
	   
	   

	  //Fill the data in the textboxes
		
	   public void FillTextboxes(String xPath, String data) {
		   WebElement ele = driver.findElement(By.xpath(xPath));
		   ele.sendKeys(data);
		   
		   
	   }
	   
	   //method to select multiple checkboxes with the specified value
	   public void ClickAllCheckboxes(String xpath,String labelxpath, String valueToSelect) throws InterruptedException {
		
		   List<WebElement> options=driver.findElements(By.xpath(xpath));
		   int checkboxcount=options.size();
		   
		   for(int k=0;k<checkboxcount;k++)
			 {
				 
				 List <WebElement> optionslabel=driver.findElements(By.xpath(labelxpath));
				 String actualtext=optionslabel.get(k).getText();
				 System.out.println(valueToSelect+""+actualtext);
				 if(valueToSelect.equalsIgnoreCase(actualtext))
				 {
					    options.get(k).click();
					    Thread.sleep(3000);
					    break;
				 }
				 
				 
			 }
		   
	   }
	   
	   //method to select radio button with the specified value
	   public void SelectRadioButton(String xpath, String valueToSelect) throws InterruptedException {
		
		   List<WebElement> options=driver.findElements(By.xpath(xpath));
		      int size=options.size();
	          for(int i=0;i<size;i++)
	          {
	        	  options=driver.findElements(By.xpath(xpath));
	        	  String rbvalue=options.get(i).getAttribute("value");
	        	  if(rbvalue.equalsIgnoreCase(valueToSelect))
	        	  {
	        		  options.get(i).click();
	        		  Thread.sleep(3000);
	        		  break;
	        	  }
	          }
	   }
	   
	   
	  public void closeBrowser() {
		
		driver.close();
		
	}	
	  
	  //read excel data and return object array 
	  public Object[][] readExcelData(String FileName,int SheetIndex,String FilePath) throws FileNotFoundException, IOException {
			src=new File(FilePath+FileName);
			FileInputStream fis = new FileInputStream(src);
			wb=new XSSFWorkbook(fis);
			Sheet1=wb.getSheetAt(SheetIndex);
			int rownum=Sheet1.getPhysicalNumberOfRows();
			System.out.println(rownum);//31036
			int colnum=Sheet1.getRow(0).getPhysicalNumberOfCells();
			System.out.println(colnum);//3
			String TestData[][]=new String[rownum-1][colnum];
			int count=0;
			  for(int i=1; i<rownum; i++) //Loop work for Rows
			    {    Row row= Sheet1.getRow(i);
			         
			        for (int j=0; j<colnum; j++) //Loop work for colNum
			        {
			            if(row==null)
			            {
			            	TestData[i][j]= "";
			            	
			            } 
			            else
			            {
			                Cell cell= row.getCell(j);
			                if(cell==null || cell.getCellType() == CellType.BLANK)
			                {
			                	//DataFormatter hdf = new DataFormatter();
			                   // String value=hdf.formatCellValue(cell);
			                	TestData[count][j]= "";//if it get Null value it pass no data 
			                }
			                	
			                else
			                {
			                	
			                	DataFormatter hdf = new DataFormatter();
			                    String value=hdf.formatCellValue(cell);
			                    TestData[count][j]=value; //This formatter get my all values as string i.e integer, float all type data value
			                }
			            }
			        }
			        count++;
			    }
			
			  return TestData;
		}
	  
	  // remove quotes from start and end of string
	 /* private static String removeQuotesFromStartAndEndOfString(String inputStr) {
		    String result = inputStr;
		    int firstQuote = inputStr.indexOf('\"');
		    int lastQuote = result.lastIndexOf('\"');
		    int strLength = inputStr.length();
		    if (firstQuote == 0 && lastQuote == strLength - 1) {
		        result = result.substring(1, strLength - 1);
		    }
		    return result;
		}*/
	 
	}


