package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class ReadExcel {
	
	    File src;
	    XSSFWorkbook wb;
	    XSSFSheet Sheet1;
	    String cellValue;
	    
	@Test
	public void readdata() throws IOException
	{
		src=new File("D:\\testdata.xlsx");
		FileInputStream fis = new FileInputStream(src);
		wb=new XSSFWorkbook(fis);
		Sheet1=wb.getSheetAt(0);
		int rowcount=Sheet1.getPhysicalNumberOfRows();
		int colcount=Sheet1.getRow(0).getPhysicalNumberOfCells();
		//System.out.println(rowcount);
		//System.out.println(colcount);
		for(int i=0;i<rowcount;i++)
		{
			Row row=Sheet1.getRow(i);
			for(int j=0;j<colcount;j++)
			{
				Cell cell=row.getCell(j);
				if(cell.getCellType() == CellType.NUMERIC)
				{
					Double value=cell.getNumericCellValue();
					Long val=value.longValue();
					System.out.println(val.toString());
				}
				else
				{
					System.out.println(cell.getStringCellValue());
				}
				
	            
	             wb.close();
			}
		}
	}

}
