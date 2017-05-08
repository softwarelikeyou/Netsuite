package com.softwarelikeyou.server.netsuite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

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
import com.netsuite.webservices.setup.customization.CustomRecordSearch;
import com.netsuite.webservices.setup.customization.CustomRecordSearchAdvanced;
import com.softwarelikeyou.server.util.Helper;

public class HPDIFileHelper extends Helper
{
	private static Logger logger = Logger.getLogger(HPDIFileHelper.class);
	
	protected ResourceBundle rb;
	
	public HPDIFileHelper()
	{
		rb = ResourceBundle.getBundle(getMode());
	}
	
	public boolean save(final HashMap<String, Object> file)
	{
		boolean ret = false;
		final CustomRecordHelper helper = new CustomRecordHelper();
		helper.getRecType().setInternalId(rb.getString("NetSuiteHPDIFileId"));
		
		helper.addCustomFieldRef(helper.create(rb.getString("NetSuiteHPDIFieldFileName"), (String)file.get("name")));
		helper.addCustomFieldRef(helper.create(rb.getString("NetSuiteHPDIFieldContentsName"), (String)file.get("contents")));
		
		try
		{
		    if (!contains((String)file.get("name")))
               ret = add(helper.get());
		    else
		    	logger.info("Already have received file " + file.get("name"));
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		
		return ret;
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
			   logger.info("add custom record: " + record.getInternalId() + " " + helper.message(response));
			}
			else
			   logger.info("add custom record: FAILED for " + record.getInternalId() + " " + helper.message(response));
		}
		catch (final Exception e)
		{
			logger.error(e);
		}
		return result;
	}
	
	protected boolean contains(String name) throws Exception
	{
		if (name == null) throw new NullPointerException();
		
		boolean result = false;
	
		final CustomRecordSearchAdvanced customRecordSearchAdvanced = new CustomRecordSearchAdvanced();
		
		final CustomRecordSearch criteria = new CustomRecordSearch();
		
		final CustomRecordSearchBasic basic = new CustomRecordSearchBasic();
		
		final RecordRef HPDIFile = new RecordRef();
		HPDIFile.setInternalId(rb.getString("NetSuiteHPDIFileId"));
		basic.setRecType(HPDIFile);
		
		final SearchCustomFieldList customFieldList = new SearchCustomFieldList();
		final SearchCustomField[] customFields = new SearchCustomField[1];
		final SearchStringCustomField NAME = new SearchStringCustomField();
		NAME.setInternalId(rb.getString("NetSuiteHPDIFieldFileName"));
		NAME.setOperator(SearchStringFieldOperator.is);
		NAME.setSearchValue(name);
		customFields[0] = NAME;
		
		customFieldList.setCustomField(customFields);
		
		basic.setCustomFieldList(customFieldList);
		
		criteria.setBasic(basic);
		
		customRecordSearchAdvanced.setCriteria(criteria);
		
		final SearchResult response = this.getNetSuiteProxy().getNetSuitePort().search(customRecordSearchAdvanced);
		
		if (!response.getStatus().isIsSuccess()) throw new Exception();
		
		if (response.getTotalRecords() > 0) result = true;
		
		return result;
	}

	private boolean update(final CustomRecord record, String rowInternalId)
	{
		boolean result = false;
		
		try
		{	
			final RecordRef HPDIFileRef = new RecordRef();
			HPDIFileRef.setInternalId(rb.getString("NetSuiteHPDIFileId"));
			HPDIFileRef.setName(rb.getString("NetSuiteHPDIFileName"));
				
			final CustomRecord HPDIFile = record;
			HPDIFile.setRecType(HPDIFileRef);
			HPDIFile.setInternalId(rowInternalId);
			
			final WriteResponse response = this.getNetSuiteProxy().getNetSuitePort().update(HPDIFile);
			
			if (response.getStatus().isIsSuccess())
			{
				logger.info("update custom record: " + record.getInternalId());
				result = true;
			}
			else
				logger.info("update custom record: FAILED for " + record.getInternalId() + "-" + response.getStatus().getStatusDetail()[0].getMessage());
				
		}
		catch (final Exception e)
		{
			logger.error(e);
		}
		
		return result;
	}
	
	private String getRowInternalId(String name) throws Exception
	{
		String result = null;
		
		if (name == null) throw new NullPointerException();
		
		final CustomRecordSearchAdvanced customRecordSearchAdvanced = new CustomRecordSearchAdvanced();
		
		final CustomRecordSearch criteria = new CustomRecordSearch();
		
		final CustomRecordSearchBasic basic = new CustomRecordSearchBasic();
		
		final RecordRef HPDIFile = new RecordRef();
		HPDIFile.setInternalId(rb.getString("NetSuiteHPDIFileId"));
		
		basic.setRecType(HPDIFile);
		
		final SearchCustomFieldList customFieldList = new SearchCustomFieldList();
		final SearchCustomField[] customFields = new SearchCustomField[1];
		final SearchStringCustomField NAME = new SearchStringCustomField();
		NAME.setInternalId(rb.getString("NetSuiteHPDIFieldFileName"));
		NAME.setOperator(SearchStringFieldOperator.is);
		NAME.setSearchValue(name);
		customFields[0] = NAME;
		
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
	
	public boolean setProcessed(final HashMap<String, Object> file)
	{
		boolean ret = false;
		
		try
		{
	       final CustomRecordHelper helper = new CustomRecordHelper();
		   helper.getRecType().setInternalId(rb.getString("NetSuiteHPDIFileId"));
	       helper.addCustomFieldRef(helper.create(rb.getString("NetSuiteHPDIFieldProcessedName"),Boolean.valueOf("true")));
	       ret = update(helper.get(), getRowInternalId((String)file.get("name")));
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		
		return ret;
	}
	
	public boolean setErrorRows(final HashMap<String, Object> file, final String errorRows)
	{
		boolean ret = false;
		
		try
		{
	       final CustomRecordHelper helper = new CustomRecordHelper();
		   helper.getRecType().setInternalId(rb.getString("NetSuiteHPDIFileId"));
	       helper.addCustomFieldRef(helper.create(rb.getString("NetSuiteHPDIFieldErrorRowsName"), errorRows));
	       ret = update(helper.get(), getRowInternalId((String)file.get("name")));
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		
		return ret;
	}
	
	public Collection<HashMap<String, Object>> getFaclityLocations()
	{
		final Collection<HashMap<String, Object>> facilities = new ArrayList<HashMap<String, Object>>();
		
		final CustomRecordSearchAdvanced customRecordSearchAdvanced = new CustomRecordSearchAdvanced();
		
		final CustomRecordSearch criteria = new CustomRecordSearch();
		
		final CustomRecordSearchBasic basic = new CustomRecordSearchBasic();
		
		final RecordRef HPDIFile = new RecordRef();
		HPDIFile.setInternalId(rb.getString("NetSuiteFacilityLocationsId"));
		
		basic.setRecType(HPDIFile);
		
		criteria.setBasic(basic);
		
		customRecordSearchAdvanced.setCriteria(criteria);
		
		try
		{
		   final SearchResult response = this.getNetSuiteProxy().getNetSuitePort().search(customRecordSearchAdvanced);
		   
		   if (!response.getStatus().isIsSuccess()) throw new Exception();
			
		   if (response.getTotalRecords() == 0) 
		      return facilities;
		   
		   Record[] records = response.getRecordList().getRecord();

		   for (int i = 0; i < records.length; i++)
		   {
	          CustomRecord customRecord = (CustomRecord)records[i];
			
	          CustomFieldList customFieldList = customRecord.getCustomFieldList();
	          
	          CustomFieldRef[] customFieldRefs = customFieldList.getCustomField();
	         
	          HashMap<String, Object> facility = new HashMap<String, Object>();
	          for (int j = 0; j < customFieldRefs.length; j++)
	          {
	             StringCustomFieldRef SCFR = (StringCustomFieldRef)customFieldRefs[j];
	             facility.put(SCFR.getInternalId(), SCFR.getValue());
	          }     
	          facilities.add(facility);
		   }
		}
		catch (Exception e)
		{
			logger.error(e);
		}
        
		return facilities;
		
	}
}
