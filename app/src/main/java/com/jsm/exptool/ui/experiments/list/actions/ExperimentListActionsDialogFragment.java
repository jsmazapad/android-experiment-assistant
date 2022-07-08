package com.jsm.exptool.ui.experiments.list.actions;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.R;
import com.jsm.exptool.databinding.ExperimentsListDialogMenuActionsBinding;
import com.jsm.exptool.entities.Experiment;

public class ExperimentListActionsDialogFragment extends DialogFragment {
    Context context;
    ExperimentListActionsDialogViewModel viewModel;
    Experiment experiment;

    public ExperimentListActionsDialogFragment(Experiment experiment) {
        this.experiment = experiment;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getActivity();
        this.viewModel = new ViewModelProvider(this, new ExperimentListActionDialogViewModelFactory(getActivity().getApplication(), experiment)).get(ExperimentListActionsDialogViewModel.class);
        LayoutInflater layoutInflater = getLayoutInflater();
        ExperimentsListDialogMenuActionsBinding binding = ExperimentsListDialogMenuActionsBinding.inflate(layoutInflater);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        View mView = binding.getRoot();


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(mView);
        alertDialogBuilder.setNegativeButton(R.string.default_modal_closeButton, (dialog, which) -> {
            viewModel.cancelPendingWorks();
        });


        binding.titleTV.setText(experiment.getTitle());
        binding.descriptionTV.setText(experiment.getDescription());

        if (viewModel != null) {
            binding.initExperimentButton.setOnClickListener(v -> viewModel.initExperiment(context, experiment, this.getDialog()));
            binding.seeDataButton.setOnClickListener(v -> viewModel.viewExperimentData(context, experiment, this.getDialog()));
            binding.exportDataButton.setOnClickListener(v -> viewModel.exportExperiment(context, experiment, this.getDialog()));
            binding.syncButton.setOnClickListener(v -> viewModel.syncExperiment(context, experiment, this.getDialog()));
            binding.endExperimentButton.setOnClickListener(v -> viewModel.endExperiment(context,experiment,  this.getDialog()));
            binding.continueExperimentButton.setOnClickListener(v -> viewModel.continueExperiment(context, experiment, this.getDialog()));
            binding.deleteExperimentButton.setOnClickListener(v -> viewModel.deleteExperiment(context, experiment, this.getDialog()));
            binding.copyExperimentButton.setOnClickListener(v -> viewModel.createExperimentByCopyingExperimentConfig(context, experiment, this.getDialog()));

            switch (experiment.getStatus()) {
                case CREATED:
                    binding.initExperimentButton.setVisibility(View.VISIBLE);
                    binding.seeDataButton.setVisibility(View.GONE);
                    binding.exportDataButton.setVisibility(View.GONE);
                    binding.syncLL.setVisibility(View.GONE);
                    binding.endLL.setVisibility(View.GONE);
                    binding.continueExperimentButton.setVisibility(View.GONE);
                    binding.deleteExperimentButton.setVisibility(View.VISIBLE);
                    break;
                case INITIATED:
                    binding.initExperimentButton.setVisibility(View.GONE);
                    binding.seeDataButton.setVisibility(View.VISIBLE);
                    binding.exportDataButton.setVisibility(View.GONE);
                    binding.syncLL.setVisibility(View.GONE);
                    binding.endLL.setVisibility(View.VISIBLE);
                    binding.continueExperimentButton.setVisibility(View.VISIBLE);
                    binding.deleteExperimentButton.setVisibility(View.VISIBLE);
                    break;
                case FINISHED:
                    binding.initExperimentButton.setVisibility(View.GONE);
                    binding.seeDataButton.setVisibility(View.VISIBLE);
                    binding.exportDataButton.setVisibility(View.VISIBLE);
                    binding.syncLL.setVisibility(experiment.isSyncPending() || experiment.isEmbeddingPending() ? View.VISIBLE : View.GONE);
                    binding.endLL.setVisibility(View.GONE);
                    binding.continueExperimentButton.setVisibility(View.VISIBLE);
                    binding.deleteExperimentButton.setVisibility(View.VISIBLE);

            }

        }

        AlertDialog dialog = alertDialogBuilder.create();

        this.viewModel.initObservers(this, this.getContext(), dialog);

        return dialog;
    }

    public static ExperimentListActionsDialogFragment newInstance(Experiment experiment) {
        ExperimentListActionsDialogFragment f = new ExperimentListActionsDialogFragment(experiment);
        return f;
    }

}

