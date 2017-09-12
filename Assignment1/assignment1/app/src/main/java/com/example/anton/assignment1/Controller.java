package com.example.anton.assignment1;


public class Controller {

    private String username = "hej";
    private String password = "hejhej";
    private UserActivity userActivity;
    private fragment_income fragmentIncome;
    private fragment_expenditure fragmentExpenditure;
    private fragment_user fragmentUser;
    private fragment_main fragmentMain;
    private int currentFragment = 0;

    public Controller(){

    }

    public Controller(UserActivity userActivity){
        this.userActivity = userActivity;
        this.fragmentIncome = new fragment_income();
        this.fragmentExpenditure = new fragment_expenditure();
        this.fragmentUser = new fragment_user();
        this.fragmentMain = new fragment_main();

        fragmentIncome.setController(this);
        fragmentExpenditure.setController(this);
        fragmentUser.setController(this);
        fragmentMain.setController(this);

        userActivity.setFragment(fragmentMain, true);
    }


    public void signUp() {

    }

    public boolean login(String username, String password) {

        if(this.username.equals(username) && this.password.equals(password)){
            return true;
        }else {
            return false;
        }
    }

    public void updateUsername(){
        fragmentMain.setUserName(username);
    }


    public void changeFragment(int position) {
        if(position != currentFragment){
            switch(position){
                case 0:
                    userActivity.setFragment(fragmentMain, true);
                    break;
                case 1:
                    userActivity.setFragment(fragmentUser, true);
                    break;
                case 2:
                    userActivity.setFragment(fragmentIncome, true);
                    break;
                case 3:
                    userActivity.setFragment(fragmentExpenditure, true);
                    break;

            }
        }
        currentFragment = position;
    }
}
