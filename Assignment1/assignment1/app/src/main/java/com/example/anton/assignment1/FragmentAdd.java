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

import java.util.ArrayList;
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
    private EditText etTitle, etAmount, etBarCodeId;
    private RadioButton rbIncome, rbExpense;
    private String expense = "";
    private BarCode barCode = null;
    private String barCodeId = "";

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.possibleRescueMission();
        barCode = controller.setBarCodeInformation(barCode);
        barCodeId = controller.setBarId(barCodeId);
    }

    private void initializeComponents(View rootView) {
        rbExpense = (RadioButton) rootView.findViewById(R.id.rbExpend);
        rbIncome = (RadioButton) rootView.findViewById(R.id.rbInc);
        etTitle = (EditText) rootView.findViewById(R.id.etTitle);
        etAmount = (EditText) rootView.findViewById(R.id.etAmount);
        etBarCodeId = (EditText) rootView.findViewById(R.id.etBarCodeId);
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
            etBarCodeId.setOnClickListener(new BarCodeScanner());
        }
    }

    public void setSpinnerAdapter(int spinnerAdapter) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                spinnerAdapter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCategory.setAdapter(adapter);
    }

    public String getBarCodeId() {
        return barCodeId;
    }

    public void setBarCodeId(String barCodeId) {
        this.barCodeId = barCodeId;
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
                    String result = controller.addTransaction();
                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                    controller.clearOptions();
                    controller.moveBack(expense);
            }
        }
    }

    private class BarCodeScanner implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            controller.startBarCodeActivity();
        }
    }

    private class DatePickerListener implements DatePickerDialog.OnDateSetListener{

        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            btnDate.setText(controller.padDate(year, month, dayOfMonth));

        }
    }

    public void setRgType(int id){
        rgType.check(id);
    }

    public void clearRadioGroup(){
        rgType.clearCheck();
    }

    public void checkExpenditure(){
        rgType.check(R.id.rbExpend);
    }
    public void setBtnDate(String text){
        btnDate.setText(text);
    }

    public String getBtnDate(){
        return btnDate.getText().toString();
    }

    public void setEtTitle(String text){
        etTitle.setText(text);
    }

    public String getEtTitle(){
        return etTitle.getText().toString();
    }

    public void setEtAmount(String text){
        etAmount.setText(text);
    }

    public String getEtAmount(){
        return etAmount.getText().toString();
    }


    public void setBarCode(BarCode barCode){
        this.barCode = barCode;
    }

    public void setsCategory(int choice){
        sCategory.setSelection(choice);
    }

    public String getsCategory(){
        return sCategory.getSelectedItem().toString();
    }

    public void setExpense(String text){
        this.expense = text;
    }

    public String getExpense(){
        return this.expense;
    }

    public void setEtBarCodeId(String text){
        etBarCodeId.setText(text);
    }

    public String getEtBarCodeIt(){
        return this.etBarCodeId.getText().toString();
    }

    public int getCheckId(){
        return rgType.getCheckedRadioButtonId();
    }

}
