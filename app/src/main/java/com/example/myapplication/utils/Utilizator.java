package com.example.myapplication.utils;

public class Utilizator {

    private String Nume;
    private int Varsta;
    private String Email;
    private String Parola;

    public Utilizator(String nume, int varsta, String email, String parola) {
        Nume = nume;
        Varsta = varsta;
        Email = email;
        Parola = parola;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "Nume='" + Nume + '\'' +
                ", Varsta=" + Varsta +
                ", Email='" + Email + '\'' +
                ", Parola='" + Parola + '\'' +
                '}';
    }

    public String getNume() {
        return Nume;
    }

    public void setNume(String nume) {
        Nume = nume;
    }

    public int getVarsta() {
        return Varsta;
    }

    public void setVarsta(int varsta) {
        Varsta = varsta;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getParola() {
        return Parola;
    }

    public void setParola(String parola) {
        Parola = parola;
    }
}
