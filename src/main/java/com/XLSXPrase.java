package com;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSXPrase
{
	public List<List<String>> parse () throws IOException
	{
		List<String> pc_member;
		List<List<String>> pc = new LinkedList<List<String>>();
		
		FileInputStream pc_file =  new FileInputStream("D://pc.xlsx");
        Workbook diski = new XSSFWorkbook(pc_file);
        Sheet list_1 = diski.getSheetAt(0);
        for (Row row:list_1)
        {
        	pc_member = new LinkedList<String>();
            for (Cell cell:row)
            {
	        	if (cell.getCellType() == 1)
	        		pc_member.add(cell.getStringCellValue() );
	        	else
	        		pc_member.add("");
            }
            pc.add(pc_member);
        }
        
/*        Iterator<List<String>> itr = pc.iterator();
        while(itr.hasNext())
        {
        	Iterator<String> itr2 = itr.next().iterator();
        	while(itr2.hasNext())
        	{
        		System.out.print(itr2.next() + " | ");
        	}
        	System.out.println();
        }*/

        return pc;
	}
}
