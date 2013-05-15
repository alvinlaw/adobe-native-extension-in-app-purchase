package com.freshplanet.inapppurchase;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class RestoreTransactionFunction implements FREFunction {

	private static String TAG = "RestoreTransaction";
	
	
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
            
            Log.d(TAG, data);

            
            Extension.context.dispatchStatusEventAsync("PRODUCT_INFO_RECEIVED", data) ;
        }
    };

	
	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		
		Log.d(TAG, "restoring transactions");

		IabHelper mHIabHelper = ExtensionContext.mHelper;
		if (mHIabHelper == null)
		{
			Log.e(TAG, "iabHelper is not init");
			return null;
		}
		
		mHIabHelper.queryInventoryAsync(mGotInventoryListener);
		
		return null;
	}

}
