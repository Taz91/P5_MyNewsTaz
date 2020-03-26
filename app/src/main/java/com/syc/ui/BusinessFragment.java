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
import com.syc.models.BusinessDoc;
import com.syc.models.BusinessNYT;
import com.syc.models.BusinessResponse;
import com.syc.utils.BusinessAdapter;
import com.syc.utils.GetNewsDataService;
import com.syc.utils.RetrofitInstance;
import java.util.List;
import static com.syc.utils.Utils.getApiKey;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BusinessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusinessFragment extends Fragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;

      public BusinessFragment() {
        // Required empty public constructor
    }

    public static BusinessFragment newInstance() {
        BusinessFragment fragment = new BusinessFragment();
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
        View v =  inflater.inflate(R.layout.fragment_business, container, false);
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
        //TODO: if time, include parameter in help Activity
        String fq = "Business";
        Call<BusinessNYT> call = newsDataService.getBusinessNews( fq ,getApiKey() );

        call.enqueue(new Callback<BusinessNYT>(){
            @Override
            public void onResponse(Call<BusinessNYT> call, Response<BusinessNYT> response) {
                BusinessResponse businessResponse = response.body().getResponse();
                List<BusinessDoc> result = businessResponse.getDocs();

                BusinessAdapter adapter = new BusinessAdapter(result , Glide.with(getView()), getContext());

                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rvList.setLayoutManager(verticalLayoutManager);
                rvList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<BusinessNYT> call, Throwable t) {
                // TODO : if time, throw exception in differente case (item : preformated with img and text to inform)
                Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
            }
        });
    }
}
