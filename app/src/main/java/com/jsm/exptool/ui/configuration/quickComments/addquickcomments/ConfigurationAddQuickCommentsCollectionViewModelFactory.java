package com.jsm.exptool.ui.configuration.quickComments.addquickcomments;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.QuickCommentsCollection;
import com.jsm.exptool.ui.experiments.perform.ExperimentPerformViewModel;


public class ConfigurationAddQuickCommentsCollectionViewModelFactory implements ViewModelProvider.Factory {

    private Application app;
    private QuickCommentsCollection collection;


    public ConfigurationAddQuickCommentsCollectionViewModelFactory(Application app, QuickCommentsCollection collection) {
        this.app = app;
        this.collection = collection;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == ConfigurationAddQuickCommentsCollectionViewModel.class) {
            return (T) new ConfigurationAddQuickCommentsCollectionViewModel(app, collection);
        }else{
            return null;
        }
    }
}
