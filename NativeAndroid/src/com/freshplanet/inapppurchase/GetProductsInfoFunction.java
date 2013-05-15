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

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.adobe.fre.FREArray;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class GetProductsInfoFunction implements FREFunction {

	private static String TAG = "ProductInfo";
	
	
    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");
            if (result.isFailure()) {
                Log.e(TAG, "Failed to query inventory: " + result);
                Extension.context.dispatchStatusEventAsync("PRODUCT_INFO_ERROR", "YES");
                return;
            }
            Log.d(TAG, "Query inventory was successful.");
            String data = "";
            if (inventory != null)
            {
            	data = inventory.toString();
            }
            Extension.context.dispatchStatusEventAsync("PRODUCT_INFO_RECEIVED", data) ;
        }
    };

	
	
	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		
		FREArray skusArray = (FREArray) arg1[0];
		long arrayLength;
		try {
			arrayLength = skusArray.getLength();
			Log.d(TAG, "get array length" +Long.toString(arrayLength));

		} catch (FREInvalidObjectException e1) {
			Log.d(TAG, "invalid object exception");

			e1.printStackTrace();
			arrayLength = 0;
		} catch (FREWrongThreadException e1) {
			Log.d(TAG, "wrong thread exception");
			e1.printStackTrace();
			arrayLength = 0;
		}
		
		Log.d(TAG, "Create String[]");

		List<String> skusName = new ArrayList<String>();
		for (int i = 0; i < arrayLength; i++)
		{
			Log.d(TAG, "for i - "+Integer.toString(i));
			try
			{
				skusName.add(skusArray.getObjectAt((long) i).getAsString());
				Log.d(TAG, "for sku i - "+skusName.get(i));
			} catch (IllegalStateException e) {
				Log.d(TAG, "Illegal State Exception");
				e.printStackTrace();
			} catch (FRETypeMismatchException e) {
				Log.d(TAG, "Type Mismatch Exception ");
				e.printStackTrace();
			} catch (FREInvalidObjectException e) {
				Log.d(TAG, "Invalie Object Exception ");
				e.printStackTrace();
			} catch (FREWrongThreadException e) {
				Log.d(TAG, "Wrong Thread Exception ");
				e.printStackTrace();
			}
		}
		
		
		FREArray skusSubsArray = (FREArray) arg1[1];
		try {
			arrayLength = skusSubsArray.getLength();
			Log.d(TAG, "get array length" +Long.toString(arrayLength));

		} catch (FREInvalidObjectException e1) {
			Log.d(TAG, "invalid object exception");

			e1.printStackTrace();
			arrayLength = 0;
		} catch (FREWrongThreadException e1) {
			Log.d(TAG, "wrong thread exception");
			e1.printStackTrace();
			arrayLength = 0;
		}
		
		Log.d(TAG, "Create String[]");

		List<String> skusSubsName = new ArrayList<String>();
		for (int i = 0; i < arrayLength; i++)
		{
			Log.d(TAG, "for i - "+Integer.toString(i));
			try
			{
				skusSubsName.add(skusSubsArray.getObjectAt((long) i).getAsString());
				Log.d(TAG, "for sub suk i - "+skusName.get(i));
			} catch (IllegalStateException e) {
				Log.d(TAG, "Illegal State Exception");
				e.printStackTrace();
			} catch (FRETypeMismatchException e) {
				Log.d(TAG, "Type Mismatch Exception ");
				e.printStackTrace();
			} catch (FREInvalidObjectException e) {
				Log.d(TAG, "Invalie Object Exception ");
				e.printStackTrace();
			} catch (FREWrongThreadException e) {
				Log.d(TAG, "Wrong Thread Exception ");
				e.printStackTrace();
			}
		}
		
		IabHelper mHIabHelper = ExtensionContext.mHelper;
		if (mHIabHelper == null)
		{
			Log.e(TAG, "iabHelper is not init");
			Extension.context.dispatchStatusEventAsync("PRODUCT_INFO_ERROR", "YES");
			return null;
		}
		
		
		mHIabHelper.queryInventoryAsync(true , skusName , skusSubsName, mGotInventoryListener);
		
		return null;
	}

}
