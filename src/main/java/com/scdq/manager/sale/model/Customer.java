package com.scdq.manager.sale.model;

import com.scdq.manager.common.BasicModel;

/**
 * Created by Administrator on 2020/1/4.
 */
public class Customer extends BasicModel {

    private String name;

    private String phone;

    private String address;

    private int integral;

    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
