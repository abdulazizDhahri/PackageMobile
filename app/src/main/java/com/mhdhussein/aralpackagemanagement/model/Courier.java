package com.mhdhussein.aralpackagemanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Courier {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id_number")
    @Expose
    private String idNumber;
    @SerializedName("iqama_number")
    @Expose
    private String iqamaNumber;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("car_type")
    @Expose
    private String carType;
    @SerializedName("plate")
    @Expose
    private String plate;
    @SerializedName("notes")
    @Expose
    private Object notes;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("supervisor_id")
    @Expose
    private String supervisorId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("hire_date")
    @Expose
    private String hireDate;
    @SerializedName("job_title")
    @Expose
    private String jobTitle;
    @SerializedName("insurance")
    @Expose
    private String insurance;
    @SerializedName("insurance_exp")
    @Expose
    private String insuranceExp;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("iqama_exp")
    @Expose
    private String iqamaExp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIqamaNumber() {
        return iqamaNumber;
    }

    public void setIqamaNumber(String iqamaNumber) {
        this.iqamaNumber = iqamaNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getInsuranceExp() {
        return insuranceExp;
    }

    public void setInsuranceExp(String insuranceExp) {
        this.insuranceExp = insuranceExp;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIqamaExp() {
        return iqamaExp;
    }

    public void setIqamaExp(String iqamaExp) {
        this.iqamaExp = iqamaExp;
    }
}
