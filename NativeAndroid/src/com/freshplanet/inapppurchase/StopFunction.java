package com.freshplanet.inapppurchase;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class StopFunction implements FREFunction {

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
	
		if (ExtensionContext.mHelper != null)
		{
			ExtensionContext.mHelper.dispose();
			ExtensionContext.mHelper = null;
		}
		return null;
		
		
		
	}

}
