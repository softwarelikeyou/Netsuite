package com.softwarelikeyou.server.model;

import com.softwarelikeyou.server.event.TableModelEvent;
import com.softwarelikeyou.server.listener.TableModelListener;

public class Table implements TableModelListener
{
	private TableModel model;
	
	public Table()
	{
	}
	
	public Table (final TableModel model)
	{
		setModel(model);
	}
	
	protected TableModel getModel()
	{
		return (model == null) ? new SelectionTableModel() : model;
	}
	
	protected void setModel(final TableModel model)
	{
		if (model == null) throw new NullPointerException();
		this.model = model;
	}
	
	public void setCell(final int row, final int column, final Object value)
	{
		if (row < 0 || column < 0)	throw new IllegalArgumentException();
		if (value instanceof String) setCell(row, column, (String) value);
		if (value instanceof Boolean) setCell(row, column, (Boolean) value);
	}
	
	public void onTableChanged(final TableModelEvent event)
	{
		final int row = event.getFirstRow();
		final int column = event.getColumn();
		final TableModel source = (TableModel) event.getSource();
		final Object value = source.getValueAt(row, column);
		setCell(row, column, value);		
	}
}
