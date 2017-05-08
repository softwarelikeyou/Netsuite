package com.softwarelikeyou.server.handler;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.softwarelikeyou.server.netsuite.HPDIFileHelper;
import com.softwarelikeyou.server.util.EmailHelper;
import com.softwarelikeyou.server.controller.Controller;
import com.softwarelikeyou.server.event.Event;
import com.softwarelikeyou.server.event.EventHandler;
import com.softwarelikeyou.server.event.Events;

public class HPDIEmailHandler extends EventHandler
{
   private final static Logger logger = Logger.getLogger(HPDIEmailHandler.class);

   protected Controller controller = null;
   
   public HPDIEmailHandler() { }

   public HPDIEmailHandler(final Controller controller)
   {
      this.controller = controller;
   }

   public void process(final Event event)
   {
      if (event == null)
      {
         logger.info("Recieved a null event");
         throw new NullPointerException();
      }

      if (!event.getType().equals(Events.onRetreiveHPDIEmail)) 
      {
         logger.info("Recieved an invalid event type of " + event.getType().toString());
         return;
      }
		
      this.init();
		
      try
      {
     	 final HPDIFileHelper fileHelper = new HPDIFileHelper();
     	 
    	 final EmailHelper emailHelper = new EmailHelper();
    	 
    	 final ArrayList<HashMap<String, Object>> files = emailHelper.checkEmail();
    	 
    	 if (files == null) 
         {
    		 logger.info("No emails to process");
    		 return;
         }
    	 
    	 for (HashMap<String, Object> file : files)
    	 {
    		 if (((String)file.get("name")).contains("Daily Permits"))
    		 {
    			 if (fileHelper.save(file))
    			    controller.process(new Event(Events.onLoadHPDIPermits, this, file));
    		 }
    			 
    		 if (((String)file.get("name")).contains("Rig Location"))	 
    	     {
    	    	 if (fileHelper.save(file))
    	    	    controller.process(new Event(Events.onLoadHPDIRigLocations,this, file));
    	     }
    	 }
      }
      catch (NullPointerException e)
      {
	     logger.error(e);
	  } 
	  catch (Exception e)
	  {
	     logger.error(e);
	  } 
   }
	
   public void init()
   {
	   ;
   }
}
