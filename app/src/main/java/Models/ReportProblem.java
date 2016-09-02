package Models;

import android.net.Uri;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Abhijith Sreekar on 7/11/2016.
 */
public class ReportProblem {


    public Long getComplaintNo() {
        return complaintNo;
    }

    public void setComplaintNo(Long complaintNo) {
        this.complaintNo = complaintNo;
    }

    @SerializedName("complaintNo")
    private Long complaintNo;
    @SerializedName("userId")
    private String userId;
    @SerializedName("name")
    private String name;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("place")
    private String place;
    @SerializedName("department")
    private String department;
    @SerializedName("subject")
    private String subject;
    @SerializedName("problem")
    private String problem;
    @SerializedName("date")
    private String date;
    @SerializedName("status")
    private String status;
    @SerializedName("imageUri")
    private String imageRef;

    public ReportProblem(String userId, String name, String mobile, String place, String department, String subject, String problem, String date, String status,String imageRef) {
        this.userId = userId;
        this.name = name;
        this.mobile = mobile;
        this.place = place;
        this.department = department;
        this.subject = subject;
        this.problem = problem;
        this.date = date;
        this.status = status;
        this.imageRef = imageRef;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //public String getImage() {
      //  return image;
    //}

   // public void setImage(String image) {
   //     this.image = image;
   // }
}
