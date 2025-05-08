package lap.Facade;

public class Main {

    public static void main(String[] args) {
        BankFacade bankFacade1 =new BankFacade();
        bankFacade1.addAccount(new Account("101","An",100.01));
        bankFacade1.addAccount(new Account("102","Bin",1500.01));
        bankFacade1.printAll();
        bankFacade1.deposit("101", "An",500.0);
        bankFacade1.withDraw("102","Bin",500.0);
        bankFacade1.withDraw("102","Bin",2000.0);
        bankFacade1.deposit("101", "An",500.0);
        System.out.println("danh sach rut tien");
        bankFacade1.printWithDraw();
        System.out.println("danh sach cac giao dich");
        bankFacade1.printAllTransaction();
    }
}
