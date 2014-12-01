package com;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;


public class XMLCreate
{
	private Iterator<String> itr_str;
	private Iterator<List<String>> itr_list;
	private Map<String, Map<String, String>> paper;
	private List<List<String>> pc;
	
	XMLCreate (Map<String, Map<String, String>> paper, List<List<String>> pc)
	{
		this.paper = paper;
		this.pc = pc;
	}
	

	void Create () throws FileNotFoundException, IOException
	{
		Element snapshot = new Element("snapshot");
		
		Element pc = new Element("pc");
		AddPC(pc);
		
		Element submissions = new Element("submissions");
		int count = 1;
		itr_str = paper.keySet().iterator();
		while (itr_str.hasNext())
		{
			String title = itr_str.next();
			AddSubmission(Integer.toString(count++), "yes", title, paper.get(title), submissions);
		}
		

		
		snapshot.addContent(pc);
		snapshot.addContent(submissions);

		
		XMLOutputter outputter = new XMLOutputter();
		outputter.output(new Document(snapshot), new FileOutputStream ("d://data.xml"));	
	}
	
	private void AddPC (Element parent)
	{	
		itr_list = pc.iterator();
		itr_list.next();
		while (itr_list.hasNext())
		{
			List<String> pc_member = itr_list.next();
			AddPCMember(pc_member, parent);
		}		
	}
	
	private void AddPCMember (List<String> pc_member, Element parent)
	{
		Element el_pc_member = new Element("pc_member");
		
		itr_str = pc_member.iterator();
		String name = itr_str.next() + " " + itr_str.next();
		
		AddName(name, el_pc_member);
		AddSameAs(name, el_pc_member);
		
		el_pc_member.addContent(new Element("email").setText(itr_str.next() ) );
		
		el_pc_member.addContent(new Element("homepage").setText(itr_str.next() ) );
		
		String[] organization = itr_str.next().split(",");
		el_pc_member.addContent(new Element("country").setText(organization[organization.length-1]));
		el_pc_member.addContent(new Element("affiliation").setText(organization[0]));
		
		String role;
		if (!(role = itr_str.next()).equals("") );
			el_pc_member.addContent(new Element("role").setText(role) );
		
		parent.addContent(el_pc_member);
	}
	
	private void AddName (String name, Element parent)
	{					
		String[] full_name = name.split(" ");
		//ןמהדמעמגטעü טלÿ
		
		Element first_name = new Element("first_name");
		first_name.setText(full_name[0]);
		
		Element last_name = new Element("last_name");
		last_name.setText(full_name[full_name.length-1]);
		
		parent.addContent(first_name);
		parent.addContent(last_name);
	}
	
	private void AddSameAs (String name, Element parent)
	{
		String[] full_name = name.split(" ");
		
		Element sameAs = new Element("sameAs");		
		sameAs.setText("http://data.semanticweb.org/person/" + full_name[0] + "-" + full_name[full_name.length-1]);
		parent.addContent(sameAs);
	}
	
	private void AddSubmission (String number, String paper, String title_name, Map<String, String> name, Element parent)
	{
		Element submission = new Element("submission");
		submission.setAttribute("number", number);
		submission.setAttribute("paper", paper);
		
		Element title = new Element("title");
		title.setText(title_name);
		submission.addContent(title);
		
		Element authors = new Element("authors");
		Iterator<String> itr = name.keySet().iterator();
		while (itr.hasNext())
		{
			Element author = new Element("author");
			String author_name = itr.next();
			AddName(author_name, author);
			AddSameAs(author_name, author);
			authors.addContent(author);
		}
		submission.addContent(authors);
		
		Element decision = new Element("decision");
		decision.setText("accept");
		submission.addContent(decision);
		
		parent.addContent(submission);
	}
	
}
