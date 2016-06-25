package br.inatel.lojaonline.tasks;

import java.util.List;

import br.inatel.lojaonline.models.Order;
import br.inatel.lojaonline.webservice.WebServiceResponse;

/**
 * Created by bccre on 22/06/2016.
 */
public interface OrderEvents {
    void getOrdersFinished(List<Order> orders);
    void getOrdersFailed(WebServiceResponse webServiceResponse);
    void getOrderByIdFinished(Order order);
    void getOrderByIdFailed(WebServiceResponse webServiceResponse);
}
