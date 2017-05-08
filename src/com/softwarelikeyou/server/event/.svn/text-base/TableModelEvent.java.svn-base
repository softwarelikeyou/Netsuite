package com.softwarelikeyou.server.event;


import java.io.Serializable;
import java.util.EventObject;

import com.softwarelikeyou.server.model.TableModel;

public class TableModelEvent extends EventObject implements Serializable
{

	private static final long serialVersionUID = 1L;

	public enum TableModelEvents
	{
		Insert,
		Update,
		Delete,
		Structure
	}
	
	protected TableModelEvents type;
	
	protected int firstRow;
	
	protected int lastRow;
	
	protected int column;
	
	public TableModelEvent(final TableModel source, final TableModelEvents type)
	{
		this(source, 0, 0, 0, type);
	}
	
	public TableModelEvent(final TableModel source, final int firstRow, final int lastRow, final int column)
	{
		this(source, firstRow, lastRow, column, TableModelEvents.Update);
	}
	
	public TableModelEvent(final TableModel source, final int firstRow, final int lastRow, final int column, final TableModelEvents type)
	{
		super(source);
		this.firstRow = firstRow;
		this.lastRow = lastRow;
		this.column = column;
		this.type = type;
	}

	public int getFirstRow()
	{
		return firstRow;
	}

	public int getLastRow()
	{
		return lastRow;
	}

	public int getColumn()
	{
		return column;
	}

	public TableModelEvents getType()
	{
		return type;
	}
}