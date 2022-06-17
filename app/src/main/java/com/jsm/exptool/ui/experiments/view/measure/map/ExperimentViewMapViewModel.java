package com.jsm.exptool.ui.experiments.view.measure.map;

import android.app.Application;

import androidx.annotation.NonNull;

import com.jsm.exptool.core.ui.base.BaseViewModel;
import com.jsm.exptool.entities.register.ExperimentRegister;

import java.util.List;

public class ExperimentViewMapViewModel extends BaseViewModel {

    private List<ExperimentRegister> registers;

    public ExperimentViewMapViewModel(@NonNull Application application) {
        super(application);
    }

    public ExperimentViewMapViewModel(@NonNull Application application, List<ExperimentRegister> registers) {
        super(application);
        this.registers = registers;
    }

    public List<ExperimentRegister> getRegisters() {
        return registers;
    }

    protected void setRegisters(List<ExperimentRegister> registers) {
        this.registers = registers;
    }
}
