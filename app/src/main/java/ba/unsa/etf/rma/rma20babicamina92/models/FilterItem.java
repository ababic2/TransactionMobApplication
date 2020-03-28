package ba.unsa.etf.rma.rma20babicamina92.models;

public class FilterItem {
    private String filterName;
    private int fImage;

    public FilterItem(String filterName, int fImage) {
        this.filterName = filterName;
        this.fImage = fImage;
    }

    public String getFilterName() {
        return filterName;
    }

    public int getfImage() {
        return fImage;
    }
}
