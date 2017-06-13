package com.loviraj.fingerprintsample.auth;

import android.content.Context;

import com.fingerprints.service.FingerprintManager;

/**
 * Created by muthukrishnan on 13/06/17.
 */

public class MeiZuFP implements LFPManager {

    private Context mContext;

    private FPStatusListener mFpStatusListener;

    private FingerprintManager mFingerprintManager;

    private FingerprintManager.IdentifyCallback mIdentifyCallback;

    public MeiZuFP(Context context, FPStatusListener fpStatusListener) {

        mContext = context;
        mFpStatusListener = fpStatusListener;

        try {
            mFingerprintManager = FingerprintManager.open();
        } catch (Exception e) {

        }

    }

    @Override
    public boolean isFingerPrintAvailable() {
        if (mFingerprintManager == null) {
            return false;
        }

        return true;
    }

    @Override
    public boolean hasFingerprintRegistered() {

        if (!isFingerPrintAvailable() || mFingerprintManager.getIds() == null) {
            return false;
        }

        return true;
    }

    @Override
    public void startListening() {
        if (hasFingerprintRegistered() == false) {
            return;
        }

        if (mIdentifyCallback == null) {
            mIdentifyCallback = createIdentifyCallback();
        }

        mFingerprintManager.startIdentify(mIdentifyCallback, mFingerprintManager.getIds());
    }

    @Override
    public void stopListening() {
        release();
    }

    private void release() {
        if (mFingerprintManager != null) {
            mFingerprintManager.release();
        }
    }

    private FingerprintManager.IdentifyCallback createIdentifyCallback() {
        return new FingerprintManager.IdentifyCallback() {
            @Override
            public void onIdentified(int i, boolean b) {
                mFpStatusListener.fpAuthSuccess();

                release();
            }

            @Override
            public void onNoMatch() {
                mFpStatusListener.fpAuthFailed();

                release();
            }
        };
    }

}
