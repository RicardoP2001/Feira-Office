package com.example.sheetsandpicks.Controllers.Users;

public class CodSingleton {
    private static CodSingleton instance;
    private String cod;

    private CodSingleton() {
    }

    public static CodSingleton getInstance() {
        if (instance == null) {
            instance = new CodSingleton();
        }
        return instance;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }
}
