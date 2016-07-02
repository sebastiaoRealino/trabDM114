package br.inatel.lojaonline.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
public class ProductRegisterFragment extends Fragment implements ProductEvents, View.OnClickListener {


    EditText mIdNewProduct;
    EditText mNameNovoProduto;
    EditText mEditTextPrice;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Gegistro de Produto");
        View rootView = inflater.inflate(R.layout.fragment_product_register,
                container, false);

        mIdNewProduct    = (EditText) rootView.findViewById(R.id.idNewProduct) ;
        mNameNovoProduto = (EditText) rootView.findViewById(R.id.nameNovoProduto);
        mEditTextPrice   = (EditText) rootView.findViewById(R.id.editTextPrice);

        Button buttonRegister = (Button) rootView.findViewById(R.id.btnRegister);
        buttonRegister.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void getProductsFinished(List<Product> products) {
        for (Product product:products) {
            Log.i("ProductFragment", "Nome: " + product.getNome() + " Descricao: " + product.getDescricao());
        }

    }

    @Override
    public void getProductsFailed(WebServiceResponse webServiceResponse) {

    }

    @Override
    public void postProductFinished(String message) {
        Log.i("ProducRegisterFragment"," postProductFinished POST!");
    }

    @Override
    public void postProductFailed(String error) {
        Log.i("ProducRegisterFragment"," postProductFinished Failed! MSG: "+ error);
    }

    @Override
    public void onClick(View v) {
        Product product = new Product();
        product.setCodigo((mIdNewProduct.getText().toString()));
        product.setNome(mNameNovoProduto.getText().toString());
        double d = Double.parseDouble(mEditTextPrice.getText().toString());
        product.setPreco(d);

        ProductsTasks productsTasks = new ProductsTasks(getActivity(), this);
        productsTasks.postNewProduct(product);
    }
}
