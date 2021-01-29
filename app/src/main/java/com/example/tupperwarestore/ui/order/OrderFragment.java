package com.example.tupperwarestore.ui.order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tupperwarestore.R;
import com.example.tupperwarestore.TupperwareUtils;
import com.example.tupperwarestore.databinding.BagItemBinding;
import com.example.tupperwarestore.databinding.FragmentOrderBinding;
import com.example.tupperwarestore.repository.Bag;
import com.example.tupperwarestore.ui.GlobalAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class OrderFragment extends Fragment {

    private OrderViewModel orderViewModel;

    RecyclerView recyclerViewBag;
    Fragment fragment = this;

    //make binding ajaa lah biar gampang, lumayan sambil belajar MVVM
    FragmentOrderBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderViewModel =
                new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        orderViewModel.setContext(getContext());
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        orderViewModel.getListOfBag();
        recyclerViewBag = binding.recycleItemProduct;
        binding.btnCheckout.setOnClickListener(v -> {
            orderViewModel.updateCartServer();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setTitle("Apakah anda yakin ingin melakukan Checkout?");
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            orderViewModel.checkoutProducts();

                        }
                    })
                    .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            // membuat alert dialog dari builder
            AlertDialog alertDialog = alertDialogBuilder.create();

            // menampilkan alert dialog
            alertDialog.show();
        });
        orderViewModel.getIsSuccessCheckout().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
//                    Snackbar mySnackbar = Snackbar.make(getView(), "Berhasil Melakukan Checkout", Snackbar.LENGTH_LONG);
//                    mySnackbar.show();
                    NavDirections action = OrderFragmentDirections.actionNavigationOrderToSuccessCheckout();
                    NavHostFragment.findNavController(fragment).navigate(action);
                }

            }
        });
        orderViewModel.getMessageCheckout().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar mySnackbar = Snackbar.make(getView(), s, Snackbar.LENGTH_LONG);
                mySnackbar.show();
            }
        });
        orderViewModel.getListBag().observe(getViewLifecycleOwner(), new Observer<List<Bag>>() {
            @Override
            public void onChanged(List<Bag> bags) {
                if(bags.size()>0){
                    Double total = 0d;
                    for (Bag b:bags) {
                        total = total+(b.getPrice()*b.getQty());
                    }
                    binding.btnCheckout.setClickable(true);
                    binding.keranjangExist.setVisibility(View.VISIBLE);
                    binding.totalPrice.setText(TupperwareUtils.moneyFormatter(total));
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
                            dataBinding.priceItem.setText(TupperwareUtils.moneyFormatter(model.getPrice()));
                            dataBinding.qty.setText(String.valueOf(model.getQty()));
                            dataBinding.titleItem.setText(model.getName());
                            dataBinding.plusBtn.setOnClickListener(v -> {
                                orderViewModel.updateQtyCart(model, true);
                            });
                            dataBinding.minusBtn.setOnClickListener(v -> {
                                orderViewModel.updateQtyCart(model, false);
                            });
                            dataBinding.trashBtn.setOnClickListener(v -> {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                alertDialogBuilder.setTitle("Apakah anda yakin ingin menghapus ini dari keranjang?");
                                alertDialogBuilder
                                        .setCancelable(false)
                                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                orderViewModel.deleteItemCheckout(model);
                                                notifyDataSetChanged();
                                            }
                                        })
                                        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                // membuat alert dialog dari builder
                                AlertDialog alertDialog = alertDialogBuilder.create();

                                // menampilkan alert dialog
                                alertDialog.show();
                            });
                        }

                        @Override
                        public void onItemClick(Bag model, int position) {

                        }
                    });
                }else{
                    binding.totalPrice.setText(TupperwareUtils.moneyFormatter(0d));
                    binding.btnCheckout.setClickable(false);
                    binding.keranjangExist.setVisibility(View.INVISIBLE);
                }
            }
        });
        orderViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.loading.setVisibility(View.VISIBLE);
                    binding.emptyBackground.setVisibility(View.INVISIBLE);
                }else{
                    int vi = orderViewModel.getListBag().getValue().size() > 0 ? View.INVISIBLE :View.VISIBLE;
                    binding.emptyBackground.setVisibility(vi);
                    binding.loading.setVisibility(View.INVISIBLE);
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroy() {
        orderViewModel.updateCartServer();
        super.onDestroy();
    }


}