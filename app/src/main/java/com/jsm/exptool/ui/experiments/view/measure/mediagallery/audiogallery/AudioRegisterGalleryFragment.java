package com.jsm.exptool.ui.experiments.view.measure.mediagallery.audiogallery;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.databinding.ExperimentViewGalleryAudioFragmentBinding;
import com.jsm.exptool.entities.register.ExperimentRegister;
import com.jsm.exptool.ui.experiments.view.measure.ExperimentViewRegistersFragment;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.MediaRegisterGalleryFragment;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.MediaRegisterGalleryViewModelFactory;

import java.util.List;


public class AudioRegisterGalleryFragment extends MediaRegisterGalleryFragment<ExperimentViewGalleryAudioFragmentBinding, AudioRegisterGalleryViewModel> {


    @Override
    protected ExperimentViewGalleryAudioFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiment_view_gallery_audio_fragment, container, false);
    }

    @Override
    protected AudioRegisterGalleryAdapter createAdapter() {
        return new AudioRegisterGalleryAdapter(getContext(), viewModel, viewModel.getElements(), ((BaseActivity)this.getActivity()).getNavController(),getListItemResourceId());
    }

    @Override
    protected AudioRegisterGalleryViewModel createViewModel() {

        List<ExperimentRegister> registers =((ExperimentViewRegistersFragment)getParentFragment()).getViewModel().getElements().getValue();

        return new ViewModelProvider(this, new MediaRegisterGalleryViewModelFactory(getActivity().getApplication(), registers)).get(AudioRegisterGalleryViewModel.class);
    }

    @Override
    public void executeExtraActionsInsideBindingInit() {
        super.executeExtraActionsInsideBindingInit();
        // Instantiate the player.
        viewModel.initPlayer( getContext(),  binding.mediaPV);

        viewModel.getFileSelectedPath().observe(getViewLifecycleOwner(), path -> {
            viewModel.loadAudio(path, getContext());
        });
    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.experiment_view_gallery_audio_list_item;
    }

    @Override
    protected void setupRecyclerView() {
        super.setupRecyclerView();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));

    }
}
