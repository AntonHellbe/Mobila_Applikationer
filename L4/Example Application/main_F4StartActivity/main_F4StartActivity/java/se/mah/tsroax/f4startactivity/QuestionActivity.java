package se.mah.tsroax.f4startactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class QuestionActivity extends Activity {
    private TextView tvQuestion;
    private ListView lvAlternatives;
    private Button btnAnswer;
    private Question question;
    private boolean[] answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        tvQuestion = (TextView)findViewById(R.id.tvQuestion);
        lvAlternatives = (ListView)findViewById(R.id.lvAlternatives);
        btnAnswer = (Button)findViewById(R.id.btnOk);
        getData();
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra(MainActivity.ANSWER,answers);
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });

    }

    private void getData() {
        Intent data = getIntent();
        question = (Question)data.getSerializableExtra(MainActivity.QUESTION);
        answers = new boolean[question.getAlternatives().length]; // false default
        tvQuestion.setText(question.getQuestion());
        lvAlternatives.setAdapter(new WithOutAnswers(this, question.getAlternatives()));
    }

    private class WithOutAnswers extends ArrayAdapter<String> {
        private LayoutInflater inflater;
        private View.OnClickListener listener = new CBListener();

        public WithOutAnswers(Context context, String[] content) {
            super(context, R.layout.question_row, content);
            inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String text = getItem(position);
            if(convertView==null) {
                convertView = inflater.inflate(R.layout.question_row,parent,false);
            }
            TextView tvAlternative = (TextView)convertView.findViewById(R.id.tvAlternative);
            CheckBox cbAlternative = (CheckBox)convertView.findViewById(R.id.cbAlternative);
            tvAlternative.setText(text);
            cbAlternative.setChecked(answers[position]);
            cbAlternative.setTag(position);
            cbAlternative.setOnClickListener(listener);
            return convertView;
        }
    }

    private class CBListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            CheckBox cb = (CheckBox)v;
            int index = (Integer)cb.getTag();
            answers[index] = cb.isChecked();
        }
    }
}
