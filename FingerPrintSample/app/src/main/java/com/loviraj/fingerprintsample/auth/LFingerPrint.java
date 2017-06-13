package com.loviraj.fingerprintsample.auth;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by muthukrishnan on 18/11/16.
 */

public class LFingerPrint {
    private static final String TAG = LFingerPrint.class.getSimpleName();

    private LFPManager mLFingerPrintManager;

    public LFingerPrint(Context context, FPStatusListener listener) {

        final boolean isSamsungDevice = isSamsungDevice(Build.MANUFACTURER, Build.BRAND);
        final boolean isMeiZuDevice = isMeiZuDevice(Build.MANUFACTURER);

        if (Build.VERSION.SDK_INT >= 23) { // 23 -> Build.VERSION_CODES.M
            Log.d(TAG, "LFingerPrint: " + Build.VERSION_CODES.M);
            mLFingerPrintManager = new MarshmallowFP(context, listener);

            if (!mLFingerPrintManager.isFingerPrintAvailable()) {
                if (isSamsungDevice) {
                    Log.d(TAG, "LFingerPrint: samsung");
                    mLFingerPrintManager = new SamsungPassFP(context, listener);
                } else if (isMeiZuDevice) {
                    Log.d(TAG, "LFingerPrint: MeiZuDevice");
                    mLFingerPrintManager = new MeiZuFP(context, listener);
                }
            }
        } else if (Build.VERSION.SDK_INT >= 21 && isMeiZuDevice) { // // 23 -> Build.VERSION_CODES.L
            Log.d(TAG, "LFingerPrint: MeiZuDevice");
            mLFingerPrintManager = new MeiZuFP(context, listener);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && isSamsungDevice) {
            Log.d(TAG, "LFingerPrint: samsung");
            mLFingerPrintManager = new SamsungPassFP(context, listener);
        } else {
            mLFingerPrintManager = new LegacyFP(listener);
        }
    }

    public LFPManager getManager() {
        return mLFingerPrintManager;
    }

    private boolean isSamsungDevice(String strManufacturer, String strBrand) {
        return (strBrand != null && strManufacturer != null)
                && (strBrand.compareToIgnoreCase("Samsung") == 0
                || strManufacturer.compareToIgnoreCase("Samsung") == 0);
    }

    private static boolean isMeiZuDevice(String strManufacturer) {
        return !TextUtils.isEmpty(strManufacturer) && strManufacturer.toLowerCase().contains("meizu");
    }
}
