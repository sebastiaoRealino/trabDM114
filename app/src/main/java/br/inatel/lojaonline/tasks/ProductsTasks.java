package br.inatel.lojaonline.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import br.inatel.lojaonline.interfaces.ProductEvents;
import br.inatel.lojaonline.models.Order;
import br.inatel.lojaonline.models.Product;
import br.inatel.lojaonline.util.WSUtil;
import br.inatel.lojaonline.webservice.WebServiceClient;
import br.inatel.lojaonline.webservice.WebServiceResponse;

/**
 * Created by bccre on 22/06/2016.
 */
public class ProductsTasks {
    private static final String GET_PRODUCTS = "/api/Products";
    private static final String GET_ORDERS_BY_ID = "/api/Products";
    private ProductEvents productEvents;
    private Context context;
    private String baseAddress;

    public ProductsTasks(Context context, ProductEvents productEvents) {
        String host;
        int port;
        this.context = context;
        this.productEvents = productEvents;
        baseAddress = WSUtil.getHostAddress(context);
    }
    public void getProducts() {
        new AsyncTask<Void, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Void... params) {
                return WebServiceClient.getInstance().get(context,
                        baseAddress + GET_PRODUCTS);
            }
            @Override
            protected void onPostExecute(
                    WebServiceResponse webServiceResponse) {
                Log.i("RESULT", ""+webServiceResponse.getResponseCode());
                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        List<Product> product = gson.fromJson(
                                webServiceResponse.getResultMessage(),
                                new TypeToken<List<Product>>() {
                                }.getType());
                        productEvents.getProductsFinished(product);
                    } catch (Exception e) {
                        productEvents.getOrdersFailed(webServiceResponse);
                    }
                } else {
                    productEvents.getOrdersFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }
    public void getProductById(int id) {
        new AsyncTask<Integer, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Integer... id) {
                return WebServiceClient.getInstance().get(context,
                        baseAddress + GET_ORDERS_BY_ID + "/" +
                                Integer.toString(id[0]));
            }
            @Override
            protected void onPostExecute(
                    WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        Product order = gson.fromJson(
                                webServiceResponse.getResultMessage(),
                                Product.class);
                        productEvents.getOrderByIdFinished(order);
                    } catch (Exception e) {
                        productEvents.getOrderByIdFailed(webServiceResponse);
                    }
                } else {
                    productEvents.getOrderByIdFailed(webServiceResponse);
                }
            }
        }.execute(id, null, null);
    }
}