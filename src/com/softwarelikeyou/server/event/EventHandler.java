package com.softwarelikeyou.server.event;

import java.util.ResourceBundle;

public abstract class EventHandler implements EventListener
{	
	protected final EventNotifier notifier = new EventNotifier();

	private String mode = "Test";
	
	protected ResourceBundle rb;
	
	protected EventHandler()
	{
		rb = ResourceBundle.getBundle("Config");
		mode = rb.getString("Mode");
	}
	
	public String getMode()
	{
		return mode;
	}
	
	public abstract void process(Event event);

	protected void broadcast(final Event event)
	{
		notifier.broadcast(event);
	}

	public void get(final EventListener listener)
	{
		notifier.get(listener);
	}

	public void add(final EventListener listener)
	{
		if (listener == this) throw new IllegalArgumentException();
		if (!contains(listener)) notifier.add(listener);
	}

	public void remove(final EventListener listener)
	{
		notifier.remove(listener);
	}

	public boolean contains(final EventListener listener)
	{
		return notifier.contains(listener);
	}

	public int size()
	{
		return notifier.size();
	}
}
