package by.pak.SAXParserXML;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import by.pak.testxmlfile.R;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ParserXML
{
	private Resources res=null;
	String rssResult="";
	boolean item=false;
	List<Entry> mes;
/*

		ParseOFXML();

		WebView webview =(WebView)findViewById(R.id.webView1);
		rssResult="<?xml version=\"1.0\" encoding=\"UTF-8\"?><html><body>"+rssResult+"</body></html>";//
		webview.loadData(rssResult, "text/html;charset=utf-8","utf-8");
*/
	
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
			//rss.setText(rssResult);
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
		
		private boolean update,entry,id,description,title,type,stars,icon,latitude,longitude,shorttext,text,specification,item;
		
		public void startElement(String uri, String localName, String qName,Attributes attrs) throws SAXException
		{
			//Log.i("XML","\n\nlocalName: "+localName+"\nqName: "+qName);
			
			tmp="";
			/*
			for(int i=0;i<attrs.getLength();i++)
			{
				tmp=tmp+attrs.getLocalName(i)+" = "+attrs.getValue(i)+", ";
			}
			if(!TextUtils.isEmpty(tmp)) Log.i("XML","\nAttributes: "+tmp);
			*/
			if(localName.equals(TEXT)) text=true;
			if(text==true && !localName.equals(TEXT)) rssResult=rssResult+" <"+localName+">";
		}

		public void endElement(String namespaceURI, String localName,String qName) throws SAXException
		{
			if(text==true && !localName.equals(TEXT)) rssResult=rssResult+"</"+localName+"> ";
			if(localName.equals(TEXT)) text=false;
		}

		public void characters(char[] ch, int start, int length) throws SAXException
		{
			String cdata=new String(ch,start,length);
			if(text==true) rssResult=rssResult+cdata.trim();//).replaceAll("\\s+"," ")+"\t";
			Log.i("XML","Data: "+cdata);
		}

	}
	
	public String getResult()
	{
		return rssResult;
	}

	private InputStream readXml()
	{
		return res.openRawResource(R.raw.postraw2);
	}
}