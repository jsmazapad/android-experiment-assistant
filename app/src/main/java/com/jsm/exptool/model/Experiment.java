package com.jsm.exptool.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.jsm.exptool.libs.camera.CameraProvider;
import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;

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
    @Ignore private List<MySensor> sensors = new ArrayList<>();
    @Ignore private ArrayList<ImageRegister> images = new ArrayList<>();;
    @Ignore private ArrayList<SoundRegister> sounds = new ArrayList<>();;
    private int sdkDevice;
    private String device;


    @Ignore
    public Experiment(int id, int userId, String title, String description, Date initDate, Date endDate, ExperimentStatus status, ExperimentConfiguration configuration, List<MySensor> sensors, ArrayList<ImageRegister> images, ArrayList<SoundRegister> sounds, int sdkDevice, String device) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.initDate = initDate;
        this.endDate = endDate;
        this.configuration = configuration;
        this.status = status;
        this.sensors = sensors;
        this.images = images;
        this.sounds = sounds;
        this.sdkDevice = sdkDevice;
        this.device = device;
    }

    public Experiment(long internalId, int id, int userId, String title, String description, Date initDate, Date endDate, ExperimentStatus status, ExperimentConfiguration configuration, List<MySensor> sensors, ArrayList<ImageRegister> images, ArrayList<SoundRegister> sounds, int sdkDevice, String device) {
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
        this.sdkDevice = sdkDevice;
        this.device = device;
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

    public List<MySensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<MySensor> sensors) {
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
        dest.writeParcelable(this.configuration,0);
        dest.writeList(this.sensors);
        dest.writeList(this.images);
        dest.writeList(this.sounds);
        dest.writeInt(this.sdkDevice);
        dest.writeString(this.device);
    }

    public void readFromParcel(Parcel source) {
        this.internalId = source.readLong();
        this.id = source.readInt();
        this.userId = source.readInt();
        this.title = source.readString();
        this.description = source.readString();
        long tmpInit_date = source.readLong();
        this.initDate = tmpInit_date == -1 ? null : new Date(tmpInit_date);
        long tmpEnd_date = source.readLong();
        this.endDate = tmpEnd_date == -1 ? null : new Date(tmpEnd_date);
        int tmpExperimentStatus = source.readInt();
        this.status = tmpExperimentStatus == -1 ? null : ExperimentStatus.values()[tmpExperimentStatus];
        this.configuration = source.readParcelable(ExperimentConfiguration.class.getClassLoader());
        this.sensors = new ArrayList<>();
        source.readList(this.sensors, Integer.class.getClassLoader());
        this.images = new ArrayList<ImageRegister>();
        source.readList(this.images, ImageRegister.class.getClassLoader());
        this.sounds = new ArrayList<SoundRegister>();
        source.readList(this.sounds, SoundRegister.class.getClassLoader());
        this.sdkDevice = source.readInt();
        this.device = source.readString();
    }

    public Experiment() {
    }

    protected Experiment(Parcel in) {
        readFromParcel(in);
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

}
