package com.gahee.countryflags.di;


import com.gahee.countryflags.model.CountriesService;
import com.gahee.countryflags.viewmodel.ListViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {
    //Module 과 Injection location 사이의 다리를 바련한다.
    void inject(CountriesService countriesService);
    //어디다가 Module 을 inject 하는지 알려주는 것

    void inject(ListViewModel listViewModel);
}
