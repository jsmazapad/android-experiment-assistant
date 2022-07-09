package com.jsm.exptool.ui.experiments.perform;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.camera.view.PreviewView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.jsm.exptool.R;
import com.jsm.exptool.core.libs.ModalMessage;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerFragment;
import com.jsm.exptool.databinding.ExperimentPerformFragmentBinding;
import com.jsm.exptool.entities.eventbus.WorkFinishedEvent;
import com.jsm.exptool.libs.requestpermissions.PermissionResultCallbackForViewModel;
import com.jsm.exptool.entities.CommentSuggestion;
import com.jsm.exptool.entities.Experiment;

import com.jsm.exptool.providers.RequestPermissionsProvider;
import com.jsm.exptool.libs.requestpermissions.RequestPermissionsInterface;
import com.jsm.exptool.ui.main.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class ExperimentPerformFragment extends BaseRecyclerFragment<ExperimentPerformFragmentBinding, ExperimentPerformViewModel> implements RequestPermissionsInterface {

    ActivityResultLauncher<String[]> cameraRequestPermissions;
    ActivityResultLauncher<String[]> audioRequestPermissions;
    ActivityResultLauncher<String[]> locationRequestPermissions;
    MaterialAutoCompleteTextView autoCompleteTextView;
    PreviewView previewView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    PermissionResultCallbackForViewModel cameraPermissionsResultCallback = new PermissionResultCallbackForViewModel() {
        @Override
        public void onPermissionsAccepted() {
           viewModel.onCameraPermissionsAccepted(ExperimentPerformFragment.this);
        }

        @Override
        public void onPermissionsError(List<String> rejectedPermissions) {
            viewModel.onCameraPermissionsRejected(rejectedPermissions, ExperimentPerformFragment.this);
        }
    };

    PermissionResultCallbackForViewModel audioPermissionsResultCallback = new PermissionResultCallbackForViewModel() {
        @Override
        public void onPermissionsAccepted() {
            viewModel.onAudioPermissionsAccepted(ExperimentPerformFragment.this);
        }

        @Override
        public void onPermissionsError(List<String> rejectedPermissions) {
            viewModel.onAudioPermissionsRejected(rejectedPermissions, ExperimentPerformFragment.this);
        }
    };

    PermissionResultCallbackForViewModel locationPermissionsResultCallback = new PermissionResultCallbackForViewModel() {
        @Override
        public void onPermissionsAccepted() {
            viewModel.onLocationPermissionsAccepted(ExperimentPerformFragment.this);
        }

        @Override
        public void onPermissionsError(List<String> rejectedPermissions) {
            viewModel.onLocationPermissionsRejected(rejectedPermissions, ExperimentPerformFragment.this);
        }
    };



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inicializamos los requestPermissions
        cameraRequestPermissions = RequestPermissionsProvider.registerForCameraPermissions(this, cameraPermissionsResultCallback);
        audioRequestPermissions = RequestPermissionsProvider.registerForAudioPermissions(this, audioPermissionsResultCallback);
        locationRequestPermissions = RequestPermissionsProvider.registerForLocationFinePermissions(this, locationPermissionsResultCallback);
        setHasOptionsMenu(true);
    }

    OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            if(viewModel.getExperimentInitiated().getValue() != null && viewModel.getExperimentInitiated().getValue()){
                // Para futuras versiones
                // ModalMessage.showModalMessage(getContext(), "Atención", "Si sale ahora el experimento no se finalizará, ¿está seguro?", "Salir", (dialog, which) -> requireActivity().onBackPressed(), null, null);
            }else{
                onBackPressedCallback.remove();
                requireActivity().onBackPressed();

            }
        }
    };
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  super.onCreateView(inflater, container, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);
        return v;
    }

    @Override
    protected ExperimentPerformViewModel createViewModel() {
        //TODO Código pruebas, comentar
        //Experiment experiment = MockExamples.registerExperimentForLocationTest();
        Experiment experiment = ExperimentPerformFragmentArgs.fromBundle(getArguments()).getExperiment();

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
        viewModel.initLocationProvider(getContext(), getViewLifecycleOwner(), this);
        viewModel.checkIfRemoteSyncOnlyInitIsNecessary(getContext());
        viewModel.initWorkInfoObservers(getViewLifecycleOwner());
        viewModel.getSuggestions().removeObservers(getViewLifecycleOwner());
        viewModel.getSuggestions().observe(getViewLifecycleOwner(), elements ->{
            if (elements != null){
                ArrayAdapter<CommentSuggestion> adapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, elements);
                autoCompleteTextView.setAdapter(adapter);
            }
        });

        //En caso de que el experimento tenga imágenes se observa el estado del elemento previewView para saber cuando ha terminado de cargar
        if(viewModel.getImageCardEnabled().getValue()) {
            previewView.getPreviewStreamState().removeObservers(getViewLifecycleOwner());
            previewView.getPreviewStreamState().observe(getViewLifecycleOwner(), streamState -> {

                viewModel.setIsLoading(!PreviewView.StreamState.STREAMING.equals(streamState));
            });
        }

        //Observamos la finalización del completado del experimento,
        //se dispara si es necesario esperar porque no se cancelen las tareas en background
        viewModel.getWaitEndedToCompleteExperiment().removeObservers(getViewLifecycleOwner());
        viewModel.getWaitEndedToCompleteExperiment().observe(getViewLifecycleOwner(), waitFinished ->{
            if(waitFinished != null && waitFinished == true) {
                viewModel.completeExperimentFinishing(getContext());
            }
        });

    }

    @Override
    protected void setupRecyclerView() {
        super.setupRecyclerView();
        viewModel.getUpdateElementInRecycler().removeObservers(getViewLifecycleOwner());
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

    //TODO Verificar comportamiento permisos al solicitar al inicio, verificar comportamiento app al no tener permisos
    @Override
    public void requestPermissions(){
        viewModel.handleRequestPermissions(this);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(WorkFinishedEvent event) {
        Log.d("EVENTO", "EVENTO");
        this.viewModel.onMessageEvent(event);

    }


}