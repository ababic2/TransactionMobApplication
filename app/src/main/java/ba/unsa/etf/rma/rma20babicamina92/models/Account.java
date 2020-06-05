package ba.unsa.etf.rma.rma20babicamina92.models;

public class Account {
    private int id;
    private int budget;
    private int totalLimit;
    private int monthLimit;

    public Account(int budget, int totalLimit, int monthLimit) {
        this.budget = budget;
        this.totalLimit = totalLimit;
        this.monthLimit = monthLimit;
    }

    public Account(int id, int budget, int totalLimit, int monthLimit) {
        this.id = id;
        this.budget = budget;
        this.totalLimit = totalLimit;
        this.monthLimit = monthLimit;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setTotalLimit(int totalLimit) {
        this.totalLimit = totalLimit;
    }

    public void setMonthLimit(int monthLimit) {
        this.monthLimit = monthLimit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBudget() {
        return budget;
    }

    public int getTotalLimit() {
        return totalLimit;
    }

    public int getMonthLimit() {
        return monthLimit;
    }
}
