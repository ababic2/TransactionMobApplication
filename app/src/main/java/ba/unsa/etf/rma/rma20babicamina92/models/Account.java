package ba.unsa.etf.rma.rma20babicamina92.models;

import java.math.BigDecimal;

public class Account {
    private BigDecimal budget;
    private BigDecimal totalLimit;
    private BigDecimal monthLimit;

    public Account(BigDecimal budget, BigDecimal totalLimit, BigDecimal monthLimit) {
        this.budget = budget;
        this.totalLimit = totalLimit;
        this.monthLimit = monthLimit;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(BigDecimal totalLimit) {
        this.totalLimit = totalLimit;
    }

    public BigDecimal getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(BigDecimal monthLimit) {
        this.monthLimit = monthLimit;
    }
}
