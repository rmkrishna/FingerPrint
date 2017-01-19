package com.loviraj.fingerprintsample.auth;

/**
 * Created by muthukrishnan on 18/11/16.
 */

public interface LFPManager {

    /**
     * T check whether the Finger print authentication is available int the device or not.
     *
     * @return
     */
    boolean isFingerPrintAvailable();

    boolean hasFingerprintRegistered();

    void startListening();

    void stopListening();
}
