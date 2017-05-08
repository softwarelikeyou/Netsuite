package com.softwarelikeyou.server.controller;

import com.softwarelikeyou.server.controller.Controller;
import com.softwarelikeyou.server.event.Events;
//import com.softwarelikeyou.server.handler.DailyPermitLoadHandler;
import com.softwarelikeyou.server.handler.HPDIEmailHandler;
import com.softwarelikeyou.server.handler.PermitHandler;
import com.softwarelikeyou.server.handler.RigLocationHandler;

public class DefaultController extends Controller
{
	public void initialize()
	{
		add(Events.onRetreiveHPDIEmail, new HPDIEmailHandler(this));
		add(Events.onLoadHPDIPermits, new PermitHandler());
		add(Events.onLoadHPDIRigLocations, new RigLocationHandler());
	}
}
