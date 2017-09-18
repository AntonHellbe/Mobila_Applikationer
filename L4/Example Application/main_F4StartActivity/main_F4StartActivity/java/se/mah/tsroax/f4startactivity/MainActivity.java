package se.mah.tsroax.f4startactivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends Activity {
    public final static String NAMES = "se.mah.tsroax.laboration4.NAME";
    public final static String POINTS = "se.mah.tsroax.laboration4.POINTS";
    public final static String SCORES = "se.mah.tsroax.laboration4.SCORES";

    public final static String QUESTION = "se.mah.tsroax.laboration4.QUESTION";
    public final static String ANSWER = "se.mah.tsroax.laboration4.ANSWER";
    public final static int ANSWER_QUESTION = 1;

    private Button btnHighscore;
    private Button btnQuestion;
    private Button btnAction;
    private TextView tvAnswer;

    private Score[] scores = {new Score("David",1200),new Score("Emmie",1450),new Score("Anna",900),
            new Score("Rolf",600),new Score("Robin",990)};
    private String[] names = {"David","Emmie","Anna","Rolf","Robin"};
    private int[] points = {1200,1450,900,600,990};

    private Question question = new Question("Vilka av följande bokstäver är konsonanter?",
                    new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r"},
                    new int[]{2,3,4});

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        btnHighscore = (Button)findViewById(R.id.btnHighscore);
        btnHighscore.setOnClickListener(new HighscoreListener());

        btnQuestion = (Button)findViewById(R.id.btnQuestion);
        tvAnswer = (TextView)findViewById(R.id.tvAnswer);
        btnQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,QuestionActivity.class);
                intent.putExtra(QUESTION,question);
                startActivityForResult(intent,ANSWER_QUESTION);
            }
        });

        btnAction = (Button)findViewById(R.id.btnAction);
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open phone-client
//                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:+46767775544"));

                // Launch home-screen
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                startActivity(intent);

                // Open sms-client
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                        + "+46709123456"));
                intent.putExtra("sms_body", "Hej student! Skickat från min app. :-)");
                startActivity(intent);

                // Send sms
//                SmsManager smsManager = SmsManager.getDefault();
//                smsManager.sendTextMessage("+46709123456", null, "Hej student! Skickat från min app. :-)", null, null);

                // Open email-client
//                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
//                emailIntent.setData(Uri.parse("mailto:"));
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"rolf.axelsson@mah.se", "lundarax@gmail.com"}); // recipients
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
//                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text, Thats all");
//                startActivity(emailIntent);
            }
        });
    }

    private class HighscoreListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,HighscoreActivity.class);
//            intent.putExtra(MainActivity.NAMES,names);
//            intent.putExtra(MainActivity.POINTS,points);
            intent.putExtra(MainActivity.SCORES,scores);

            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==Activity.RESULT_OK && requestCode==ANSWER_QUESTION) {
            boolean[] answer = data.getBooleanArrayExtra(ANSWER);
            tvAnswer.setText(Arrays.toString(answer));
        }
    }
}
