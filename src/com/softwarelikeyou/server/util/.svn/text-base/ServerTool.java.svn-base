package com.softwarelikeyou.server.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.softwarelikeyou.server.model.DefaultTableModel;
import com.softwarelikeyou.server.util.ClientTool;

public final class ServerTool extends ClientTool
{
	private static Logger logger = Logger.getLogger(ServerTool.class);
	
	public static final String readFile(final String path)
	{
		BufferedReader bufReader = null;
		FileReader fileReader = null;
		try
		{
			final File file = new File(path);
			fileReader = new FileReader(file);
			bufReader = new BufferedReader(fileReader);
			char[] result = new char[(int)file.length()];
			bufReader.read(result);
			return String.valueOf(result);
		}
		catch (final Exception e)
		{
			throw new RuntimeException(e.getClass().getName());
		}
		finally
		{
			close(bufReader);
			close(fileReader);
		}
	}
	
	public static final DefaultTableModel create(final String csv) throws Exception
	{
		if (csv == null) throw new NullPointerException();
		final CSVReader reader = new CSVReader( new StringReader(csv));
		final List<?> data = reader.readAll();
		final DefaultTableModel result = new DefaultTableModel(0);
		for (int row = 0 ; row < data.size() ; row++)
		{
			final String[] line = (String[])data.get(row);
			for (int column = 0 ; column < line.length ; column++) result.setValueAt((String)line[column], row, column);
		}
		return result;
	}
	
	public static final String[] ls(final String path)
	{
		if (path == null) throw new NullPointerException();
		final File dir = new File(path);
		return dir.list();
	}
	
	private static final void close(final FileReader reader)
	{
		try
		{
			if (reader != null) reader.close();
		} 
		catch (final Exception e) 
		{  
			logger.error(e);
		}
	}
	
	private static final void close(final BufferedReader reader)
	{
		try
		{
			if (reader != null) reader.close();
		} 
		catch (final Exception e) 
		{ 
			logger.error(e); 
		}
	}
	
	private ServerTool()
	{
		
	}
}
