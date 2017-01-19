package com.loviraj.fingerprintsample.auth;

/**
 * Created by muthukrishnan on 18/11/16.
 */

public class LegacyFP implements LFPManager {

    private FPStatusListener mFpStatusListener;

    public LegacyFP(FPStatusListener listener) {
        mFpStatusListener = listener;
    }

    @Override
    public boolean isFingerPrintAvailable() {
        return false;
    }

    @Override
    public boolean hasFingerprintRegistered() {
        return false;
    }

    @Override
    public void startListening() {
        mFpStatusListener.fpAuthFailed();
    }

    @Override
    public void stopListening() {

    }
}
