package com.softwarelikeyou.server.util;

import java.util.ResourceBundle;

import com.center.server.mappoint.MappointRouteProxy;

public class MappointHelper extends Helper
{
	protected ResourceBundle rb;
	
	public MappointHelper()
	{
		rb = ResourceBundle.getBundle(getMode());
	}

    public double calculateSimpleRoute(double startLatitude, double startLongitude, double endLatitude, double endLongitude) throws Exception
    {	
    	final MappointRouteProxy mappoint = new MappointRouteProxy(rb.getString("MappointURL"));
    	mappoint.setUserName(rb.getString("MappointAccount"));
    	mappoint.setPassword(rb.getString("MappointPassword"));
    	mappoint.setDistanceUnitMiles();
    	mappoint.setPreferences();
    	mappoint.setStartLatLong(Double.toString(startLatitude), Double.toString(startLongitude));
    	mappoint.setEndLatLong(Double.toString(endLatitude), Double.toString(endLongitude));
    	mappoint.login();
    	return mappoint.calculateSimpleRoute();
    }
}
