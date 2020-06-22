package com.gahee.countryflags.di;

import com.gahee.countryflags.model.CountriesApi;
import com.gahee.countryflags.model.CountriesService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private static final String BASE_URL = "https://raw.githubusercontent.com";

    //creates objects
    @Provides
    public CountriesApi provideCountriesApi(){
        return new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountriesApi.class);
    }

    //JSON 응답을 Java 객체로 변환한다.
    //Adapter Factory 는 List 를 RxJava 컴포넌트로 변환한다.
    //Single 로 바꾸는 것. Retrofit 이 Single 타입으로 응답을 변환하도록 도와주는 것.
    @Provides
    public CountriesService provideCountriesService(){
        return CountriesService.getInstance();
    }
}
