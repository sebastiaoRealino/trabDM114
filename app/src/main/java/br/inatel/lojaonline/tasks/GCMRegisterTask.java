package br.inatel.lojaonline.tasks;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import br.inatel.lojaonline.interfaces.GCMPutEvents;
import br.inatel.lojaonline.util.WSUtil;
import br.inatel.lojaonline.webservice.WebServiceClient;
import br.inatel.lojaonline.webservice.WebServiceResponse;

/**
 * Created by Seba on 02/07/2016.
 */
public class GCMRegisterTask {

    private static final String PUT_GCM = "/api/users";

    private Context mContext;
    private String baseAddress;
    private GCMPutEvents mGCMPutEvents;

    public GCMRegisterTask(Context context, GCMPutEvents gcmPutEvents) {
        String host;
        int port;
        this.mContext = context;
        this.mGCMPutEvents = gcmPutEvents;
        baseAddress = WSUtil.getHostAddress(mContext);
    }
    public void putRegId(final String regId) {
        new AsyncTask<Integer, Void, WebServiceResponse>() {

            @Override
            protected WebServiceResponse doInBackground(Integer... id) {
                JSONObject productJson = new JSONObject();

                return WebServiceClient.getInstance().put(mContext,
                        baseAddress + "/update_gcm_reg_id"+"/{"+regId+"}", productJson.toString());
            }
            @Override
            protected void onPostExecute(
                    WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    mGCMPutEvents.gcmPushRegisterFinished(webServiceResponse.getResponseMessage());
                } else {
                    mGCMPutEvents.gcmPushRegisterFailed(webServiceResponse.getResponseMessage());
                }
            }
        }.execute();
    }
}
