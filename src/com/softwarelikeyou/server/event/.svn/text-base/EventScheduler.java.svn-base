package com.softwarelikeyou.server.event;


import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import com.softwarelikeyou.server.scheduler.ScheduledTask;

public class EventScheduler extends EventHandler
{
	private final static Logger logger = Logger.getLogger(EventScheduler.class);
	private final Timer timer = new Timer();

	public void initialize()
	{
	    Long delay = null;
	    Long wakeupInterval = null;

		final ResourceBundle rb = ResourceBundle.getBundle("Config");
		delay = Long.valueOf(rb.getString("SchedulerDelay"));
		wakeupInterval = Long.valueOf(rb.getString("SchedulerWakeupInterval"));

		final ScheduledTask task = new ScheduledTask(this);
		task.add(Events.onRetreiveHPDIEmail);
		
		timer.schedule(task, delay, wakeupInterval);
		logger.info("Started with delay of " + delay + " and wakeup interval of " + wakeupInterval);
	}

	public void process (final Event event)
	{
		if (event == null) return;
		this.broadcast(event);
	}

	public void schedule(final TimerTask task, final long delay, final long interval)
	{
		if (task == null) throw new NullPointerException();
		if (delay < 1 || interval < 1) throw new IllegalArgumentException();
		timer.schedule(task, delay, interval);
	}
}
