package com.softwarelikeyou.server.event;

import java.util.EventObject;

public class Event extends EventObject
{
	private static final long serialVersionUID = 1L;
	
	private Object data = new Empty();
	
	private Events type = Events.noop;
	
	public Event(final Events type, final Object source)
	{
		this(type, source, new Empty());
	}
	
	public Event(final Events type, final Object source, final Object data)
	{
		super(source);
		setType(type);
		setSource(source);
		setData(data);
	}
	
	public Object getData()
	{
		return data;
	}
	
	protected void setData(final Object data)
	{
		if (data == null) throw new NullPointerException();
		this.data = data;
	}
	
	public Object getSource()
	{
		return source;
	}
	
	protected void setSource(final Object source)
	{
		if (source == null) throw new NullPointerException();
		this.source = source;
	}
	
	public Events getType()
	{
		return type;
	}
	
	protected void setType(final Events type)
	{
		if (type == null) throw new NullPointerException();
		this.type = type;
	}
}
