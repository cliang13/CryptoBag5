package au.edu.unsw.infs3634.cryptobag;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import au.edu.unsw.infs3634.cryptobag.Entities.Coin;
import au.edu.unsw.infs3634.cryptobag.Entities.CoinLoreResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;


public class DetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    private Coin mCoin;
    private TextView mName;
    private TextView mSymbol;
    private TextView mValue;
    private TextView mChange1h;
    private TextView mChange24h;
    private TextView mChange7d;
    private TextView mMarketcap;
    private TextView mVolume;
    private ImageView mSearch;
    private String TAG = "DetailFragment";


    public DetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {

        //    Gson gson = new Gson();
          //  CoinLoreResponse response = gson.fromJson(CoinLoreResponse.json, CoinLoreResponse.class);

            //List<Coin> coins = response.getData();

          //  try {

                //create Retrofit instance & parse the retrieved json using Gson deserillser
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.coinlore.net/").addConverterFactory(GsonConverterFactory.create()).build();

// get service & call object for the request
                CoinService service = retrofit.create(CoinService.class);
                Call<CoinLoreResponse> coinsCall = service.getCoins();


//execute network request
             //   Response<CoinLoreResponse> coinsResponse = coinsCall.execute();
                coinsCall.enqueue(new Callback<CoinLoreResponse>() {
                    @Override
                    public void onResponse(Call<CoinLoreResponse> call, Response<CoinLoreResponse> response) {
                        Log.d(TAG, "onResponse: SUCCESS");
                        List<Coin> coins = response.body().getData();
                        for (Coin coin : coins) {
                            if (coin.getId().equals(getArguments().getString(ARG_ITEM_ID))) {
                                mCoin = coin;
                            }
                        }
                        updateUi();
                        DetailFragment.this.getActivity().setTitle(mCoin.getName());
                    }

                    @Override
                    public void onFailure(Call<CoinLoreResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: FAILURE");


                    }
                });




   //     }  catch (IOException e) {


     //       e.printStackTrace();
       // }
                                  }








    //        for (Coin coin : coins) {
      //          if (coin.getId().equals(getArguments().getString(ARG_ITEM_ID))) {
        //            mCoin = coin;
          //      }
         //   }
          //  getActivity().setTitle(mCoin.getName());
        }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
updateUi();

return v;
    }

    private void updateUi() {
View v = getView();
        mName = v.findViewById(R.id.tvName);
        mSymbol = v.findViewById(R.id.tvSymbol);
        mValue = v.findViewById(R.id.tvValueField);
        mChange1h = v.findViewById(R.id.tvChange1hField);
        mChange24h = v.findViewById(R.id.tvChange24hField);
        mChange7d = v.findViewById(R.id.tvChange7dField);
        mMarketcap = v.findViewById(R.id.tvMarketcapField);
        mVolume = v.findViewById(R.id.tvVolumeField);
        mSearch = v.findViewById(R.id.ivSearch);


        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        mName.setText(mCoin.getName());
        mSymbol.setText(mCoin.getSymbol());
        mValue.setText(formatter.format(Double.parseDouble(mCoin.getPriceUsd())));
        mChange1h.setText(String.valueOf(mCoin.getPercentChange1h()) + " %");
        mChange24h.setText(String.valueOf(mCoin.getPercentChange24h()) + " %");
        mChange7d.setText(String.valueOf(mCoin.getPercentChange7d()) + " %");
        mMarketcap.setText(formatter.format(Double.parseDouble(mCoin.getMarketCapUsd())));
        mVolume.setText(formatter.format(mCoin.getVolume24()));
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCoin(mCoin.getName());
            }
        });


    }


    private void searchCoin(String name) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + name));
        startActivity(intent);
    }
}