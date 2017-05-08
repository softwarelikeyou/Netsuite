package com.softwarelikeyou.server;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.softwarelikeyou.server.controller.DefaultController;
import com.softwarelikeyou.server.controller.MasterController;
import com.softwarelikeyou.server.event.EventScheduler;

public class Web extends HttpServlet implements Servlet
{
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(Web.class);
	
	private final MasterController controller = new MasterController();
	
	private final DefaultController defaultController = new DefaultController();
	
	private final EventScheduler scheduler = new EventScheduler();

	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
		throws ServletException, IOException
	{
		doProcess(request, response);
	}
	
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
		throws ServletException, IOException
	{
		doProcess(request, response);
	}
	
	protected void doProcess(final HttpServletRequest request, final HttpServletResponse response)
		throws ServletException, IOException
	{
		final WebRequest requestor = new WebRequest(request, response);
		logger.info("Received a web request for " + requestor.getEvent().getType().name());
		controller.process(requestor.getEvent());
	}
	
	public void init(final ServletConfig sc) throws ServletException
	{
		super.init(sc);
		defaultController.initialize();
		scheduler.add(defaultController);
		scheduler.initialize();
	}
	
	protected RequestDispatcher getRequestDispatcher(final String resource)
	{
		return getServletContext().getRequestDispatcher(resource);
	}
	
	public String getServletInfo()
	{
		return getClass().getName();
	}
	
	public void destroy()
	{
	}
}
