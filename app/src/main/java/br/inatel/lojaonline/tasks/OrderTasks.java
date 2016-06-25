package br.inatel.lojaonline.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import br.inatel.lojaonline.models.Order;
import br.inatel.lojaonline.util.WSUtil;
import br.inatel.lojaonline.webservice.WebServiceClient;
import br.inatel.lojaonline.webservice.WebServiceResponse;

/**
 * Created by bccre on 22/06/2016.
 */
public class OrderTasks {
    private static final String GET_ORDERS = "/api/Orders";
    private static final String GET_ORDERS_BY_ID = "/api/Orders";
    private OrderEvents orderEvents;
    private Context context;
    private String baseAddress;

    public OrderTasks(Context context, OrderEvents orderEvents) {
        String host;
        int port;
        this.context = context;
        this.orderEvents = orderEvents;
        baseAddress = WSUtil.getHostAddress(context);
    }
    public void getOrders() {
        new AsyncTask<Void, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Void... params) {
                return WebServiceClient.get(context,
                        baseAddress + GET_ORDERS);
            }
            @Override
            protected void onPostExecute(
                    WebServiceResponse webServiceResponse) {
                Log.i("RESULT", ""+webServiceResponse.getResponseCode());
                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        List<Order> orders = gson.fromJson(
                                webServiceResponse.getResultMessage(),
                                new TypeToken<List<Order>>() {
                                }.getType());
                        orderEvents.getOrdersFinished(orders);
                    } catch (Exception e) {
                        orderEvents.getOrdersFailed(webServiceResponse);
                    }
                } else {
                    orderEvents.getOrdersFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }
    public void getOrderById(int id) {
        new AsyncTask<Integer, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Integer... id) {
                return WebServiceClient.get(context,
                        baseAddress + GET_ORDERS_BY_ID + "/" +
                                Integer.toString(id[0]));
            }
            @Override
            protected void onPostExecute(
                    WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        Order order = gson.fromJson(
                                webServiceResponse.getResultMessage(),
                                Order.class);
                        orderEvents.getOrderByIdFinished(order);
                    } catch (Exception e) {
                        orderEvents.getOrderByIdFailed(webServiceResponse);
                    }
                } else {
                    orderEvents.getOrderByIdFailed(webServiceResponse);
                }
            }
        }.execute(id, null, null);
    }
}