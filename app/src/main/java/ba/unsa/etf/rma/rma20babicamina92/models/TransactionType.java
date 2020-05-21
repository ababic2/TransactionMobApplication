package ba.unsa.etf.rma.rma20babicamina92.models;

import java.io.Serializable;

public class TransactionType implements Serializable {
    private int id;
    private String name;
    private int image;

    public TransactionType(int id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionType that = (TransactionType) o;

        return name != null ? name.replace(" ","").toLowerCase().equals(that.name.replace(" ","").toLowerCase()) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
