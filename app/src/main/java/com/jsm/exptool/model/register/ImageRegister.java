package com.jsm.exptool.model.register;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Date;
import java.util.List;

@Entity(tableName = ImageRegister.TABLE_NAME, inheritSuperIndices = true)
public class ImageRegister extends MediaRegister {

    /** The name of the table. */
    public static final String TABLE_NAME = "imageRegisters";

    private List<Double> embedding;
    private boolean embeddingRemoteSynced;

    @Ignore
    public ImageRegister(String fileName, String fileDirectory, List<Double> embedding, boolean dataRemoteSynced, boolean fileRemoteSynced, boolean embeddingRemoteSynced, long experimentId, Date date) {
        super( experimentId, date, fileName, fileDirectory,   dataRemoteSynced, fileRemoteSynced);

        this.embedding = embedding;
        this.embeddingRemoteSynced = embeddingRemoteSynced;
    }

    public ImageRegister(long internalId, String fileName, String fileDirectory, List<Double> embedding, boolean dataRemoteSynced, boolean fileRemoteSynced, boolean embeddingRemoteSynced, long experimentId, Date date) {
        super(internalId, experimentId,  date, fileName, fileDirectory,   dataRemoteSynced, fileRemoteSynced);

        this.embedding = embedding;
        this.embeddingRemoteSynced = embeddingRemoteSynced;
    }

    public List<Double> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<Double> embedding) {
        this.embedding = embedding;
    }

    public boolean isEmbeddingRemoteSynced() {
        return embeddingRemoteSynced;
    }

    public void setEmbeddingRemoteSynced(boolean embeddingRemoteSynced) {
        this.embeddingRemoteSynced = embeddingRemoteSynced;
    }

}
