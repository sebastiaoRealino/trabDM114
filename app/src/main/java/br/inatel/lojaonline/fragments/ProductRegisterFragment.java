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
import br.inatel.lojaonline.adapters.ProductAdapter;
import br.inatel.lojaonline.interfaces.ProductEvents;
import br.inatel.lojaonline.models.Product;
import br.inatel.lojaonline.tasks.ProductsTasks;
import br.inatel.lojaonline.webservice.WebServiceResponse;

/**
 * Created by Seba on 28/06/2016.
 */
public class ProductRegisterFragment extends Fragment implements ProductEvents {

    private ListView mListViewProducts;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_product_list,
                container, false);
        getActivity().setTitle("Produtos");

        mListViewProducts = (ListView) rootView.
                findViewById(R.id.product_list);


        ProductsTasks productsTasks = new ProductsTasks(getActivity(), this);
        productsTasks.getProducts();
        return rootView;
    }

    @Override
    public void getProductsFinished(List<Product> products) {
        for (Product product:products) {
            Log.i("ProductFragment", "Nome: " + product.getNome() + " Descricao: " + product.getDescricao());
        }


        ProductAdapter proctAdapter = new ProductAdapter(
                getActivity(), products);
        mListViewProducts.setAdapter(proctAdapter);

    }

    @Override
    public void getOrdersFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void getOrderByIdFinished(Product product) {

    }

    @Override
    public void getOrderByIdFailed(WebServiceResponse webServiceResponse) {

    }
}
