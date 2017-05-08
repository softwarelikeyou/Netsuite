package com.softwarelikeyou.server.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.softwarelikeyou.server.model.DefaultTableModel;
import com.softwarelikeyou.server.controller.Controller;
import com.softwarelikeyou.server.event.Event;
import com.softwarelikeyou.server.event.EventHandler;
import com.softwarelikeyou.server.event.Events;
import com.softwarelikeyou.server.netsuite.CustomRecordHelper;
import com.softwarelikeyou.server.netsuite.HPDIFileHelper;
import com.softwarelikeyou.server.netsuite.NetSuiteProxy;
import com.softwarelikeyou.server.netsuite.NetSuiteProxyHelper;
import com.softwarelikeyou.server.util.EmailHelper;
import com.softwarelikeyou.server.util.MappointHelper;
import com.softwarelikeyou.server.util.ServerTool;
import com.netsuite.webservices.platform.common.CustomRecordSearchBasic;
import com.netsuite.webservices.platform.core.CustomFieldList;
import com.netsuite.webservices.platform.core.CustomFieldRef;
import com.netsuite.webservices.platform.core.Record;
import com.netsuite.webservices.platform.core.RecordRef;
import com.netsuite.webservices.platform.core.SearchCustomField;
import com.netsuite.webservices.platform.core.SearchCustomFieldList;
import com.netsuite.webservices.platform.core.SearchResult;
import com.netsuite.webservices.platform.core.SearchStringCustomField;
import com.netsuite.webservices.platform.core.StringCustomFieldRef;
import com.netsuite.webservices.platform.core.types.SearchStringFieldOperator;
import com.netsuite.webservices.platform.messages.WriteResponse;
import com.netsuite.webservices.setup.customization.CustomRecord;
import com.netsuite.webservices.setup.customization.CustomRecordCustomField;
import com.netsuite.webservices.setup.customization.CustomRecordSearch;
import com.netsuite.webservices.setup.customization.CustomRecordSearchAdvanced;
import com.netsuite.webservices.setup.customization.types.CustomizationFieldType;

public class RigLocationHandler extends EventHandler
{
	private final static Logger logger = Logger.getLogger(RigLocationHandler.class);
	
	protected DefaultTableModel table = null;
	protected ResourceBundle rb;
	protected Controller controller = null;
	protected HashMap<String, Object> file;
	protected String errorRows = "";
	protected HPDIFileHelper fileHelper;
	protected EmailHelper emailHelper;
	protected Collection<HashMap<String, Object>> facilityLocations;
	
	public RigLocationHandler()
	{
		;
	}
	
	public RigLocationHandler(final Controller controller)
	{
		this.controller = controller;
	}
	
	@SuppressWarnings("unchecked")
	public void process(final Event event)
	{	
	   if (event == null)
	   {
	      logger.info("Recieved a null event");
		  throw new NullPointerException();
	   }

	   if (!event.getType().equals(Events.onLoadHPDIRigLocations)) 
	   {
	      logger.info("Recieved an invalid event type of " + event.getType().toString());
		  return;
	   }
	      
	   init();
		
	   try
	   {	
	      file = (HashMap<String, Object>)event.getData();
		  table = this.getTableModel((String)file.get("contents"));
		  
		  // validate columns, add new ones,
		  final CustomRecordHelper columnHelper = new CustomRecordHelper();
	      columnHelper.getRecType().setInternalId(rb.getString("NetSuiteRigLocationId"));
		  for (int i=0; i<table.getColumnCount(); i++)
		  {
		     final String internalId = getCustomRecordName((String) table.getValueAt(0, i));
		     //logger.info(internalId);
			 final CustomRecordCustomField field = new CustomRecordCustomField();
		     field.setFieldType(CustomizationFieldType._freeFormText);
		     field.setRecType(rb.getString("NetSuiteRigLocationId"));
		     field.setLabel(internalId);
		     field.setInternalId(internalId);
			 for (int j = 0 ; j < 2 ; j++) // Try 2 times, just in case of a network glich
			 {
		        if (add(field)) 
				   break;
			 }
		  }
		  
		  for (int row = 1 ; row <  table.getRowCount() ; row++)
		  {
		     final CustomRecordHelper helper = new CustomRecordHelper();
			 helper.getRecType().setInternalId(rb.getString("NetSuiteRigLocationId"));
		     
			 for (int column = 0 ; column < table.getColumnCount() ; column++)
			 {
			    final String value = (String) table.getValueAt(row, column);
				if (value.length() > 0)
				{
				   final String internalId = getCustomRecordName((String) table.getValueAt(0, column));
				   helper.addCustomFieldRef(helper.create(internalId.trim(),value.trim()));
				}
			 }
				
			 System.setProperty("axis.ClientConfigFile", "mappoint-client-config.wsdd");
			 this.calculateSimpleRoute(helper,
						              (String) table.getValueAt(row, table.find("WELL_LAT")),
						              (String) table.getValueAt(row, table.find("WELL_LONG")));
		     System.clearProperty("axis.ClientConfigFile");
		    	
		     final String dril_name = (String) table.getValueAt(row, table.find("DRIL_NAME"));
		     final String rig_num = (String) table.getValueAt(row, table.find("RIG_NUM"));
		     
			 if (!contains(dril_name,rig_num))
			 {
				boolean success = false;
			    for (int i = 0 ; i < 2 ; i++) // Try 2 times, just in case of a network glich
			    {
				   if (add(helper.get())) 
				   {
					  success = true;
				      break;
				   }
			    }
			    if (!success)
			    {
			    	errorRows += "Update: " + String.valueOf(row) + ",";
			    	logger.info("add rig location: FAILED for row " + String.valueOf(row));
			    }
			 }
			 else
			 {
				boolean success = false;
			    for (int i = 0 ; i < 2 ; i++) // try 2 time, just in case of a network glich
			    {
			       if (update(helper.get(), getRowInternalId(dril_name,rig_num)))
			       {
			    	  success = true;
				      break;
			       }
				}
			    if (!success)
			    {
			    	errorRows += "Update: " + String.valueOf(row) + ",";
			    	logger.info("update rig location: FAILED for row " + String.valueOf(row));
			    }
			 }
		  }
		  
	      if (fileHelper.setProcessed(file))
	    	  logger.info("updated " + file.get("name") + " to processed");
	      else
	    	  logger.info("could not update " + file.get("name") + " to processed");
	      
	      if (errorRows.length() > 0)
	      {
		      if (fileHelper.setErrorRows(file, errorRows))
		    	  logger.info("set error rows for " + file.get("name"));
		      else
		    	  logger.info("could not set error rows for " + file.get("name"));
	      }
	      
	      for (int i = 0 ; i < 2 ; i++) // Try 2 time, just in case of a network glich
	      {
	         if (emailHelper.sendEmail(file, "HPDI Rig Locations", errorRows))
	        	 break;
	      }
	   }
	   catch (final Exception e)
	   {
	      logger.error(e);
	   }
	}
	
	protected DefaultTableModel getTableModel(String s) throws Exception
	{
		return ServerTool.create(s);
	}
	
	protected String getCustomRecordName(final String name)
	{
		return "custrecord" + name.toLowerCase();
	}
	
	protected boolean add(final CustomRecord record)
	{
		boolean result = false;
		try
		{
			final CustomRecordHelper helper = new CustomRecordHelper(record);
			final WriteResponse response = getNetSuiteProxy().addCustomRecord(record);
			result = helper.isSuccess(response);
			if (result) 
			{
			   helper.setInternalId(response.getBaseRef());
			   logger.info("add rig location: " + record.getInternalId() + " " + helper.message(response));
			}
			else
			   logger.info("add rig location: FAILED for " + record.getInternalId() + " " + helper.message(response));
		}
		catch (final Exception e)
		{
			logger.error(e);
		}
		return result;
	}
	
	protected ArrayList<String> getFields(final CustomRecord record)
	{
		ArrayList<String> result = new ArrayList<String>();
		
		try
		{			
			final CustomRecordSearchAdvanced customRecordSearchAdvanced = new CustomRecordSearchAdvanced();
			final CustomRecordSearch criteria = new CustomRecordSearch();
			final CustomRecordSearchBasic basic = new CustomRecordSearchBasic();
			final RecordRef HPDIRigReport = new RecordRef();
			HPDIRigReport.setInternalId(rb.getString("NetSuiteRigLocationId"));
			basic.setRecType(HPDIRigReport);
			criteria.setBasic(basic);
			customRecordSearchAdvanced.setCriteria(criteria);
			final SearchResult response = this.getNetSuiteProxy().getNetSuitePort().search(customRecordSearchAdvanced);
			
			if (!response.getStatus().isIsSuccess()) throw new Exception();
			
			if (response.getTotalRecords() > 0)
			{
				Record[] records = response.getRecordList().getRecord();

		        CustomRecord customRecord = (CustomRecord)records[0];
		        
		        CustomFieldList cfl = customRecord.getCustomFieldList();
		        
		        CustomFieldRef[] cfrs = cfl.getCustomField();
		        
		        for (int i = 0; i < cfrs.length; i++)
		        {
		           if (cfrs[i].getClass().toString().equals((String)"class com.netsuite.webservices.platform.core.StringCustomFieldRef"))
                   {
		        	   StringCustomFieldRef StCFR = new StringCustomFieldRef();
                       StCFR = (StringCustomFieldRef)cfrs[i];
                       logger.info(StCFR.getInternalId());
                       result.add(StCFR.getInternalId());
                   }
		        }
			}
		}
		catch (final Exception e)
		{
			logger.error(e);
		}
		return result;
	}
	
	protected boolean add(final CustomRecordCustomField field)
	{
		Boolean ret = false;
		try
		{
			final WriteResponse response = getNetSuiteProxy().addRecord(field);
			ret = response.getStatus().isIsSuccess();
			if (ret)
			   logger.info("add rig location field: " +  response.getStatus().getStatusDetail()[0].getMessage());
		}
		catch (final Exception e)
		{
			logger.error("add field", e);
		}
		
		return ret;
	}
	
	protected boolean update(final CustomRecord record, String rowInternalId)
	{
		boolean result = false;
		
		try
		{	
			final RecordRef HPDIRigReportRef = new RecordRef();
			HPDIRigReportRef.setInternalId(rb.getString("NetSuiteRigLocationId"));
			HPDIRigReportRef.setName(rb.getString("NetSuiteRigLocationName"));
				
			final CustomRecord HPDIRigReport = record;
			HPDIRigReport.setRecType(HPDIRigReportRef);
			HPDIRigReport.setInternalId(rowInternalId);
			
			final WriteResponse response = this.getNetSuiteProxy().getNetSuitePort().update(HPDIRigReport);
			
			if (response.getStatus().isIsSuccess())
			{
				logger.info("update rig location: " + record.getInternalId());
				result = true;
			}
			else
				logger.info("update rig location: FAILED for " + record.getInternalId());
				
		}
		catch (final Exception e)
		{
			logger.error(e);
		}
		return result;
	}
	
	protected NetSuiteProxy getNetSuiteProxy() throws Exception
	{
		final NetSuiteProxyHelper helper = new NetSuiteProxyHelper();
		helper.setClearHeaders();
		
		helper.setRequestLevelCredentials(rb.getString("NetSuiteAccount"), 
				                          rb.getString("NetSuiteEmail"), 
		                                  rb.getString("NetSuitePassword"));
		helper.setPreferences();	
		return helper.get();
	}

	protected boolean contains(String dril_name, String rig_num) throws Exception
	{
		if (dril_name == null || rig_num == null) throw new NullPointerException();
		
		boolean result = false;
	
		final CustomRecordSearchAdvanced customRecordSearchAdvanced = new CustomRecordSearchAdvanced();
		
		final CustomRecordSearch criteria = new CustomRecordSearch();
		
		final CustomRecordSearchBasic basic = new CustomRecordSearchBasic();
		
		final RecordRef HPDIRigReport = new RecordRef();
		HPDIRigReport.setInternalId(rb.getString("NetSuiteRigLocationId"));
		basic.setRecType(HPDIRigReport);
		
		final SearchCustomFieldList customFieldList = new SearchCustomFieldList();
		final SearchCustomField[] customFields = new SearchCustomField[2];
		final SearchStringCustomField DRIL_NAME = new SearchStringCustomField();
		DRIL_NAME.setInternalId("custrecorddril_name");
		DRIL_NAME.setOperator(SearchStringFieldOperator.is);
		DRIL_NAME.setSearchValue(dril_name);
		customFields[0] = DRIL_NAME;
		final SearchStringCustomField RIG_NUM = new SearchStringCustomField();
		RIG_NUM.setInternalId("custrecordrig_num");
		RIG_NUM.setOperator(SearchStringFieldOperator.is);
		RIG_NUM.setSearchValue(rig_num);
		customFields[1] = RIG_NUM;
		
		customFieldList.setCustomField(customFields);
		
		basic.setCustomFieldList(customFieldList);
		
		criteria.setBasic(basic);
		
		customRecordSearchAdvanced.setCriteria(criteria);
		
		final SearchResult response = this.getNetSuiteProxy().getNetSuitePort().search(customRecordSearchAdvanced);
		
		if (!response.getStatus().isIsSuccess()) throw new Exception();
		
		if (response.getTotalRecords() > 0) result = true;
		
		return result;
	}

	public String getRowInternalId(String dril_name, String rig_num) throws Exception
	{
		String result = null;
		
		if (dril_name == null || rig_num == null) throw new NullPointerException();
		
		final CustomRecordSearchAdvanced customRecordSearchAdvanced = new CustomRecordSearchAdvanced();
		
		final CustomRecordSearch criteria = new CustomRecordSearch();
		
		final CustomRecordSearchBasic basic = new CustomRecordSearchBasic();
		
		final RecordRef HPDIRigReport = new RecordRef();
		HPDIRigReport.setInternalId(rb.getString("NetSuiteRigLocationId"));
		
		basic.setRecType(HPDIRigReport);
		
		final SearchCustomFieldList customFieldList = new SearchCustomFieldList();
		final SearchCustomField[] customFields = new SearchCustomField[2];
		final SearchStringCustomField DRIL_NAME = new SearchStringCustomField();
		DRIL_NAME.setInternalId("custrecorddril_name");
		DRIL_NAME.setOperator(SearchStringFieldOperator.is);
		DRIL_NAME.setSearchValue(dril_name);
		customFields[0] = DRIL_NAME;
		final SearchStringCustomField RIG_NUM = new SearchStringCustomField();
		RIG_NUM.setInternalId("custrecordrig_num");
		RIG_NUM.setOperator(SearchStringFieldOperator.is);
		RIG_NUM.setSearchValue(rig_num);
		customFields[1] = RIG_NUM;
		
		customFieldList.setCustomField(customFields);
		
		basic.setCustomFieldList(customFieldList);
		
		criteria.setBasic(basic);
		
		customRecordSearchAdvanced.setCriteria(criteria);
		
		final SearchResult response = this.getNetSuiteProxy().getNetSuitePort().search(customRecordSearchAdvanced);
		
		if (!response.getStatus().isIsSuccess()) throw new Exception();
		
		if (response.getTotalRecords() == 0) return result;
		
		Record[] records = response.getRecordList().getRecord();

        CustomRecord customRecord = (CustomRecord)records[0];
        
        result = customRecord.getInternalId();
		
		return result;
	}
	
	public void init()
	{
        try
        {
        	rb = ResourceBundle.getBundle(getMode());
            fileHelper = new HPDIFileHelper();
            emailHelper = new EmailHelper();
            facilityLocations = fileHelper.getFaclityLocations();
        }
        catch(Exception e)
        {
           logger.error(e);
        }
    }
	
    private boolean isDouble(String s)
    {
    	boolean result = false;
    	
    	try 
    	{
    		Double.parseDouble(s);
    		result = true;
    	} 
    	catch (NumberFormatException e) 
    	{
    		logger.error(e);
    	}
    	return result;
    		
    }
    
	public void addDrivingDistancesColumnHeaders() throws Exception
	{
		int nextColumn = this.table.getColumnCount();
		for(HashMap<String, Object> facility : facilityLocations)
		{
			table.setValueAt(facility.get("custrecordname"), 0, nextColumn++);
		}
	}
	
	public void calculateSimpleRoute(CustomRecordHelper helper, String wellLatitude, String wellLongitude)
	{
		try
		{
			for(HashMap<String, Object> facility: facilityLocations)
			{
				final String facilityLatitude = (String) facility.get("custrecordlatitude");
				final String facilityLongitude = (String) facility.get("custrecordlongitude");
				final MappointHelper mappointHelper = new MappointHelper();
				
				if (facilityLatitude.length() <= 0 || facilityLongitude.length() <= 0) return;
				if (!isDouble(facilityLatitude) || !isDouble(facilityLongitude)) return;				
					
		        if (wellLatitude.length() <= 0 || wellLongitude.length() <= 0) return;
		        if (!isDouble(wellLatitude) || !isDouble(wellLongitude)) return;
		        if (Double.parseDouble(wellLatitude) != 0.0 && 
		            Double.parseDouble(wellLongitude) != 0.0 &&
					Double.parseDouble(facilityLatitude) != 0.0 && 
					Double.parseDouble(facilityLongitude) != 0.0)
		        {		            	
		            Double value = mappointHelper.calculateSimpleRoute(Double.parseDouble(facilityLatitude), 
		            		                                           Double.parseDouble(facilityLongitude),
		            		                                           Double.parseDouble(wellLatitude), 
		            		                                           Double.parseDouble(wellLongitude));

		            helper.addCustomFieldRef(helper.create(getCustomRecordName((String)facility.get("custrecordname")),String.valueOf(value)));
				}
			}
		}
		catch (final Exception e)
		{
			logger.error(e);
		}
	}
}