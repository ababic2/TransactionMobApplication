package ba.unsa.etf.rma.rma20babicamina92.models;

public class AccountAction {
    private String name;
    private Account account;

    public AccountAction(String name, Account account) {
        this.name = name;
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
