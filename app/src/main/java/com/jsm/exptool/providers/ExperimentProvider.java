package com.jsm.exptool.providers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.jsm.exptool.R;
import com.jsm.exptool.databinding.ExperimentsListDialogMenuActionsBinding;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.repositories.AudioRepository;
import com.jsm.exptool.repositories.CommentRepository;
import com.jsm.exptool.repositories.ExperimentsRepository;
import com.jsm.exptool.repositories.ImagesRepository;
import com.jsm.exptool.repositories.SensorsRepository;

public class ExperimentProvider {

    public static String getTranslatableStringFromExperimentStatus(@NonNull Experiment.ExperimentStatus status, @NonNull Context context){
        String stringToReturn = "";
        switch (status){
            case CREATED:
                stringToReturn = context.getString(R.string.experiment_filter_name_create);
                break;
            case INITIATED:
                stringToReturn = context.getString(R.string.experiment_filter_name_init);
                break;
            case FINISHED:
                stringToReturn = context.getString(R.string.experiment_filter_name_finished);
                break;
        }
        return stringToReturn;
    }

    public static long endExperiment(Experiment experiment){
        experiment.setStatus(Experiment.ExperimentStatus.FINISHED);
        return ExperimentsRepository.updateExperiment(experiment);
    }

    public static void createActionsDialog(Context context, Experiment experiment, ExperimentActionsInterface experimentActions) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ExperimentsListDialogMenuActionsBinding binding = ExperimentsListDialogMenuActionsBinding.inflate(layoutInflater);
        View mView = binding.getRoot();
        binding.titleTV.setText(experiment.getTitle());
        binding.descriptionTV.setText(experiment.getDescription());
        ExperimentConfiguration configuration = experiment.getConfiguration();
        if (configuration != null) {
            binding.hasAudio.setText(context.getString(experiment.getConfiguration().isAudioEnabled() ? R.string.yes_configured : R.string.no_configured));
            if (configuration.isAudioEnabled()) {
                binding.numAudio.setText(String.format(context.getString(R.string.num_registers_format), AudioRepository.countRegistersByExperimentId(experiment.getInternalId())));
            }

            int numImages = 0;
            binding.hasImages.setText(context.getString(experiment.getConfiguration().isCameraEnabled() ? R.string.yes_configured : R.string.no_configured));
            if (configuration.isCameraEnabled()) {
                numImages = ImagesRepository.countRegistersByExperimentId(experiment.getInternalId());
                binding.numImages.setText(String.format(context.getString(R.string.num_registers_format), numImages));
            }

            binding.hasEmbedding.setText(context.getString(experiment.getConfiguration().isEmbeddingEnabled() ? R.string.yes_configured : R.string.no_configured));
            if (configuration.isEmbeddingEnabled()) {
                binding.numEmbedding.setText(String.format(context.getString(R.string.num_registers_format), numImages - ImagesRepository.countImagesWithoutEmbeddingsByExperimentId(experiment.getInternalId())));
            }

            binding.hasSensors.setText(context.getString(experiment.getConfiguration().isSensorEnabled() ? R.string.yes_configured : R.string.no_configured));
            if (configuration.isSensorEnabled()) {
                binding.numSensors.setText(String.format(context.getString(R.string.num_registers_format), SensorsRepository.countRegistersByExperimentId(experiment.getInternalId())));
            }
            binding.hasComments.setText(context.getString(R.string.yes_configured));
            binding.numComments.setText(String.format(context.getString(R.string.num_registers_format), CommentRepository.countRegistersByExperimentId(experiment.getInternalId())));

        }
        if (experimentActions != null) {
            binding.initExperimentButton.setOnClickListener(v -> {
                experimentActions.initExperiment(context, experiment);
            });
            binding.seeDataButton.setOnClickListener(v -> {
                experimentActions.viewExperimentData(context, experiment);
            });
            binding.exportDataButton.setOnClickListener(v -> {
                experimentActions.exportExperiment(context, experiment);
            });
            binding.syncButton.setOnClickListener(v -> {
                experimentActions.syncExperiment(context, experiment);
            });
            binding.endExperimentButton.setOnClickListener(v -> {
                experimentActions.endExperiment(context, experiment);
            });
            binding.continueExperimentButton.setOnClickListener(v -> {
                experimentActions.continueExperiment(context, experiment);
            });
            binding.deleteExperimentButton.setOnClickListener(v -> {
                experimentActions.deleteExperiment(context, experiment);
            });

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
                    binding.continueExperimentButton.setVisibility(View.GONE);
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


            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
            mBuilder.setView(mView);
            mBuilder.setNegativeButton(R.string.default_modal_closeButton, (dialog, which) -> {
            });

            AlertDialog alertDialog = mBuilder.create();
            alertDialog.show();
        }
    }
}
