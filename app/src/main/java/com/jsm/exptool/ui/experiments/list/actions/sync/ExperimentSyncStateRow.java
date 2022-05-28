package com.jsm.exptool.ui.experiments.list.actions.sync;

public class ExperimentSyncStateRow {

    private String typeTitle;
    private String resultExplanation;
    private boolean successful;

    public ExperimentSyncStateRow(String typeTitle, String resultExplanation, boolean successful) {
        this.typeTitle = typeTitle;
        this.resultExplanation = resultExplanation;
        this.successful = successful;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public String getResultExplanation() {
        return resultExplanation;
    }

    public void setResultExplanation(String resultExplanation) {
        this.resultExplanation = resultExplanation;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
