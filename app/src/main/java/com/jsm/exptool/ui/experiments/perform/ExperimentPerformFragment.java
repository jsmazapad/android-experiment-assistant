package com.jsm.exptool.ui.experiments.perform;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.camera.view.PreviewView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerFragment;
import com.jsm.exptool.core.ui.baserecycler.OnRecyclerItemSelectedListener;
import com.jsm.exptool.data.mock.MockExamples;
import com.jsm.exptool.databinding.ExperimentPerformFragmentBinding;
import com.jsm.exptool.libs.PermissionResultCallbackForViewModel;
import com.jsm.exptool.model.CommentSuggestion;
import com.jsm.exptool.model.Experiment;

import com.jsm.exptool.providers.RequestPermissionsProvider;
import com.jsm.exptool.libs.requestpermissions.RequestPermissionsInterface;
import com.jsm.exptool.ui.main.MainActivity;

import java.util.List;

public class ExperimentPerformFragment extends BaseRecyclerFragment<ExperimentPerformFragmentBinding, ExperimentPerformViewModel> implements RequestPermissionsInterface {

    ActivityResultLauncher<String[]> cameraRequestPermissions;
    ActivityResultLauncher<String[]> audioRequestPermissions;
    MaterialAutoCompleteTextView autoCompleteTextView;
    PreviewView previewView;


    PermissionResultCallbackForViewModel cameraPermissionsResultCallback = new PermissionResultCallbackForViewModel() {
        @Override
        public void onPermissionsAccepted() {
           viewModel.onCameraPermissionsAccepted(ExperimentPerformFragment.this);
        }

        @Override
        public void onPermissionsError(List<String> rejectedPermissions) {
            viewModel.onPermissionsError(rejectedPermissions, ExperimentPerformFragment.this);
        }
    };

    PermissionResultCallbackForViewModel audioPermissionsResultCallback = new PermissionResultCallbackForViewModel() {
        @Override
        public void onPermissionsAccepted() {
            viewModel.onAudioPermissionsAccepted(ExperimentPerformFragment.this);
        }

        @Override
        public void onPermissionsError(List<String> rejectedPermissions) {
            viewModel.onPermissionsError(rejectedPermissions, ExperimentPerformFragment.this);
        }
    };



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inicializamos los requestPermissions
        cameraRequestPermissions = RequestPermissionsProvider.registerForCameraPermissions(this, cameraPermissionsResultCallback);
        audioRequestPermissions = RequestPermissionsProvider.registerForAudioPermissions(this, audioPermissionsResultCallback);

    }

    @Override
    protected ExperimentPerformViewModel createViewModel() {
        //TODO Código pruebas, comentar

        Experiment experiment = MockExamples.registerExperimentForPerformanceTest();
        //Experiment experiment = ExperimentPerformFragmentArgs.fromBundle(getArguments()).getExperiment();

        return new ViewModelProvider(this, new ExperimentPerformViewModelFactory(getActivity().getApplication(), experiment)).get(ExperimentPerformViewModel.class);
    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.experiment_perform_sensor_list_item;
    }

    @Override
    protected ExperimentPerformFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
         return DataBindingUtil.inflate(inflater, R.layout.experiment_perform_fragment, container, false);
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new ExperimentPerformAdapter(getContext(), viewModel, viewModel.getElements(), ((BaseActivity)getActivity()).getNavController(), getListItemResourceId());
    }

    @Override
    public void executeExtraActionsInsideBindingInit() {
        super.executeExtraActionsInsideBindingInit();
        autoCompleteTextView = binding.commentsAutoCompleteTV;
        autoCompleteTextView.setThreshold(0);
        previewView = binding.cameraPreview;
        cameraPermissionsResultCallback.setViewModel(viewModel);
        viewModel.initCameraProvider(getContext(), getViewLifecycleOwner(), this, binding.getRoot().findViewById(R.id.cameraPreview));
        viewModel.initAudioProvider(getContext(), getViewLifecycleOwner(), this);
        viewModel.initWorkInfoObservers(getViewLifecycleOwner());
        viewModel.getSuggestions().observe(getViewLifecycleOwner(), elements ->{
            if (elements != null){
                ArrayAdapter<CommentSuggestion> adapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, elements);
                autoCompleteTextView.setAdapter(adapter);
            }
        });

        //En caso de que el experimento tenga imágenes se observa el estado del elemento previewView para saber cuando ha terminado de cargar
        if(viewModel.getImageCardEnabled().getValue()) {
            previewView.getPreviewStreamState().observe(getViewLifecycleOwner(), streamState -> {

                viewModel.setIsLoading(!PreviewView.StreamState.STREAMING.equals(streamState));
            });
        }

    }

    @Override
    protected void setupRecyclerView() {
        super.setupRecyclerView();
        viewModel.getUpdateElementInRecycler().observe(getViewLifecycleOwner(), position ->{
            if (position != null){
                recyclerView.getAdapter().notifyItemChanged(position);
            }
        });

        //Seteo del recyclerView para los comentarios rápidos
        binding.quickCommentsRV.setAdapter(new QuickCommentsAdapter(getContext(), (position, navController, c) -> viewModel.onQuickCommentSelected(position), viewModel.getQuickComments(), ((MainActivity)getActivity()).getNavController(), R.layout.quick_comment_list_item));
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        //layoutManager.setJustifyContent(JustifyContent.FLEX_END);
        binding.quickCommentsRV.setLayoutManager(layoutManager);
    }

    @Override
    public void requestPermissions(){
        viewModel.handleRequestPermissions(this);
    }


}