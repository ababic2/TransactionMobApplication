package ba.unsa.etf.rma.rma20babicamina92.models;

public class TransactionListItem {
    private String title;
    private int amount;
    private int tImage;

    public TransactionListItem(String title, int amount, int tImage) {
        this.title = title;
        this.amount = amount;
        this.tImage = tImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int gettImage() {
        return tImage;
    }

    public void settImage(int tImage) {
        this.tImage = tImage;
    }
}
