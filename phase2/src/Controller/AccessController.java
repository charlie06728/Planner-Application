package Controller;

import Entity.Account;
import Gateway.AccountGateway;
import UseCase.*;
//import com.sun.org.apache.xpath.internal.operations.String;

import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * Account accessibility controller.
 */
public class AccessController{
    private AccountManager accManager;
    private AccountGateway accGateway;

    public AccessController(){
        accManager = new AccountManager();
        accGateway = new AccountGateway(accManager);
    }

    /**
     * Save the data to the database, call this function when a saving is needed. Must be called
     * when exit the application.
     * @return A boolean value representing whether the loading process is successful or not.
     */
    public boolean save() {
        return this.accGateway.save();
    }

    /**
     * Load in the data from database to AccountManager.
     * @return A boolean value representing whether the loading process is successful or not.
     */
    public boolean load() {
        return this.accGateway.load();
    }

    /**
     * Check whether the given password is correct to to account.
     * @param retriever A String representing User ID or Email.
     * @param passWord A String representing password.
     * @return A boolean value representing whether the password is correct or not.
     */
    public boolean logIn(String retriever, String passWord) {
        if (accManager.findAccount(retriever) == null ||
        accManager.findAccount(retriever).getSuspendedTime().isAfter(LocalDateTime.now())) {
            return false;
        }
        return accManager.findAccount(retriever).getPassword().equals(passWord);
    }

    /**
     * Check whether a account is an admin account or not.
     * @param retriever A String representing the User ID or Email.
     * @return A String value representing whether the account is an admin account, regular
     * account or a trial account.
     */
    public String isAdmin(String retriever) {
        Account acc = accManager.findAccount(retriever);
        if (acc != null) {
            return accManager.checkAccountRole(retriever);
        }
        return null;
    }

    /**
     * Create a new account.
     * @param email A String representing the email of this account.
     * @param userName A String representing the user name of this account.
     * @param passWord A String representing the password of this account.
     * @return A String representing the unique user ID of this account.
     */
    public String createAccount(String email, String userName, String passWord) {
        String id = accManager.createAccount(email);
        accManager.setPassword(id, passWord);
        accManager.setUserName(id, userName);
        return id;
    }

    /**
     * Reset the password of given account, user need to enter the correct original password to proceed.
     * @param retriever A String representing the User ID or Email.
     * @param oldPassWord A String representing the original password.
     * @param newPassWord A String representing the new password.
     * @return A boolean representing whether the password is successfully changed or not.
     */
    public boolean changePassword(String retriever, String oldPassWord, String newPassWord) {
        if (!accManager.checkPassword(retriever, oldPassWord)){
            return false; // old password incorrect
        }
        //TODO: sends a temporary password that the user can use to log back into the system before changing it to a
        // more permanent password

        if (!accManager.checkPassword(retriever, newPassWord)){
            return false; // new password cannot be the same as the old password
        }

        return accManager.setPassword(retriever, newPassWord); // new password is not complex enough
// if none of the above is satisfied, then able to successfully change password
    }

    /**
     * Reset the user name.
     * @param retriever A String representing the User ID or Email.
     * @param userName A String representing the new user name.
     */
    public void changeUserName(String retriever, String userName) {
        accManager.setUserName(retriever, userName);
    }


    /**
     * Remove an account. User can only remove an account after they logged in or when a trial account logged out.
     * @param retriever A String representing the User ID or Email.
     * @return A boolean value representing whether the remove operation is successful or not.
     */
    public boolean removeAccount(String retriever) {
        String accountType = accManager.findAccount(retriever).getAccountType();
        if(accountType.equals("temporary")){
            return accManager.deleteTempAccount(retriever);
        }
        else {
            return accManager.removeAccount(retriever);
        }
    }

    /**
     * Log out the account, delete the account if it is a trial account.
     * @param retriever A String representing the User ID or Email.
     * @return A boolean representing whether the log out is success.
     */
    public boolean logOut(String retriever){
        String accountType = accManager.findAccount(retriever).getAccountType();

        if(accountType.equals("trial") | accountType.equals("temporary")){
            this.removeAccount(retriever);
        }
        return true;
    }

    /**
     *
     * @param retriever A String representing the User ID or Email.
     * @param plannerId A planner id that need to be added to the account.
     * @return A boolean value representing whether the adding is successful or not..
     */
    public boolean setPlanner(String retriever, String  plannerId){
        return this.accManager.setPlanners(retriever, plannerId);
    }

    /**
     * Add new planner to a given account. return true if any one of the planners is added.
     * @param retriever A String representing the User ID or Email.
     * @param planner A planner id that need to be added to the account.
     * @return A boolean value representing whether the adding is successful or not..
     */
    public boolean setPlanner(String retriever, ArrayList<String > planner){
        return this.accManager.setPlanners(retriever, planner);
    }

    /**
     * return the list of all accounts
     * @return allAccount: the list that contains all accounts.
     */
    public ArrayList<Account> getAllAccount() {
        return this.accManager.getAllAccount();
    }


    /**
     * Retuan the account information of every account.
     * @return An ArrayList of
     */
    public ArrayList<String> viewAllAccount(){
        ArrayList<Account> acc = getAllAccount();
        ArrayList<String> info = new ArrayList<>();
        for (Account i : acc){
            info.add(i.toString());
        }
        return info;
    }

    /**
     *
     * @param retriever A String representing the User ID or Email.
     * @return An ArrayList of Planner that owned by this account.
     */
    public ArrayList<String> getPlanners(String retriever){
        return accManager.getPlanners(retriever);
    }


    /**
     * Return the information of the Account with given id.
     * @param retriever A String representing information of account
     * @return A String representing the information of the account.
     */
    public String getInfo(String retriever) {
        return this.accManager.findAccount(retriever).toString();
    }


    /**
     * remove planner from the user's planners
     * @param retriever A String representing information of account
     * @param plannerId A String that identifies the planner
     */
    public void removePlanner(String retriever, String plannerId) {
        this.accManager.removePlanner(retriever, plannerId);
    }

    public void suspendUser(String retriever, long days){
        accManager.suspendUser(retriever, days);
    }

    public void unSuspendUser(String retriever){
        accManager.unSuspendUser(retriever);
    }

    public boolean getSuspensionStatus(String retriever){
        return accManager.suspendedStatus(retriever);
    }


}
