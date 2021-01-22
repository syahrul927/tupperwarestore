package com.example.tupperwarestore.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tupperwarestore.R;
import com.example.tupperwarestore.databinding.BagItemBinding;
import com.example.tupperwarestore.databinding.FragmentOrderBinding;
import com.example.tupperwarestore.repository.Bag;
import com.example.tupperwarestore.ui.GlobalAdapter;

import java.util.List;

public class OrderFragment extends Fragment {

    private OrderViewModel orderViewModel;

    RecyclerView recyclerViewBag;

    //make binding ajaa lah biar gampang, lumayan sambil belajar MVVM
    FragmentOrderBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderViewModel =
                new ViewModelProvider(this).get(OrderViewModel.class);
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        orderViewModel.getListOfBag(getContext());
        recyclerViewBag = binding.recycleItemProduct;
        orderViewModel.getListBag().observe(getViewLifecycleOwner(), new Observer<List<Bag>>() {
            @Override
            public void onChanged(List<Bag> bags) {
                Double total = 0d;
                for (Bag b:bags) {
                    total = total+b.getPrice();
                }
                binding.totalPrice.setText("Rp. "+String.valueOf(total));
                recyclerViewBag.setAdapter(new GlobalAdapter<Bag, BagItemBinding>(getContext(), bags) {
                    @Override
                    public int getLayoutResId() {
                        return R.layout.bag_item;
                    }

                    @Override
                    public void onBindData(Bag model, int position, BagItemBinding dataBinding) {
                        //setup image
                        Glide.with(root)
                                .load(model.getPict())
                                .centerCrop()
                                .placeholder(R.drawable.ic_unavailable_pict_background)
                                .into(dataBinding.imageItem);
                        dataBinding.priceItem.setText("Rp. "+String.valueOf(model.getPrice()));
                        dataBinding.qty.setText(String.valueOf(model.getQty()));
                        dataBinding.titleItem.setText(model.getName());
                    }

                    @Override
                    public void onItemClick(Bag model, int position) {

                    }
                });
            }
        });
        return root;
    }
}