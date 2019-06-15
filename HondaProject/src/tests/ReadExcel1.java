package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReadExcel1 {
	
	    File src;
	    XSSFWorkbook wb;
	    XSSFSheet Sheet1;
	    String cellValue;
	    
	@DataProvider(name="testdata")
	public Object[][] readdata() throws IOException
	{
		src=new File("D:\\testdata.xlsx");
		FileInputStream fis = new FileInputStream(src);
		wb=new XSSFWorkbook(fis);
		Sheet1=wb.getSheetAt(0);
		int row=Sheet1.getPhysicalNumberOfRows();
		System.out.println(row);//7
		int col=Sheet1.getRow(0).getPhysicalNumberOfCells();
		System.out.println(col);//3
		
		String TestData[][]=new String[row-1][col];
		
		int count=0;
		
		for(int i=1;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				Cell cell=Sheet1.getRow(i).getCell(j);
				TestData[count][j]=cell.getStringCellValue();
				
			}
			count++;
		}
				
	    return TestData;
		
		
	}
	
	@Test(dataProvider="testdata")
	public void testD(String name,String phone,String valmessage)
	{
		System.out.println(name+phone+valmessage);
	}

}
