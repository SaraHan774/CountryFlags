package com.gahee.countryflags.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gahee.countryflags.R;
import com.gahee.countryflags.model.CountryModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> {

    private List<CountryModel> countries;
    public CountryListAdapter(List<CountryModel> countries){
        this.countries = countries;
    }

    public void updateCountries(List<CountryModel> newCountries){
        countries.clear();
        countries.addAll(newCountries);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.country_view_holder, parent, false);

        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        holder.bind(countries.get(position));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img_country_flag)
        ImageView countryImage;
        @BindView(R.id.tv_country_name)
        TextView countryName;
        @BindView(R.id.tv_country_capital)
        TextView countryCapital;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(CountryModel countryModel){
            countryName.setText(countryModel.getCountryName());
            countryCapital.setText(countryModel.getCapital());
            //글라이드를 통해서 백그라운드 스레드에서 이미지가 로드 될 것이다.
            Util.loadImage(
                    countryImage,
                    countryModel.getFlag(),
                    Util.getProgressDrawable(countryImage.getContext())
            );
            Log.d("GLIDE_DEBUG", countryModel.getFlag());
        }
    }
}
