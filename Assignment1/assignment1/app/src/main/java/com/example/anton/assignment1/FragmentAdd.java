package com.example.anton.assignment1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import static java.lang.Float.parseFloat;

/**
 * Created by Anton on 2017-09-14.
 */

public class FragmentAdd extends Fragment{

    private Controller controller;
    private RadioGroup rgType;
    private Spinner sCategory;
    private Button btnDate, btnAdd;
    private EditText etTitle, etAmount;
    private RadioButton rbIncome, rbExpense;
    private String expense;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.clearOptions();
        sCategory.setAdapter(null);
    }

    private void initializeComponents(View rootView) {
        rbExpense = (RadioButton) rootView.findViewById(R.id.rbExpend);
        rbIncome = (RadioButton) rootView.findViewById(R.id.rbInc);
        etTitle = (EditText) rootView.findViewById(R.id.etTitle);
        etAmount = (EditText) rootView.findViewById(R.id.etAmount);
        rgType = (RadioGroup) rootView.findViewById(R.id.rgType);
        sCategory = (Spinner) rootView.findViewById(R.id.sCategory);
        btnDate = (Button) rootView.findViewById(R.id.btnDate);
        btnAdd = (Button) rootView.findViewById(R.id.btnAdd);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }


    private void registerListeners() {
        if(controller != null) {
            rgType.setOnCheckedChangeListener(new RadioGroupListener());
            btnDate.setOnClickListener(new ButtonListener());
            btnAdd.setOnClickListener(new ButtonListener());
        }
    }

    public void setSpinnerAdapter(int spinnerAdapter) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                spinnerAdapter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCategory.setAdapter(adapter);
    }

    private class RadioGroupListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            switch(i){
                case R.id.rbExpend:
                    controller.changeSpinner(0);
                    expense = rbExpense.getText().toString();
                    break;
                case R.id.rbInc:
                    controller.changeSpinner(1);
                    expense = rbIncome.getText().toString();
                    break;
                }
        }
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.btnDate:
                    Calendar cal = Calendar.getInstance();
                    new DatePickerDialog(getActivity(), new DatePickerListener(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
                    break;
                case R.id.btnAdd:
                    try{
                        Transaction trans = new Transaction(expense, etTitle.getText().toString(), controller.getCurrentUserName(),
                                parseFloat(etAmount.getText().toString()), sCategory.getSelectedItem().toString(), btnDate.getText().toString());
                        controller.addTransaction(trans);
                        Toast.makeText(getActivity(), "Transaction added!", Toast.LENGTH_SHORT).show();
                        controller.moveBack(expense);
                    }catch(Exception e){
                        Toast.makeText(getActivity(), "One or more fields missing!", Toast.LENGTH_SHORT).show();
                    }
                    break;


            }
        }
    }

    private class DatePickerListener implements DatePickerDialog.OnDateSetListener{

        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            btnDate.setText(controller.padDate(year, month, dayOfMonth));

        }
    }

    public void clearRadioGroup(){
        rgType.clearCheck();
    }

    public void setBtnDate(String text){
        btnDate.setText(text);
    }

    public void setEtTitle(String text){
        etTitle.setText(text);
    }

    public void setEtAmount(String text){
        etAmount.setText(text);
    }


}
