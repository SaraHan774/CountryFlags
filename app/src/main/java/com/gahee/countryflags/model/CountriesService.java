package com.gahee.countryflags.model;

import com.gahee.countryflags.di.DaggerApiComponent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CountriesService {

    private static CountriesService instance;

    //creation 과 use of the object 를 분리하자
    @Inject
    public CountriesApi api;

    private CountriesService(){
        //interface ApiComponent 의 이름과 비슷하게 만들어짐
        DaggerApiComponent.create().inject(this);
    }
    //Singleton
    public static CountriesService getInstance(){
        if(instance == null){
            instance = new CountriesService();
        }
        return instance;
    }

    public Single<List<CountryModel>> getCountries(){
        return api.getCountries();
    }
}
