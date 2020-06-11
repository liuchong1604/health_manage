package com.health.dairy.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/28 10:15
 */
@Table(name = "tb_dairy")
public class Dairy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date createdtime;
    private String content;
    private Integer sleephour;
    private String mood;
    private Integer cigarette;
    private Float bmi;
    private Float pressure;
    private Float bhigh;
    private Float blow;
    private Float bs;
    private Long userId;
    private Boolean status;

    @Override
    public String toString() {
        return "Dairy{" +
                "id=" + id +
                ", createdtime=" + createdtime +
                ", content='" + content + '\'' +
                ", sleephour=" + sleephour +
                ", mood='" + mood + '\'' +
                ", cigarette=" + cigarette +
                ", bmi=" + bmi +
                ", pressure=" + pressure +
                ", bhigh=" + bhigh +
                ", blow=" + blow +
                ", bs=" + bs +
                ", userId=" + userId +
                ", status=" + status +
                '}';
    }

    public Dairy(Date createdtime) {
        this.createdtime = createdtime;
    }

    public Dairy() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public Integer getSleephour() {
        return sleephour;
    }

    public void setSleephour(Integer sleephour) {
        this.sleephour = sleephour;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public Integer getCigarette() {
        return cigarette;
    }

    public void setCigarette(Integer cigarette) {
        this.cigarette = cigarette;
    }

    public Float getBmi() {
        return bmi;
    }

    public void setBmi(Float bmi) {
        this.bmi = bmi;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Float getBhigh() {
        return bhigh;
    }

    public void setBhigh(Float bhigh) {
        this.bhigh = bhigh;
    }

    public Float getBlow() {
        return blow;
    }

    public void setBlow(Float blow) {
        this.blow = blow;
    }

    public Float getBs() {
        return bs;
    }

    public void setBs(Float bs) {
        this.bs = bs;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
