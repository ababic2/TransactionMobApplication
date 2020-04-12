package ba.unsa.etf.rma.rma20babicamina92.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class FilterItem implements Parcelable {
    private String filterName;
    private int image;

    public FilterItem(String filterName, int image) {
        this.filterName = filterName;
        this.image = image;
    }

    protected FilterItem(Parcel in) {
        filterName = in.readString();
        image = in.readInt();
    }

    public static final Creator<FilterItem> CREATOR = new Creator<FilterItem>() {
        @Override
        public FilterItem createFromParcel(Parcel in) {
            return new FilterItem(in);
        }

        @Override
        public FilterItem[] newArray(int size) {
            return new FilterItem[size];
        }
    };

    public String getFilterName() {
        return filterName;
    }

    public int getImage() {
        return image;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterItem that = (FilterItem) o;

        return Objects.equals(filterName, that.filterName);
    }

    @Override
    public int hashCode() {
        return filterName != null ? filterName.hashCode() : 0;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filterName);
        dest.writeInt(image);
    }
}
