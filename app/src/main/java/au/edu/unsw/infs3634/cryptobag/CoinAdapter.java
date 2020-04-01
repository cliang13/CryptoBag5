package au.edu.unsw.infs3634.cryptobag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import au.edu.unsw.infs3634.cryptobag.Entities.Coin;

import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {
    private MainActivity mParentActivity;
    private List<Coin> mCoins;
    private boolean mTwoPane;
    private View.OnClickListener mOnClickListener = (v) {
        Coin coin = (Coin) v.getTag();
    if(mTwoPane) {

        Bundle arguments = new Bundle();
        arguments.putString(DetailFragment.ARG_ITEM_ID.coin.getId());
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(arguments);
        mParentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, fragment).commit();
    } else {
        Context context = v.getContext();
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailFragment.ARG_ITEM_ID), coin.getId());
context.startActivity(intent);
    }
};

public CoinAdapter(MainActivity parent, List<Coin> coins, boolean twoPane) {
        mParentActivity = parent;
        mCoins = coins;
        mTwoPane = twoPane;
        }

        public static class CoinViewHolder extends RecyclerView.ViewHolder {
            public TextView name, value, change;

            public CoinViewHolder(View v) {

                super(v);
                name = v.findViewById(R.id.tvName);
                value = v.findViewById(R.id.tvValue);
                change = v.findViewById(R.id.tvChange);


            }

        }

            @Override
            public CoinAdapter.CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_list_row, parent, false);
                return new CoinViewHolder(v);
            }


            @Override
            public void onBindViewHolder(CoinViewHolder holder, int position) {
                Coin coin = mCoins.get(position);
                holder.name.setText(coin.getName());
                holder.value.setText(NumberFormat.getCurrencyInstance().format(Double.valueOf(coin.getPriceUsd())));
                holder.change.setText((coin.getPercentChange24h()) + " %");

                holder.itemView.setTag(coin);
                holder.itemView.setOnClickListener(mOnClickListener);
            }

            @Override
            public int getItemCount() {
                return mCoins.size();
            }

        };



/* public CoinAdapter(List<Coin> coins, RecyclerViewClickListener listener) {
        mCoins = coins;
        mListener = listener;
    }

    public CoinAdapter(MainActivity mainActivity, List<Coin> coins, boolean mTwoPane) {
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    public static class CoinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, value, change;
        private RecyclerViewClickListener mListener;

        public CoinViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            mListener = listener;
            v.setOnClickListener(this);
            name = v.findViewById(R.id.tvName);
            value = v.findViewById(R.id.tvValue);
            change = v.findViewById(R.id.tvChange);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    @

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override

    //    holder.value.setText(NumberFormat.getCurrencyInstance().format(coin.getValue()));
      //  holder.change.setText(String.valueOf(coin.getChange1h()) + " %");



    }

    // Return the size of your dataset (invoked by the layout manager)
