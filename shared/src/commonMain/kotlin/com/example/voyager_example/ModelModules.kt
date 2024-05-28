package com.example.voyager_example

import com.example.voyager_example.vm.CalendarViewModel
import com.example.voyager_example.vm.HomeViewModel
import com.example.voyager_example.vm.MineViewModel
import com.example.voyager_example.vm.SplashViewModel
import com.example.voyager_example.vm.StudyViewModel
import com.example.voyager_example.vm.TabViewModel
import com.example.voyager_example.vm.mooc.CourseTopListViewModel
import com.example.voyager_example.vm.tabs.home.HomeCertificateViewModel
import com.example.voyager_example.vm.tabs.home.HomeDegreeViewModel
import com.example.voyager_example.vm.tabs.home.HomeMoocViewModel
import com.example.voyager_example.vm.tabs.home.HomeRecommendViewModel
import com.example.voyager_example.vm.tabs.study.StudyCertificateViewModel
import com.example.voyager_example.vm.tabs.study.StudyDegreeViewModel
import com.example.voyager_example.vm.tabs.study.StudyMoocViewModel
import org.koin.dsl.module


val modelModule = module {
    // 创建一个单例的TabViewModel
    single {
        TabViewModel()
    }
    single {
        HomeViewModel()
    }

    single {
        HomeRecommendViewModel()
    }
    single {
        HomeMoocViewModel()
    }
    single {
        HomeDegreeViewModel()
    }
    single {
        HomeCertificateViewModel()
    }



    single {
        SplashViewModel()
    }


    factory {
        StudyMoocViewModel()
    }
    factory {
        StudyDegreeViewModel()
    }
    factory {
        StudyCertificateViewModel()
    }


    factory {
        CourseTopListViewModel()
    }

    factory {
        CalendarViewModel()
    }
    factory {
        StudyViewModel()
    }

    factory {
        MineViewModel()
    }
}