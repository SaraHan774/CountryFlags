package com.gahee.countryflags;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.gahee.countryflags.model.CountriesService;
import com.gahee.countryflags.model.CountryModel;
import com.gahee.countryflags.viewmodel.ListViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class ListViewModelTest {

    //1. countries service 를 ViewModel 에 inject 한다.
    //2. countries service 를 새로운 thread 에서 subscribe 하므로
    //백그라운드 스레드를 기다리지 않아 test 가 실패할 수 있다. 이를 처리해준다.
    //3. LiveData 값들을 확인한다.

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
    //task will be instant.

    @Mock
    CountriesService countriesService;

    @InjectMocks
    ListViewModel listViewModel = new ListViewModel();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void setUpRxSchedulers (){
        Scheduler immediate = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(
                        Runnable::run
                        , true);
            }
        };
        RxJavaPlugins.setInitNewThreadSchedulerHandler(schedulerCallable -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> immediate);
    }

    private Single<List<CountryModel>> testSingle;

    @Test
    public void getCountiesSuccess(){
        CountryModel countryModel = new CountryModel(
                "countryName", "capital", "flag"
        );

        ArrayList<CountryModel> countryModels = new ArrayList<>();
        countryModels.add(countryModel);

        testSingle = Single.just(countryModels);

        Mockito.when(countriesService.getCountries()).thenReturn(testSingle);

        listViewModel.refresh();

        Assert.assertEquals(1, listViewModel.countries.getValue().size());
        Assert.assertEquals(false, listViewModel.countryLoadError.getValue());
        Assert.assertEquals(false, listViewModel.loading.getValue());
    }

    @Test
    public void getCountriesError(){
        //test single that returns throwable
        testSingle = Single.error(new Throwable());
        Mockito.when(countriesService.getCountries()).thenReturn(testSingle);
        listViewModel.refresh();
        Assert.assertEquals(true, listViewModel.countryLoadError.getValue());
        Assert.assertEquals(false, listViewModel.loading.getValue());
    }

}