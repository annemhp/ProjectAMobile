package Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Development on 8/13/2016.
 */

public class StatusDetail implements Parcelable {


    @SerializedName("message")
    public String message;
    @SerializedName("status")
    public String status;
    @SerializedName("date")
    public Date date;

    public long statusdetailDate;

    public StatusDetail(String message, String status, Date date)
    {
        this.message = message;
        this.status = status;
        this.date = date;
    }

    protected StatusDetail(Parcel in) {
        message = in.readString();
        status = in.readString();
        statusdetailDate = in.readLong();
    }

    public static final Creator<StatusDetail> CREATOR = new Creator<StatusDetail>() {
        @Override
        public StatusDetail createFromParcel(Parcel in) {
            return new StatusDetail(in);
        }

        @Override
        public StatusDetail[] newArray(int size) {
            return new StatusDetail[size];
        }
    };

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return new Date(statusdetailDate);
    }

    public void setDate(Date date) {
        statusdetailDate = date.getTime();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(message);
        parcel.writeString(status);
        parcel.writeLong(statusdetailDate);
    }
}