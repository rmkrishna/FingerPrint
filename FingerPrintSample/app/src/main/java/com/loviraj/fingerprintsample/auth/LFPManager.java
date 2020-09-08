package com.loviraj.fingerprintsample.auth;

/**
 * Created by muthukrishnan on 18/11/16.
 */

public interface LFPManager {

    /**
     * To check whether the Finger print authentication is available int the device or not.
     *
     * @return
     */
    boolean isFingerPrintAvailable();

    /**
     * To check whether atleast one finger print is added in the device or not
     * @return
     */
    boolean hasFingerprintRegistered();

    /**
     * To start listening the finger print
     */
    void startListening();

    /**
     * To listening the finger print sensor
     */
    void stopListening();
}
