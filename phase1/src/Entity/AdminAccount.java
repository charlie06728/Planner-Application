package entity;

/**
 * A instance of this class represents a admin account in this application.
 */
public class AdminAccount  extends Account {

    public AdminAccount() {
        super();
        super.accountType = "admin";
    }

    /**
     * @param email represent the email of this admin account.
     */
    public AdminAccount(String email) {
        super();
        this.accountType = "admin";
        super.email = email;
        super.userId = ((Integer) (email).hashCode()).toString();
    }

    /**
     * Return the role of this account: admin, regular or trial.
     * @return Return false.
     */
    @Override
    public String getAccountType() {
        return  this.accountType;
    }

    /**
     * Return the available information of this account including username, id, and email.
     * @return A String that contains the user name, id and email of this account.
     */
    @Override
    public String toString() {
        String result;

        result = "This is an Admin Account with following information available:\n" +
                "User Name: " + this.userName + "\n" +
                "User ID: " + this.userId + "\n" +
                "User Email" + this.email + "\n";
        return result;
    }
}
