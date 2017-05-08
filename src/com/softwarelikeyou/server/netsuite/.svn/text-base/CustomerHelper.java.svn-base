package com.softwarelikeyou.server.netsuite;


import java.util.ResourceBundle;

import com.netsuite.webservices.lists.relationships.Customer;
import com.netsuite.webservices.lists.relationships.CustomerAddressbook;
import com.netsuite.webservices.lists.relationships.CustomerAddressbookList;
import com.netsuite.webservices.platform.core.BaseRef;
import com.netsuite.webservices.platform.core.Record;
import com.netsuite.webservices.platform.core.RecordRef;
import com.netsuite.webservices.platform.core.types.RecordType;

public class CustomerHelper extends NetSuiteHelper
{
	
	private Customer customer;
	
	public static RecordRef CUSTOMER_DEFAULT_ENTITYSTATUS;
	
	protected ResourceBundle rb;

	public CustomerHelper(final Customer customer)
	{
		set(customer);
		
		rb = ResourceBundle.getBundle(getMode());

		CUSTOMER_DEFAULT_ENTITYSTATUS = new RecordRef("", rb.getString("NetSuiteCustomerDEFAULT_ENTITYSTATUS_ID"), "", RecordType.customerStatus);
	}
	
	public void setCompanyName(final String value)
	{
		if (value == null) throw new NullPointerException();
		customer.setCompanyName(value);
	}
	
	public void setDefaultValues()
	{
		if (customer == null) throw new NullPointerException();
		customer.setEntityStatus(CUSTOMER_DEFAULT_ENTITYSTATUS);
	}
	
	public void addAddress(final CustomerAddressbook value)
	{
		if (value == null) throw new NullPointerException("contact");
		final CustomerAddressbook[] result = { value };
		addAddresses(result);
	}
	
	public void addAddresses(final CustomerAddressbook[] address)
	{
		if (address == null) throw new NullPointerException("address");
		final CustomerAddressbook[] current = getAddressbook();
		final CustomerAddressbook[] result = new CustomerAddressbook[current.length + address.length];
		System.arraycopy(current, 0, result, 0, current.length);  
		System.arraycopy(address, 0, result, current.length, address.length);
		getCustomerAddressbookList().setAddressbook(result);
	}
	
	public void setCustomerAddressbookList(final CustomerAddressbookList list)
	{
		if (list == null) throw new NullPointerException("list");
		get().setAddressbookList(list);
	}
	
	public void setAddressbook(final CustomerAddressbook[] address)
	{
		if (address == null) throw new NullPointerException("address");
		getCustomerAddressbookList().setAddressbook(address);
	}
	
	public CustomerAddressbookList getCustomerAddressbookList()
	{
		if (get().getAddressbookList() == null) setCustomerAddressbookList(new CustomerAddressbookList());
		return get().getAddressbookList();
	}
	
	public CustomerAddressbook[] getAddressbook()
	{
		if (getCustomerAddressbookList().getAddressbook() == null) setAddressbook(new CustomerAddressbook[0]);
		return getCustomerAddressbookList().getAddressbook();
	}
	
	public Customer get()
	{
		return customer;
	}
	
	public void set(final Customer customer)
	{
		if (customer == null) throw new NullPointerException();
		this.customer = customer;
	}
	
	public Customer cast(final Record record)
	{
		if (record == null) throw new NullPointerException();
		return (Customer) record;
	}
	
	public void setInternalId(final Record record)
	{
		customer.setInternalId(cast(record).getInternalId());
	}
	
	public void setInternalId(final BaseRef ref)
	{
		setInternalId((RecordRef) ref);
	}
	
	public void setInternalId(final RecordRef ref)
	{
		customer.setInternalId(ref.getInternalId());
	}
	
	public void setIsInactive(final Record record)
	{
		customer.setIsInactive(cast(record).getIsInactive());
	}
	
	public void setCompanyName(final Record record)
	{
		customer.setCompanyName(cast(record).getCompanyName());
	}
}
