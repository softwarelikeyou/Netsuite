package com.softwarelikeyou.server.netsuite;

import java.util.ResourceBundle;

import com.netsuite.webservices.lists.relationships.Customer;
import com.netsuite.webservices.platform.common.OpportunitySearchBasic;
import com.netsuite.webservices.platform.core.BaseRef;
import com.netsuite.webservices.platform.core.CustomFieldList;
import com.netsuite.webservices.platform.core.CustomFieldRef;
import com.netsuite.webservices.platform.core.Record;
import com.netsuite.webservices.platform.core.RecordRef;
import com.netsuite.webservices.platform.core.SearchCustomField;
import com.netsuite.webservices.platform.core.SearchCustomFieldList;
import com.netsuite.webservices.platform.core.SearchStringCustomField;
import com.netsuite.webservices.platform.core.StringCustomFieldRef;
import com.netsuite.webservices.platform.core.types.RecordType;
import com.netsuite.webservices.platform.core.types.SearchStringFieldOperator;
import com.netsuite.webservices.transactions.sales.Opportunity;


public class OpportunityHelper extends NetSuiteHelper
{
	public static RecordRef OPPORTUNITY_DEFAULT_LEADSOURCE;
	public static RecordRef OPPORTUNITY_DEFAULT_LOCATION;
	public static RecordRef OPPORTUNITY_DEFAULT_DEPARTMENT;
	public static RecordRef OPPORTUNITY_DEFAULT_CLASSIFICATION;
	public static RecordRef OPPORTUNITY_DEFAULT_ENTITYSTATUS;
	public static double OPPORTUNITY_DEFAULT_PROBABILITY;
	
	private Opportunity opportunity;
	
	protected ResourceBundle rb;
	
	public OpportunityHelper(final Opportunity opportunity)
	{
		set(opportunity);
		
		rb = ResourceBundle.getBundle(getMode());
		
		OPPORTUNITY_DEFAULT_LEADSOURCE = new RecordRef("", rb.getString("NetSuiteOpportunityDEFAULT_LEADSOURCE_ID"), "", RecordType.leadSource);
		OPPORTUNITY_DEFAULT_LOCATION = new RecordRef(rb.getString("NetSuiteOpportunityDEFAULT_LOCATION_NAME"), rb.getString("NetSuiteOpportunityDEFAULT_LOCATION_ID"), "", RecordType.location);
		OPPORTUNITY_DEFAULT_DEPARTMENT = new RecordRef(rb.getString("NetSuiteOpportunityDEFAULT_DEPARTMENT_NAME"), rb.getString("NetSuiteOpportunityDEFAULT_DEPARTMENT_ID"), "", RecordType.department);
		OPPORTUNITY_DEFAULT_CLASSIFICATION = new RecordRef(rb.getString("NetSuiteOpportunityDEFAULT_CLASSIFICATION_NAME"), rb.getString("NetSuiteOpportunityDEFAULT_CLASSIFICATION_ID"), "", RecordType.classification);
		OPPORTUNITY_DEFAULT_ENTITYSTATUS = new RecordRef("", rb.getString("NetSuiteOpportunityDEFAULT_ENTITYSTATUS_ID"), "", RecordType.customerStatus);
		OPPORTUNITY_DEFAULT_PROBABILITY = Double.valueOf(rb.getString("NetSuiteOpportunityDEFAULT_PROBABILITY"));
	}
	
	public Opportunity get()
	{
		return opportunity;
	}
	
	public void set(final Opportunity value)
	{
		if (value == null) throw new NullPointerException();
		opportunity = value;
	}
	
	public void setEntity(final Customer value)
	{
		if (value == null) throw new NullPointerException();
		opportunity.setEntity(create(value));
	}
	
	public void setDefaultValues()
	{
		//opportunity.setLeadSource(NetSuite.OPPORTUNITY_DEFAULT_LEADSOURCE);
		//opportunity.setProbability(NetSuite.OPPORTUNITY_DEFAULT_PROBABILITY);
		opportunity.setLocation(OPPORTUNITY_DEFAULT_LOCATION);
		opportunity.setDepartment(OPPORTUNITY_DEFAULT_DEPARTMENT);
		opportunity.set_class(OPPORTUNITY_DEFAULT_CLASSIFICATION);
		//opportunity.setEntityStatus(NetSuite.OPPORTUNITY_DEFAULT_ENTITYSTATUS);;
	}
	
	public CustomFieldRef[] getCustomField()
	{
		if (getCustomFieldList().getCustomField() == null) setCustomField(new CustomFieldRef[0]);
		return getCustomFieldList().getCustomField();
	}
	
	public void setCustomField(final CustomFieldRef[] value)
	{
		getCustomFieldList().setCustomField(value);
	}
	
	public CustomFieldList getCustomFieldList()
	{
		if (opportunity.getCustomFieldList() == null) setCustomFieldList(new CustomFieldList(new CustomFieldRef[0]));
		return opportunity.getCustomFieldList();
	}
	
	public void setCustomFieldList(final CustomFieldList value)
	{
		opportunity.setCustomFieldList(value);
	}
	
	public void addCustomFieldRef(final CustomFieldRef ref)
	{
		if (ref == null) throw new NullPointerException("ref");
		final CustomFieldRef[] result = { ref };
		addCustomFieldRef(result);
	}
	
	public void addCustomFieldRef(final CustomFieldRef[] ref)
	{
		if (ref == null) throw new NullPointerException("ref");
		setCustomField(addCustomFieldRef(getCustomField(), ref));
	}
	
	public CustomFieldRef[] addCustomFieldRef(final CustomFieldRef[] first, final CustomFieldRef[] second)
	{
		if (first == null || second == null) throw new NullPointerException();
		final CustomFieldRef[] result = new CustomFieldRef[first.length + second.length];
		System.arraycopy(first, 0, result, 0, first.length);  
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
	
	public Opportunity cast(final Record record)
	{
		return (Opportunity) record;
	}
	
	public void setInternalId(final Record record)
	{
		opportunity.setInternalId(cast(record).getInternalId());
	}
	
	public void setInternalId(final BaseRef ref)
	{
		setInternalId((RecordRef) ref);
	}
	
	public void setInternalId(final RecordRef ref)
	{
		opportunity.setInternalId(ref.getInternalId());
	}
	
	public OpportunitySearchBasic getSearchRecordByStringCustomFieldRef()
	{
		final SearchCustomField[] field = new SearchCustomField[2];
		field[0] = getSearchStringCustomField(rb.getString("NetSuiteOpportunityAPI_FLD"));
		field[1] = getSearchStringCustomField(rb.getString("NetSuiteOpportunityPERMIT_NO_FLD"));
		
		for (SearchCustomField custom: field) cast(custom).setOperator(SearchStringFieldOperator.is);
		
		final SearchCustomFieldList list = new SearchCustomFieldList();
		list.setCustomField(field);
		
		final OpportunitySearchBasic result = new OpportunitySearchBasic();
		result.setCustomFieldList(list);
		return result;
	}
	
	public StringCustomFieldRef getStringCustomFieldRef(final String internalId)
	{
		if(internalId == null) throw new NullPointerException();
		final CustomFieldRef[] fields = getCustomField();
		for (CustomFieldRef field : fields) if (cast(field).getInternalId().contentEquals(internalId)) return cast(field);
		return new StringCustomFieldRef(internalId, "");
	}
	
	public SearchStringCustomField getSearchStringCustomField(final String internalId)
	{
		if(internalId == null) throw new NullPointerException();
		return create(getStringCustomFieldRef(internalId));
	}
	
	public SearchStringCustomField create(final StringCustomFieldRef field)
	{
		if (field == null) throw new NullPointerException();
		final SearchStringCustomField result = new SearchStringCustomField(field.getInternalId(), null, field.getValue());
		return result;
	}
	
	public SearchStringCustomField cast(final SearchCustomField field)
	{
		if (field == null) throw new NullPointerException();
		return (SearchStringCustomField) field;
	}
}

