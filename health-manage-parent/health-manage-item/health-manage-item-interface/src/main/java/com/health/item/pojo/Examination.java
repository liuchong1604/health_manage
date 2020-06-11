package com.health.item.pojo;

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
 * @create 2020/5/3 21:12
 */
@Table(name = "tb_exam")
public class Examination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date examedtime;
    private Float sbp;
    private Float dpp;
    private Float bmi;
    private Float whr;
    private Float bun;
    private Float ua;
    private Float crea;
    private Float tg;
    private Float chol;
    private Float hdl;
    private Float ldl;
    private Float glu;
    private Float hcy;
    private Float mAlb;
    private Float mAlbCrea;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getExamedtime() {
        return examedtime;
    }

    public void setExamedtime(Date examedtime) {
        this.examedtime = examedtime;
    }

    public Float getSbp() {
        return sbp;
    }

    public void setSbp(Float sbp) {
        this.sbp = sbp;
    }

    public Float getDpp() {
        return dpp;
    }

    public void setDpp(Float dpp) {
        this.dpp = dpp;
    }

    public Float getBmi() {
        return bmi;
    }

    public void setBmi(Float bmi) {
        this.bmi = bmi;
    }

    public Float getWhr() {
        return whr;
    }

    public void setWhr(Float whr) {
        this.whr = whr;
    }

    public Float getBun() {
        return bun;
    }

    public void setBun(Float bun) {
        this.bun = bun;
    }

    public Float getUa() {
        return ua;
    }

    public void setUa(Float ua) {
        this.ua = ua;
    }

    public Float getCrea() {
        return crea;
    }

    public void setCrea(Float crea) {
        this.crea = crea;
    }

    public Float getTg() {
        return tg;
    }

    public void setTg(Float tg) {
        this.tg = tg;
    }

    public Float getChol() {
        return chol;
    }

    public void setChol(Float chol) {
        this.chol = chol;
    }

    public Float getHdl() {
        return hdl;
    }

    public void setHdl(Float hdl) {
        this.hdl = hdl;
    }

    public Float getLdl() {
        return ldl;
    }

    public void setLdl(Float ldl) {
        this.ldl = ldl;
    }

    public Float getGlu() {
        return glu;
    }

    public void setGlu(Float glu) {
        this.glu = glu;
    }

    public Float getHcy() {
        return hcy;
    }

    public void setHcy(Float hcy) {
        this.hcy = hcy;
    }

    public Float getmAlb() {
        return mAlb;
    }

    public void setmAlb(Float mAlb) {
        this.mAlb = mAlb;
    }

    public Float getmAlbCrea() {
        return mAlbCrea;
    }

    public void setmAlbCrea(Float mAlbCrea) {
        this.mAlbCrea = mAlbCrea;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
