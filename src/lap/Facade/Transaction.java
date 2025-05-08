package lap.Facade;

import lap.factorry.Date;

import java.time.LocalDate;
import java.util.UUID;

public class Transaction {
    private String idTrans;
    private LocalDate dateTrans;
    private String type;
    private String idAcc;
    private double amount;

    public Transaction( String type, String idAcc,double amount) {
        this.idTrans = UUID.randomUUID().toString();
        this.dateTrans = LocalDate.now();
        this.type = type;
        this.idAcc = idAcc;
        this.amount=amount;
    }

    public String getType() {
        return type;
    }
}
