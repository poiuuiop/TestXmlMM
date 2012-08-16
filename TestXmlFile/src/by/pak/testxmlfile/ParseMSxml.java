package by.pak.testxmlfile;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.TextElementListener;
import android.util.Log;
import android.util.Xml;

import org.xml.sax.Attributes;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ParseMSxml
{
	// XML тэги
	static final String CHANNEL="channel";
	static final String PUB_DATE="pubDate";
	static final String LINK="link";
	
	
	static final String UPDATE="update";
	static final String ENTRY="entry";
	static final String ID="id";
	static final String DESCRIPTION="description";
	static final String TITLE="title";
	static final String TYPE="type";
	static final String STARS="stars";
	static final String ICON="icon";
	static final String LATITUDE="latitude";
	static final String LONGITUDE="longitude";
	static final String SHORTTEXT="shorttext";
	static final String TEXT="text";
	static final String SPECIFICATION="specification";
	static final String ITEM="item";

	private /*final*/URL feedUrl=null;
	private InputStream istream;

	public ParseMSxml(String feedUrl)
	{
		try
		{
			this.feedUrl=new URL(feedUrl);
		}
		catch (MalformedURLException e)
		{
			Log.e("error",e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	public ParseMSxml(InputStream ist)
	{
		istream=ist;
	}

	private InputStream getInputStream()
	{
		/*try
		{
			return feedUrl.openConnection().getInputStream();
		}
		catch (IOException e)
		{
			Log.e("error",e.getMessage());
			throw new RuntimeException(e);
		}*/
		return istream;
	}

	public List<Message> parse()
	{
		final Message currentMessage=new Message();
		RootElement root=new RootElement(UPDATE);
		final List<Message> messages=new ArrayList<Message>();
		
		Element entry=root.getChild(ENTRY);
		entry.setEndElementListener(new EndElementListener()
		{
			public void end()
			{
				messages.add(currentMessage.copy());
			}
		});
		entry.getChild(ID).setEndTextElementListener(
				new EndTextElementListener()
				{
					public void end(String body)
					{
						currentMessage.setId(body);
					}
				});
		
		Element desc=entry.getChild(DESCRIPTION);
		
		desc.getChild(TITLE).setEndTextElementListener(
				new EndTextElementListener()
				{
					public void end(String body)
					{
						currentMessage.setTitle(body);
					}
				});
		desc.getChild(TYPE).setEndTextElementListener(
				new EndTextElementListener()
				{
					public void end(String body)
					{
						currentMessage.setType(body);
					}
				});
		desc.getChild(STARS).setEndTextElementListener(
				new EndTextElementListener()
				{
					public void end(String body)
					{
						currentMessage.setStars(body);
					}
				});
		desc.getChild(ICON).setEndTextElementListener(
				new EndTextElementListener()
				{
					public void end(String body)
					{
						currentMessage.setIcon(body);
					}
				});
		desc.getChild(LATITUDE).setEndTextElementListener(
				new EndTextElementListener()
				{
					public void end(String body)
					{
						currentMessage.setLatitude(body);
					}
				});
		desc.getChild(LONGITUDE).setEndTextElementListener(
				new EndTextElementListener()
				{
					public void end(String body)
					{
						currentMessage.setLongitude(body);
					}
				});
		Element st=desc.getChild(SHORTTEXT);
		currentMessage.setShorttext(st.toString());
		/*
		desc.getChild(SHORTTEXT).setEndTextElementListener(
				new EndTextElementListener()
				{
					public void end(String body)
					{
						currentMessage.setShorttext(body);
					}
				});
		desc.getChild(TEXT).setEndTextElementListener(
				new EndTextElementListener()
				{
					public void end(String body)
					{
						currentMessage.setText(body);
					}
				});
		*/
		Element spec=entry.getChild(SPECIFICATION);
		
		spec.getChild(ITEM).setTextElementListener(new MyTextElementListener(currentMessage));
		
		try
		{
			Xml.parse(this.getInputStream(),Xml.Encoding.UTF_8,root.getContentHandler());
		}
		catch (Exception e)
		{
			Log.e("ERROR",e.toString());
			throw new RuntimeException(e);
		}
		return messages;
	}
	
	private class MyTextElementListener implements TextElementListener
	{
		private Message currentMessage;
		private String name=null,type=null;
		
		public MyTextElementListener(Message mes)
		{
			this.currentMessage=mes;
		}
		
		public void start(Attributes attributes)
		{
			for(int i=0;i<attributes.getLength();i++)
			{
				if(attributes.getLocalName(i).equalsIgnoreCase("name")) name=attributes.getValue(i);
				
				if(attributes.getLocalName(i).equalsIgnoreCase("type")) type=attributes.getValue(i);
			}
		}

		public void end(String value)
		{
			currentMessage.addItem(name, type, value);
		}
		
	}
}
