
public final class Money {

     private final long amount;
     private final Currency currency;

     public Money (long amount,Currency currency){
         if (amount<0) {
             throw new IllegalArgumentException("Amount cannot be 0 or less");
         }
         if (currency == null) {
             throw new IllegalArgumentException("currency cannot be null");
         }
         this.amount=amount;
         this.currency=currency;
     }

     private void ensureSameCurrency (Money other){
         if (other == null) throw new IllegalArgumentException("Money other cannot be null");
         if(!this.currency.equals(other.currency)){
             throw new IllegalArgumentException("different currency");
         }
     }

     public Money add (Money other){
         ensureSameCurrency(other);
         return new Money(this.amount+other.amount, this.currency);
     }

     public Money subtract (Money other){
         ensureSameCurrency(other);
         if (this.amount-other.amount<0){
             throw new IllegalArgumentException("Negative result not allowed");
         }
         return new Money(this.amount-other.amount,this.currency);
     }

    public long getAmount() {
        return amount;
    }

    public Currency getCurrency(){
         return currency;
    }
}
