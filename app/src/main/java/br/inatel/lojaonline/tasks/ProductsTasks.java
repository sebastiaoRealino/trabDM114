package br.inatel.lojaonline.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.inatel.lojaonline.interfaces.ProductEvents;
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
    private static final String POST_NEW_PRODUCT = "/api/Products";
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
                        productEvents.getProductsFailed(webServiceResponse);
                    }
                } else {
                    productEvents.getProductsFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }
    public void postNewProduct(final Product product) {
        new AsyncTask<Integer, Void, WebServiceResponse>() {

            @Override
            protected WebServiceResponse doInBackground(Integer... id) {
                JSONObject productJson = new JSONObject();
                try {

                    productJson.put("Id", product.getId());
                    productJson.put("nome", product.getNome());
                    productJson.put("codigo", product.getCodigo());
                    productJson.put("preco", product.getPreco());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return WebServiceClient.getInstance().post(context,
                        baseAddress + POST_NEW_PRODUCT, productJson.toString());
            }
            @Override
            protected void onPostExecute(
                    WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    productEvents.postProductFinished(webServiceResponse.getResponseMessage());
                } else {
                    productEvents.postProductFailed(webServiceResponse.getResponseMessage());
                }
            }
        }.execute();
    }

}