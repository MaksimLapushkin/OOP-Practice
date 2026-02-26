import java.util.UUID;

public class Account {
    private final UUID id;
    private final String owner;
    private final Currency currency;
    private Money balance;

    public Account(UUID id, String owner, Currency currency){

        if (owner==null || owner.isBlank()){
            throw new IllegalArgumentException("owner cannot be empty");
        }
        if (currency == null) {
            throw new IllegalArgumentException("currency cannot be null");
        }
        if (id == null){
            throw new IllegalArgumentException("id cannot be null");
        }
        this.id = id;
        this.owner = owner;
        this.currency = currency;
        balance = new Money(0,currency);
    }

    public UUID getId(){
        return id;
    }

    public String getOwner(){
        return owner;
    }

    public Currency getCurrency(){
        return currency;
    }

    public Money getBalance(){
        return balance;
    }

    public void credit (Money amount){
        if (amount == null || amount.getAmount()<0 )
        {
            throw new IllegalArgumentException("wrong amount");
        }
        balance=balance.add(amount);
    }

    public void debit(Money amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        if (amount.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        if (amount.getCurrency() != this.currency) {
            throw new IllegalArgumentException("Currency mismatch");
        }

        if (this.balance.getAmount() < amount.getAmount()) {
            throw new IllegalStateException("Insufficient funds");
        }

        this.balance = this.balance.subtract(amount);
    }
}
