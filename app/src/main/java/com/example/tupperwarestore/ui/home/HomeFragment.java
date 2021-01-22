package com.example.tupperwarestore.ui.home;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupperwarestore.R;
import com.example.tupperwarestore.databinding.CategoryItemBinding;
import com.example.tupperwarestore.databinding.ProductItemBinding;
import com.example.tupperwarestore.model.CategoryModel;
import com.example.tupperwarestore.model.ProductModel;
import com.example.tupperwarestore.ui.CategoryAdapter;
import com.example.tupperwarestore.ui.GlobalAdapter;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private List<CategoryModel> sourceCategory;
    private List<ProductModel> sourceProductOri ;
    private List<ProductModel> sourceProduct ;
    private RecyclerView recyclerViewCategory;
    private RecyclerView recyclerViewProduct;
    private CategoryAdapter categoryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewCategory = (RecyclerView) root.findViewById(R.id.recyclerview);
        recyclerViewProduct = (RecyclerView) root.findViewById(R.id.recycleItemProduct);
        sourceCategory = CategoryModel.init();
        sourceProduct = ProductModel.init();
        sourceProductOri = sourceProduct;
        categoryAdapter = new CategoryAdapter(sourceCategory);
        recyclerViewCategory.setAdapter(new GlobalAdapter<CategoryModel, CategoryItemBinding>(this.getContext(), sourceCategory) {

            @Override
            public int getLayoutResId() {
                return R.layout.category_item;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onBindData(CategoryModel model, int position, CategoryItemBinding holder) {
                holder.titleCategory.setText(model.getCategory());
                int colorText = model.getStatus() ? Color.rgb(255,255,255 ):Color.rgb(0,0,0);
                int colorBackground = model.getStatus() ? Color.rgb(253, 136, 27 ):Color.rgb(255,255,255);
                holder.titleCategory.setTextColor(colorText);
                holder.titleCategory.setBackgroundTintList(ColorStateList.valueOf(colorBackground));
                holder.titleCategory.setOnClickListener(v -> {
                    for (int i = 0; i<sourceCategory.size();i++){
                        sourceCategory.get(i).setStatus(i == position);
                    }
                    sourceProduct = sourceProductOri.stream().filter(d -> d.getIdCategory() == model.getId()).collect(Collectors.toList());
                    notifyDataSetChanged();
                });
            }

            @Override
            public void onItemClick(CategoryModel model, int position) {

            }
        });
        recyclerViewProduct.setAdapter(new GlobalAdapter<ProductModel, ProductItemBinding>(this.getContext(), sourceProduct) {

            @Override
            public int getLayoutResId() {
                return R.layout.product_item;
            }

            @Override
            public void onBindData(ProductModel model, int position, com.example.tupperwarestore.databinding.ProductItemBinding dataBinding) {
                dataBinding.titleItem.setText(model.getTitleProduct());
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(model.getUrl()).getContent());
                    bitmap = Bitmap.createScaledBitmap(bitmap, 80, 100, true);
                    dataBinding.imageItem.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Log.e("Error when call image", "error boss : "+e.getMessage());
                }
            }

            @Override
            public void onItemClick(ProductModel model, int position) {

            }
        });
        return root;
    }
}