package com.softwarelikeyou.server.netsuite;


import com.netsuite.webservices.platform.core.BaseRef;
import com.netsuite.webservices.platform.core.CustomFieldList;
import com.netsuite.webservices.platform.core.CustomFieldRef;
import com.netsuite.webservices.platform.core.CustomRecordRef;
import com.netsuite.webservices.platform.core.RecordRef;
import com.netsuite.webservices.setup.customization.CustomRecord;


public class CustomRecordHelper extends NetSuiteHelper
{

	private CustomRecord record;
	
	public CustomRecordHelper()
	{
		get();
	}
	
	public CustomRecordHelper(final CustomRecord record)
	{
		set(record);
	}
	
	public void setInternalId(final BaseRef ref)
	{
		setInternalId((CustomRecordRef) ref);
	}
	
	public void setInternalId(final CustomRecordRef ref)
	{
		get().setInternalId(ref.getInternalId());
	}
	
	public void setCustomFieldList(final CustomFieldList value)
	{
		get().setCustomFieldList(value);
	}
	
	public void setCustomField(final CustomFieldRef[] value)
	{
		getCustomFieldList().setCustomField(value);
	}
	
	public CustomFieldRef[] getCustomField()
	{
		if (getCustomFieldList().getCustomField() == null) setCustomField(new CustomFieldRef[0]);
		return getCustomFieldList().getCustomField();
	}
	
	public CustomFieldList getCustomFieldList()
	{
		if (get().getCustomFieldList() == null) setCustomFieldList(new CustomFieldList(new CustomFieldRef[0]));
		return get().getCustomFieldList();
	}
	
	public String getInternalId()
	{
		return get().getInternalId();
	}
	
	public RecordRef getRecType()
	{
		if (get().getRecType() == null) record.setRecType(new RecordRef());
		return get().getRecType();
	}
	
	public CustomRecord get()
	{
		if (record == null) set(new CustomRecord());
		return record;
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
	
	protected void set(final CustomRecord record)
	{
		if (record == null) throw new NullPointerException();
		this.record = record;
	}
}
