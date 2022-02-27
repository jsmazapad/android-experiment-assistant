package com.jsm.exptool.data.database.typeconverters;

import androidx.room.TypeConverter;

import com.jsm.exptool.libs.camera.CameraProvider;
import com.jsm.exptool.model.Experiment;

public class FlashModesConverter {

        @TypeConverter
        public static int fromEnum(CameraProvider.FlashModes value) {
            return value == null ? -1 : value.ordinal();
        }

        @TypeConverter
        public static CameraProvider.FlashModes toEnum(int value) {
            CameraProvider.FlashModes [] values = CameraProvider.FlashModes.values();
            //Si no tiene valor en BD le colocamos el primero por defecto ya que se entiende que está creado
            return value >= 0 && value < values.length  ? values[value] : values[0];
        }
}
