package com.softwarelikeyou.server.handler;

import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.softwarelikeyou.server.controller.Controller;
import com.softwarelikeyou.server.event.Event;
import com.softwarelikeyou.server.event.EventHandler;
import com.softwarelikeyou.server.event.Events;
import com.softwarelikeyou.server.netsuite.ContactHelper;
import com.softwarelikeyou.server.netsuite.CustomerHelper;
import com.softwarelikeyou.server.netsuite.HPDIFileHelper;
import com.softwarelikeyou.server.netsuite.NetSuiteProxyHelper;
import com.softwarelikeyou.server.netsuite.NetSuiteProxy;
import com.softwarelikeyou.server.netsuite.OpportunityHelper;
import com.softwarelikeyou.server.model.OpportunityTableModelFilter;
import com.softwarelikeyou.server.util.EmailHelper;
import com.softwarelikeyou.server.util.MappointHelper;
import com.softwarelikeyou.server.util.ServerTool;
import com.netsuite.webservices.lists.relationships.Contact;
import com.netsuite.webservices.lists.relationships.Customer;
import com.netsuite.webservices.platform.common.ContactSearchBasic;
import com.netsuite.webservices.platform.common.CustomerSearchBasic;
import com.netsuite.webservices.platform.core.Record;
import com.netsuite.webservices.platform.core.SearchStringField;
import com.netsuite.webservices.platform.core.StringCustomFieldRef;
import com.netsuite.webservices.platform.core.types.SearchStringFieldOperator;
import com.netsuite.webservices.platform.messages.WriteResponse;
import com.netsuite.webservices.transactions.sales.Opportunity;

public class PermitHandler extends EventHandler
{
   private static final Logger logger = Logger.getLogger(PermitHandler.class);

   protected ResourceBundle rb;
   protected HPDIFileHelper fileHelper;
   protected EmailHelper emailHelper;
   protected Collection<HashMap<String, Object>> facilityLocations;
   protected HashMap<String, Object> file;
   protected String errorRows = "";
   protected Controller controller;
   
   public PermitHandler()
   {
      controller = null;
   }

   public PermitHandler(Controller controller)
   {
      this.controller = null;
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

	  if (!event.getType().equals(Events.onLoadHPDIPermits)) 
	  {
	     logger.info("Recieved an invalid event type of " + event.getType().toString());
	     return;
	  }
      
	  init();
		
	  try
	  {	
		 file = (HashMap<String, Object>) event.getData();
		 final OpportunityTableModelFilter table = new OpportunityTableModelFilter(ServerTool.create((String)file.get("contents")));
		 for (int row = 1 ; row < table.getRowCount() ; row++)
		 {  
			final CustomerHelper customer = new CustomerHelper((Customer) table.getValueAt(row, 0));
				
			final ContactHelper contact = new ContactHelper((Contact) table.getValueAt(row, 1));
				
			final OpportunityHelper opportunity = new OpportunityHelper((Opportunity) table.getValueAt(row, 2));
							
			if (process(customer.get()))
			{
			   contact.setCompany(customer.get());
			   opportunity.setEntity(customer.get());
					
			   if (!process(contact.get())) 
			   {
				  errorRows += "Contact-" + String.valueOf(row) + ",";
				  logger.info("Contact processing failed for file " + file.get("name") + " at row " + row);
			   }
			   if (!process(opportunity.get()))
			   {
				  errorRows += "Opportunity-" + String.valueOf(row) + ",";
			      logger.info("Opportunity processing failed for file " + file.get("name") + " at row " + row);
			   }
			}
			else
			{
				errorRows += "Customer-" + String.valueOf(row) + ",";
			    logger.info("Customer processing failed for file " + file.get("name") + " at row " + row);
			}
         }
		 fileHelper.setProcessed(file);
	     emailHelper.sendEmail(file, "HPDI Daily Permits", errorRows);
	  } 
	  catch (final Exception e)
	  {
	     logger.error(e);
	  }
   }
	
   protected boolean process(final Customer customer)
   {
      boolean result = false;
      try
	  {
	     result = contains(customer) ? true : add(customer);
	  } 
      catch (final Exception e)
      {
	     logger.error(e);
	  }
	  
      return result;
   }
	
   protected boolean process(final Contact contact)
   {
      boolean result = false;
      
      try
      {
	     result = contains(contact) ? true : add(contact);
	  } 
	  catch (final Exception e)
	  {
	     logger.error(e);
	  }
	  
	  return result;
   }
	
   protected boolean process(final Opportunity opportunity)
   {
      boolean result = false;
	  try
	  {
	     System.setProperty("axis.ClientConfigFile", "mappoint-client-config.wsdd");
		 calculateSimpleRoute(opportunity);
	     System.clearProperty("axis.ClientConfigFile");

		 generatePermitURL(opportunity);
		 generateWellMap(opportunity);
		 result = contains(opportunity) ? update(opportunity) : add(opportunity);
      }
	  catch (final Exception e)
	  {
	     logger.error(e);
	  }
	  
	  return result;
   }
	
   protected boolean contains(final Customer customer) throws Exception
   {
      if (customer == null) throw new NullPointerException();
		
	  final CustomerHelper helper = new CustomerHelper(customer);
		
	  final SearchStringField field = new SearchStringField();
	  field.setSearchValue(customer.getCompanyName());
	  field.setOperator(SearchStringFieldOperator.is);
		
	  final CustomerSearchBasic basic = new CustomerSearchBasic();
	  basic.setCompanyName(field);
		
	  final Record[] record = getNetSuiteProxy().search(basic);
	  final boolean result = (record == null) ? false : record.length == 1;

	  if (result) 
	  {
		  helper.setInternalId(record[0]);
		  helper.setIsInactive(record[0]);
		  helper.setCompanyName(record[0]);
		  
		  logger.info("contains customer " + customer.getInternalId() + "-" + customer.getCompanyName() + 
				      " with inactive status of " + String.valueOf(customer.getIsInactive()));
	  }
	  else
		  logger.info("contains customer: NOT FOUND for " + (customer.getCompanyName().length() < 1 ? "NULL" : customer.getCompanyName()));
		  
	  return result;
   }
	
   protected boolean contains(final Contact contact) throws Exception
   {
      if (contact == null) throw new NullPointerException();

	  final ContactHelper helper = new ContactHelper(contact);
		
	  final SearchStringField setEntityId = new SearchStringField();
	  setEntityId.setSearchValue((contact.getEntityId() == null) ? "" : contact.getEntityId());
	  setEntityId.setOperator(SearchStringFieldOperator.is);
		
	  final ContactSearchBasic basic = new ContactSearchBasic();
	  basic.setEntityId(setEntityId);
		
	  final Record[] record = getNetSuiteProxy().search(basic);
	  final boolean result = (record == null) ? false : record.length == 1;
		
	  if (result)
	  {
		  helper.setInternalId(record[0]);
		  helper.setIsInactive(record[0]);
		  
		  logger.info("contains contact: " + contact.getEntityId() + "-" +
				      contact.getFirstName() + " " + contact.getLastName() + 
				      " with inactive status of " + String.valueOf(contact.getIsInactive()));
	  }
	  else
	  {
		  logger.info("contains contact: NOT FOUND for " + (contact.getEntityId().length() < 1 ? "NULL" : contact.getEntityId()));
	  }
	  
	  return result;
   }
	
   protected boolean contains(final Opportunity opportunity) throws Exception
   {
      if (opportunity == null) throw new NullPointerException();
		
      final OpportunityHelper helper = new OpportunityHelper(opportunity);
		
      final Record[] record = getNetSuiteProxy().search(helper.getSearchRecordByStringCustomFieldRef());
      final boolean result = (record == null) ? false : record.length == 1;
		
      if (result) 
      {
    	  helper.setInternalId(record[0]);
          logger.info("contains opportunity: " + opportunity.getInternalId());
      }
      else
          logger.info("contains opportunity: NOT FOUND for " + opportunity.getInternalId());
	  
	  return result;
   }
	
   protected boolean add(final Customer customer) throws Exception
   {
      if (customer == null) throw new NullPointerException();
		
	  final CustomerHelper helper = new CustomerHelper(customer);
	  helper.setDefaultValues();

	  final WriteResponse response = getNetSuiteProxy().add(customer); 
	  final boolean result = helper.isSuccess(response);
	  
	  if (result) 
	  {
		  helper.setInternalId(response.getBaseRef());
		  logger.info("add customer: " + customer.getInternalId() + " " + customer.getCompanyName() + 
		          (helper.message(response).length() > 0 ? " " + helper.message(response) : ""));
	  }
	  else
		  logger.info("add customer: FAILED for " + customer.getCompanyName() + 
		             (helper.message(response).length() > 0 ? " " + helper.message(response) : ""));
		
	  return result;
   }
	
   protected boolean add(final Contact contact) throws Exception
   {
      if (contact == null) throw new NullPointerException();
		
      if (contact.getEntityId().length() < 1)
    	  return false;
      
	  final ContactHelper helper = new ContactHelper(contact);
		
	  final WriteResponse response = getNetSuiteProxy().add(contact); 
	  final boolean result = helper.isSuccess(response);
	  if (result) 
	  {
		  helper.setInternalId(response.getBaseRef());
		  helper.setIsInactive(response.getBaseRef());
		  helper.setFirstName(response.getBaseRef());
		  helper.setLastName(response.getBaseRef());
		  helper.setEntityId(response.getBaseRef());
		  
		  logger.info("add contact: " + contact.getInternalId() + "-" + 
			          contact.getFirstName() + " " + contact.getLastName() + 
			          (helper.message(response).length() > 0 ? " " + helper.message(response) : ""));
	  }
	  else
		  logger.info("add contact: FAILED for " + contact.getEntityId() + 
			         (helper.message(response).length() > 0 ? " " + helper.message(response) : ""));
		
	  return result;
   }
	
   protected boolean add(final Opportunity opportunity) throws Exception
   {
      if (opportunity == null) throw new NullPointerException();
		
	  final OpportunityHelper helper = new OpportunityHelper(opportunity);
	  helper.setDefaultValues();
		
	  final WriteResponse response = getNetSuiteProxy().add(opportunity); 
	  final boolean result = helper.isSuccess(response);
	  if (result) helper.setInternalId(response.getBaseRef());
		
	  logger.info("add opportunity: FAILED for " + opportunity.getInternalId() + 
		          (helper.message(response).length() > 0 ? " " + helper.message(response) : ""));
		
	  return result;
	}
	
   protected boolean update(final Customer customer) throws Exception
   {
      if (customer == null) throw new NullPointerException();
		
	  final CustomerHelper helper = new CustomerHelper(customer);
		
	  final WriteResponse response = getNetSuiteProxy().update(customer);
	  final boolean result = helper.isSuccess(response);
	  if (result) helper.setInternalId(response.getBaseRef());
		
	  logger.info("update customer: " + customer.getInternalId() + " " + customer.getInternalId() + 
		         (helper.message(response).length() > 0 ? " " + helper.message(response) : ""));
		
	  return result;
   }
	
   protected boolean update(final Contact contact) throws Exception
   {
      if (contact == null) throw new NullPointerException();
		
	  final ContactHelper helper = new ContactHelper(contact);
		
	  final WriteResponse response = getNetSuiteProxy().update(contact);
	  final boolean result = helper.isSuccess(response);
	  if (result) helper.setInternalId(response.getBaseRef());

	  logger.info("update contact: " + contact.getInternalId() +  
			      contact.getFirstName() + " " + contact.getLastName() + 
			      (helper.message(response).length() > 0 ? " " + helper.message(response) : ""));
		
	  return result;
   }
	
   protected boolean update(final Opportunity opportunity) throws Exception
   {
      if (opportunity == null) throw new NullPointerException();
		
	  final OpportunityHelper helper = new OpportunityHelper(opportunity);
		
	  final WriteResponse response = getNetSuiteProxy().update(opportunity);
	  final boolean result = helper.isSuccess(response);
	  if (result) 
	  {
		  helper.setInternalId(response.getBaseRef());
		  logger.info("update opportunity: " + opportunity.getInternalId() + 
			      (helper.message(response).length() > 0 ? " " + helper.message(response) : ""));
	  }
	  else
		  logger.info("update opportunity: FAILED for " + opportunity.getInternalId() + 
			      (helper.message(response).length() > 0 ? " " + helper.message(response) : ""));

	  return result;
   }
	
   protected NetSuiteProxy getNetSuiteProxy() throws Exception
   {
      final NetSuiteProxy service = new NetSuiteProxy();
	  final NetSuiteProxyHelper helper = new NetSuiteProxyHelper(service);
	  helper.setClearHeaders();
	  helper.setRequestLevelCredentials(rb.getString("NetSuiteAccount"), 
                                        rb.getString("NetSuiteEmail"), 
                                        rb.getString("NetSuitePassword"));
	  helper.setPreferences();
	  return service;
   }
	
   public void calculateSimpleRoute(final Opportunity opportunity)
   {
      try
	  {
	     OpportunityHelper helper = new OpportunityHelper(opportunity);
		 String startLatitude = helper.getStringCustomFieldRef(rb.getString("NetSuiteOpportunityLATITUDE_FLD")).getValue();
		 String startLongitude = helper.getStringCustomFieldRef(rb.getString("NetSuiteOpportunityLONGITUDE_FLD")).getValue();
		 final MappointHelper mappointHelper = new MappointHelper();
		 
		 if (!isDouble(startLatitude) || !isDouble(startLongitude)) 
	        return;
			
		 if (Double.parseDouble(startLatitude)== 0 || Double.parseDouble(startLongitude) == 0) 
			 return;
			
		 for (HashMap<String, Object> facility : facilityLocations)
		 {
		    final String internalId = "custbody" + (String) facility.get("custrecordname");
			final Double value = mappointHelper.calculateSimpleRoute(Double.parseDouble(startLatitude),
					                                                 Double.parseDouble(startLongitude), 
						                                             Double.parseDouble((String)facility.get("custrecordlatitude")), 
						                                             Double.parseDouble((String)facility.get("custrecordlongitude")));
			final StringCustomFieldRef field = new StringCustomFieldRef(internalId, String.valueOf(value));
			helper.addCustomFieldRef(field);
         }
      }
	  catch (final Exception e)
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
   
   public void generatePermitURL(final Opportunity opportunity)
   {
      try
	  {
	     OpportunityHelper helper = new OpportunityHelper(opportunity);
		 String url = null;
		
		 if (helper.getStringCustomFieldRef(rb.getString("NetSuiteOpportunityAPI_FLD")).getValue().length() < 10) 
			 return;
		 if (helper.getStringCustomFieldRef(rb.getString("NetSuiteOpportunitySTATE_NAME_FLD")).getValue().equals("NM"))
			url = rb.getString("NetSuiteOpportunityNM_PERMIT_URL") + helper.getStringCustomFieldRef(rb.getString("NetSuiteOpportunityAPI_FLD")).getValue().substring(0,10) + "0000";
		 if (helper.getStringCustomFieldRef(rb.getString("NetSuiteOpportunitySTATE_NAME_FLD")).getValue().equals("TX"))
		    url = rb.getString("NetSuiteOpportunityTX_PERMIT_URL") + 
		          "?statusNo=" + helper.getStringCustomFieldRef(rb.getString("NetSuiteOpportunityPERMIT_NO_FLD")).getValue() +
				  "&countyCode=" + helper.getStringCustomFieldRef(rb.getString("NetSuiteOpportunityAPI_FLD")).getValue().substring(2, 5) +
				  "&apiSeqNo=" + helper.getStringCustomFieldRef(rb.getString("NetSuiteOpportunityAPI_FLD")).getValue().substring(5, 10);
			
	     if (url == null) 
	    	 return;
			
	     final StringCustomFieldRef field = new StringCustomFieldRef(rb.getString("NetSuiteOpportunityPERMITURL_FLD"), url);
	     helper.addCustomFieldRef(field);
      }
	  catch (final Exception e)
	  {
	     logger.error(e);
	  }
   }
	
   public void generateWellMap(final Opportunity opportunity)
   {
      try
      {
	     OpportunityHelper helper = new OpportunityHelper(opportunity);
		 String api = helper.getStringCustomFieldRef(rb.getString("NetSuiteOpportunityAPI_FLD")).getValue();
	     String permno = helper.getStringCustomFieldRef(rb.getString("NetSuiteOpportunityPERMIT_NO_FLD")).getValue();
	     String host = rb.getString("NetSuiteOpportunityWELLMAP_URL");
		 String URL = host + "/wellMap.jsp?api=" + api + "&permno=" + permno;
			
		 final StringCustomFieldRef field = new StringCustomFieldRef(rb.getString("NetSuiteOpportunityWELLMAP_FLD"), URL);
		 helper.addCustomFieldRef(field);
      }
	  catch (final Exception e)
	  {
	     logger.error(e);
	  }
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
}