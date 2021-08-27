package com.example.myapplication.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class Job implements Parcelable {

    private String Id;
    private String NumePozitie;
    private String Departament;
    private String Oras;

    public Job(){

    }

    public Job(String id, String numePozitie, String departament, String oras) {
        Id = id;
        NumePozitie = numePozitie;
        Departament = departament;
        Oras = oras;
    }

    protected Job(Parcel in) {
        Id = in.readString();
        NumePozitie = in.readString();
        Departament = in.readString();
        Oras = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(NumePozitie);
        dest.writeString(Departament);
        dest.writeString(Oras);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNumePozitie() {
        return NumePozitie;
    }

    public void setNumePozitie(String numePozitie) {
        NumePozitie = numePozitie;
    }

    public String getDepartament() {
        return Departament;
    }

    public void setDepartament(String departament) {
        Departament = departament;
    }

    public String getOras() {
        return Oras;
    }

    public void setOras(String oras) {
        Oras = oras;
    }
}
