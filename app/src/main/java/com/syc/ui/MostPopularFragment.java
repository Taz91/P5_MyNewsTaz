package com.syc.ui;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.syc.R;
import com.syc.models.MostPopularNYT;
import com.syc.models.MostResult;
import com.syc.utils.GetNewsDataService;
import com.syc.utils.MostAdapter;
import com.syc.utils.RetrofitInstance;
import java.util.List;
import static com.syc.utils.Utils.getApiKey;

/**
 * Created by Chazette Sylvain
 * content consumption of API NYT - MostPopular, with only viewed - 7 day
 *
 * A simple {@link Fragment} subclass.
 * Use the {@link MostPopularFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MostPopularFragment extends Fragment {
    @BindView(R.id.rv_list) RecyclerView rvList;

    public MostPopularFragment() {
        // Required empty public constructor
    }

    public static MostPopularFragment newInstance() {
        MostPopularFragment fragment = new MostPopularFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_most_popular, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData();
    }

    /**
     * initializes the consumption of the API NYT - mostpopular
     * service parameter : only viewed - 7 day
     *
     */
    private void loadData() {
        GetNewsDataService newsDataService = RetrofitInstance.getRetrofitInstance().create(GetNewsDataService.class);
        Call<MostPopularNYT> call = newsDataService.getPopularNews( getApiKey());
                /*
                MostPopular : 3 types (emailed/viewed/shared), periode 1,7,30j
                https://api.nytimes.com/svc/mostpopular/v2/emailed/7.json?api-key=yourkey
                https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json?api-key=yourkey

                Cas shared: shared type = email, facebook, twitter, periode = 1,7,30j
                https://api.nytimes.com/svc/mostpopular/v2/shared/1/facebook.json?api-key=yourkey
                https://api.nytimes.com/svc/mostpopular/v2/shared/1.json?api-key=yourkey
                */
                /*
                MostPopular : 3 types (emailed/viewed/shared), periode 1,7,30j
                https://api.nytimes.com/svc/mostpopular/v2/emailed/7.json?api-key=yourkey
                https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json?api-key=yourkey
                */

        call.enqueue(new Callback<MostPopularNYT>(){

            @Override
            public void onResponse(Call<MostPopularNYT> call, Response<MostPopularNYT> response) {

                List<MostResult> result = response.body().getResults();

                MostAdapter adapter = new MostAdapter(result , Glide.with(getContext()), getContext());

                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rvList.setLayoutManager(verticalLayoutManager);
                rvList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MostPopularNYT> call, Throwable t) {
                Toast.makeText(getContext(), "Error consumption of API NYT - Mostpopular", Toast.LENGTH_LONG).show();
            }
        });
    }
}
