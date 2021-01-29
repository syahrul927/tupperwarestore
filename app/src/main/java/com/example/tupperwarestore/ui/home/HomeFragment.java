package com.example.tupperwarestore.ui.home;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tupperwarestore.R;
import com.example.tupperwarestore.TupperwareUtils;
import com.example.tupperwarestore.databinding.CategoryItemBinding;
import com.example.tupperwarestore.databinding.FragmentHomeBinding;
import com.example.tupperwarestore.databinding.ProductItemBinding;
import com.example.tupperwarestore.model.CategoryModel;
import com.example.tupperwarestore.model.ProductModel;
import com.example.tupperwarestore.repository.User;
import com.example.tupperwarestore.ui.GlobalAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private List<CategoryModel> sourceCategory  = new ArrayList<>();
    private List<ProductModel> sourceProductOri  = new ArrayList<>();
    private List<ProductModel> sourceProduct  = new ArrayList<>();
    private RecyclerView recyclerViewCategory;
    private RecyclerView recyclerViewProduct;
    private static final String TAG = "HomeFragment";
    private static final String HEY = "Hey ";

    Fragment fragment = this;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerViewCategory = (RecyclerView) root.findViewById(R.id.recyclerview);
        recyclerViewProduct = (RecyclerView) root.findViewById(R.id.recycleItemProduct);
//        sourceProduct = ProductModel.init();
        homeViewModel.getListProduct();
        homeViewModel.getUser();
        homeViewModel.getUserInfo().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user!=null){

                    binding.textUsername.setText(HEY+user.getName().split(" ")[0]);
                }
            }
        });
        homeViewModel.getCategory().observe(getViewLifecycleOwner(), new Observer<List<CategoryModel>>() {
            @Override
            public void onChanged(List<CategoryModel> categoryModels) {
                sourceCategory = categoryModels;
                notifyDataCategory(root, root.getContext());
            }
        });
        homeViewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                sourceProduct = productModels;
                sourceProductOri = sourceProduct;
                notifyDataProduct(root, root.getContext());
            }
        });
    ///Suaraa gua gk kedengeran?
        return root;
    }

    private void notifyDataCategory(View root, Context context){
        recyclerViewCategory.setAdapter(new GlobalAdapter<CategoryModel, CategoryItemBinding>(context, sourceCategory) {

            @Override
            public int getLayoutResId() {
                return R.layout.category_item;
            }

            @Override
            public void onBindData(CategoryModel model, int position, CategoryItemBinding holder) {
                notifyDataProduct(root, root.getContext());
                holder.titleCategory.setText(model.getCategory());
                int colorText = model.getStatus() ? Color.rgb(255,255,255 ):Color.rgb(0,0,0);
                int colorBackground = model.getStatus() ? Color.rgb(253, 136, 27 ):Color.rgb(255,255,255);
                holder.titleCategory.setTextColor(colorText);
                holder.titleCategory.setBackgroundTintList(ColorStateList.valueOf(colorBackground));
                holder.titleCategory.setOnClickListener(v -> {
                    for (int i = 0; i<sourceCategory.size();i++){
                        sourceCategory.get(i).setStatus(i == position);
                    }
                    notifyDataSetChanged();
                    sourceProduct = new ArrayList<>();
                    if(model.getId().equals(0)){
                        sourceProduct = sourceProductOri;
                    }else{
                        for (ProductModel mod: sourceProductOri) {
                            if (mod.getIdCategory().equals(model.getId())) {
                                sourceProduct.add(mod);
                            }
                        }
                    }

                    notifyDataProduct(root, root.getContext());
                });
            }

            @Override
            public void onItemClick(CategoryModel model, int position) {

            }
        });
    }
    private void notifyDataProduct(View root, Context context){
        recyclerViewProduct.setAdapter(new GlobalAdapter<ProductModel, ProductItemBinding>(context, sourceProduct) {

            @Override
            public int getLayoutResId() {
                return R.layout.product_item;
            }

            @Override
            public void onBindData(ProductModel model, int position, com.example.tupperwarestore.databinding.ProductItemBinding dataBinding) {
                dataBinding.titleItem.setText(model.getTitleProduct());
                Glide.with(root)
                        .load(model.getUrl())
                        .centerCrop()
                        .placeholder(R.drawable.ic_unavailable_pict_background)
                        .into(dataBinding.imageItem);
                dataBinding.priceItem.setText(TupperwareUtils.moneyFormatter(Double.valueOf(model.getPriceProduct())));

            }

            @Override
            public void onItemClick(ProductModel model, int position) {
                homeViewModel.setProduct(model);
                homeViewModel.setIsShowProduct(true);
                NavDirections action = HomeFragmentDirections.actionNavigationHomeToDetailProductFragment();
                NavHostFragment.findNavController(fragment).navigate(action);
            }
        });
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}