package com.jsm.exptool.data.database.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.MySensor;

import java.util.List;

public class ExperimentWithSensors {
    @Embedded public Experiment experiment;
    @Relation(parentColumn = Experiment.COLUMN_ID,
    entityColumn = "experimentId")
    public List<MySensor> sensors;
    
}
