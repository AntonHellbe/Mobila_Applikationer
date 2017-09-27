package com.antonhellbegmail.labb7c;


import android.widget.Toast;

/**
 * Created by tsroax on 2014-09-30.
 */
public class Controller {
    private MainActivity activity;
    private MainFragment mainFragment;
    private TCPConnectionFragment connection;
    private boolean connected = false;
    private ReceiveListener listener;


    public Controller(MainActivity activity, MainFragment mainFragment) {
        this.activity = activity;
        this.mainFragment = mainFragment;
        mainFragment.setController(this);
        listener =new RL();
        connection = (TCPConnectionFragment) activity.getFragmentManager().findFragmentByTag("tcp");
        if(connection == null){
            connection = new TCPConnectionFragment();
            connection.setIp("195.178.227.53");
            connection.setConnectionPort(9384);
            connection.startThread();
            activity.getFragmentManager().beginTransaction().add(connection, "tcp").commit();
        }

        setConnectionStatus(connection.getStatus());

    }

    private void setConnectionStatus(String status) {
        String message = status;
        Exception e = connection.getException();
        if ("CONNECTED".equals(status)) {
            mainFragment.setConnectEnabled(false);
            mainFragment.setDisconnectEnabled(true);
            mainFragment.setSendEnabled(true);
            connected = true;
        } else if ("CLOSED".equals(status)) {
            mainFragment.setConnectEnabled(true);
            mainFragment.setDisconnectEnabled(false);
            mainFragment.setSendEnabled(false);
            connected = false;
        } else if ("EXCEPTION".equals(status) && e != null) {
            message = e.toString();
        }
        mainFragment.setResult(message);

    }

    public void connectClicked() {
        connection.connect();
    }

    public void disconnectClicked() {
        if(connected) {
            connection.disconnect();
        }
    }

    public void sendClicked() {
        char operation;
        try {
            int number1 = Integer.parseInt(mainFragment.getNbr1());
            int number2 = Integer.parseInt(mainFragment.getNnr2());
            String operStr = mainFragment.getOperation();
            if(operStr.length()>0)
                operation = operStr.charAt(0);
            else
                operation = '?';
            Expression exp = new Expression(number1,operation,number2);
            connection.send(exp);
        }catch(NumberFormatException e) {
            listener.newMessage("Bad arguments: " +mainFragment.getNbr1() +
                    ", " + mainFragment.getNnr2());
        }
    }

    private class RL implements ReceiveListener {
        public void newMessage(final String answer) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    setConnectionStatus(answer);
                }
            });
        }
    }

    public ReceiveListener getListener(){
        return this.listener;
    }

}
