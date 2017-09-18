package se.mah.tsroax.f4startactivity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tsroax on 04/09/15.
 */
public class Score implements Parcelable {
    private String name;
    private int points;

    public Score(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(points);
    }

    public static final Creator<Score> CREATOR = new Creator<Score>() {
        public Score createFromParcel(Parcel source) {
            return new Score(source.readString(),source.readInt());
        }

        public Score[] newArray(int size) {
            return new Score[size];
        }
    };
}
