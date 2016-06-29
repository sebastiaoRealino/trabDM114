package br.inatel.lojaonline.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.inatel.lojaonline.R;
import br.inatel.lojaonline.tasks.OrderTasks;
import br.inatel.lojaonline.util.CheckNetworkConnection;

/**
 * Created by Seba on 28/06/2016.
 */
public class ProductListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_product_list,
                container, false);

        return rootView;
    }
}
