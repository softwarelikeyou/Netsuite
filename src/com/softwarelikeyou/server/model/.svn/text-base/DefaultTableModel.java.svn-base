package com.softwarelikeyou.server.model;


import java.io.Serializable;
import java.util.Vector;

import com.softwarelikeyou.server.listener.TableModelListener;
import com.softwarelikeyou.server.util.ClientTool;

public class DefaultTableModel extends AbstractTableModel implements TableModel, Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static final int HEADER_ID = 0;
	
	public DefaultTableModel()
	{
		this(1);
	}
	
	public DefaultTableModel(final int column)
	{
		this(new Object[column], new Object[0][column]);
	}
	
	public DefaultTableModel(final Object[] header, final Object[][] data)
	{
		this(ClientTool.toVector(header), ClientTool.toVector(data));
	}
	
	public DefaultTableModel(final Vector<Object> header, final Vector<Vector<Object>> data)
	{
		setHeader(header);
		addData(data);
	}
	
	public Vector<Object> getHeader()
	{
		return elementAt(HEADER_ID);
	}
	
	public void setHeader(final Vector<Object> header)
	{
		if (header == null) throw new NullPointerException();
		if (getHeader() != null) setElementAt(header, HEADER_ID);
	}
	
	public void addHeader(final Vector<Object> header)
	{
		if (header == null) throw new NullPointerException();
		insertElementAt(header, HEADER_ID);
	}
	
	public Class<?> getColumnClass(final int column)
	{
		if (column < 0 || column > getColumnCount()) throw new IllegalArgumentException();
		return elementAt(getHeader(), column).getClass();
	}
	
	public String getColumnName(final int column)
	{
		if (column < 0 || column > getColumnCount()) throw new IllegalArgumentException();
		return (getColumnClass(column) == String.class) ? (String) elementAt(getHeader(), column) : "";
	}
	
	public int getColumnCount()
	{
		return getHeader().size();
	}

	@Override
	public void addTableModelListener(TableModelListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener listener) {
		// TODO Auto-generated method stub
		
	}
}
