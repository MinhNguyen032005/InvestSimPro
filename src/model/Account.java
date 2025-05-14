package model;

import java.time.LocalDate;

public abstract class Account {
    protected String idAccount;
    protected String fullName;
    protected String nameAccount;
    protected String passwdAccount;
    protected String email;
    protected String role;
    protected LocalDate createAt;

    public Account(String idAccount, String name, String nameAccount, String passwdAccount, String email) {
        this.idAccount = idAccount;
        this.fullName = name;
        this.nameAccount = nameAccount;
        this.passwdAccount = passwdAccount;
        this.email = email;
        this.createAt = LocalDate.now();
    }

    public Account(String idAccount, String fullName, String nameAccount, String passwdAccount, String email, String role, LocalDate createAt) {
        this.idAccount = idAccount;
        this.fullName = fullName;
        this.nameAccount = nameAccount;
        this.passwdAccount = passwdAccount;
        this.email = email;
        this.role = role;
        this.createAt = createAt;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public String getPasswdAccount() {
        return passwdAccount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "idAccount='" + idAccount + '\'' +
                ", name='" + fullName + '\'' +
                ", nameAccount='" + nameAccount + '\'' +
                ", passwdAccount='" + passwdAccount + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", createAt=" + createAt +
                '}';
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setPasswdAccount(String passwdAccount) {
        this.passwdAccount = passwdAccount;
    }
}
