package com.loviraj.fingerprintsample.auth;

import android.content.Context;

import com.samsung.android.sdk.pass.Spass;
import com.samsung.android.sdk.pass.SpassFingerprint;

/**
 * Created by muthukrishnan on 18/11/16.
 */

public class SamsungPassFP implements LFPManager {

    private Context mContext;
    private Spass spass;
    private SpassFingerprint spassFingerprint;
    private FPStatusListener mFpStatusListener;

    public SamsungPassFP(Context context, FPStatusListener fpStatusListener) {

        mContext = context;
        mFpStatusListener = fpStatusListener;

        try {
            spass = new Spass();
            spass.initialize(mContext);
        } catch (SecurityException e) {
            spass = null;
        } catch (Exception e) {
            spass = null;
        }
    }

    @Override
    public boolean isFingerPrintAvailable() {
        try {
            return spass != null && spass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT);
        } catch (Exception e) {
        }

        return false;
    }

    @Override
    public boolean hasFingerprintRegistered() {
        try {
            if (isFingerPrintAvailable()) {
                if (spassFingerprint == null) {
                    spassFingerprint = new SpassFingerprint(mContext);
                }
                return spassFingerprint.hasRegisteredFinger();
            }
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public void startListening() {
        if (spassFingerprint == null) {
            spassFingerprint = new SpassFingerprint(mContext);
        }
        try {
            if (!spassFingerprint.hasRegisteredFinger()) {
                mFpStatusListener.fpAuthFailed();
                return;
            }
        } catch (Throwable ignored) {
            mFpStatusListener.fpAuthFailed();
            return;
        }

        stopListening();

        try {
            spassFingerprint.startIdentify(mIdentifyListener);
        } catch (Throwable t) {
            mFpStatusListener.fpAuthFailed();
        }


    }

    @Override
    public void stopListening() {
        try {
            if (spassFingerprint != null) {
                spassFingerprint.cancelIdentify();
            }
        } catch (Throwable t) {
        }
    }

    private SpassFingerprint.IdentifyListener mIdentifyListener = new SpassFingerprint.IdentifyListener() {
        @Override
        public void onFinished(int eventStatus) {

            switch (eventStatus) {
                case SpassFingerprint.STATUS_AUTHENTIFICATION_SUCCESS:
                case SpassFingerprint.STATUS_AUTHENTIFICATION_PASSWORD_SUCCESS:
                    mFpStatusListener.fpAuthSuccess();
                    break;
                case SpassFingerprint.STATUS_OPERATION_DENIED:
                case SpassFingerprint.STATUS_USER_CANCELLED:
                case SpassFingerprint.STATUS_TIMEOUT_FAILED:
                case SpassFingerprint.STATUS_QUALITY_FAILED:
                default:
                    mFpStatusListener.fpAuthFailed();
            }
        }

        @Override
        public void onReady() {
        }

        @Override
        public void onStarted() {
        }

        @Override
        public void onCompleted() {
        }
    };
}
