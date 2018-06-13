package com.company;

import com.company.pojo.Address;
import com.company.pojo.Employee;
import com.company.pojo.Phone;
import com.company.pojo.Project;
import orm.ObjectRelationalMapping;
import orm.lib.Annotation.GeneratedValue;
import orm.lib.Common.Aggregate;
import orm.lib.Common.Sorting;
import orm.lib.Condition.CompoudCondition;
import orm.lib.Condition.SimpleCondition;
import orm.lib.SelectStatement;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        ObjectRelationalMapping orm = new ObjectRelationalMapping("localhost", "employees", "root", "root");
        orm.openConnection();   // Open Connection

//        demoSelect(orm);
//        demoSelectWithCondition(orm);
//        demoSelectWithGroupBy(orm);
//        insertEmployee(orm);
//        updateEmployee(orm);
        demoRelationship(orm);

        orm.closeConnection();  // Close Connection
    }

    public static void demoSelect(ObjectRelationalMapping orm) throws SQLException, IllegalAccessException, InstantiationException {
        // Select 10 Employee
        // Select * from Employee limit 10;
        List<Employee> ls = orm.select(Arrays.asList("*")).from(Employee.class).limit(10).execute();
        for (Employee employee : ls) {
            System.out.println(employee);
        }
    }

    public static void demoSelectWithCondition(ObjectRelationalMapping orm) throws Exception {
        // Select * from Employee where (id > 10001) and (id < 10009)
        List<Employee> ls = orm.select(Arrays.asList("*"))
                                .from(Employee.class)
                                .where(new CompoudCondition(new SimpleCondition("id").greater(10001)).and(new SimpleCondition("id").lesser(10009)))
                                .execute();
        for (Employee employee : ls) {
            System.out.println(employee);
        }
    }

    public static void demoSelectWithGroupBy(ObjectRelationalMapping orm) throws SQLException, IllegalAccessException, InstantiationException {
        // select Employee.* from Employee inner join Phone on Phone.employee_id = Employee.id group by id having (count(*) > 2);
        SelectStatement selectStatement = orm.select(Arrays.asList("Employee.*"))
                                .from(Employee.class)
                                .join("Phone", "employee_id", "Employee.id")
                                .groupBy(Arrays.asList("id"))
                                .having(new SimpleCondition("2").lesser(new Aggregate().count("*")));
        List<Employee> ls = selectStatement.execute();
        for (Employee employee : ls) {
            System.out.println(employee);
        }
    }

    public static void insertEmployee(ObjectRelationalMapping orm) throws Exception {
        // Insert into Employee (firstName, lastName) values ("Phan", "Nguyen")
        orm.save(new Employee(1, "Phan", "Nguyen"));
    }

    public static void updateEmployee(ObjectRelationalMapping orm) throws Exception {
        // update Employee set firstName = "Nguyen", lastName = "Phan Quang", id = 1 where id = 1
        orm.update(new Employee(1, "Nguyen", "Phan Quang")).execute();
    }

    public static void demoRelationship(ObjectRelationalMapping orm) throws Exception {
        List<Employee> ls = orm.select(Arrays.asList("*")).from(Employee.class).limit(1).execute();
        Employee employee = ls.get(0);
        System.out.println(employee);

        System.out.println("One to Many");
        List<Phone> phones = employee.getPhones(orm);
        for (Phone phone : phones)
            System.out.println(phone);

        System.out.println("Many to Many");
        List<Project> projects = employee.getProjects(orm);
        for (Project project : projects)
            System.out.println(project);

        System.out.println("One to One");
        Address address = employee.getAddress(orm);
        System.out.println(address);
    }
}
