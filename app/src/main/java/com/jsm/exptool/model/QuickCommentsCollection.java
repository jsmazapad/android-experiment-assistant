package com.jsm.exptool.model;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.jsm.exptool.model.register.CommentRegister;

import java.util.List;

@Entity(tableName = QuickCommentsCollection.TABLE_NAME)
public class QuickCommentsCollection {

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
}
