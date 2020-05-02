package com.cybage.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "record")
public class Records implements Parcelable {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("_id")


    @Expose
    private Integer id;
    @ColumnInfo(name = "quarter")
    @SerializedName("quarter")
    @Expose
    private String quarter;
    @ColumnInfo(name = "volume")
    @SerializedName("volume_of_mobile_data")
    @Expose
    private String volumeOfMobileData;
    @ColumnInfo(name = "year")
    private String year;
    private String total;
    private String Q1;
    private String Q2;
    private String Q3;
    private String Q4;


    public Records(Integer id, String quarter, String volumeOfMobileData) {
        this.id = id;
        this.quarter = quarter;
        this.volumeOfMobileData = volumeOfMobileData;
    }

    @Ignore
    public Records() {
    }

    protected Records(Parcel in) {
        this.volumeOfMobileData = in.readString();
        this.quarter = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getQ1() {
        return Q1;
    }

    public void setQ1(String q1) {
        Q1 = q1;
    }

    public String getQ2() {
        return Q2;
    }

    public void setQ2(String q2) {
        Q2 = q2;
    }

    public String getQ3() {
        return Q3;
    }

    public void setQ3(String q3) {
        Q3 = q3;
    }

    public String getQ4() {
        return Q4;
    }

    public void setQ4(String q4) {
        Q4 = q4;
    }

    public String getVolumeOfMobileData() {
        return volumeOfMobileData;
    }

    public void setVolumeOfMobileData(String volumeOfMobileData) {
        this.volumeOfMobileData = volumeOfMobileData;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        Records record = (Records) obj;
        return record.id == this.id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.volumeOfMobileData);
        dest.writeString(this.quarter);
        dest.writeValue(this.id);
    }

    public static final Creator<Records> CREATOR = new Creator<Records>() {

        @Override
        public Records createFromParcel(Parcel source) {
            return new Records(source);
        }

        @Override
        public Records[] newArray(int size) {
            return new Records[size];
        }};
        public static DiffUtil.ItemCallback<Records> DIFF_CALLBACK = new DiffUtil.ItemCallback<Records>() {
        @Override
        public boolean areItemsTheSame(@NonNull Records oldItem, @NonNull Records newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Records oldItem, @NonNull Records newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public String toString() {
        return "Records{" +
                "volumeOfMobileData='" + volumeOfMobileData + '\'' +
                ", quarter='" + quarter + '\'' +
                ", id=" + id +
                ", year='" + year + '\'' +
                ", Q1='" + Q1 + '\'' +
                ", Q2='" + Q2 + '\'' +
                ", Q3='" + Q3 + '\'' +
                ", total='" + total + '\'' +
                ", Q4='" + Q4 + '\'' +
                '}';
    }
}
