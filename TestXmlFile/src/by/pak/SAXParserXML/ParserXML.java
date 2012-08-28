package by.pak.SAXParserXML;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.RootElement;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import by.pak.testxmlfile.Message;
import by.pak.testxmlfile.R;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ParserXML
{
	private Resources res=null;
	String result="";
	boolean update=false;
	boolean entry=false;
	boolean id=false;
	boolean description=false;
	boolean title=false;
	boolean type=false;
	boolean stars=false;
	boolean icon=false;
	boolean latitude=false;
	boolean longitude=false;
	boolean shorttext=false;
	boolean text=false;
	boolean specification=false;
	boolean item=false;

	final Entry currentEntry=new Entry();
	final List<Entry> messages=new ArrayList<Entry>();
	
	private Attributes tmpAttrs;
	private String tmpValue;
	
	/**
	 * SAX Parser
	 */
	public ParserXML(Context context)
	{
		res=context.getResources();
		try
		{
			//URL rssUrl=new URL("http://feeds2.feedburner.com/Mobilab");
			SAXParserFactory factory=SAXParserFactory.newInstance();
			SAXParser saxParser=factory.newSAXParser();
			XMLReader xmlReader=saxParser.getXMLReader();
			XMLHandler xmlHandler=new XMLHandler();
			xmlReader.setContentHandler(xmlHandler);
			//InputSource inputSource=new InputSource(rssUrl.openStream());
			InputSource inputSource=new InputSource(readXml());
			xmlReader.parse(inputSource);
		}
		catch (IOException e)
		{
			Log.e("errorParser",e.getMessage());
		}
		catch (SAXException e)
		{
			Log.e("errorParser",e.getMessage());
		}
		catch (ParserConfigurationException e)
		{
			Log.e("errorParser",e.getMessage());
		}
	}
	
	public String getResult()
	{
		return result;
	}

	private InputStream readXml()
	{
		return res.openRawResource(R.raw.postraw2);
	}

	/**
	 * Handler for SAX Parser
	 */
	private class XMLHandler extends DefaultHandler
	{
		private String tmp;
		
		private static final String UPDATE="update";
		private static final String ENTRY="entry";
		private static final String ID="id";
		private static final String DESCRIPTION="description";
		private static final String TITLE="title";
		private static final String TYPE="type";
		private static final String STARS="stars";
		private static final String ICON="icon";
		private static final String LATITUDE="latitude";
		private static final String LONGITUDE="longitude";
		private static final String SHORTTEXT="shorttext";
		private static final String TEXT="text";
		private static final String SPECIFICATION="specification";
		private static final String ITEM="item";

		/*
		public List<Entry> parse()
		{*/

		@Override
		public void startElement(String uri, String localName, String qName,Attributes attrs) throws SAXException
		{
			if(localName.equals(ENTRY))
			{
				entry=true;
				return;
			}
			if(localName.equals(ID))
			{
				id=true;
				return;
			}
			if(localName.equals(DESCRIPTION))
			{
				description=true;
				return;
			}
			if(localName.equals(TITLE))
			{
				title=true;
				return;
			}
			if(localName.equals(TYPE))
			{
				type=true;
				return;
			}
			if(localName.equals(STARS))
			{
				stars=true;
				return;
			}
			if(localName.equals(ICON))
			{
				icon=true;
				return;
			}
			if(localName.equals(LATITUDE))
			{
				latitude=true;
				return;
			}
			if(localName.equals(LONGITUDE))
			{
				longitude=true;
				return;
			}
			if(localName.equals(SHORTTEXT))
			{
				shorttext=true;
				return;
			}
			if(localName.equals(TEXT))
			{
				text=true;
				return;
			}
			if(localName.equals(SPECIFICATION))
			{
				specification=true;
				return;
			}
			if(localName.equals(ITEM))
			{
				item=true;
				return;
			}
			
			if(text==true && !localName.equals(TEXT))
			{
				result=result+" <"+localName+">";
				return;
			}
			if(shorttext==true && !localName.equals(SHORTTEXT))
			{
				result=result+" <"+localName+">";
				return;
			}
			if(item==true && !localName.equals(ITEM))
			{
				result=result+" <"+localName+">";
				return;
			}
		}
		
		@Override
		public void endElement(String namespaceURI, String localName,String qName) throws SAXException
		{
			if(text==true && !localName.equals(TEXT))
			{
				result=result+"</"+localName+"> ";
				return;
			}
			if(shorttext==true && !localName.equals(SHORTTEXT))
			{
				result=result+"</"+localName+"> ";
				return;
			}
			if(item==true && !localName.equals(ITEM))
			{
				result=result+"</"+localName+"> ";
				return;
			}
			
			if(localName.equals(ID))
			{
				id=false;
				return;
			}
			if(localName.equals(DESCRIPTION))
			{
				description=false;
				return;
			}
			if(localName.equals(TITLE))
			{
				title=false;
				return;
			}
			if(localName.equals(TYPE))
			{
				type=false;
				return;
			}
			if(localName.equals(STARS))
			{
				stars=false;
				return;
			}
			if(localName.equals(ICON))
			{
				icon=false;
				return;
			}
			if(localName.equals(LATITUDE))
			{
				latitude=false;
				return;
			}
			if(localName.equals(LONGITUDE))
			{
				longitude=false;
				return;
			}
			if(localName.equals(SHORTTEXT))
			{
				shorttext=false;
				return;
			}
			if(localName.equals(TEXT))
			{
				text=false;
				return;
			}
			if(localName.equals(SPECIFICATION))
			{
				specification=false;
				return;
			}
			if(localName.equals(ITEM))
			{
				item=false;
				currentEntry.addItem(tmpAttrs,tmpValue);
				return;
			}
			if(localName.equals(ENTRY))
			{
				entry=false;
				messages.add(currentEntry.copy());
				return;
			}
		}
		
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException
		{
			String cdata=new String(ch,start,length);
			if(text==true) result=result+cdata.trim();
		}
	}
}