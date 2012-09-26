package by.pak.testxmlfile;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import by.pak.SAXParserXML.Entry;
import by.pak.SAXParserXML.ParserXML;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class Main extends Activity
{
	
	List<Entry> Result;
	boolean item=false;
	//List<Message> mes;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//ParseFXML();
		

		ParserXML parse=new ParserXML(this.getBaseContext());
		Result=parse.getResult();
		
		for(Entry m : Result) Log.i("XML","msg: "+m.getId()+" "+m.getTitle());
			
		
		WebView webview =(WebView)findViewById(R.id.webView1);
		String tmpResult="<?xml version=\"1.0\" encoding=\"UTF-8\"?><html><body>"+Result.get(0).getText()+"</body></html>";//
		webview.loadData(tmpResult, "text/html;charset=utf-8","utf-8");
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
}