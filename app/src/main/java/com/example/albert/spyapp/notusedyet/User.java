package com.example.albert.spyapp.notusedyet;


public abstract class User {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    User(String name, int id)
    {
        id=id;
        name=name;
    }

    User()
    {

    }

}
