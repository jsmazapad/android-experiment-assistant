package com.jsm.exptool.model.register;

import android.os.Parcel;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.ArrayList;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeList(this.embedding);
        dest.writeByte(this.embeddingRemoteSynced ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        this.embedding = new ArrayList<Double>();
        source.readList(this.embedding, Double.class.getClassLoader());
        this.embeddingRemoteSynced = source.readByte() != 0;
    }

    protected ImageRegister(Parcel in) {
        super(in);
        this.embedding = new ArrayList<Double>();
        in.readList(this.embedding, Double.class.getClassLoader());
        this.embeddingRemoteSynced = in.readByte() != 0;
    }

    public static final Creator<ImageRegister> CREATOR = new Creator<ImageRegister>() {
        @Override
        public ImageRegister createFromParcel(Parcel source) {
            return new ImageRegister(source);
        }

        @Override
        public ImageRegister[] newArray(int size) {
            return new ImageRegister[size];
        }
    };
}
