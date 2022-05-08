package com.jsm.exptool.providers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.jsm.exptool.R;
import com.jsm.exptool.databinding.ExperimentsListDialogMenuActionsBinding;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.ui.experiments.list.ExperimentsListViewModel;

public class ExperimentResumeAndActionsDialogProvider {

    public static void createActionsDialog(Context context, Experiment experiment, ExperimentsListViewModel viewModel) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ExperimentsListDialogMenuActionsBinding binding = ExperimentsListDialogMenuActionsBinding.inflate(layoutInflater);
        binding.setViewModel(viewModel);
        View mView = binding.getRoot();
        AlertDialog alertDialog;

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setView(mView);
        mBuilder.setNegativeButton(R.string.default_modal_closeButton, (dialog, which) -> {
        });
        alertDialog = mBuilder.create();


        binding.titleTV.setText(experiment.getTitle());
        binding.descriptionTV.setText(experiment.getDescription());

        if (viewModel != null) {
            binding.initExperimentButton.setOnClickListener(v -> viewModel.initExperiment(context, experiment, alertDialog));
            binding.seeDataButton.setOnClickListener(v -> viewModel.viewExperimentData(context, experiment, alertDialog));
            binding.exportDataButton.setOnClickListener(v -> viewModel.exportExperiment(context, experiment, alertDialog));
            binding.syncButton.setOnClickListener(v -> viewModel.syncExperiment(context, experiment, alertDialog));
            binding.endExperimentButton.setOnClickListener(v -> viewModel.endExperiment(context,experiment,  alertDialog));
            binding.continueExperimentButton.setOnClickListener(v -> viewModel.continueExperiment(context, experiment, alertDialog));
            binding.deleteExperimentButton.setOnClickListener(v -> viewModel.deleteExperiment(context, experiment, alertDialog));

            binding.copyExperimentButton.setOnClickListener(v -> viewModel.createExperimentByCopyingExperimentConfig(context, experiment, alertDialog));

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

            alertDialog.show();
        }
    }
}
