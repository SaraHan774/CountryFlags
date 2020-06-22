package com.gahee.countryflags.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CountryModel implements Parcelable {

    @SerializedName("name")
    private String countryName;

    @SerializedName("capital")
    private String capital;

    @SerializedName("flagPNG")
    private String flag;

    public CountryModel(String countryName, String capital, String flag) {
        this.countryName = countryName;
        this.capital = capital;
        this.flag = flag;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCapital() {
        return capital;
    }

    public String getFlag() {
        return flag;
    }

    protected CountryModel(Parcel in) {
        countryName = in.readString();
        capital = in.readString();
        flag = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(countryName);
        dest.writeString(capital);
        dest.writeString(flag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CountryModel> CREATOR = new Creator<CountryModel>() {
        @Override
        public CountryModel createFromParcel(Parcel in) {
            return new CountryModel(in);
        }

        @Override
        public CountryModel[] newArray(int size) {
            return new CountryModel[size];
        }
    };

    @Override
    public String toString() {
        return "CountryModel{" +
                "countryName='" + countryName + '\'' +
                ", capital='" + capital + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
