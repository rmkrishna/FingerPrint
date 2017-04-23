package com.loviraj.fingerprintsample.auth;

import android.content.Context;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

/**
 * Created by muthukrishnan on 18/11/16.
 */

public class MarshmallowFP extends FingerprintManagerCompat.AuthenticationCallback implements LFPManager {

    private FingerprintManagerCompat mFingerprintManagerCompat;

    private CancellationSignal mCancellationSignal;
    private FPStatusListener mFpStatusListener;

    private boolean mSelfCancelled;

    public MarshmallowFP(Context context, FPStatusListener fpStatusListener) {
        mFingerprintManagerCompat = FingerprintManagerCompat.from(context);

        mFpStatusListener = fpStatusListener;
    }

    @Override
    public boolean isFingerPrintAvailable() {
        try {
            return mFingerprintManagerCompat.isHardwareDetected();
        } catch (SecurityException ignored) {
            ignored.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean hasFingerprintRegistered() {
        return mFingerprintManagerCompat.hasEnrolledFingerprints();
    }

    @Override
    public void startListening() {
        if (!(isFingerPrintAvailable() && hasFingerprintRegistered())) {
            mFpStatusListener.fpAuthFailed(null);
            return;
        }

        mCancellationSignal = new CancellationSignal();
        mSelfCancelled = false;

        mFingerprintManagerCompat.authenticate(null, 0, mCancellationSignal, this, null);
    }

    @Override
    public void stopListening() {
        if (mCancellationSignal != null) {
            mSelfCancelled = true;
            mCancellationSignal.cancel();
            mCancellationSignal = null;
        }
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        if (!mSelfCancelled) {
            mFpStatusListener.fpAuthFailed(errString.toString());
        }
    }

    @Override
    public void onAuthenticationFailed() {
        mFpStatusListener.fpAuthFailed(null);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        mFpStatusListener.fpAuthFailed(helpString.toString());
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        mFpStatusListener.fpAuthSuccess();
    }
}
