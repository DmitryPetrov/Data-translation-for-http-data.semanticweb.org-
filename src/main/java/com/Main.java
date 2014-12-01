package com;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main
{

	public static void main(String[] args )
	{
		HTMLParse html_doc = new HTMLParse();
		XLSXPrase xlsx_doc = new XLSXPrase();

		try
		{
			XMLCreate obj = new XMLCreate(html_doc.parse(), xlsx_doc.parse());
			obj.Create();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
