package com.loviraj.fingerprintsample.auth;

/**
 * Created by muthukrishnan on 18/11/16.
 */

public interface FPStatusListener {
    void fpAuthSuccess();

    void fpAuthFailed();

    /**
     * If there is any problem in authenticate using fingerprint scanner.
     * it'll be called
     * Like if you exceed more number of attempts.
     * @param error error message from the fingerprint auth.
     */
    void fpAuthError(String error);
}
