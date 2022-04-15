package com.jsm.exptool.ui.experiments.view.measure.mediagallery.imagegallery;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.databinding.ExperimentViewGalleryImageFragmentBinding;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.ui.experiments.view.measure.ExperimentViewRegistersFragment;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.MediaRegisterGalleryFragment;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.MediaRegisterGalleryViewAdapter;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.MediaRegisterGalleryViewModelFactory;


import java.util.List;


public class ImageRegisterGalleryFragment extends MediaRegisterGalleryFragment<ExperimentViewGalleryImageFragmentBinding, ImageRegisterGalleryViewModel> {


    @Override
    protected ExperimentViewGalleryImageFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiment_view_gallery_image_fragment, container, false);
    }

    @Override
    protected ImageRegisterGalleryAdapter createAdapter() {
        return new ImageRegisterGalleryAdapter(getContext(), viewModel, viewModel.getElements(), ((BaseActivity)this.getActivity()).getNavController(),getListItemResourceId());
    }

    @Override
    protected ImageRegisterGalleryViewModel createViewModel() {

        List<ExperimentRegister> registers =((ExperimentViewRegistersFragment)getParentFragment()).getViewModel().getElements().getValue();

        return new ViewModelProvider(this, new MediaRegisterGalleryViewModelFactory(getActivity().getApplication(), registers)).get(ImageRegisterGalleryViewModel.class);
    }

    @Override
    public void executeExtraActionsInsideBindingInit() {
        super.executeExtraActionsInsideBindingInit();
        viewModel.getFileSelectedPath().observe(getViewLifecycleOwner(), path -> {
            viewModel.loadFullImage(path, binding.fullImageIV);
        });
    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.experiment_view_gallery_image_list_item;
    }

    @Override
    protected void setupRecyclerView() {
        super.setupRecyclerView();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

    }
}
