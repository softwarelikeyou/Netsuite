package com.softwarelikeyou.server.model;

import java.util.ResourceBundle;

import com.netsuite.webservices.lists.relationships.Contact;
import com.netsuite.webservices.lists.relationships.ContactAddressbook;
import com.netsuite.webservices.lists.relationships.Customer;
import com.netsuite.webservices.lists.relationships.CustomerAddressbook;
import com.netsuite.webservices.transactions.sales.Opportunity;
import com.softwarelikeyou.server.listener.TableModelListener;
import com.softwarelikeyou.server.netsuite.ContactHelper;
import com.softwarelikeyou.server.netsuite.CustomerHelper;
import com.softwarelikeyou.server.netsuite.OpportunityHelper;
import com.softwarelikeyou.server.util.Helper;

public class OpportunityTableModelFilter extends Helper implements TableModel
{
	private static final long serialVersionUID = 1L;
	
	private final String[] header = new String[] { "CUSTOMER", "CONTACT", "OPPORTUNITY" };
	
	private DefaultTableModel table;
	
	private ResourceBundle rb;
	
	public OpportunityTableModelFilter(final DefaultTableModel table)
	{
		setTable(table);
		rb = ResourceBundle.getBundle(getMode());
	}
	
	public Object getValueAt(final int row, final int column)
	{
		if (row < 0 || column < 0) throw new IllegalArgumentException();
		Object result = null;
		if (row == 0) result = getColumnName(column);
		else
		{
			switch (column)
			{
				case 0:result = (Object) getCustomer(row);break;	
				case 1:result = (Object) getContact(row);break;
				case 2:result = (Object) getOpportunity(row);break;
				default: result = new Object();break;
			}
		}
		return result;
	}
	
	public void setValueAt(final Object value, final int row, final int column)
	{
		table.setValueAt(value, row, column);
	}
	
	public int getColumnCount()
	{
		return header.length;
	}
	
	public String getColumnName(final int column)
	{
		if (column < 0 || column > header.length - 1) throw new IllegalArgumentException();
		return header [column];
	}
		
	public Class<?> getColumnClass(final int column)
	{
		return table.getColumnClass(column);
	}
	
	public int getRowCount()
	{
		return table.getRowCount();
	}
	
	public boolean isCellEditable(final int row, final int column)
	{
		return table.isCellEditable(row, column);
	}
	
	public void removeTableModelListener(final TableModelListener listener)
	{
		table.removeTableModelListener(listener);
	}
	
	public void addTableModelListener(final TableModelListener listener)
	{
		table.addTableModelListener(listener);		
	}
	
	protected int find(final String heading)
	{
		return table.find(heading);
	}
	
	protected void setTable(final DefaultTableModel table)
	{
		if (table == null) throw new NullPointerException();
		this.table = table;
	}
	
	protected Customer getCustomer(final int row)
	{
		final Customer result = new Customer();
		result.setPhone(getValueAt(row, "PHONE"));
		
		final CustomerHelper helper = new CustomerHelper(result);
		helper.setCompanyName(getValueAt(row, "CURR_OPER_NAME"));
		helper.setAddressbook(getCustomerAddressbook(row));
		
		helper.setDefaultValues();
		return helper.get();
	}
	
	protected Contact getContact(final int row)
	{
		final Contact result = new Contact();
		result.setEntityId(getValueAt(row, "OPER_CONT"));
		result.setPhone(getValueAt(row, "PHONE"));

		final ContactHelper helper = new ContactHelper(result);
		helper.setAddressbook(getContactAddressbook(row));
		
		helper.setDefaultValues();
		return result;
	}
	
	protected Opportunity getOpportunity(final int row)
	{
		final Opportunity result = new Opportunity();
		result.setTitle(getValueAt(row, "CURR_OPER_NAME"));
		final OpportunityHelper helper = new OpportunityHelper(result);
		helper.addCustomFieldRef(helper.create(rb.getString("NetSuiteOpportunityAPI_FLD"), getValueAt(row, "API_NO")));
		helper.addCustomFieldRef(helper.create(rb.getString("NetSuiteOpportunityPERMIT_NO_FLD"), getValueAt(row, "PERMIT_NO")));
		helper.addCustomFieldRef(helper.create(rb.getString("NetSuiteOpportunityCOUNTY_NAME_FLD"), getValueAt(row, "COUNTY")));
		helper.addCustomFieldRef(helper.create(rb.getString("NetSuiteOpportunityLEASE_NAME_FLD"), getValueAt(row, "LEASE_NAME")));
		helper.addCustomFieldRef(helper.create(rb.getString("NetSuiteOpportunityWELL_NUMBER_FLD"), getValueAt(row, "WELL_NO")));
		helper.addCustomFieldRef(helper.create(rb.getString("NetSuiteOpportunityWELL_DEPTH_FLD"), getValueAt(row, "TOTAL_DEPTH")));
		helper.addCustomFieldRef(helper.create(rb.getString("NetSuiteOpportunityCREATED_FLD"), getValueAt(row, "DATE_ENTERED")));
		helper.addCustomFieldRef(helper.create(rb.getString("NetSuiteOpportunityLONGITUDE_FLD"), getValueAt(row, "LONGITUDE")));
		helper.addCustomFieldRef(helper.create(rb.getString("NetSuiteOpportunityLATITUDE_FLD"), getValueAt(row, "LATITUDE")));
		helper.addCustomFieldRef(helper.create(rb.getString("NetSuiteOpportunitySTATE_NAME_FLD"), getValueAt(row, "STATE")));
		helper.addCustomFieldRef(helper.create(rb.getString("NetSuiteOpportunityWELL_TYPE_NAME_FLD"), getValueAt(row, "PDEN_TYPE")));
		helper.addCustomFieldRef(helper.create(rb.getString("NetSuiteOpportunityHDV_FLD"), getValueAt(row, "DRILL_TYPE")));
		
		helper.setDefaultValues();
		return helper.get();
	}
	
	protected ContactAddressbook[] getContactAddressbook(final int row)
	{
		final ContactAddressbook[] result = new ContactAddressbook[1];
		result[0] = new ContactAddressbook();
		result[0].setLabel("Opportunity");
		result[0].setPhone(getValueAt(row, "PHONE"));
		result[0].setAddressee(getValueAt(row, "CURR_OPER_NAME"));
		result[0].setAddr1(getValueAt(row, "OPER_ADDR_1"));
		result[0].setAddr2(getValueAt(row, "OPER_ADDR_2"));
		result[0].setCity(getValueAt(row, "OPER_CITY"));
		result[0].setState(getValueAt(row, "OPER_STATE"));
		result[0].setZip(getValueAt(row, "ZIP"));
		return result;
	}
	
	protected CustomerAddressbook[] getCustomerAddressbook(final int row)
	{
		final CustomerAddressbook[] result = new CustomerAddressbook[1];
		result[0] = new CustomerAddressbook();
		result[0].setLabel("Opportunity");
		result[0].setPhone(getValueAt(row, "PHONE"));
		result[0].setAddressee(getValueAt(row, "CURR_OPER_NAME"));
		result[0].setAddr1(getValueAt(row, "OPER_ADDR_1"));
		result[0].setAddr2(getValueAt(row, "OPER_ADDR_2"));
		result[0].setCity(getValueAt(row, "OPER_CITY"));
		result[0].setState(getValueAt(row, "OPER_STATE"));
		result[0].setZip(getValueAt(row, "ZIP"));
		return result;
	}
	
	protected String getValueAt(final int row, final String header)
	{
		final int index =  find(header);
		return (index == -1) ? "" : (String) table.getValueAt(row, index);
	}
	
	protected String index(final int row)
	{
		return getValueAt(row, "API_NO").concat(getValueAt(row, "PERMIT_NO"));
	}
}
