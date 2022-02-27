package com.jsm.exptool.data.database.typeconverters;

import androidx.room.TypeConverter;

import com.jsm.exptool.providers.CameraProvider;

public class CameraPositionsConverter {

        @TypeConverter
        public static int fromEnum(CameraProvider.CameraPositions value) {
            return value == null ? -1 : value.ordinal();
        }

        @TypeConverter
        public static CameraProvider.CameraPositions toEnum(int value) {
            CameraProvider.CameraPositions [] values = CameraProvider.CameraPositions.values();
            //Si no tiene valor en BD le colocamos el primero por defecto ya que se entiende que está creado
            return value >= 0 && value < values.length  ? values[value] : values[0];
        }
}
