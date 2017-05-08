package com.softwarelikeyou.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import com.softwarelikeyou.server.event.TableModelEvent;
import com.softwarelikeyou.server.event.TableModelEvent.TableModelEvents;
import com.softwarelikeyou.server.listener.TableModelListener;
import com.softwarelikeyou.server.util.ClientTool;

public abstract class AbstractTableModel implements TableModel, Serializable
{

	private static final long serialVersionUID = 1L;

	protected ArrayList<TableModelListener> listeners = new ArrayList<TableModelListener>();

	protected Vector<Vector<Object>> data;

	protected void insertElementAt(final Vector<Object> vector, final int index)
	{
		if (vector == null)
			throw new NullPointerException();
		getData().insertElementAt(vector, index);
		fireTableRowsInserted(index, index);
	}

	protected void setElementAt(final Vector<Object> vector, final int index)
	{
		if (vector == null)
			throw new NullPointerException();
		if (index >= getData().size())
			getData().setSize(index + 1);
		getData().setElementAt(vector, index);
	}

	protected void setElementAt(final Vector<Object> vector,
			final Object value, final int index)
	{
		if (vector == null || value == null)
			throw new NullPointerException();
		if (index >= vector.size())
			vector.setSize(index + 1);
		vector.setElementAt(value, index);
	}

	protected Vector<Object> elementAt(final int index)
	{
		if (index >= getData().size())
			getData().setSize(index + 1);
		if ((Vector<Object>) getData().elementAt(index) == null)
			getData().setElementAt(new Vector<Object>(), index);
		return (Vector<Object>) getData().elementAt(index);
	}

	protected Object elementAt(final Vector<Object> vector, final int index)
	{
		if (index >= vector.size())
			vector.setSize(index + 1);
		if ((Object) vector.elementAt(index) == null)
			vector.setElementAt(new Object(), index);
		return (Object) vector.elementAt(index);
	}

	public void setValueAt(final Object value, final int row, final int column)
	{
		if (value == null)
			throw new NullPointerException();
		if (row < 0 || column < 0) throw new IllegalArgumentException();
		final Vector<Object> vector = (Vector<Object>) elementAt(row);
		if (elementAt(vector, column) == null) throw new NullPointerException();
		setElementAt(vector, value, column);
		fireTableCellUpdated(row, column);
	}
	
	public Object getValueAt(final int row, final int column)
	{
		if (row < 0 || column < 0) throw new IllegalArgumentException();
		return elementAt(elementAt(row), column);
	}
	
	public Class<?> getColumnClass(final int column)
	{
		if (column < 0) throw new IllegalArgumentException();
		return Object.class;
	}
	
	public int getRowCount()
	{
		return getData().size();
	}
	
	public int getColumnCount()
	{
		int count = 0;
		final int max = getRowCount();
		for (int i = 0; i < max; i++)
		{
			final int compare = getData().get(i).size();
			if (compare > count)
				count = compare;
		}
		return count;
	}
	
	public void setData(final Vector<Vector<Object>> data)
	{
		if (data == null)
			throw new NullPointerException();
		this.data = data;
	}
	
	public void setData(final Object[][] data)
	{
		if (data == null)
			throw new NullPointerException();
		setData(ClientTool.toVector(data));
	}
	
	public void addData(final Vector<Vector<Object>> data)
	{
		if (data == null)
			throw new NullPointerException();
		getData().addAll(data);
	}
	
	public Vector<Vector<Object>> getData()
	{
		return data = (data != null) ? data : new Vector<Vector<Object>>();
	}
	
	public String getColumnName(int column)
	{
		if (column < 0) throw new IllegalArgumentException();
		return Integer.toString(column);
	}
	
	public int find(final String heading)
	{
		if (heading == null) throw new NullPointerException();
		final int max = getColumnCount();
		for (int i = 0; i < max; i++) if (heading.contentEquals(getColumnName(i))) 	return i;
		return -1;
	}
	
	public boolean isCellEditable(final int row, final int column)
	{
		if (row < 0 || column < 0) throw new IllegalArgumentException();
		return false;
	}
	
	public void removeTableModelListener(final TableModelListener listener)
	{
		if (listener == null) throw new NullPointerException();
		listeners.remove(listener);
	}
	
	public void addTableModelListener(final TableModelListener listener)
	{
		if (listener == null) throw new NullPointerException();
		listeners.add(listener);
	}
	
	public void fireTableChanged(final TableModelEvent event)
	{
		if (event == null) throw new NullPointerException();
		for (TableModelListener listener : listeners) listener.onTableChanged(event);
	}
	
	public void fireTableCellUpdated(final int row, final int column)
	{
		fireTableChanged(new TableModelEvent(this, row, row, column));
	}
	
	private void fireTableRowsInserted(final int lower, final int upper)
	{
		fireTableChanged(new TableModelEvent(this, TableModelEvents.Structure));
	}
	
	public void fireTableStructureChanged()
	{
		fireTableChanged(new TableModelEvent(this, TableModelEvents.Structure));
	}
}
