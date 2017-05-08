function testCallback ()
{	
	var response = nlapiRequestURL("http://app.softwarelikeyou.com/RigPermits/Web?handler=", null, null, responseHandler);
	function responseHandler(response)
	{
		alert("Hello");
	}
}

function createWellCallback ()
{
	var url = "http://app.softwarelikeyou.com/RigPermits/Web?handler=";
	var xmlDeclaration = "<\?xml version=\"1.0\" encoding=\"UTF-8\"\?>";
	var requestOpenTag = "<request>";
	var requestCloseTag = "</request>";
	var handlerOpenTag = "<handler>";
	var handlerCloseTag = "</handler>";
	var internalIdOpenTag = "<internalId>";
	var internalIdCloseTag = "</internalId>";
	var internalId = nlapiGetRecordId();
	
	// Change this variable
	var handler = "com.softwarelikeyou.server.handler.netsuite.CreateWellCallback";
	
	var postdata = xmlDeclaration + 
	               requestOpenTag + 
	               handlerOpenTag + handler + handlerCloseTag +
	               internalIdOpenTag + internalId + internalIdCloseTag +
	               requestCloseTag;
	
	var response = nlapiRequestURL(url+handler, postdata, null, null);
}

function deleteWellCallback ()
{
	var url = "http://app.softwarelikeyou.com/server/Web?handler=";
	var xmlDeclaration = "<\?xml version=\"1.0\" encoding=\"UTF-8\"\?>";
	var requestOpenTag = "<request>";
	var requestCloseTag = "</request>";
	var handlerOpenTag = "<handler>";
	var handlerCloseTag = "</handler>";
	var internalIdOpenTag = "<internalId>";
	var internalIdCloseTag = "</internalId>";
	var internalId = nlapiGetRecordId();
	
	// Change this variable
	var handler = "com.softwarelikeyou.server.handler.netsuite.DeleteWellCallback";
	
	var postdata = xmlDeclaration + 
	               requestOpenTag + 
	               handlerOpenTag + handler + handlerCloseTag +
	               internalIdOpenTag + internalId + internalIdCloseTag +
	               requestCloseTag;
	
	var response = nlapiRequestURL(url+handler, postdata, null, null);
}

function createCustomerCallback ()
{
	var url = "http://app.softwarelikeyou.com/RigPermits/Web?handler=";
	var xmlDeclaration = "<\?xml version=\"1.0\" encoding=\"UTF-8\"\?>";
	var requestOpenTag = "<request>";
	var requestCloseTag = "</request>";
	var handlerOpenTag = "<handler>";
	var handlerCloseTag = "</handler>";
	var internalIdOpenTag = "<internalId>";
	var internalIdCloseTag = "</internalId>";
	var internalId = nlapiGetRecordId();
	
	// Change this variable
	var handler = "com.softwarelikeyou.server.handler.netsuite.CreateCustomerCallback";
	
	var postdata = xmlDeclaration + 
	               requestOpenTag + 
	               handlerOpenTag + handler + handlerCloseTag +
	               internalIdOpenTag + internalId + internalIdCloseTag +
	               requestCloseTag;
	
	var response = nlapiRequestURL(url+handler, postdata, null, null);
}

function deleteCustomerCallback ()
{
	var url = "http://app.softwarelikeyou.com/RigPermits/Web?handler=";
	var xmlDeclaration = "<\?xml version=\"1.0\" encoding=\"UTF-8\"\?>";
	var requestOpenTag = "<request>";
	var requestCloseTag = "</request>";
	var handlerOpenTag = "<handler>";
	var handlerCloseTag = "</handler>";
	var internalIdOpenTag = "<internalId>";
	var internalIdCloseTag = "</internalId>";
	var internalId = nlapiGetRecordId();
	
	// Change this variable
	var handler = "com.softwarelikeyou.server.handler.netsuite.DeleteCustomerCallback";
	
	var postdata = xmlDeclaration + 
	               requestOpenTag + 
	               handlerOpenTag + handler + handlerCloseTag +
	               internalIdOpenTag + internalId + internalIdCloseTag +
	               requestCloseTag;
	
	var response = nlapiRequestURL(url+handler, postdata, null, null);
}

function createItemCallback ()
{
	var url = "http://app.softwarelikeyou.com/RigPermits/Web?handler=";
	var xmlDeclaration = "<\?xml version=\"1.0\" encoding=\"UTF-8\"\?>";
	var requestOpenTag = "<request>";
	var requestCloseTag = "</request>";
	var handlerOpenTag = "<handler>";
	var handlerCloseTag = "</handler>";
	var internalIdOpenTag = "<internalId>";
	var internalIdCloseTag = "</internalId>";
	var internalId = nlapiGetRecordId();
	
	// Change this variable
	var handler = "com.softwarelikeyou.server.handler.netsuite.CreateItemCallback";
	
	var postdata = xmlDeclaration + 
	               requestOpenTag + 
	               handlerOpenTag + handler + handlerCloseTag +
	               internalIdOpenTag + internalId + internalIdCloseTag +
	               requestCloseTag;
	
	var response = nlapiRequestURL(url+handler, postdata, null, null);
}

function deleteItemCallback ()
{
	var url = "http://app.softwarelikeyou.com/RigPermits/Web?handler=";
	var xmlDeclaration = "<\?xml version=\"1.0\" encoding=\"UTF-8\"\?>";
	var requestOpenTag = "<request>";
	var requestCloseTag = "</request>";
	var handlerOpenTag = "<handler>";
	var handlerCloseTag = "</handler>";
	var internalIdOpenTag = "<internalId>";
	var internalIdCloseTag = "</internalId>";
	var internalId = nlapiGetRecordId();
	
	// Change this variable
	var handler = "com.softwarelikeyou.server.handler.netsuite.DeleteItemCallback";
	
	var postdata = xmlDeclaration + 
	               requestOpenTag + 
	               handlerOpenTag + handler + handlerCloseTag +
	               internalIdOpenTag + internalId + internalIdCloseTag +
	               requestCloseTag;
	
	var response = nlapiRequestURL(url+handler, postdata, null, null);
}

function createLeaseCallback ()
{
	var url = "http://app.softwarelikeyou.com/RigPermits/Web?handler=";
	var xmlDeclaration = "<\?xml version=\"1.0\" encoding=\"UTF-8\"\?>";
	var requestOpenTag = "<request>";
	var requestCloseTag = "</request>";
	var handlerOpenTag = "<handler>";
	var handlerCloseTag = "</handler>";
	var internalIdOpenTag = "<internalId>";
	var internalIdCloseTag = "</internalId>";
	var internalId = nlapiGetRecordId();
	
	// Change this variable
	var handler = "com.softwarelikeyou.server.handler.netsuite.CreateLeaseCallback";
	
	var postdata = xmlDeclaration + 
	               requestOpenTag + 
	               handlerOpenTag + handler + handlerCloseTag +
	               internalIdOpenTag + internalId + internalIdCloseTag +
	               requestCloseTag;
	
	var response = nlapiRequestURL(url+handler, postdata, null, null);
}

function deleteLeaseCallback ()
{
	var url = "http://app.softwarelikeyou.com/RigPermits/Web?handler=";
	var xmlDeclaration = "<\?xml version=\"1.0\" encoding=\"UTF-8\"\?>";
	var requestOpenTag = "<request>";
	var requestCloseTag = "</request>";
	var handlerOpenTag = "<handler>";
	var handlerCloseTag = "</handler>";
	var internalIdOpenTag = "<internalId>";
	var internalIdCloseTag = "</internalId>";
	var internalId = nlapiGetRecordId();
	
	// Change this variable
	var handler = "com.softwarelikeyou.server.handler.netsuite.DeletehLeaseCallback";
	
	var postdata = xmlDeclaration + 
	               requestOpenTag + 
	               handlerOpenTag + handler + handlerCloseTag +
	               internalIdOpenTag + internalId + internalIdCloseTag +
	               requestCloseTag;
	
	var response = nlapiRequestURL(url+handler, postdata, null, null);
}

function createPartnerCallback ()
{
	var url = "http://app.softwarelikeyou.com/RigPermits/Web?handler=";
	var xmlDeclaration = "<\?xml version=\"1.0\" encoding=\"UTF-8\"\?>";
	var requestOpenTag = "<request>";
	var requestCloseTag = "</request>";
	var handlerOpenTag = "<handler>";
	var handlerCloseTag = "</handler>";
	var internalIdOpenTag = "<internalId>";
	var internalIdCloseTag = "</internalId>";
	var internalId = nlapiGetRecordId();
	
	// Change this variable
	var handler = "com.softwarelikeyou.server.handler.netsuite.CreatePartnerCallback";
	
	var postdata = xmlDeclaration + 
	               requestOpenTag + 
	               handlerOpenTag + handler + handlerCloseTag +
	               internalIdOpenTag + internalId + internalIdCloseTag +
	               requestCloseTag;
	
	var response = nlapiRequestURL(url+handler, postdata, null, null);
}

function deletePartnerCallback ()
{
	var url = "http://app.softwarelikeyou.com/RigPermits/Web?handler=";
	var xmlDeclaration = "<\?xml version=\"1.0\" encoding=\"UTF-8\"\?>";
	var requestOpenTag = "<request>";
	var requestCloseTag = "</request>";
	var handlerOpenTag = "<handler>";
	var handlerCloseTag = "</handler>";
	var internalIdOpenTag = "<internalId>";
	var internalIdCloseTag = "</internalId>";
	var internalId = nlapiGetRecordId();
	
	// Change this variable
	var handler = "com.softwarelikeyou.server.handler.netsuite.DeletePartnerCallback";
	
	var postdata = xmlDeclaration + 
	               requestOpenTag + 
	               handlerOpenTag + handler + handlerCloseTag +
	               internalIdOpenTag + internalId + internalIdCloseTag +
	               requestCloseTag;
	
	var response = nlapiRequestURL(url+handler, postdata, null, null);
}

function modifyOpportunityCallback()
{
	var url = "http://app.softwarelikeyou.com/RigPermits/Web?handler=";
	var xmlDeclaration = "<\?xml version=\"1.0\" encoding=\"UTF-8\"\?>";
	var requestOpenTag = "<request>";
	var requestCloseTag = "</request>";
	var handlerOpenTag = "<handler>";
	var handlerCloseTag = "</handler>";
	var API_NOOpenTag = "<API_NO>";
	var API_NOCloseag = "</API_NO>";
	var api_no = nlapigetFieldValue("custbodyhpdipermittabapi_no");
	var PERMIT_NOOpenTag = "<PERMIT_NO>";
	var PERMIT_NOCloseag = "</PERMIT_NO>";
	var permit_no = nlapigetFieldValue("custbodyhpdipermittabpermit_no"");
	var permitDisposalOpenTag = "<permitDisposal>";
	var permitDisposalCloseTag = "</permitDisposal>";
	var permitDisposal = nlapiGetFieldValue("custbodydisposal");
	var closedLoopOpenTag = "<closedLoop>";
	var closedLoopCloseTag = "</closedLoop>";
	var closedLoop = nlapiGetFieldValue("custbodyclosedloop")
	var solidsOpenTag = "<solids>";
	var solidsCloseTag = "</solids>";
	var solids = nlapiGetFieldValue("custbodysolids")
	
	var handler = "com.softwarelikeyou.server.handler.hpdi.ModifyOpportunityCallback";
	
	var postdata = xmlDeclaration + 
	               requestOpenTag + 
	               handlerOpenTag + handler + handlerCloseTag +
	               API_NOOpenTag + api_no + API_NOCloseTag +
	               PERMIT_NOOpenTag + permit_no + PERMIT_NOCloseTag +
	               permitDisposalOpenTag + permitDisposal + permitDisposalCloseTag +
	               closedLoopOpenTag + closedLoop + closedLoopCloseTag +
	               solidsOpenTag + solids + solidsCloseTag +
	               requestCloseTag;
	
	var response = nlapiRequestURL(url+handler, postdata, null, responseHandler);
	
	function responseHandler(response)
	{
		alert("Hello");
	}
}