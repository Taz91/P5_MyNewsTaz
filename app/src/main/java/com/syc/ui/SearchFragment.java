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
import com.syc.models.SearchDoc;
import com.syc.models.SearchNYT;
import com.syc.models.SearchResponse;
import com.syc.utils.GetNewsDataService;
import com.syc.utils.RetrofitInstance;
import com.syc.utils.SearchAdapter;
import java.util.List;
import static com.syc.utils.Utils.getApiKey;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    //private static String API_KEY = "J0iJw0a8fdshubHztJsOJxEEg6hPstOG";
    @BindView(R.id.rv_list)
    RecyclerView rvList;

      public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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
        View v =  inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData();
    }

    private void loadData() {
        GetNewsDataService newsDataService = RetrofitInstance.getRetrofitInstance().create(GetNewsDataService.class);
        Call<SearchNYT> call = newsDataService.getSearchNews( getApiKey() );

        call.enqueue(new Callback<SearchNYT>(){

            @Override
            public void onResponse(Call<SearchNYT> call, Response<SearchNYT> response) {

                SearchResponse searchResponse = response.body().getResponse();
                List<SearchDoc> result = searchResponse.getDocs();

                SearchAdapter adapter = new SearchAdapter(result , Glide.with(getView()), getContext());

                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rvList.setLayoutManager(verticalLayoutManager);
                rvList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<SearchNYT> call, Throwable t) {
                // TODO : gestion message de retour : soit le site est inaccessible
                // y mettre une image ou un fragment spécifique ?

                Toast.makeText(getContext(), "Une erreur", Toast.LENGTH_LONG).show();
            }
        });

    }

}
