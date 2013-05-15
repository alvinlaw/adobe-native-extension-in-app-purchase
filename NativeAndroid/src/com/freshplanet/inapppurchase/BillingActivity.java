package com.freshplanet.inapppurchase;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class BillingActivity extends Activity {

	private static String TAG = "BillingActivity";

	public static String MAKE_PURCHASE = "MakePurchase";
	public static String MAKE_SUBSCRIPTION = "MakeSubscription";

	static final int RC_REQUEST = 10001;

	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			Log.d(TAG, "Purchase finished: " + result + ", purchase: "
					+ purchase);
			if (result.isFailure()) {
				Log.e(TAG, "Error purchasing: " + result);
				Extension.context.dispatchStatusEventAsync("PURCHASE_ERROR",
						"ERROR");

				finish();

				return;
			}

			JSONObject resultObject = new JSONObject();
			try {
				resultObject.put("receipt", purchase.getOriginalJson());
				resultObject.put("receiptType", "GooglePlay");

				IabHelper mHIabHelper = ExtensionContext.mHelper;
				mHIabHelper.consumeAsync(purchase,
						new IabHelper.OnConsumeFinishedListener() {

							@Override
							public void onConsumeFinished(Purchase purchase,
									IabResult result) {
								// TODO Auto-generated method stub

							}

						});
			} catch (JSONException e) {
				e.printStackTrace();
			}

			Extension.context.dispatchStatusEventAsync("PURCHASE_SUCCESSFUL",
					resultObject.toString());
			Log.d(TAG, "Purchase successful.");
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "create activity");
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		Bundle values = this.getIntent().getExtras();
		String mtype = values.getString("type");

		Log.d(TAG, "type : " + mtype);

		IabHelper mHIabHelper = ExtensionContext.mHelper;

		if (mtype == null) {
			Log.e(TAG, "unsupported type: " + mtype);
		} else if (mtype.equals(MAKE_PURCHASE)) {
			Log.d(TAG, "starting " + mtype);
			String purchaseId = values.getString("purchaseId");
			try {
				mHIabHelper.launchPurchaseFlow(this, purchaseId, RC_REQUEST,
						mPurchaseFinishedListener, null);
			} catch (IllegalStateException e) {
				finish();
				Extension.context.dispatchStatusEventAsync("PURCHASE_ERROR",
						"ERROR");
				return;
			}

		} else if (mtype.equals(MAKE_SUBSCRIPTION)) {
			Log.d(TAG, "starting " + mtype);
			String purchaseId = values.getString("purchaseId");
			try {
				mHIabHelper.launchSubscriptionPurchaseFlow(this, purchaseId,
						RC_REQUEST, mPurchaseFinishedListener);
			} catch (IllegalStateException e) {
				finish();
				Extension.context.dispatchStatusEventAsync("PURCHASE_ERROR",
						"ERROR");
				return;
			}

		} else {
			Log.e(TAG, "unsupported type: " + mtype);
		}
		Log.d(TAG, "creation done");
	}

	@Override
	protected void onStart() {
		Log.d(TAG, "start activity");
		super.onStart();
	}

	@Override
	protected void onRestart() {
		Log.d(TAG, "restart activity");
		super.onRestart();
		finish();
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "resume activity");
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "pause activity");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "stop activity");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "destroy activity");
		super.onDestroy();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + ","
				+ data);

		if (ExtensionContext.mHelper != null) {
			Log.e(TAG, "IabHelper is null");
			ExtensionContext.mHelper.handleActivityResult(requestCode,
					resultCode, data);
		} else {
			Log.e(TAG, "IabHelper is null");
		}

		finish();
	}

}
