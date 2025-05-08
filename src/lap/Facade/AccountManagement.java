package lap.Facade;

import java.util.ArrayList;
import java.util.List;

public class AccountManagement {
    private List<Account> list=new ArrayList<>();

    public void addAccount(Account acc) {
        if (list.isEmpty()) {
            list.add(acc);
            return;
        } else {
            boolean foundDuplicate = false;
            for (Account account : list) {
                if (account.checkID(account.getIdAccount())) {
                    foundDuplicate = true;
                }
            }
            if (!foundDuplicate) {
                list.add(acc);
            }
        }
    }

    public Account loginAccount(String id, String name) {
        if (list.isEmpty()) {
            return null;
        } else {
            for (Account account : list) {
                if (account.checkLogin(id, name)) {
                    return account;
                }
            }
            return null;
        }
    }

    public Account veryfyAccount(Account acc) {
        Account account = loginAccount(acc.getIdAccount(), acc.getName());
        if (account != null && account.checkVerify(acc)) {
            return account;
        }
        return null;
    }

    public List<Account> getList() {
        return list;
    }
}
