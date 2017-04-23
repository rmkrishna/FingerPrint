package com.loviraj.fingerprintsample.auth;

import android.support.annotation.Nullable;

/**
 * Created by muthukrishnan on 18/11/16.
 */

public interface FPStatusListener {
    void fpAuthSuccess();

    /**
     * If there is any problem in authenticate using fingerprint scanner.
     * it'll be called
     * Like if you exceed more number of attempts.
     * @param error error message from the fingerprint auth.
     */
    void fpAuthFailed(@Nullable String error);
}
