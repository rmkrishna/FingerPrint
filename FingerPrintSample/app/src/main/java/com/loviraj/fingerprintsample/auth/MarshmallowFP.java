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

    private Context mContext;

    public MarshmallowFP(Context context, FPStatusListener fpStatusListener) {
        mContext = context;

        mFingerprintManagerCompat = FingerprintManagerCompat.from(mContext);

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
            mFpStatusListener.fpAuthFailed();
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
            mFpStatusListener.fpAuthFailed();
        }
    }

    @Override
    public void onAuthenticationFailed() {
        mFpStatusListener.fpAuthFailed();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        mFpStatusListener.fpAuthFailed();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        mFpStatusListener.fpAuthSuccess();
    }
}