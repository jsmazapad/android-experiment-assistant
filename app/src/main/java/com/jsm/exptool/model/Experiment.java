package com.jsm.exptool.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.CommentRegister;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.model.register.SensorRegister;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = Experiment.TABLE_NAME)
public class Experiment implements Parcelable {

    /** The name of the table. */
    public static final String TABLE_NAME = "experiments";

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private long internalId;
    private int id;
    private int userId;
    private String title;
    private String description;
    private Date initDate;
    private Date endDate;
    private ExperimentStatus status;
    @Embedded private ExperimentConfiguration configuration;
    @Ignore private List<SensorRegister> sensors = new ArrayList<>();
    @Ignore private ArrayList<ImageRegister> images = new ArrayList<>();
    @Ignore private ArrayList<AudioRegister> sounds = new ArrayList<>();
    @Ignore private ArrayList<CommentRegister> comments = new ArrayList<>();
    private int sdkDevice;
    private String device;
    private boolean syncPending, embeddingPending, exportedPending;


    public Experiment(long internalId, int id, int userId, String title, String description, Date initDate, Date endDate, ExperimentStatus status, ExperimentConfiguration configuration, List<SensorRegister> sensors, ArrayList<ImageRegister> images, ArrayList<AudioRegister> sounds, ArrayList<CommentRegister> comments, int sdkDevice, String device, boolean syncPending, boolean embeddingPending, boolean exportedPending) {
        this.internalId = internalId;
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.initDate = initDate;
        this.endDate = endDate;
        this.status = status;
        this.configuration = configuration;
        this.sensors = sensors;
        this.images = images;
        this.sounds = sounds;
        this.comments = comments;
        this.sdkDevice = sdkDevice;
        this.device = device;
        this.syncPending = syncPending;
        this.embeddingPending = embeddingPending;
        this.exportedPending = exportedPending;
    }

    public Experiment(int id, int userId, String title, String description, Date initDate, Date endDate, ExperimentStatus status, ExperimentConfiguration configuration, List<SensorRegister> sensors, ArrayList<ImageRegister> images, ArrayList<AudioRegister> sounds, ArrayList<CommentRegister> comments, int sdkDevice, String device, boolean syncPending, boolean embeddingPending, boolean exportedPending) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.initDate = initDate;
        this.endDate = endDate;
        this.status = status;
        this.configuration = configuration;
        this.sensors = sensors;
        this.images = images;
        this.sounds = sounds;
        this.comments = comments;
        this.sdkDevice = sdkDevice;
        this.device = device;
        this.syncPending = syncPending;
        this.embeddingPending = embeddingPending;
        this.exportedPending = exportedPending;
    }

    public Experiment() {
    }

    public long getInternalId() {
        return internalId;
    }

    public void setInternalId(long internalId) {
        this.internalId = internalId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ExperimentStatus getStatus() {
        return status;
    }

    public void setStatus(ExperimentStatus status) {
        this.status = status;
    }

    public ExperimentConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ExperimentConfiguration configuration) {
        this.configuration = configuration;
    }

    public List<SensorRegister> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorRegister> sensors) {
        this.sensors = sensors;
    }

    public ArrayList<ImageRegister> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageRegister> images) {
        this.images = images;
    }

    public ArrayList<AudioRegister> getSounds() {
        return sounds;
    }

    public void setSounds(ArrayList<AudioRegister> sounds) {
        this.sounds = sounds;
    }

    public ArrayList<CommentRegister> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentRegister> comments) {
        this.comments = comments;
    }

    public int getSdkDevice() {
        return sdkDevice;
    }

    public void setSdkDevice(int sdkDevice) {
        this.sdkDevice = sdkDevice;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public boolean isSyncPending() {
        return syncPending;
    }

    public void setSyncPending(boolean syncPending) {
        this.syncPending = syncPending;
    }

    public boolean isEmbeddingPending() {
        return embeddingPending;
    }

    public void setEmbeddingPending(boolean embeddingPending) {
        this.embeddingPending = embeddingPending;
    }

    public boolean isExportedPending() {
        return exportedPending;
    }

    public void setExportedPending(boolean exportedPending) {
        this.exportedPending = exportedPending;
    }



    public enum ExperimentStatus{
        CREATED("CREATED"),
        INITIATED("INITIATED"),
        FINISHED("FINISHED");

        public final String status;

        ExperimentStatus(String status) {
            this.status = status;
        }

        public static ExperimentStatus chooseByStatus(String status){
            ExperimentStatus returnValue = null;
            for (ExperimentStatus statusEnum: ExperimentStatus.values()) {
                if (statusEnum.status == status)
                {
                    returnValue = statusEnum;
                }

            }
            return returnValue;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.internalId);
        dest.writeInt(this.id);
        dest.writeInt(this.userId);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeLong(this.initDate != null ? this.initDate.getTime() : -1);
        dest.writeLong(this.endDate != null ? this.endDate.getTime() : -1);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
        dest.writeParcelable(this.configuration, flags);
        dest.writeTypedList(this.sensors);
        dest.writeTypedList(this.images);
        dest.writeTypedList(this.sounds);
        dest.writeTypedList(this.comments);
        dest.writeInt(this.sdkDevice);
        dest.writeString(this.device);
        dest.writeByte(this.syncPending ? (byte) 1 : (byte) 0);
        dest.writeByte(this.embeddingPending ? (byte) 1 : (byte) 0);
        dest.writeByte(this.exportedPending ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.internalId = source.readLong();
        this.id = source.readInt();
        this.userId = source.readInt();
        this.title = source.readString();
        this.description = source.readString();
        long tmpInitDate = source.readLong();
        this.initDate = tmpInitDate == -1 ? null : new Date(tmpInitDate);
        long tmpEndDate = source.readLong();
        this.endDate = tmpEndDate == -1 ? null : new Date(tmpEndDate);
        int tmpStatus = source.readInt();
        this.status = tmpStatus == -1 ? null : ExperimentStatus.values()[tmpStatus];
        this.configuration = source.readParcelable(ExperimentConfiguration.class.getClassLoader());
        this.sensors = source.createTypedArrayList(SensorRegister.CREATOR);
        this.images = source.createTypedArrayList(ImageRegister.CREATOR);
        this.sounds = source.createTypedArrayList(AudioRegister.CREATOR);
        this.comments = source.createTypedArrayList(CommentRegister.CREATOR);
        this.sdkDevice = source.readInt();
        this.device = source.readString();
        this.syncPending = source.readByte() != 0;
        this.embeddingPending = source.readByte() != 0;
        this.exportedPending = source.readByte() != 0;
    }

    protected Experiment(Parcel in) {
        this.internalId = in.readLong();
        this.id = in.readInt();
        this.userId = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        long tmpInitDate = in.readLong();
        this.initDate = tmpInitDate == -1 ? null : new Date(tmpInitDate);
        long tmpEndDate = in.readLong();
        this.endDate = tmpEndDate == -1 ? null : new Date(tmpEndDate);
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : ExperimentStatus.values()[tmpStatus];
        this.configuration = in.readParcelable(ExperimentConfiguration.class.getClassLoader());
        this.sensors = in.createTypedArrayList(SensorRegister.CREATOR);
        this.images = in.createTypedArrayList(ImageRegister.CREATOR);
        this.sounds = in.createTypedArrayList(AudioRegister.CREATOR);
        this.comments = in.createTypedArrayList(CommentRegister.CREATOR);
        this.sdkDevice = in.readInt();
        this.device = in.readString();
        this.syncPending = in.readByte() != 0;
        this.embeddingPending = in.readByte() != 0;
        this.exportedPending = in.readByte() != 0;
    }

    public static final Creator<Experiment> CREATOR = new Creator<Experiment>() {
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
