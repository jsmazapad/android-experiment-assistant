package com.jsm.exptool.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = QuickCommentsCollection.TABLE_NAME)
public class QuickCommentsCollection implements Parcelable {

    /** The name of the table. */
    public static final String TABLE_NAME = "quickCommentsCollections";
    /**
     * The name of the ID column.
     */
    public static final String COLUMN_ID = BaseColumns._ID;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    protected long internalId;

    private String name;
    private List<String> quickComments;

    @Ignore
    public QuickCommentsCollection(String name, List<String> quickComments) {
        this.name = name;
        this.quickComments = quickComments;
    }

    public QuickCommentsCollection(long internalId, String name, List<String> quickComments) {
        this.internalId = internalId;
        this.name = name;
        this.quickComments = quickComments;
    }

    public long getInternalId() {
        return internalId;
    }

    public void setInternalId(long internalId) {
        this.internalId = internalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getQuickComments() {
        return quickComments;
    }

    public void setQuickComments(List<String> quickComments) {
        this.quickComments = quickComments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.internalId);
        dest.writeString(this.name);
        dest.writeStringList(this.quickComments);
    }

    public void readFromParcel(Parcel source) {
        this.internalId = source.readLong();
        this.name = source.readString();
        this.quickComments = source.createStringArrayList();
    }
    @Ignore
    protected QuickCommentsCollection(Parcel in) {
        readFromParcel(in);
    }

    public static final Parcelable.Creator<QuickCommentsCollection> CREATOR = new Parcelable.Creator<QuickCommentsCollection>() {
        @Override
        public QuickCommentsCollection createFromParcel(Parcel source) {
            return new QuickCommentsCollection(source);
        }

        @Override
        public QuickCommentsCollection[] newArray(int size) {
            return new QuickCommentsCollection[size];
        }
    };
}
