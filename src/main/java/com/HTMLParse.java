package com;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParse
{

	public Map<String, Map<String, String>> parse() throws IOException
	{
		File input = new File("d://accepted.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		
		Map<String, Map<String, String>> paper = new LinkedHashMap<>();;
		
		Elements e1 = doc.body().children();
		Iterator<Element> itr1 = e1.iterator();
		while(itr1.hasNext() )
		{
			Element t = itr1.next();
			if(t.tagName().equals("div") )
			{
				if (t.hasAttr("class") )
				{
					if (t.attr("class").equals("paper") )
					{
						Elements e2 = t.children();
						String title = null;
						String names = null;
						Map<String, String> name_ref = null;
						
						
						Iterator<Element> itr2 = e2.iterator();
						while (itr2.hasNext())
						{
							t = itr2.next();
							if(t.tagName().equals("span") )
							{
								if (t.hasAttr("class"))
								{
									if(t.attr("class").equals("authors"))
									{
										names = t.text();
										name_ref = new LinkedHashMap<String, String>();
										
										Elements e3 = t.children();
										Iterator<Element> itr3 = e3.iterator();
										p(t.toString());
										
										while(itr3.hasNext())
										{
											t = itr3.next();

											if (t.tagName().equals("span"))
											{
												Elements e4 = t.children();
												Iterator<Element> itr4 = e4.iterator();
												
												while(itr4.hasNext())
												{
													t = itr4.next();

													if(t.tagName().equals("a"))
													{
														if (t.hasAttr("href") )
														{
															name_ref.put(t.text(), t.attr("href"));
															names = names.replaceAll(t.text(), "");
														}
													}		
												}	
											} else if(t.tagName().equals("a"))
											{
												if (t.hasAttr("href") )
												{
													name_ref.put(t.text(), t.attr("href"));
													names = names.replaceAll(t.text(), "");
												} else {}
											}
										}
										
										names = names.replaceAll("\\.$", "");
										String[] str1 = names.split(", ");
										for (String name: str1)
										{
											if (name.indexOf(" and ") != -1)
											{
												String[] str2 = name.split(" and ");
												for (String s: str2)
												{
													s.trim();
													if (!s.equals(""))
														name_ref.put(s.trim(), "");
												}
											} else if (!name.equals(""))
											{
												name_ref.put(name.trim(), "");
											}
										}
										
/*										Iterator<String> itr5 = name_ref.keySet().iterator();
										while(itr5.hasNext())
										{
											String s = itr5.next();
											p(s + " " + name_ref.get(s ) );
										}*/
									}	
									if(t.attr("class").equals("title"))
									{
										title = t.text();
										//p("text " + title);
									}							
								}
							}
						}
					paper.put(title, name_ref);
					//p("");	
					}
				}
			}	
		}
		
/*		Iterator<String> itr = paper.keySet().iterator();
		while (itr.hasNext())
		{
			String title = itr.next();
			p(title);
			
			Iterator<String> itr2 = paper.get(title).keySet().iterator();
			while(itr2.hasNext())
			{
				String name = itr2.next();
				p(name + "  " + paper.get(title).get(name));
			}
			p("");
		}*/
		
		return paper;
	}
	
	static void p(String s )
	{
		System.out.println(s);
	}

}
