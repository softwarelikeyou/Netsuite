package com.softwarelikeyou.server.model;

import java.io.Serializable;
import java.util.Vector;

import com.softwarelikeyou.server.util.ClientTool;

public class SelectionTableModel extends AbstractTableModel implements TableModel, Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static final int HEADER_ID = 0;
	
	public SelectionTableModel()
	{
		this(1);
	}
	
	public SelectionTableModel(final int column)
	{
		this(new Object[column], new Object[0][column]);
	}
	
	public SelectionTableModel(final Object[] header, final Object[][] data)
	{
		this(ClientTool.toVector(header), ClientTool.toVector(data));
	}
	
	public SelectionTableModel(final Vector<Object> header, final Vector<Vector<Object>> data)
	{
		setHeader(header);
		addData(data);
	}
	
	public void setHeader(final Vector<Object> header)
	{
		if (header == null) throw new NullPointerException();
		if (getHeader() != null) setElementAt(header, HEADER_ID);
	}
	
	public Vector<Object> getHeader()
	{
		return elementAt(HEADER_ID);
	}
	
	public void setValueAt(final Object value, final int row, final int column)
	{
		if (value == null) throw new NullPointerException();
		if (row < 0 || column < 0) throw new IllegalArgumentException();
		if (row == 0) 
		{
			// setting header value to a boolean value will set all value for the column to the value of the header
			if (value instanceof Boolean) 
				for (int i = 1 ; i < this.getRowCount() ; i++)
					if (getValueAt(i, column) instanceof Boolean) super.setValueAt(value, i, column);
		}
		super.setValueAt(value, row, column);
	}
}
