package com.example.myapplication.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class Produs implements Parcelable {

    private String Id;
    private String Imagine;
    private String Nume;
    private String Marca;
    private int PretActual;
    private ArrayList<Float> Pret;
    private ArrayList<Integer> An;
    private String Rezolutie;
    private String placaVideo;

    public Produs(){

    }

    public Produs(String id, String imagine, String nume, String marca, int pretActual, ArrayList<Float> pret, ArrayList<Integer> an, String rezolutie, String placaVideo) {
        Id = id;
        Imagine = imagine;
        Nume = nume;
        Marca = marca;
        PretActual = pretActual;
        Pret = pret;
        An = an;
        Rezolutie = rezolutie;
        this.placaVideo = placaVideo;
    }

        protected Produs(Parcel in) {
        Id = in.readString();
        Imagine = in.readString();
        Nume = in.readString();
        Marca = in.readString();
        PretActual = in.readInt();
        Rezolutie = in.readString();
        this.Pret = in.readArrayList(null);
        this.An = in.readArrayList(null);
        placaVideo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Imagine);
        dest.writeString(Nume);
        dest.writeString(Marca);
        dest.writeFloat(PretActual);
        dest.writeString(Rezolutie);
        dest.writeList(Pret);
        dest.writeList(An);
        dest.writeString(placaVideo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Produs> CREATOR = new Creator<Produs>() {
        @Override
        public Produs createFromParcel(Parcel in) {
            return new Produs(in);
        }

        @Override
        public Produs[] newArray(int size) {
            return new Produs[size];
        }
    };

    @Override
    public String toString() {
        return "Produs{" +
                "Id='" + Id + '\'' +
                ", Imagine='" + Imagine + '\'' +
                ", Nume='" + Nume + '\'' +
                ", Marca='" + Marca + '\'' +
                ", PretActual=" + PretActual +
                ", Pret=" + Pret +
                ", An=" + An +
                ", Rezolutie='" + Rezolutie + '\'' +
                ", placaVideo='" + placaVideo + '\'' +
                '}';
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getImagine() {
        return Imagine;
    }

    public void setImagine(String imagine) {
        Imagine = imagine;
    }

    public String getNume() {
        return Nume;
    }

    public void setNume(String nume) {
        Nume = nume;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public int getPretActual() {
        return PretActual;
    }

    public void setPretActual(int pretActual) {
        PretActual = pretActual;
    }

    public ArrayList<Float> getPret() {
        return Pret;
    }

    public void setPret(ArrayList<Float> pret) {
        Pret = pret;
    }

    public ArrayList<Integer> getAn() {
        return An;
    }

    public void setAn(ArrayList<Integer> an) {
        An = an;
    }

    public String getRezolutie() {
        return Rezolutie;
    }

    public void setRezolutie(String rezolutie) {
        Rezolutie = rezolutie;
    }

    public String getPlacaVideo() {
        return placaVideo;
    }

    public void setPlacaVideo(String placaVideo) {
        this.placaVideo = placaVideo;
    }

}
