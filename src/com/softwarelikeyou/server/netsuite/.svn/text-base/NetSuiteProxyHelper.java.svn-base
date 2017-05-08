package com.softwarelikeyou.server.netsuite;

import java.util.ResourceBundle;

import com.softwarelikeyou.server.util.Helper;
import com.netsuite.webservices.platform.core.Passport;
import com.netsuite.webservices.platform.core.RecordRef;
import com.netsuite.webservices.platform.messages.Preferences;

public class NetSuiteProxyHelper extends Helper
{
	
	private NetSuiteProxy netsuite;
	
	protected ResourceBundle rb;
	
	public NetSuiteProxyHelper()
	{
		rb = ResourceBundle.getBundle(getMode());
		
		get();
	}
	
	public NetSuiteProxyHelper(final NetSuiteProxy service)
	{
		rb = ResourceBundle.getBundle(getMode());
		
		set(service);
	}
	
	public void setClearHeaders() throws Exception
	{
		netsuite.setClearHeader();
	}
	
	public void setRequestLevelCredentials(final String account, final String email, final String password) throws Exception
	{
		if (account == null || email == null || password == null) throw new NullPointerException();
		if (netsuite == null) return;
		netsuite.setRequestLevelCredentials(create(account, email, password));
	}
	
	public Passport create(final String account, final String email, final String password)
	{
		final Passport passport = new Passport();
		passport.setEmail(email.trim());
		passport.setPassword(password.trim());
		final RecordRef role = new RecordRef();
		role.setInternalId(rb.getString("NetSuiteADMINSTRATOR_ROLE"));
		passport.setRole(role);
		passport.setAccount(account.trim());
		return passport;
	}
	
	public void setPreferences() throws Exception
	{
		netsuite.setPreferences(createPreferences());
	}
	
	public Preferences createPreferences()
	{
		Preferences preferences = new Preferences();
		preferences.setIgnoreReadOnlyFields(Boolean.valueOf(rb.getString("NetSuiteIGNOREREADONLYFIELDS")));
		preferences.setUseConditionalDefaultsOnAdd(Boolean.valueOf(rb.getString("NetSuiteUSECONDITIONALDEFAULTSONADD")));
		preferences.setWarningAsError(Boolean.valueOf(rb.getString("NetSuiteWARNINGASERROR")));
		return preferences;
	}
	
	public NetSuiteProxy get()
	{
		if (netsuite == null) set(new NetSuiteProxy());
		return netsuite;
	}
	
	protected void set(final NetSuiteProxy service)
	{
		if (service == null) throw new NullPointerException();
		this.netsuite = service;
	}
}
