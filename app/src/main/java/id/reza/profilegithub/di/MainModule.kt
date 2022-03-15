package id.reza.profilegithub.di

import id.reza.profilegithub.ui.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { MainViewModel(get(), get()) }
}