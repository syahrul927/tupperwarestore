package com.example.tupperwarestore.ui.product;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tupperwarestore.R;
import com.example.tupperwarestore.TupperwareUtils;
import com.example.tupperwarestore.databinding.FragmentDetailProductBinding;
import com.example.tupperwarestore.databinding.VariantItemBinding;
import com.example.tupperwarestore.model.ProductModel;
import com.example.tupperwarestore.ui.GlobalAdapter;
import com.example.tupperwarestore.ui.home.HomeViewModel;
import com.google.android.material.snackbar.Snackbar;

public class DetailProductFragment extends Fragment {

    private FragmentDetailProductBinding binding;
    private HomeViewModel mViewmodel ;
    private DetailProductViewModel detailProductViewModel ;
    private RecyclerView recyclerViewVariant;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDetailProductBinding.inflate(inflater, container, false);
        recyclerViewVariant = binding.variantList;
        mViewmodel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        detailProductViewModel = new ViewModelProvider(this).get(DetailProductViewModel.class);
        binding.previousBtn.setOnClickListener(v -> {
            NavDirections action = DetailProductFragmentDirections.actionDetailProductFragmentPop();
            NavHostFragment.findNavController(this).navigate(action);
        });
        binding.btnKeranjang.setOnClickListener(v -> {
            if(detailProductViewModel.getSelectedColor().getValue() == null){
                Toast.makeText(getContext(), "Pilih warna terlebih dahulu", Toast.LENGTH_LONG).show();
            }else{
                ProductModel product = mViewmodel.getProduct().getValue();
                detailProductViewModel.saveBag(product, getContext());
                Snackbar mySnackbar = Snackbar.make(getView(), "Sukses menambahkan kedalam keranjang", Snackbar.LENGTH_LONG);
                mySnackbar.show();

            }
        });
        mViewmodel.getProduct().observe(getViewLifecycleOwner(), new Observer<ProductModel>() {
            @Override
            public void onChanged(ProductModel productModel) {
                binding.texttitle.setText(productModel.getTitleProduct());
                Glide.with(binding.getRoot())
                        .load(productModel.getUrl())
                        .centerCrop()
                        .placeholder(R.drawable.ic_unavailable_pict_background)
                        .into(binding.headerimage);
                binding.textDesc.setText(productModel.getDesc().replace("\\n", "\n"));
                binding.textPrice.setText(TupperwareUtils.moneyFormatter(Double.valueOf(productModel.getPriceProduct())));

                //adaptercolor variant
                recyclerViewVariant.setAdapter(new GlobalAdapter<String, VariantItemBinding>(binding.getRoot().getContext(), productModel.getVariant().getColor()) {
                    @Override
                    public int getLayoutResId() {
                        return R.layout.variant_item;
                    }

                    @Override
                    public void onBindData(String model, int position, VariantItemBinding dataBinding) {
                        model = model.replace(" ", "");
                        Integer selected = detailProductViewModel.getSelectedColor().getValue();
                        GradientDrawable drawable = (GradientDrawable) dataBinding.colorContainer.getBackground();
                        if(selected !=null && position==selected){
                            drawable.setStroke(5, Color.parseColor("#FD881B"));
                        }else{
                            drawable.setStroke(2, Color.parseColor("#a5b1c2"));
                        }
                        dataBinding.colorView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(model)));
                    }

                    @Override
                    public void onItemClick(String model, int position) {
                        detailProductViewModel.setSelectedColor(position);
                        notifyDataSetChanged();
                    }
                });
            }
        });
//        binding
        return binding.getRoot();
    }
    public static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }
    @Override
    public void onDestroy() {
        mViewmodel.setIsShowProduct(false);
        super.onDestroy();
    }
}
