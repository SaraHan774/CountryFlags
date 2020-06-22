package com.gahee.countryflags.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gahee.countryflags.di.DaggerApiComponent;
import com.gahee.countryflags.model.CountriesService;
import com.gahee.countryflags.model.CountryModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {
    //list of countries

    //1. define variables
    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<>();
    //live data is an object that generates value
    //값들은 비동기적으로 생성된다. Observable 이다.
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public CountriesService countriesService;

    public ListViewModel(){
        super(); //super 를 호출해야 한다.
        DaggerApiComponent.create().inject(this);
    }

    private CompositeDisposable disposable = new CompositeDisposable();

    public void refresh(){
        fetchCountries();
    }

    private void fetchCountries() {
        loading.setValue(true); //로딩을 일단 true 로 해둔다.
        //기다리는 동안 loading spinner 를 볼 수 있도록 해준다.

        //백그라운드 스레드에서 돌아가는 동안 app process 가 죽을 수 있다.
        //메모리 로스를 막기 위해서 RxJava 의 컴포넌트 사용.
        //disposable 을 이용한다.
        disposable.add(
                countriesService.getCountries() //retrofit 으로 백엔드와 통신
                .subscribeOn(Schedulers.newThread()) //Single 을 백그라운드 스레드에서 매니징 한다.
                .observeOn(AndroidSchedulers.mainThread()) //Main 에서 응답을 받을 수 있도록 한다.
                .subscribeWith(new DisposableSingleObserver<List<CountryModel>>(){
                //Single 로 반환을 받으므로 Single Observer,
                //Disposable 에 추가하므로 Disposable ~ Observer
                    @Override
                    public void onSuccess(List<CountryModel> countryModels) {
                        countries.setValue(countryModels);
                        countryLoadError.setValue(false);
                        loading.setValue(false);
                        //TEST : 세개의 LiveData 값들을 확인한다.
                    }
                    @Override
                    public void onError(Throwable e) {
                        //백엔드에서 에러가 나면 여기로 콜백이 온다.
                        countryLoadError.setValue(true);
                        loading.setValue(false);
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
