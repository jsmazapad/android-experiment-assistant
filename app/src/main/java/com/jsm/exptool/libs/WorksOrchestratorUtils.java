package com.jsm.exptool.libs;

import androidx.work.Data;

import java.util.Map;

public class WorksOrchestratorUtils {

    public static Data createInputData(Map<String, Object> valuesMap) {
        Data.Builder builder = new Data.Builder();
        for (Map.Entry<String, Object> entry : valuesMap.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                if (entry.getValue() instanceof String) {
                    builder.putString(entry.getKey(), (String) entry.getValue());
                } else if (entry.getValue() instanceof Integer) {
                    builder.putInt(entry.getKey(), (Integer) entry.getValue());
                } else if (entry.getValue() instanceof Double) {
                    builder.putDouble(entry.getKey(), (Double) entry.getValue());
                } else if (entry.getValue() instanceof Float) {
                    builder.putFloat(entry.getKey(), (Float) entry.getValue());
                } else if (entry.getValue() instanceof Long) {
                    builder.putLong(entry.getKey(), (Long) entry.getValue());
                } else if (entry.getValue() instanceof Float[]) {
                    builder.putFloatArray(entry.getKey(), (float[]) entry.getValue());
                } else if (entry.getValue() instanceof String[]) {
                    builder.putStringArray(entry.getKey(), (String[]) entry.getValue());
                }
            }
        }
        return builder.build();
    }
}
