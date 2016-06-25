package br.inatel.lojaonline.tasks;

import android.content.Context;
import android.os.AsyncTask;

import br.inatel.lojaonline.interfaces.LoginInterface;
import br.inatel.lojaonline.webservice.WebServiceClient;
import br.inatel.lojaonline.webservice.WebServiceResponse;

/**
 * Created by Seba on 25/06/2016.
 */
public class LoginTask extends AsyncTask<Void, Integer, WebServiceResponse> {

    private LoginInterface mLoginInterface;
    private Context mContext;

    public LoginTask(LoginInterface loginInterface, Context context){
        this.mLoginInterface = loginInterface;
        this.mContext = context;

    }
    @Override
    protected WebServiceResponse doInBackground(Void... params) {
        WebServiceClient wsClient = WebServiceClient.getInstance();
        return wsClient.authenticate(mContext);
    }
    @Override
    protected void onPostExecute(
            WebServiceResponse webServiceResponse) {
        mLoginInterface.LoginResponse(webServiceResponse);
    }
}
