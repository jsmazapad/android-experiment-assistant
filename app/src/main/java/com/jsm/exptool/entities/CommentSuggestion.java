package com.jsm.exptool.entities;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = CommentSuggestion.TABLE_NAME)
public class CommentSuggestion {

    /** The name of the table. */
    public static final String TABLE_NAME = "commentSuggestions";

    /**
     * The name of the ID column.
     */
    public static final String COLUMN_ID = BaseColumns._ID;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private long internalId;
    private int usedTimesCounter;
    private String comment;

    public CommentSuggestion(long internalId, int usedTimesCounter, String comment) {
        this.internalId = internalId;
        this.usedTimesCounter = usedTimesCounter;
        this.comment = comment;
    }

    @Ignore
    public CommentSuggestion(int usedTimesCounter, String comment) {
        this.usedTimesCounter = usedTimesCounter;
        this.comment = comment;
    }

    public long getInternalId() {
        return internalId;
    }

    public void setInternalId(long internalId) {
        this.internalId = internalId;
    }

    public int getUsedTimesCounter() {
        return usedTimesCounter;
    }

    public void setUsedTimesCounter(int order) {
        this.usedTimesCounter = order;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return comment;
    }
}
