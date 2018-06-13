package com.company.pojo;

public class Phone {
    private Integer employee_id;
    private String phone_number;

    @Override
    public String toString() {
        return "Phone{" +
                "employee_id=" + employee_id +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}
