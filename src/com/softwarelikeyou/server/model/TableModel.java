package com.softwarelikeyou.server.model;

import com.softwarelikeyou.server.listener.TableModelListener;

public interface TableModel
{
   public int getRowCount();

   public int getColumnCount();

   public String getColumnName(int column);

   public Class<?> getColumnClass(int column);

   public boolean isCellEditable(int row, int column);

   public Object getValueAt(int row, int column);

   public void setValueAt(Object value, int row, int column);

   public void addTableModelListener(TableModelListener listener);

   public void removeTableModelListener(TableModelListener listener);
}
