package ba.unsa.etf.rma.rma20babicamina92.models;

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
}
