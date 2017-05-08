package com.softwarelikeyou.server.controller;

import java.util.HashMap;
import java.util.Map;

import com.softwarelikeyou.server.event.Event;
import com.softwarelikeyou.server.event.EventHandler;
import com.softwarelikeyou.server.event.EventListener;
import com.softwarelikeyou.server.event.Events;

public abstract class Controller extends EventHandler implements EventListener
{
	final protected Map<Events, EventListener> handlers = new HashMap<Events, EventListener>();
	
	protected boolean initialized = false;
	
	public abstract void initialize();
	
	public final void process(final Event event)
	{
		if (event == null) throw new NullPointerException();
		if (!initialized)
		{
			initialize();
			initialized = true;
		}
		final EventListener handler = get(event.getType());
		if (handler != null) 
		{
			handler.process(event);
			return;
		}
		broadcast(event);
	}

	public void add(final Events key, final EventListener listener)
	{
		if (key == null || listener == null) throw new NullPointerException();
		if (listener == this) throw new IllegalArgumentException();
		handlers.put(key, listener);
	}

	public EventListener get(final Events key)
	{
		if (key == null) throw new NullPointerException();
		return contains(key) ? handlers.get(key) : null;
	}
	
	public void remove(final Events key)
	{
		if (key == null) throw new NullPointerException();
		if (contains(key)) handlers.remove(key);
	}
	
	public boolean contains(final Events key)
	{
		if (key == null) throw new NullPointerException();
		return handlers.containsKey(key);
	}
}
