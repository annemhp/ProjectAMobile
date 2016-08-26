package Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Development on 8/13/2016.
 */


public class Status implements Serializable{



    public String complaintNo;
    @SerializedName("date")
    public Date date;
    @SerializedName("department")
    public String department;
    @SerializedName("mobile")
    public String mobile;
    @SerializedName("name")
    public String name;
    @SerializedName("place")
    public String place;
    @SerializedName("problem")
    public String problem;
    @SerializedName("status")
    public String status;
    @SerializedName("subject")
    public String subject;
    @SerializedName("updates")
    public HashMap<String,StatusDetail> updates;
    @SerializedName("userId")
    public String userId;



    public Status(String complaintNo,Date date, String department, String mobile, String name,
                        String place, String problem, String subject, String status,
                            HashMap<String,StatusDetail> updates,String userId) {
        this.complaintNo = complaintNo;
        this.date = date;
        this.department = department;
        this.mobile = mobile;
        this.name = name;
        this.place = place;
        this.problem = problem;
        this.subject = subject;
        this.status = status;
        this.updates = updates;
        this.userId = userId;

    }

    public String getComplaintNo() {
        return complaintNo;
    }

    public void setComplaintNo(String complaintNo) {
        this.complaintNo = complaintNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String,StatusDetail> getUpdates() {
        return updates;
    }

    public void setUpdates(HashMap<String,StatusDetail> updates) {
        this.updates = updates;
    }
}
