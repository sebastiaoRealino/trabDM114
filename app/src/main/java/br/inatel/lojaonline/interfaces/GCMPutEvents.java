package br.inatel.lojaonline.interfaces;

import java.io.IOException;

/**
 * Created by Seba on 02/07/2016.
 */
public interface GCMPutEvents {
    void gcmPushRegisterFinished(String registrationID);
    void gcmPushRegisterFailed(String error);
}
