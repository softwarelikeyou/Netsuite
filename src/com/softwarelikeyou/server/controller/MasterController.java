package com.softwarelikeyou.server.controller;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

//import com.softwarelikeyou.server.Registry;
//import com.softwarelikeyou.server.Store;
import com.softwarelikeyou.server.controller.Controller;
import com.softwarelikeyou.server.event.EventListener;
//import com.softwarelikeyou.server.event.EventScheduler;

public class MasterController extends Controller implements EventListener
{
	private final static Logger logger = Logger.getLogger(MasterController.class);
	
	protected ResourceBundle config;
	
	public MasterController()
	{
		config = ResourceBundle.getBundle("Config");
	}
	
	public void initialize()
	{
		addController();
		addEventHandler();
		logger.info(this.getClass().getName() + " initialized");
	}
	
	protected void addController()
	{
		add(new WebController());
		add(new DefaultController());
	}
	
	protected void addEventHandler()
	{
	
	}
}
