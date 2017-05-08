package com.softwarelikeyou.server.util;

import java.util.Vector;

public class ClientTool
{
	
	public static final Vector<Object> toVector(final Object[] array)
	{
		if (array == null) return toVector(new Object[0]);
		final Vector<Object> vector = new Vector<Object>(array.length);
		for (int i = 0; i < array.length; i++) 	vector.addElement(array[i]);
		return vector;
	}
	
	public static final Vector<Vector<Object>> toVector(final Object[][] array)
	{
		if (array == null) return toVector(new Object[0][0]);
		final Vector<Vector<Object>>  vector = new Vector<Vector<Object>> (array.length);
		for (int i = 0; i < array.length; i++) 	vector.addElement(toVector(array[i]));
		return vector;
	}
}
