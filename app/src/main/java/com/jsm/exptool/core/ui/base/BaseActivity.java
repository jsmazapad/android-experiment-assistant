package com.jsm.exptool.core.ui.base;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.jsm.exptool.R;
import com.jsm.exptool.ui.main.MainViewModel;

/**
 * Clase base para Activities con jetpack navigation
 */
public abstract class BaseActivity<VM extends ViewModel> extends AppCompatActivity {

    private NavController navController;
    protected VM viewModel;
    public VM getViewModel() {
        return this.viewModel;
    }
    public NavController getNavController() {
        return navController;
    }


    @Override
    protected void onResume() {
        super.onResume();
        navController = Navigation.findNavController(this, getNavigationDiagramResource());

    }

    /**
     * Proporciona el diagrama de navegación que usa el activity
     * @return El recurso de navegación del activity
     */
    protected abstract int getNavigationDiagramResource();

}
