package com.softwarelikeyou.server.netsuite;


import com.netsuite.webservices.lists.relationships.Customer;
import com.netsuite.webservices.platform.core.BooleanCustomFieldRef;
import com.netsuite.webservices.platform.core.CustomFieldRef;
import com.netsuite.webservices.platform.core.RecordRef;
import com.netsuite.webservices.platform.core.StatusDetail;
import com.netsuite.webservices.platform.core.StringCustomFieldRef;
import com.netsuite.webservices.platform.core.types.RecordType;
import com.netsuite.webservices.platform.messages.WriteResponse;
import com.softwarelikeyou.server.util.Helper;


public abstract class NetSuiteHelper extends Helper
{
	
	public StringCustomFieldRef create(final String internalId, final String value)
	{
		if (internalId == null || value == null) throw new NullPointerException();
		final StringCustomFieldRef result = new StringCustomFieldRef(internalId, value);
		return result;
	}
	
	public BooleanCustomFieldRef create(final String internalId, final Boolean value)
	{
		if (internalId == null || value == null) throw new NullPointerException();
		final BooleanCustomFieldRef result = new BooleanCustomFieldRef(internalId, value);
		return result;
	}
	
	public RecordRef create(final Customer customer)
	{
		final RecordRef result = new RecordRef();
		result.setInternalId(customer.getInternalId());
		result.setType(RecordType.customer);
		return result;
	}
	
	public StringCustomFieldRef cast(final CustomFieldRef value)
	{
		return (StringCustomFieldRef) value;
	}
	
	public boolean isSuccess(final WriteResponse response)
	{
		return response.getStatus().isIsSuccess();
	}
	
	public String message (final WriteResponse response)
	{
		if (response == null) throw new NullPointerException();
		final StatusDetail[] details = (response.getStatus().getStatusDetail() == null) ? new StatusDetail[0] : response.getStatus().getStatusDetail();
		final StringBuilder message = new StringBuilder();
		for (StatusDetail  detail :details) message.append(detail.getMessage() + "/n");
		return message.toString();
	}
}
