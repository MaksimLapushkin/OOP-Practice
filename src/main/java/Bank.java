import java.util.*;

public class Bank {
    private Map <UUID, Account> accounts = new HashMap<>();
    private Set<UUID> processedTransactions = new HashSet<>();

    public void register(Account account){
        if(account==null){
            throw new IllegalArgumentException("account cant be null");
        };
        if(account.getId()==null){
            throw new IllegalArgumentException("id cant be null");
        }
        if(accounts.containsKey(account.getId())){
            throw new IllegalStateException("ID must be unique");
        };
        accounts.put(account.getId(),account);
    }

    public Account getAccount(UUID id){

        if (id==null){
            throw new IllegalArgumentException("id cannot be null");
        }
        if (!accounts.containsKey(id)){
            throw new IllegalStateException("account not found");
        }

        return accounts.get(id);
    }

    public void transfer(UUID txId, UUID fromId, UUID toId, Money amount){


        if (txId == null){
            throw new IllegalArgumentException("wrong txId");
        }
        if (processedTransactions.contains(txId)){
            return;
        }
        if(fromId == null || toId == null || fromId.equals(toId)){
            throw new IllegalArgumentException("wrong id");
        }
        if(amount == null || amount.getAmount() <= 0){
            throw new IllegalArgumentException("wrong amount");
        }
        if(!accounts.containsKey(fromId)||!accounts.containsKey(toId)){
            throw new IllegalStateException("wrong IDs");
        }
        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);
        Account first;
        Account second;

        if (fromId.compareTo(toId) < 0) {
            first = from;
            second = to;
        } else {
            first = to;
            second = from;
        }
        synchronized (first) {
            synchronized (second) {
                if (from.getCurrency() != to.getCurrency()) {
                    throw new IllegalArgumentException("different currencies");
                }
                if (from.getCurrency() != amount.getCurrency()) {
                    throw new IllegalStateException("Currency of account and amount are different");
                }

                if (from.getBalance().getAmount() < amount.getAmount()) {
                    throw new IllegalStateException("insufficient funds");
                }

                from.debit(amount);
                to.credit(amount);
                processedTransactions.add(txId);
            }
        }
    }
}
