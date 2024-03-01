package sus.chep1.database;

import jdk.internal.misc.VM;

public class User {
    private String login;
    private String pass;
    private boolean isBlocked;
    private boolean passLimited;

    public User(String login, String pass) {
        this.login = login;
        this.pass = pass;
        this.isBlocked = false;
        this.passLimited = false;
    }

    public User(User old_user) {
        this.login = old_user.getLogin();
        this.pass = old_user.getPass();
        this.isBlocked = old_user.getBlockStatus();
        this.passLimited = old_user.getPassLimitStatus();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean getBlockStatus() {
        return isBlocked;
    }

    public void setBlockStatus(boolean status) {
        this.isBlocked = status;
    }

    public boolean getPassLimitStatus() {
        return passLimited;
    }

    public void setPassLimitStatus(boolean status) {
        this.passLimited = status;
    }

    public boolean isNew(){
        if (getPass() == ""){
            return true;
        }else{
            return false;
        }
    }
}
