package com.softwarelikeyou.server.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import com.softwarelikeyou.server.event.Event;
import com.softwarelikeyou.server.event.EventListener;
import com.softwarelikeyou.server.event.Events;

public class ScheduledTask extends TimerTask
{
	final List<Events> events = new ArrayList<Events>();
	EventListener listener;

	public ScheduledTask (final EventListener listener)
	{
		if (listener == null) throw new NullPointerException();
		this.listener = listener;
	}

	public boolean add(final Events event)
	{
		if (event == null) throw new NullPointerException();
		return events.add(event);
	}

	public void run()
	{
		if (listener == null) return;
		for (Events event : events) listener.process(new Event(event, this));
	}
}
