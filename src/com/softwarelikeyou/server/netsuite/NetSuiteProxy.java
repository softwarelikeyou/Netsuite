package com.softwarelikeyou.server.netsuite;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.log4j.Logger;

import com.netsuite.webservices.lists.relationships.Contact;
import com.netsuite.webservices.lists.relationships.Customer;
import com.netsuite.webservices.lists.relationships.CustomerSearch;
import com.netsuite.webservices.platform.NetSuiteBindingStub;
import com.netsuite.webservices.platform.NetSuitePortType;
import com.netsuite.webservices.platform.NetSuiteServiceLocator;
import com.netsuite.webservices.platform.common.CustomerSearchBasic;
import com.netsuite.webservices.platform.core.Passport;
import com.netsuite.webservices.platform.core.Record;
import com.netsuite.webservices.platform.core.RecordRef;
import com.netsuite.webservices.platform.core.SearchRecord;
import com.netsuite.webservices.platform.core.SearchResult;
import com.netsuite.webservices.platform.core.Status;
import com.netsuite.webservices.platform.core.StatusDetail;
import com.netsuite.webservices.platform.core.types.RecordType;
import com.netsuite.webservices.platform.messages.Preferences;
import com.netsuite.webservices.platform.messages.ReadResponse;
import com.netsuite.webservices.platform.messages.WriteResponse;
import com.netsuite.webservices.setup.customization.CustomList;
import com.netsuite.webservices.setup.customization.CustomListCustomValue;
import com.netsuite.webservices.setup.customization.CustomRecord;
import com.netsuite.webservices.transactions.sales.Opportunity;
import com.netsuite.webservices.transactions.sales.SalesOrder;

public class NetSuiteProxy implements NetSuite
{
	
	public enum SearchType { SearchMoreWithId, SearchMore }
	
	private final static Logger logger = Logger.getLogger(NetSuiteProxy.class);
	
	protected final NetSuiteServiceLocator service = new NetSuiteServiceLocator();
	
	protected NetSuitePortType port;
	
	public NetSuitePortType getNetSuitePort() throws ServiceException
	{
		if (port == null) port = service.getNetSuitePort();
		return port;
	}
	
	public NetSuiteBindingStub getNetSuiteBindingStub() throws ServiceException
	{
		final NetSuiteBindingStub stub = (NetSuiteBindingStub) getNetSuitePort();
		if (stub == null) throw new NullPointerException("stub");
		return stub;
	}
	
	public Status login(final Passport passport) throws Exception
	{
		if (passport == null) throw new NullPointerException();
		try
		{
			System.setProperty("axis.socketSecureFactory", "org.apache.axis.components.net.SunFakeTrustSocketFactory");
			service.setMaintainSession(true);
			getNetSuiteBindingStub().setTimeout(1000 * 60 * 60 * 2);
			final Status status = getNetSuitePort().login(passport).getStatus();
			((javax.xml.rpc.Stub) getNetSuitePort())._setProperty(HTTPConstants.HEADER_COOKIE, new String[] { "ACCOUNT=" + passport.getAccount() });
			return status;
		} 
		catch (final Exception e)
		{
			logger.error(e.getClass().getName(), e);
			throw new RuntimeException(e.getClass().getName());
		}
	}
	
	public Status logout() throws Exception
	{
		try
		{
			return getNetSuitePort().logout().getStatus();
		}
		catch (final Exception e)
		{
			logger.error(e.getClass().getName(), e);
			throw new RuntimeException(e.getClass().getName());
		}
	}
	
	public void setClearHeader() throws SOAPException, ServiceException
	{
		getNetSuiteBindingStub().clearHeaders();
	}
	
	public void setRequestLevelCredentials(final Passport passport) throws SOAPException, ServiceException
	{
		SOAPHeaderElement passportHeader = new SOAPHeaderElement("urn:messages.platform.webservices.netsuite.com", "passport");
		passportHeader.setObjectValue(passport);
		getNetSuiteBindingStub().setHeader(passportHeader);
		getNetSuiteBindingStub().setMaintainSession(false);
	}
	
	public void setPreferences(final Preferences preferences) throws SOAPException, ServiceException
	{
		if (preferences == null) throw new NullPointerException();
		SOAPHeaderElement preferencesHeader = new SOAPHeaderElement("urn:messages.platform.webservices.netsuite.com","preferences");
		preferencesHeader.setObjectValue(preferences);
		getNetSuiteBindingStub().setHeader(preferencesHeader);
	}
	
	public CustomListCustomValue[] getCustomList(final String internalId) throws Exception
	{
		if (internalId == null) throw new NullPointerException();
		CustomList list = null;
		ReadResponse response = null;
		final RecordRef recordRef = new RecordRef();
		recordRef.setInternalId(internalId);
		recordRef.setType(RecordType.customList);
		
		try
		{
			response = getNetSuitePort().get(recordRef);
			list = (CustomList)response.getRecord();
			if (list == null)
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("response: " + response.getStatus().isIsSuccess());
					final StatusDetail[] detail = response.getStatus().getStatusDetail();
					for (int i = 0 ; i < detail.length ; i++)
					{
						logger.debug(" detail: " + detail[i].getMessage());
					}
				}
				throw new NullPointerException("list");
			}
		}
		catch (final Exception e)
		{
			logger.error(e.getClass().getName(), e);
			throw new RuntimeException(e.getClass().getName());
		}
		return list.getCustomValueList().getCustomValue();
	}
	
	public Record[] searchMoreWithId(final com.netsuite.webservices.platform.core.SearchRecord search) throws Exception
	{
		if (search == null) throw new NullPointerException();
		return search(search, SearchType.SearchMoreWithId);
	}
	
	public Record[] searchMore(final com.netsuite.webservices.platform.core.SearchRecord search) 	throws Exception
	{
		if (search == null) throw new NullPointerException();
		return search(search, SearchType.SearchMore);
	}
	
	public RecordType getRecordType(final String recordType)
	{
		if (recordType == null) throw new NullPointerException();
		if (recordType.contentEquals(RecordType.otherChargeSaleItem.getValue())) return RecordType.otherChargeSaleItem;
		if (recordType.contentEquals(RecordType.inventoryItem.getValue())) return RecordType.inventoryItem;
		if (recordType.contentEquals(RecordType.serviceSaleItem.getValue())) return RecordType.serviceSaleItem;
		if (recordType.contentEquals(RecordType.nonInventoryResaleItem.getValue())) return RecordType.nonInventoryResaleItem;
		if (recordType.contentEquals(RecordType.kitItem.getValue())) return RecordType.kitItem;
		if (recordType.contentEquals(RecordType.assemblyItem.getValue())) return RecordType.assemblyItem;
		if (recordType.contentEquals(RecordType.discountItem.getValue())) return RecordType.discountItem;
		throw new IllegalArgumentException();
	}
	
	public WriteResponse add(final com.netsuite.webservices.lists.relationships.Contact contact) throws Exception
	{
		return addRecord(contact);
	}
	
	public WriteResponse add(final SalesOrder order) throws Exception
	{
		return addRecord(order);
	}
	
	public WriteResponse add(final Opportunity opportunity) throws Exception
	{
		return addRecord(opportunity);
	}
	
	public Record[] getCustomerAll() throws Exception
	{
		final CustomerSearchBasic basic = new CustomerSearchBasic();
		return search(basic);
	}
	
	public WriteResponse add(final Customer customer) throws Exception
	{
		return addRecord(customer);
	}
	
	public WriteResponse update(final Customer customer) throws Exception
	{
		return updateRecord(customer);
	}
	
	public WriteResponse update(final Contact contact) throws Exception
	{
		return updateRecord(contact);
	}
	
	public WriteResponse update(final Opportunity opportunity) throws Exception
	{
		return updateRecord(opportunity);
	}
	
	public boolean contains(final Customer customer) throws Exception
	{
		if (customer == null || customer.getInternalId() == null) throw new NullPointerException("InternalId");
		return get(customer).getStatus().isIsSuccess();
	}
	
	public ReadResponse get(final Customer customer) throws Exception
	{
		if (customer == null || customer.getInternalId() == null) throw new NullPointerException("InternalId");
		final RecordRef recordRef = new RecordRef();
		recordRef.setInternalId(customer.getInternalId());
		recordRef.setType(RecordType.customer);
		return get(recordRef);
	}
	
	public Record[] search(final CustomerSearchBasic basic) throws Exception
	{
		if (basic == null) throw new NullPointerException("basic");
		final CustomerSearch search = new CustomerSearch();
		search.setBasic(basic);
		return search(search);
	}
	
	public WriteResponse addCustomRecord(final CustomRecord record) throws Exception
	{
		if (record == null) throw new NullPointerException();
		try
		{
			final WriteResponse response = getNetSuitePort().add(record);
			return response;
		} 
		catch (final Exception e)
		{
			logger.error(e.getClass().getName(), e);
			throw new RuntimeException(e.getClass().getName());
		}
	}
	
	public WriteResponse addRecord(final Record record) throws Exception
	{
		if (record == null) throw new NullPointerException();
		try
		{
			final WriteResponse response = getNetSuitePort().add(record);
			return response;
		} 
		catch (final Exception e)
		{
			logger.error(e.getClass().getName(), e);
			throw new RuntimeException(e.getClass().getName());
		}
	}
	
	public WriteResponse updateRecord(final Record record) throws Exception
	{
		if (record == null) throw new NullPointerException("record");
		try
		{
			return getNetSuitePort().update(record);
		} 
		catch (final Exception e)
		{
			logger.error(e.getClass().getName(), e);
			throw new RuntimeException(e.getClass().getName());
		}
	}
	
	public ReadResponse get(final RecordRef recordRef) throws Exception
	{
		if (recordRef == null) throw new NullPointerException("recordRef");
		if (recordRef.getType() == null) throw new IllegalArgumentException("RecordType");
		return getNetSuitePort().get(recordRef);
	}
	
	public Record[] search(final SearchRecord search) throws Exception
	{
		if (search == null) throw new NullPointerException();
		final SearchResult main = getNetSuitePort().search(search);
		Record[] result = main.getRecordList().getRecord();
		final int pages = main.getTotalPages();
		final String searchId = main.getSearchId();
		for (int page = 1 ; page < pages ; page++)
		{
			final SearchResult additional = getNetSuitePort().searchMoreWithId(searchId, page + 1);
			result = addRecord(result, additional.getRecordList().getRecord());
		}
		return result;
	}
	
	public Record[] search(final SearchRecord search, final SearchType type) throws Exception
	{
		if (search == null || type == null) throw new NullPointerException();
		SearchResult result = getNetSuitePort().search(search);
		final int total = result.getTotalRecords();
		if (total < 1) return null;
		final String searchId = result.getSearchId();
		final Record[] list = new Record[result.getTotalRecords()];
		final int page = result.getTotalPages();
		int index = 0;
		for (int i = 1; i <= page; i++)
		{
			if (result == null) throw new NullPointerException("result");
			if (result.getRecordList() == null) throw new NullPointerException("RecordList");
			Record[] records = result.getRecordList().getRecord();
			for (Record record : records) list[index++] = record;
			switch (type)
			{
				case SearchMoreWithId: result = getNetSuitePort().searchMoreWithId(searchId, i + 1); break;
				case SearchMore: result = getNetSuitePort().searchMore(i + 1); break;
			}
		}
		if (index != total) throw new RuntimeException("total");
		return list;
	}
	
	public RecordRef createCustomerRecordRef(final Customer customer)
	{
		final RecordRef result = new RecordRef();
		result.setType(RecordType.customer);
		result.setName(customer.getEntityId());
		result.setInternalId(customer.getInternalId());
		return result;
	}
	
	public Record[] addRecord(final Record[] first, final Record[] second)
	{
		if (first == null || second == null) throw new NullPointerException();
		final Record[] result = new Record[first.length + second.length];
		System.arraycopy(first, 0, result, 0, first.length);  
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
}
