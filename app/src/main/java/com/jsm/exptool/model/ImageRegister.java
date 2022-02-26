package com.jsm.exptool.model;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.jsm.exptool.data.database.ListConverter;

import java.util.Date;
import java.util.List;

@Entity(tableName = ImageRegister.TABLE_NAME)
public class ImageRegister {

    /** The name of the table. */
    public static final String TABLE_NAME = "imageRegisters";

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private long internalId;

    private String imageName;
    private String imageDirectory;
    private List<Double> embedding;
    private boolean dataRemoteSynced;
    private boolean imageRemoteSynced;
    private boolean embeddingRemoteSynced;
    private long experimentId;
    private Date date;

    @Ignore
    public ImageRegister(String imageName, String imageDirectory, List<Double> embedding, boolean dataRemoteSynced, boolean imageRemoteSynced, boolean embeddingRemoteSynced, long experimentId, Date date) {
        this.imageName = imageName;
        this.imageDirectory = imageDirectory;
        this.embedding = embedding;
        this.dataRemoteSynced = dataRemoteSynced;
        this.imageRemoteSynced = imageRemoteSynced;
        this.embeddingRemoteSynced = embeddingRemoteSynced;
        this.experimentId = experimentId;
        this.date = date;
    }

    public ImageRegister(long internalId, String imageName, String imageDirectory, List<Double> embedding, boolean dataRemoteSynced, boolean imageRemoteSynced, boolean embeddingRemoteSynced, long experimentId, Date date) {
        this.internalId = internalId;
        this.imageName = imageName;
        this.imageDirectory = imageDirectory;
        this.embedding = embedding;
        this.dataRemoteSynced = dataRemoteSynced;
        this.imageRemoteSynced = imageRemoteSynced;
        this.embeddingRemoteSynced = embeddingRemoteSynced;
        this.experimentId = experimentId;
        this.date = date;
    }

    public long getInternalId() {
        return internalId;
    }

    public void setInternalId(long internalId) {
        this.internalId = internalId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<Double> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<Double> embedding) {
        this.embedding = embedding;
    }

    public String getImageDirectory() {
        return imageDirectory;
    }

    public void setImageDirectory(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }

    public boolean isDataRemoteSynced() {
        return dataRemoteSynced;
    }

    public void setDataRemoteSynced(boolean dataRemoteSynced) {
        this.dataRemoteSynced = dataRemoteSynced;
    }

    public boolean isImageRemoteSynced() {
        return imageRemoteSynced;
    }

    public void setImageRemoteSynced(boolean imageRemoteSynced) {
        this.imageRemoteSynced = imageRemoteSynced;
    }

    public boolean isEmbeddingRemoteSynced() {
        return embeddingRemoteSynced;
    }

    public void setEmbeddingRemoteSynced(boolean embeddingRemoteSynced) {
        this.embeddingRemoteSynced = embeddingRemoteSynced;
    }

    public long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(long experimentId) {
        this.experimentId = experimentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
