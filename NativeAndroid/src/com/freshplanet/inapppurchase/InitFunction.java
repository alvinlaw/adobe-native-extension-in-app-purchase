package com.freshplanet.inapppurchase;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class InitFunction implements FREFunction {

    private static final String TAG = "Init";

	
	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
	
		Log.d(TAG, "v3.0");
		
		String base64EncodedPublicKey = null;
		Boolean debug = false;
		try {
			base64EncodedPublicKey = arg1[0].getAsString();
			debug = arg1[1].getAsBool();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		if (ExtensionContext.mHelper == null)
		{
			ExtensionContext.mHelper = new IabHelper(arg0.getActivity(), base64EncodedPublicKey);
		} else
		{
			ExtensionContext.mHelper.dispose();
			ExtensionContext.mHelper = new IabHelper(arg0.getActivity(), base64EncodedPublicKey);
		}

		ExtensionContext.mHelper.enableDebugLogging(debug);

		
		ExtensionContext.mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.e(TAG, "Problem setting up in-app billing: " + result);
                    return;
                }

                // Hooray, IAB is fully set up.
                Log.d(TAG, "Setup successful.");
                
                
            }
        });

		
		return null;
	
	
	}

}
