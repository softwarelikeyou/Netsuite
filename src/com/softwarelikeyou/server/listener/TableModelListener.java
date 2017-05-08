package com.softwarelikeyou.server.listener;

import java.util.EventListener;

import com.softwarelikeyou.server.event.TableModelEvent;

public interface TableModelListener extends EventListener
{
	public void onTableChanged(TableModelEvent event);
}
