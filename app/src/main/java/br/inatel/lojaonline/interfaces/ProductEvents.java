package br.inatel.lojaonline.interfaces;

import java.util.List;

import br.inatel.lojaonline.models.Product;
import br.inatel.lojaonline.webservice.WebServiceResponse;

/**
 * Created by Seba on 29/06/2016.
 */
public interface ProductEvents  {

    void getProductsFinished(List<Product> orders);
    void getProductsFailed(WebServiceResponse webServiceResponse);
    void postProductFinished(String message);
    void postProductFailed(String error);
}
