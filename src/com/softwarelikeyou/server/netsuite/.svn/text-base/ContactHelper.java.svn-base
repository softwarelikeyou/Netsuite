package com.softwarelikeyou.server.netsuite;


import com.netsuite.webservices.lists.relationships.Contact;
import com.netsuite.webservices.lists.relationships.ContactAddressbook;
import com.netsuite.webservices.lists.relationships.ContactAddressbookList;
import com.netsuite.webservices.lists.relationships.Customer;
import com.netsuite.webservices.platform.core.BaseRef;
import com.netsuite.webservices.platform.core.Record;
import com.netsuite.webservices.platform.core.RecordRef;


public class ContactHelper extends NetSuiteHelper
{
	
	private Contact contact;
	
	public ContactHelper(final Contact contact)
	{
		set(contact);
	}
	
	public void setDefaultValues()
	{
	}
	
	public void addAddress(final ContactAddressbook value)
	{
		if (value == null) throw new NullPointerException("contact");
		final ContactAddressbook[] result = { value };
		addAddresses(result);
	}
	
	public void addAddresses(final ContactAddressbook[] address)
	{
		if (address == null) throw new NullPointerException("address");
		final ContactAddressbook[] current = getAddressbook();
		final ContactAddressbook[] result = new ContactAddressbook[current.length + address.length];
		System.arraycopy(current, 0, result, 0, current.length);  
		System.arraycopy(address, 0, result, current.length, address.length);
		getContactAddressbookList().setAddressbook(result);
	}
	
	public void setContactAddressbookList(final ContactAddressbookList list)
	{
		if (list == null) throw new NullPointerException("list");
		contact.setAddressbookList(list);
	}
	
	public void setAddressbook(final ContactAddressbook[] address)
	{
		if (address == null) throw new NullPointerException("address");
		getContactAddressbookList().setAddressbook(address);
	}
	
	public ContactAddressbookList getContactAddressbookList()
	{
		if (contact.getAddressbookList() == null) setContactAddressbookList(new ContactAddressbookList());
		return contact.getAddressbookList();
	}
	
	public ContactAddressbook[] getAddressbook()
	{
		if (getContactAddressbookList().getAddressbook() == null) setAddressbook(new ContactAddressbook[0]);
		return getContactAddressbookList().getAddressbook();
	}
	
	public Contact get()
	{
		return contact;
	}
	
	public void setCompany(final Customer customer)
	{
		contact.setCompany(create(customer));
	}
	
	public Contact cast(final Record record)
	{
		if (record == null) throw new NullPointerException();
		return (Contact) record;
	}
	
	public void setInternalId(final Record record)
	{
		contact.setInternalId(cast(record).getInternalId());
	}
	
	public void setInternalId(final BaseRef ref)
	{
		setInternalId((RecordRef) ref);
	}
	
	public void setInternalId(final RecordRef ref)
	{
		contact.setInternalId(ref.getInternalId());
	}
	
	protected void set(final Contact contact)
	{
		if (contact == null) throw new NullPointerException();
		this.contact = contact;
	}
	
	public void setIsInactive(final Record record)
	{
		contact.setIsInactive((cast(record).getIsInactive()));
	}
	
	public void setIsInactive(final BaseRef ref)
	{
		setIsInactive((RecordRef) ref);
	}
	
	public void setFirstName(final Record record)
	{
		contact.setFirstName((cast(record).getFirstName()));
	}
	
	public void setFirstName(final BaseRef ref)
	{
		setFirstName((RecordRef) ref);
	}
	
	public void setLastName(final Record record)
	{
		contact.setLastName((cast(record).getLastName()));
	}
	
	public void setLastName(final BaseRef ref)
	{
		setLastName((RecordRef) ref);
	}
	
	public void setEntityId(final Record record)
	{
		contact.setEntityId((cast(record).getEntityId()));
	}
	
	public void setEntityId(final BaseRef ref)
	{
		setEntityId((RecordRef) ref);
	}
}
