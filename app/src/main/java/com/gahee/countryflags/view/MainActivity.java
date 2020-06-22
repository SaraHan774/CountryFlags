package com.gahee.countryflags.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gahee.countryflags.R;
import com.gahee.countryflags.viewmodel.ListViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_country_list)
    RecyclerView countriesList;
    @BindView(R.id.tv_list_error)
    TextView listError;
    @BindView(R.id.progress_bar)
    ProgressBar loadingView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;


    private ListViewModel listViewModel;
    private CountryListAdapter countryListAdapter = new CountryListAdapter(
            new ArrayList<>()
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        listViewModel = ViewModelProviders
                .of(this)
                .get(ListViewModel.class);

        listViewModel.refresh();

        countriesList.setLayoutManager(new LinearLayoutManager(this));
        countriesList.setAdapter(countryListAdapter);

        refreshLayout.setOnRefreshListener(() -> {
           //replace with lambda
            listViewModel.refresh();
            //make the spinner disappear
            refreshLayout.setRefreshing(false);
        });

        observeViewModel();
    }

    private void observeViewModel() {
        listViewModel.countries.observe(this,
                countryModels -> {
                    if(countryModels != null){
                        countriesList.setVisibility(View.VISIBLE);
                        countryListAdapter.updateCountries(countryModels);
                    }
                });
        listViewModel.countryLoadError.observe(this, isError -> {
            if(isError != null){
                listError.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });
        listViewModel.loading.observe(this, isLoading -> {
            if(isLoading != null){
                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if(isLoading){
                    listError.setVisibility(View.GONE);
                    countriesList.setVisibility(View.GONE);
                }
            }
        });
    }
}