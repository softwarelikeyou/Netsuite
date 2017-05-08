package com.softwarelikeyou.server.event;

import java.util.ArrayList;
import java.util.List;

public class EventNotifier
{
	protected final List<EventListener> listeners = new ArrayList<EventListener>();

	public void broadcast(final Event event)
	{
		if (event == null) throw new NullPointerException();
		for (EventListener listener : listeners) listener.process(event);
	}

	public void get(final EventListener listener)
	{
		if (listener == null) throw new NullPointerException();
		listeners.get(listeners.indexOf(listener));
	}

	public void add(final EventListener listener)
	{
		if (listener == null) throw new NullPointerException();
		if (!contains(listener)) listeners.add(listener);
	}

	public void remove(final EventListener listener)
	{
		if (listener == null) throw new NullPointerException();
		listeners.remove(listener);
	}

	public boolean contains(final EventListener listener)
	{
		if (listener == null) throw new NullPointerException();
		return listeners.contains(listener);
	}

	public int size()
	{
		return listeners.size();
	}
}
