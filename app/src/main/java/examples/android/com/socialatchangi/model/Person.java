package examples.android.com.socialatchangi.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
    private final String mName;
    private final Avatar mAvatar;

    public Person(String name, Avatar avatar) {
        mName = name;
        mAvatar = avatar;
    }

    protected Person(Parcel in) {
        mName = in.readString();
        mAvatar = Avatar.values()[in.readInt()];
    }

    public String getDisplayName() {
        return mName;
    }

    public Avatar getAvatar() {
        return mAvatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeInt(mAvatar.ordinal());
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Person player = (Person) o;

        if (mAvatar != player.mAvatar) {
            return false;
        }
        if (!mName.equals(player.mName)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = mName.hashCode();
        result = 31 * result + mAvatar.hashCode();
        return result;
    }
}

