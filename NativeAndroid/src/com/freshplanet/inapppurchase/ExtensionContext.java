//////////////////////////////////////////////////////////////////////////////////////
//
//  Copyright 2012 Freshplanet (http://freshplanet.com | opensource@freshplanet.com)
//  
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//  
//    http://www.apache.org/licenses/LICENSE-2.0
//  
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//  
//////////////////////////////////////////////////////////////////////////////////////


package com.freshplanet.inapppurchase;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

public class ExtensionContext extends FREContext {

	private static String TAG = "InAppExtensionContext";

	public static IabHelper mHelper;
	
	public ExtensionContext() {
		Log.d(TAG, "ExtensionContext.create");
	}
	
	@Override
	public void dispose() {
		Log.d(TAG, "ExtensionContext.dispose");
		Extension.context = null;
	}

	/**
	 * Registers AS function name to Java Function Class
	 */
	@Override
	public Map<String, FREFunction> getFunctions() {
		Log.d(TAG, "ExtensionContext.getFunctions");
		Map<String, FREFunction> functionMap = new HashMap<String, FREFunction>();
		functionMap.put("getProductsInfo", new GetProductsInfoFunction());
		functionMap.put("makePurchase", new MakePurchaseFunction());
		functionMap.put("userCanMakeAPurchase", new UserCanMakeAPurchaseFunction());
		functionMap.put("removePurchaseFromQueue", new RemovePurchaseFromQueuePurchase());
		functionMap.put("userCanMakeASubscription", new UserCanMakeASubscriptionFunction());
		functionMap.put("makeSubscription", new MakeSubscriptionFunction());
		functionMap.put("restoreTransaction", new RestoreTransactionFunction());
		functionMap.put("initLib", new InitFunction());
		functionMap.put("stopLib", new StopFunction());
		return functionMap;	
	}

}
