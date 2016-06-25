package br.inatel.lojaonline.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import br.inatel.lojaonline.interfaces.LoginInterface;
import br.inatel.lojaonline.models.Order;
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
        WebServiceResponse response = wsClient.authenticate(mContext);
        return null;
    }
    @Override
    protected void onPostExecute(
            WebServiceResponse webServiceResponse) {
        mLoginInterface.LoginError("TASK COMPLETED");
    }
}
