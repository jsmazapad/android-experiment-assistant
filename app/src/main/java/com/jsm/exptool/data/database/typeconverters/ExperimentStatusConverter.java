package com.jsm.exptool.data.database.typeconverters;

import androidx.room.TypeConverter;

import com.jsm.exptool.model.Experiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ExperimentStatusConverter {

        @TypeConverter
        public static int fromEnum(Experiment.ExperimentStatus value) {
            return value == null ? -1 : value.ordinal();
        }

        @TypeConverter
        public static Experiment.ExperimentStatus toEnum(int value) {
            Experiment.ExperimentStatus [] values = Experiment.ExperimentStatus.values();
            return value >= 0 && value < values.length  ? null : values[value];
        }
}
