package br.inatel.lojaonline.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.inatel.lojaonline.R;
import br.inatel.lojaonline.models.Order;
import br.inatel.lojaonline.models.Product;

/**
 * Created by bccre on 22/06/2016.
 */
public class ProductAdapter extends BaseAdapter {
    private final Activity mActivity;
    List<Product> mProductList;
    public ProductAdapter(Activity activity, List<Product> products) {
        this.mActivity = activity;
        this.mProductList = products;
    }
    @Override
    public int getCount() {
        return mProductList.size();
    }
    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return mProductList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mActivity.getLayoutInflater().inflate(
                R.layout.product_list_item, null);
        Product product = mProductList.get(position);


        TextView txtViewProductId = (TextView) view.
                findViewById(R.id.productListId);
        txtViewProductId.setText(""+product.getId());

        TextView txtViewProductName = (TextView) view.
                findViewById(R.id.productListName);
        txtViewProductName.setText(product.getNome());

        TextView txtViewProductPrice = (TextView) view.
                findViewById(R.id.productListPrice);
        txtViewProductPrice.setText(product.getPreco()+"");

        return view;
    }


}
