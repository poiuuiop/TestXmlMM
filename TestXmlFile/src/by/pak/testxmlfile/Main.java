package by.pak.testxmlfile;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main extends Activity
{
	String rssResult="";
	boolean item=false;
	List<Message> mes;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//ParseFXML();

		
		
		ParseOFXML();
		
		/*ParseMSxml pXml=new ParseMSxml(readXml());
		mes=pXml.parse();
		//Log.i("PARSE", "mes "+mes.get().getId()+mes.get(1).getTitle());
		Log.i("Parse", "mes "+mes.get(0).getShorttext());
		*/
		WebView webview =(WebView)findViewById(R.id.webView1);
		rssResult="<?xml version=\"1.0\" encoding=\"UTF-8\"?><html><body>"+rssResult+"</body></html>";//
		webview.loadData(rssResult, "text/html;charset=utf-8","utf-8");
	}
	
	/**
	 * SAX Parser
	 */
	private void ParseOFXML()
	{
		TextView rss=(TextView)findViewById(R.id.txt);
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
			rss.setText(e.getMessage());
		}
		catch (SAXException e)
		{
			rss.setText(e.getMessage());
		}
		catch (ParserConfigurationException e)
		{
			rss.setText(e.getMessage());
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
			if(localName.equals(TEXT)) item=true;
			if(!localName.equals(TEXT)&&item==true) rssResult=rssResult+"<"+localName+">";
		}

		public void endElement(String namespaceURI, String localName,String qName) throws SAXException
		{
			if(!localName.equals(TEXT)&&item==true) rssResult=rssResult+"</"+localName+">";
			if(localName.equals(TEXT)) item=false;
		}

		public void characters(char[] ch, int start, int length) throws SAXException
		{
			String cdata=new String(ch,start,length);
			if(item==true) rssResult=rssResult+cdata.trim();//).replaceAll("\\s+"," ")+"\t";
		}

	}

	/**
	 * PullParser
	 */
	private void ParseFXML()
	{
		final String LOG_TAG="myLogs";

		String tmp="";
		try
		{
			XmlPullParser xpp=prepareXpp();

			while(xpp.getEventType()!=XmlPullParser.END_DOCUMENT)
			{
				switch(xpp.getEventType())
				{
				// начало документа
					case XmlPullParser.START_DOCUMENT:
						Log.d(LOG_TAG,"START_DOCUMENT");
						break;
					// начало тэга
					case XmlPullParser.START_TAG:
						Log.d(LOG_TAG,"START_TAG: name = "+xpp.getName()
								+", depth = "+xpp.getDepth()+", attrCount = "+xpp.getAttributeCount());
						tmp="";
						for(int i=0;i<xpp.getAttributeCount();i++)
						{
							tmp=tmp+xpp.getAttributeName(i)+" = "+xpp.getAttributeValue(i)+", ";
						}
						if(!TextUtils.isEmpty(tmp)) Log.d(LOG_TAG,"Attributes: "+tmp);
						break;
					// конец тэга
					case XmlPullParser.END_TAG:
						Log.d(LOG_TAG,"END_TAG: name = "+xpp.getName());
						break;
					// содержимое тэга
					case XmlPullParser.TEXT:
						Log.d(LOG_TAG,"text = "+xpp.getText());
						break;

					default:
						break;
				}
				// следующий элемент
				xpp.next();
			}
			Log.d(LOG_TAG,"END_DOCUMENT");

		}
		catch (XmlPullParserException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private XmlPullParser prepareXpp()
	{
		return getResources().getXml(R.xml.data);
	}

	/*
	private XmlPullParser prepareXpp_v() throws XmlPullParserException
	{
		//получаем фабрику XmlPullParserFactory
		factory=XmlPullParserFactory.newInstance();
		// включаем поддержку namespace (по умолчанию выключена)
		factory.setNamespaceAware(true);
		//создаем парсер
		XmlPullParser xpp=factory.newPullParser();
		// даем парсеру	на вход
		Reader xpp.setInput(new	StringReader("<data><phone><company>Samsung</company></phone></data>"));
		return xpp;
	}
	*/

	private InputStream readXml()
	{
	    return getResources().openRawResource(R.raw.postraw);
	}
}