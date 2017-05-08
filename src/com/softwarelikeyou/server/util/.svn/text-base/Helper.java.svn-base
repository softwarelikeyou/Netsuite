package com.softwarelikeyou.server.util;

import java.util.ResourceBundle;

public abstract class Helper 
{
	protected ResourceBundle rb;
	
	private String mode = "Test";
	
	protected Helper()
	{
		rb = ResourceBundle.getBundle("Config");
		mode = rb.getString("Mode");
	}
	
	public String getMode()
	{
		return mode;
	}
}
