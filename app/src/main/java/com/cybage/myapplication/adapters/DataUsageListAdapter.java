package com.cybage.myapplication.adapters;


import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.cybage.myapplication.activity.DataUsageActivity;
import com.cybage.myapplication.databinding.DataItemBinding;
import com.cybage.myapplication.databinding.NetworkItemBinding;
import com.cybage.myapplication.model.Records;
import com.cybage.myapplication.utils.NetworkState;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class DataUsageListAdapter extends PagedListAdapter<Records, RecyclerView.ViewHolder> {

    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;
    private final DataUsageActivity activityContext;

    private Context context;
    private NetworkState networkState;
    public DataUsageListAdapter(Context context, DataUsageActivity dataUsageActivity) {
        super(Records.DIFF_CALLBACK);
        this.context = context;
        this.activityContext = dataUsageActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_PROGRESS) {
            NetworkItemBinding headerBinding = NetworkItemBinding.inflate(layoutInflater, parent, false);
            NetworkStateItemViewHolder viewHolder = new NetworkStateItemViewHolder(headerBinding);
            return viewHolder;

        } else {
            DataItemBinding itemBinding = DataItemBinding.inflate(layoutInflater, parent, false);
            ArticleItemViewHolder viewHolder = new ArticleItemViewHolder(itemBinding);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ArticleItemViewHolder) {
            ((ArticleItemViewHolder)holder).bindTo(getItem(position));
        } else {
            ((NetworkStateItemViewHolder) holder).bindView(networkState);
        }
    }


    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }


    public class ArticleItemViewHolder extends RecyclerView.ViewHolder {

        private DataItemBinding binding;
        public ArticleItemViewHolder(DataItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(Records record) {

            binding.txtVolume.setText(String.valueOf(record.getVolumeOfMobileData()));
            binding.txtYear.setText(record.getYear());
            binding.txtId.setText(String.valueOf(record.getId()));

            double[] array = new double[]{covertToLong(record.getQ1()),
                    covertToLong(record.getQ2()), covertToLong(record.getQ3()), covertToLong(record.getQ4())};
            String displayMessage = "";
            boolean showDialog = false;

            for (int i=1; i < array.length; i++) {
                if (array[i] < array[i-1]) {
                    showDialog = true;
                    displayMessage =  displayMessage + findQuarterFromIndex(i-1)+" > " + findQuarterFromIndex(i);
                }
            }

            if (showDialog) {
                binding.imageView.setVisibility(View.VISIBLE);
            }else {
                binding.imageView.setVisibility(View.GONE);
            }

            String finalDisplayMessage = displayMessage;
            binding.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activityContext);
                alertDialogBuilder.setMessage("Decrease in quarterly data usage \n  "+ finalDisplayMessage);
                alertDialogBuilder.setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                }


            });




        }

     /*   private String myFunction(double[] longs) {
            StringBuilder str = new StringBuilder();
            for (int i=1; i < longs.length; i++) {

                *//*if (i == 0) {
                    str.append(findQuarterFromIndex(i));
                } else if (longs[i] > longs[i-1]) {
                    str.append(" < " + findQuarterFromIndex(i));
                } else*//* if (longs[i] < longs[i-1]) {
                    str.append(findQuarterFromIndex(i-1)+" > " + findQuarterFromIndex(i));
                } *//*else if (longs[i] == longs[i-1]) {
                    str.append(" == " + findQuarterFromIndex(i));
                }*//*
            }
            return str.toString();
        }
*/
     private double covertToLong(String q1) {
         if (q1 != null && !q1.isEmpty())
             return Double.parseDouble(q1);
         else
             return 0;
     }

        private String findQuarterFromIndex(int i) {
            switch (i){
                case 0:
                    return "Q1";
                case 1:
                    return "Q2";
                case 2:
                    return "Q3";
                case 3:
                    return "Q4";
            }

            return "";
        }


    }


    public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        private NetworkItemBinding binding;
        public NetworkStateItemViewHolder(NetworkItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
                binding.errorMsg.setVisibility(View.VISIBLE);
                binding.errorMsg.setText(networkState.getMsg());
            } else {
                binding.errorMsg.setVisibility(View.GONE);
            }
        }
    }
}
