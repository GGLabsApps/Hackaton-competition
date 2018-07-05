package com.gglabs.materna.ViewController.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gglabs.materna.Model.Product;
import com.gglabs.materna.R;
import com.gglabs.materna.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GG on 18/02/2018.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private static final String TAG = "ProductsAdapter";

    private Context context;

    private boolean quntityOptions;
    private RecyclerView rv;
    private List<Product> products;
    private Map<Product, Integer> quantities;

    private View.OnClickListener clickListener;

    public ProductsAdapter(Context context, final RecyclerView rv, boolean quntityOptions) {
        this.context = context;
        this.rv = rv;
        this.quntityOptions = quntityOptions;

        products = new ArrayList<>();
        quantities = new HashMap<>();

        initOnClickListener();
    }

    public ProductsAdapter(Context context, RecyclerView rv) {
        this(context, rv, false);
    }

    private void initOnClickListener() {
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHolder vh = (ViewHolder) rv.findContainingViewHolder(v);
                if (vh == null) {
                    Log.e(TAG, "Error in onClick(): Cannot create ViewHolder");
                    return;
                }

                int index = vh.getLayoutPosition();
                final Product product = products.get(index);

                switch (v.getId()) {

                    case R.id.btn_add:
                        product.addQuantity();
                        notifyItemChanged(index);
                        break;

                    case R.id.btn_sub:
                        if (product.getQuantity() > 1) {
                            product.subQuantity();
                            notifyItemChanged(index);
                        } else
                            Utils.showAlertDialog(context, R.string.delete_product, R.string.delete, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    remove(product);
                                }
                            });
                        break;
                }
            }
        };
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.list_item_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, final int position) {
        Product product = products.get(vh.getLayoutPosition());
        vh.onBind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void add(Product product, boolean scrollTo) {
        products.add(product);
        quantities.put(product, product.getQuantity());
        notifyItemInserted(products.size());
        if (scrollTo) rv.smoothScrollToPosition(getItemCount());
    }

    public void add(int position, Product product) {
        products.add(product);
        quantities.put(product, product.getQuantity());
        notifyItemInserted(position);

        rv.scrollToPosition(getItemCount() - 1);
    }

    public void remove(Product product) {
        int position = products.indexOf(product);

        quantities.remove(product);
        products.remove(position);
        notifyItemRemoved(position);
    }

    public void update(Product product) {
        quantities.put(product, product.getQuantity());

        int pos = products.indexOf(product);
        products.set(pos, product);
        notifyItemChanged(pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View rootLayout;
        private TextView tvId, tvProductName, tvQuantity;
        private Button btnSub, btnAdd;

        private ViewHolder(View itemView) {
            super(itemView);
            rootLayout = (View) itemView.findViewById(R.id.rootLayout);
            tvId = (TextView) itemView.findViewById(R.id.tv_id);
            tvProductName = (TextView) itemView.findViewById(R.id.tv_name);
            tvQuantity = (TextView) itemView.findViewById(R.id.tv_quantity);

            btnAdd = (Button) itemView.findViewById(R.id.btn_add);
            btnSub = (Button) itemView.findViewById(R.id.btn_sub);
        }

        public void onBind(Product product) {
            tvId.setText(product.getBarcode());
            tvProductName.setText(product.getName());
            tvQuantity.setText(String.valueOf(product.getQuantity()));

            if (isQuntityOptionsOn()) {
                btnAdd.setOnClickListener(clickListener);
                btnSub.setOnClickListener(clickListener);
            } else {
                tvQuantity.setVisibility(View.GONE);
                btnAdd.setVisibility(View.GONE);
                btnSub.setVisibility(View.GONE);
            }
        }
    }

    public boolean contains(Object obj) {

        for (Product p : products)
            if (p.getBarcode().equals(obj.toString()))
                return true;

        return false;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setQuantityOptions(boolean quantityOptions) {
        this.quntityOptions = quantityOptions;
    }

    public boolean isQuntityOptionsOn() {
        return this.quntityOptions;
    }

}

