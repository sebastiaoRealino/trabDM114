package br.inatel.lojaonline.fragments;

/**
 * Created by bccre on 22/06/2016.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.inatel.lojaonline.R;
import br.inatel.lojaonline.adapters.OrderAdapter;
import br.inatel.lojaonline.models.Order;
import br.inatel.lojaonline.tasks.OrderEvents;
import br.inatel.lojaonline.tasks.OrderTasks;
import br.inatel.lojaonline.util.CheckNetworkConnection;
import br.inatel.lojaonline.webservice.WebServiceResponse;

public class OrdersFragment extends Fragment implements OrderEvents {

    private ListView listViewOrders;
    private ArrayList<Order> orders;

    public OrdersFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_orders_list,
                container, false);

        getActivity().setTitle("Orders");

        listViewOrders = (ListView) rootView.
                findViewById(R.id.orders_list);

        if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
            OrderTasks orderTasks = new OrderTasks(getActivity(), this);
            orderTasks.getOrders();
        }

        return rootView;
    }



    @Override
    public void getOrdersFinished(List<Order> orders) {
        OrderAdapter orderAdapter = new OrderAdapter(
                getActivity(), orders);
        listViewOrders.setAdapter(orderAdapter);

        listViewOrders.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long orderId) {
                // TODO Auto-generated method stub

                Log.d("############", "Items " + "Ok");
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                DeatailsOrderFragment deatailsOrderFragment = new DeatailsOrderFragment();
                Bundle data = new Bundle();
                data.putString("orderId", orderId+"");

                deatailsOrderFragment.setArguments(data);
                ft.replace(R.id.container, deatailsOrderFragment);
                ft.commit();
            }

        });

    }

    @Override
    public void getOrdersFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(), "Falha na consulta da lista de pedidos" +
                webServiceResponse.getResultMessage() + " - Código do erro: " +
                webServiceResponse.getResponseCode(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getOrderByIdFinished(Order order) {

    }

    @Override
    public void getOrderByIdFailed(WebServiceResponse webServiceResponse) {

    }

}
