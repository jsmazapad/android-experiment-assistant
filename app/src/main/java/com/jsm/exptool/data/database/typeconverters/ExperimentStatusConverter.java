package com.jsm.exptool.data.database.typeconverters;

import androidx.room.TypeConverter;

import com.jsm.exptool.entities.Experiment;

public class ExperimentStatusConverter {

        @TypeConverter
        public static int fromEnum(Experiment.ExperimentStatus value) {
            return value == null ? -1 : value.ordinal();
        }

        @TypeConverter
        public static Experiment.ExperimentStatus toEnum(int value) {
            Experiment.ExperimentStatus [] values = Experiment.ExperimentStatus.values();
            //Si no tiene valor en BD le colocamos el primero por defecto ya que se entiende que está creado
            return value >= 0 && value < values.length  ? values[value] : values[0];
        }
}
