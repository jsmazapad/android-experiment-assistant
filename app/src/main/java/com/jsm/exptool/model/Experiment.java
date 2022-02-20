package com.jsm.exptool.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;

@Entity(tableName = Experiment.TABLE_NAME)
public class Experiment implements Parcelable {

    /** The name of the table. */
    public static final String TABLE_NAME = "experiments";

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private int internalId;
    private int id;
    private int user_id;
    private String title;
    private String description;
    private Date init_date;
    private Date end_date;
    private int status;
    private ArrayList<Integer> sensors;
    private ArrayList<ImageRegister> images;
    private ArrayList<SoundRegister> sounds;
    private int sdk_device;
    private String device;


    public Experiment(int id, int user_id, String title, String description, Date init_date, Date end_date, int status, ArrayList<Integer> sensors, ArrayList<ImageRegister> images, ArrayList<SoundRegister> sounds, int sdk_device, String device) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.init_date = init_date;
        this.end_date = end_date;
        this.status = status;
        this.sensors = sensors;
        this.images = images;
        this.sounds = sounds;
        this.sdk_device = sdk_device;
        this.device = device;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getInit_date() {
        return init_date;
    }

    public void setInit_date(Date init_date) {
        this.init_date = init_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Integer> getSensors() {
        return sensors;
    }

    public void setSensors(ArrayList<Integer> sensors) {
        this.sensors = sensors;
    }

    public ArrayList<ImageRegister> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageRegister> images) {
        this.images = images;
    }

    public ArrayList<SoundRegister> getSounds() {
        return sounds;
    }

    public void setSounds(ArrayList<SoundRegister> sounds) {
        this.sounds = sounds;
    }

    public int getSdk_device() {
        return sdk_device;
    }

    public void setSdk_device(int sdk_device) {
        this.sdk_device = sdk_device;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.user_id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeLong(this.init_date != null ? this.init_date.getTime() : -1);
        dest.writeLong(this.end_date != null ? this.end_date.getTime() : -1);
        dest.writeInt(this.status);
        dest.writeList(this.sensors);
        dest.writeList(this.images);
        dest.writeList(this.sounds);
        dest.writeInt(this.sdk_device);
        dest.writeString(this.device);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.user_id = source.readInt();
        this.title = source.readString();
        this.description = source.readString();
        long tmpInit_date = source.readLong();
        this.init_date = tmpInit_date == -1 ? null : new Date(tmpInit_date);
        long tmpEnd_date = source.readLong();
        this.end_date = tmpEnd_date == -1 ? null : new Date(tmpEnd_date);
        this.status = source.readInt();
        this.sensors = new ArrayList<Integer>();
        source.readList(this.sensors, Integer.class.getClassLoader());
        this.images = new ArrayList<ImageRegister>();
        source.readList(this.images, ImageRegister.class.getClassLoader());
        this.sounds = new ArrayList<SoundRegister>();
        source.readList(this.sounds, SoundRegister.class.getClassLoader());
        this.sdk_device = source.readInt();
        this.device = source.readString();
    }

    public Experiment() {
    }

    protected Experiment(Parcel in) {
        this.id = in.readInt();
        this.user_id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        long tmpInit_date = in.readLong();
        this.init_date = tmpInit_date == -1 ? null : new Date(tmpInit_date);
        long tmpEnd_date = in.readLong();
        this.end_date = tmpEnd_date == -1 ? null : new Date(tmpEnd_date);
        this.status = in.readInt();
        this.sensors = new ArrayList<Integer>();
        in.readList(this.sensors, Integer.class.getClassLoader());
        this.images = new ArrayList<ImageRegister>();
        in.readList(this.images, ImageRegister.class.getClassLoader());
        this.sounds = new ArrayList<SoundRegister>();
        in.readList(this.sounds, SoundRegister.class.getClassLoader());
        this.sdk_device = in.readInt();
        this.device = in.readString();
    }

    public static final Parcelable.Creator<Experiment> CREATOR = new Parcelable.Creator<Experiment>() {
        @Override
        public Experiment createFromParcel(Parcel source) {
            return new Experiment(source);
        }

        @Override
        public Experiment[] newArray(int size) {
            return new Experiment[size];
        }
    };
}
