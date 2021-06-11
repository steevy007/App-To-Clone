package info.atalou.apps.hangovertickets.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Tickets implements Parcelable {
    private String barcode;
    private String day;
    private String gate;
    private String category;
    private String message;
    private String color;
    private String scandate;
    private int status;

    public Tickets() {
    }

    public Tickets(String barcode, String day, String gate) {
        this.barcode = barcode;
        this.day = day;
        this.gate = gate;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getScanDate() {
        return scandate;
    }

    public void setScanDate(String scandate) {
        this.scandate = scandate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static Creator<Tickets> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.barcode);
        dest.writeString(this.day);
        dest.writeString(this.gate);
        dest.writeString(this.category);
        dest.writeString(this.message);
        dest.writeString(this.color);
        dest.writeString(this.scandate);
        dest.writeInt(this.status);
    }

    protected Tickets(Parcel in) {
        this.barcode = in.readString();
        this.day = in.readString();
        this.gate = in.readString();
        this.category = in.readString();
        this.message = in.readString();
        this.color = in.readString();
        this.status = in.readInt();
        this.scandate = in.readString();
    }

    public static final Parcelable.Creator<Tickets> CREATOR = new Parcelable.Creator<Tickets>() {
        @Override
        public Tickets createFromParcel(Parcel source) {
            return new Tickets(source);
        }

        @Override
        public Tickets[] newArray(int size) {
            return new Tickets[size];
        }
    };
}
