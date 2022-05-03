package com.bornya.pim.entity;

public class User {

    private String id; //用户id
    private String first_name; //用户名
    private String last_name; //用户名
    private String address_one;  //用户地址
    private String email;  //用户邮箱
    private String city;  //城市
    private String phone;  //电话
    private String country;  //国家
    private String fax; //账户联络传真号码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress_one() {
        return address_one;
    }

    public void setAddress_one(String address_one) {
        this.address_one = address_one;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
