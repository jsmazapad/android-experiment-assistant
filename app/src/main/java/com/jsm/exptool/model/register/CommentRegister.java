package com.jsm.exptool.model.register;

import android.os.Parcel;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Date;

@Entity(tableName = CommentRegister.TABLE_NAME, inheritSuperIndices = true)
public class CommentRegister extends ExperimentRegister{

    protected String comment;

    /** The name of the table. */
    public static final String TABLE_NAME = "commentRegisters";


    public CommentRegister(long internalId, long experimentId, Date date, boolean dataRemoteSynced, String comment) {
        super(internalId, experimentId, date, dataRemoteSynced);
        this.comment = comment;
    }

    @Ignore
    public CommentRegister(long experimentId, Date date, boolean dataRemoteSynced, String comment) {
        super(experimentId, date, dataRemoteSynced);
        this.comment = comment;

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.comment);
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        this.comment = source.readString();
    }

    protected CommentRegister(Parcel in) {
        super(in);
        this.comment = in.readString();
    }

    public static final Creator<CommentRegister> CREATOR = new Creator<CommentRegister>() {
        @Override
        public CommentRegister createFromParcel(Parcel source) {
            return new CommentRegister(source);
        }

        @Override
        public CommentRegister[] newArray(int size) {
            return new CommentRegister[size];
        }
    };
}
