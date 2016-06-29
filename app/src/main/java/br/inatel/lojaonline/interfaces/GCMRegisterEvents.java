package br.inatel.lojaonline.interfaces;

/**
 * Created by siecola on 4/17/16.
 */
import java.io.IOException;

public interface GCMRegisterEvents {

    void gcmRegisterFinished(String registrationID);

    void gcmRegisterFailed(IOException ex);

    void gcmUnregisterFinished();

    void gcmUnregisterFailed(IOException ex);
}
