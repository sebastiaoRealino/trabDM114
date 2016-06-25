package br.inatel.lojaonline.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import br.inatel.lojaonline.R;
import br.inatel.lojaonline.models.Order;
import br.inatel.lojaonline.tasks.OrderEvents;
import br.inatel.lojaonline.tasks.OrderTasks;
import br.inatel.lojaonline.util.CheckNetworkConnection;
import br.inatel.lojaonline.webservice.WebServiceResponse;

/**
 * Created by Seba on 25/06/2016.
 */
public class DeatailsOrderFragment extends Fragment implements OrderEvents {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_order_details,
                container, false);
        Bundle extras = getArguments();
        String orderId = extras.get("orderId").toString();
        if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
            OrderTasks orderTasks = new OrderTasks(getActivity(), this);
            orderTasks.getOrderById(Integer.parseInt(orderId));
        }
        return rootView;
    }

    @Override
    public void getOrdersFinished(List<Order> orders) {

    }

    @Override
    public void getOrdersFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void getOrderByIdFinished(Order order) {
        Log.i("Order", order.getId() + order.getUserName() + order.getPrecoFrete());
    }

    @Override
    public void getOrderByIdFailed(WebServiceResponse webServiceResponse) {

    }
}
