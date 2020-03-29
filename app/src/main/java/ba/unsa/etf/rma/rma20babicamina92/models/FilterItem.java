package ba.unsa.etf.rma.rma20babicamina92.models;

import java.util.Objects;

public class FilterItem {
    private String filterName;
    private int image;

    public FilterItem(String filterName, int image) {
        this.filterName = filterName;
        this.image = image;
    }

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
}
