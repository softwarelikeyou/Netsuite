package com.softwarelikeyou.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.softwarelikeyou.server.event.Event;
import com.softwarelikeyou.server.event.Events;

public class WebRequest
{
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	public WebRequest(final HttpServletRequest request, final HttpServletResponse response)
	{
		setRequest(request);
		setResponse(response);
	}
	
	public HttpServletRequest getRequest()
	{
		return request;
	}
	
	public void setRequest(final HttpServletRequest request)
	{
		if (request == null) throw new NullPointerException();
		this.request = request;
	}
	
	public HttpServletResponse getResponse()
	{
		return response;
	}
	
	public void setResponse(final HttpServletResponse response)
	{
		if (response == null) throw new NullPointerException();
		this.response = response;
	}
	
	public Event getEvent()
	{
		return new Event(Events.onLoadTable, this);
	}
}
