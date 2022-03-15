package com.jsm.exptool.ui.about;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.databinding.AboutFragmentBinding;

public class AboutFragment extends BaseFragment<AboutFragmentBinding, AboutViewModel> {


    @Override
    protected AboutViewModel createViewModel() {
        return new ViewModelProvider(this).get(AboutViewModel.class);
    }

    @Override
    protected AboutFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.about_fragment, container, false);
    }
}