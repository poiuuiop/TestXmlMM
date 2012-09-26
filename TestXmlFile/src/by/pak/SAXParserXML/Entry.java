package by.pak.SAXParserXML;

import android.util.Log;

import org.xml.sax.Attributes;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entry// implements Comparable<Entry>
{
	public final static int BOOL=1;
	public final static int TEXT=2;
	public final static int INT=1;
	
	private final String TAG="Entry";
	
	//static SimpleDateFormat FORMATTER=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
	//private URL link;
	//private String description;
	//private Date date;
	
	private int id=-1;
	private String title;
	private String type;
	private int stars=0;
	private URL icon;
	private double latitude=0;
	private double longitude=0;
	private String shorttext;
	private String text;
	Map<String, String> tmpMap;
	private List<Map<String, ?>> specification=null;// = new ArrayList<Map<String, ?>>();
	
	/*
	public void setLink(String link)
	{
		try
		{
			this.link=new URL(link);
		}
		catch (MalformedURLException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	*/

	public Entry copy()
	{
		Entry copy=new Entry();
		copy.id=this.id;
		copy.title=this.title;
		copy.type=this.type;
		copy.stars=this.stars;
		copy.icon=this.icon;
		copy.latitude=this.latitude;
		copy.longitude=this.longitude;
		copy.shorttext=this.shorttext;
		copy.text=this.text;
		copy.specification=this.specification;
		
		this.specification=null;
		return copy;
	}

	@Override
	public String toString()
	{
		StringBuilder sb=new StringBuilder();
		
		sb.append("id: ");
		sb.append(id);
		sb.append('\n');
		sb.append("title: ");
		sb.append(title);
		sb.append('\n');
		sb.append("type: ");
		sb.append(type);
		sb.append('\n');
		sb.append("shorttext: ");
		sb.append(shorttext);
		sb.append('\n');
		sb.append('\n');

		return sb.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime=31;
		int result=1;
		result=prime*result+id;
		result=prime*result+((title==null) ? 0 : title.hashCode());
		result=prime*result+((type==null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this==obj) return true;
		if(obj==null) return false;
		if(getClass()!=obj.getClass()) return false;
		
		Entry other=(Entry)obj;
		if(id==-1)
		{
			if(other.id!=-1) return false;
		}
		else if(id!=other.id) return false;
		
		if(title==null)
		{
			if(other.title!=null) return false;
		}
		else if(!title.equals(other.title)) return false;
		
		if(type==null)
		{
			if(other.type!=null) return false;
		}
		else if(!type.equals(other.type)) return false;
		
		return true;
	}
	/*
	public int compareTo(Entry another)
	{
		if(another==null) return 1;
		// sort descending, most recent first
		return another.date.compareTo(date);
	}*/
	
	public int getId()
	{
		return id;
	}

	public String getTitle()
	{
		return title;
	}

	public String getType()
	{
		return type;
	}

	public int getStars()
	{
		return stars;
	}

	public URL getIcon()
	{
		return icon;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public String getShorttext()
	{
		return shorttext;
	}

	public String getText()
	{
		return text;
	}

	public List<Map<String, ?>> getSpecification()
	{
		return specification;
	}

	public void setId(String Id)
	{
		Integer k=null;
		try
		{
			k=new Integer(Id);
		}
		catch(NumberFormatException e)
		{
			k=new Integer(0);
			Log.e(TAG,"setId(): "+e.getMessage());
		}
		this.id=((k==null) ? 0 : k.intValue());
	}

	public void setTitle(String title)
	{
		this.title=title.trim();
	}

	public void setType(String type)
	{
		this.type=type.trim();
	}

	public void setStars(String stars)
	{
		Integer k=null;
		try
		{
			k=new Integer(stars);
		}
		catch(NumberFormatException e)
		{
			k=new Integer(0);
		}
		this.stars=((k==null) ? 0 : k.intValue());
	}

	public void setIcon(String iconLink)
	{
		try
		{
			this.icon=new URL("http://"+iconLink);
		}
		catch (MalformedURLException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void setLatitude(String Latitude)
	{
		Double k=null;
		try
		{
			k=new Double(Latitude);
		}
		catch(NumberFormatException e)
		{
			k=new Double(0);
		}
		this.latitude=((k==null) ? 0 : k.doubleValue());
	}

	public void setLongitude(String Longitude)
	{
		Double k=null;
		try
		{
			k=new Double(Longitude);
		}
		catch(NumberFormatException e)
		{
			k=new Double(0);
		}
		this.longitude=((k==null) ? 0 : k.doubleValue());
	}

	public void setShorttext(String Shorttext)
	{
		this.shorttext=Shorttext.trim();
	}

	public void setText(String Text)
	{
		this.text=Text.trim();
	}
	
	public void setItemAttr(Attributes attrs)
	{
		tmpMap = new HashMap<String, String>();
		
		String tmp="";
		for(int i=0;i<attrs.getLength();i++)
		{
			tmp=attrs.getLocalName(i);
			if(tmp.equals("name"))
			{
				tmpMap.put("name", attrs.getValue(i));
				continue;
			}
			if(tmp.equals("alias"))
			{
				tmpMap.put("alias", attrs.getValue(i));
				continue;
			}
			if(tmp.equals("type"))
			{
				tmpMap.put("type", attrs.getValue(i));
				continue;
			}
		}
	}
	
	public void addItem(String value)
	{
		if(specification==null) specification = new ArrayList<Map<String, ?>>();
		
		tmpMap.put("value", value);

		this.specification.add(tmpMap);
	}
}
