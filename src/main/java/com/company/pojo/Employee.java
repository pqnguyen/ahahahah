package com.company.pojo;

import orm.ObjectRelationalMapping;
import orm.lib.Annotation.*;
import orm.lib.Relationship.ManyToManyRelationship;
import orm.lib.Relationship.OneToManyRelationship;
import orm.lib.Relationship.OneToOneRelationship;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    @Id
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;

    @OneToOne(mappedBy = Address.class, foreignKey = "employeeId")
    private Address address;
    private OneToOneRelationship oneToOneAddress = new OneToOneRelationship<Address>(this);

    @OneToMany(mappedBy = Phone.class, foreignKey = "employee_id")
    private ArrayList<Phone> phones;
    private OneToManyRelationship oneToManyPhone = new OneToManyRelationship<Phone>(this);

    @ManyToMany(mappedBy = Project.class, throughTable = "Employee_Project", left = "projectId", right = "projectId")
    private ArrayList<Project> projects;
    private ManyToManyRelationship manyToManyRelationship = new ManyToManyRelationship<Project>(this);

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress(ObjectRelationalMapping orm) throws Exception {
        return ((Address) oneToOneAddress.get(orm, Address.class).get(0));
    }

    public List<Phone> getPhones(ObjectRelationalMapping orm) throws IllegalAccessException, SQLException, InstantiationException {
        return oneToManyPhone.get(orm, Phone.class);
    }

    public List<Project> getProjects(ObjectRelationalMapping orm) throws IllegalAccessException, SQLException, InstantiationException {
        return manyToManyRelationship.get(orm, Project.class);
    }

    public Employee() {}

    public Employee(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}