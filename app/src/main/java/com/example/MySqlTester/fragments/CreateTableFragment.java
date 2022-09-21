package com.example.MySqlTester.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.MySqlTester.R;
import com.example.MySqlTester.mySqlStatements.Commons;
import com.example.MySqlTester.mySqlStatements.CreateTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateTableFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView status;
    private Spinner spi1,spi2,spi3;
    private EditText etcol1,etcol2,etcol3,etname;
    private RadioButton rb_col1_pri, rb_col2_pri, rb_col3_pri;
    private RadioButton rb_col1_notnull, rb_col2_notnull, rb_col3_notnull;
    private RadioButton rb_col1_auto, rb_col2_auto, rb_col3_auto;
    private RadioButton tmpBtn;
    private final List<Boolean> radioButtonStateList = new ArrayList<>();
    private final List<RadioButton> radioButtonList = new ArrayList<>();
    private final List<Spinner> spinnerList = new ArrayList<>();
    private final int[] notnullInterval = new int[]{6,9};
    private final List<String> editTextFields = new ArrayList<>();
    private final Map<Integer, Integer> autoIndexRefactoring = new HashMap<>();
    private CreateTable create = new CreateTable();

    public CreateTableFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.createtablelayout, container, false);
        }
        initalize();
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ct_btn_run:

                putRadiobuttonsIntoAList();
                create.createTable(
                        getTableName(),
                        putEditTextFieldsIntoAList(),
                        putSpinnersSelectedItemFieldsIntoAList(),
                        putRadioButtonStatesIntoAList()
                        );
                status.setText(Commons.getMessage());
                break;

            case R.id.ct_rb_col1_pri:
                setToggleButtonBehaviour(R.id.ct_rb_col1_pri);
                setRelatedRadioButtonsState(R.id.ct_rb_col1_pri);
                break;

            case R.id.ct_rb_col2_pri:
                setToggleButtonBehaviour(R.id.ct_rb_col2_pri);
                setRelatedRadioButtonsState(R.id.ct_rb_col2_pri);
                break;

            case R.id.ct_rb_col3_pri:
                setToggleButtonBehaviour(R.id.ct_rb_col3_pri);
                setRelatedRadioButtonsState(R.id.ct_rb_col3_pri);
                break;

            case R.id.ct_rb_col1_autoincr:
                setToggleButtonBehaviour(R.id.ct_rb_col1_autoincr);
                setRelatedRadioButtonsState(R.id.ct_rb_col1_autoincr);
                break;

            case R.id.ct_rb_col2_autoincr:
                setToggleButtonBehaviour(R.id.ct_rb_col2_autoincr);
                setRelatedRadioButtonsState(R.id.ct_rb_col2_autoincr);
                break;

            case R.id.ct_rb_col3_autoincr:
                setToggleButtonBehaviour(R.id.ct_rb_col3_autoincr);
                setRelatedRadioButtonsState(R.id.ct_rb_col3_autoincr);
                break;

            case R.id.ct_rb_col1_notnull:
                setToggleButtonBehaviour(R.id.ct_rb_col1_notnull);
                break;

            case R.id.ct_rb_col2_notnull:
                setToggleButtonBehaviour(R.id.ct_rb_col2_notnull);
                break;

            case R.id.ct_rb_col3_notnull:
                setToggleButtonBehaviour(R.id.ct_rb_col3_notnull);
                break;
        }
    }

    //<editor-fold desc="UI state configurator methods">
    private void turnOnNeededRadioButtons(String typeOfRadioButton, int index) {
        switch (typeOfRadioButton) {
            case ("primary"):
                for (int i = 0; i < 9 ; i=i+3) {
                    if (i != index) {
                        radioButtonList.get(i).setEnabled(true);
                    }
                }
                break;

            case ("auto") :
                for (int i = 1; i < 9 ; i=i+3) {
                    if (i != index) {
                        radioButtonList.get(i).setEnabled(true);
                    }
                }
                break;

            case ("notnull") :
                for (int i = 2; i < 9 ; i=i+3) {
                    if (i != index) {
                        radioButtonList.get(i).setEnabled(true);
                    }
                }
                break;
        }
    }

    private void turnOffNeedlessRadiobuttons(String typeOfRadioButton, int index) {

        switch (typeOfRadioButton) {
            case ("primary"):
                for (int i = 0; i < 9 ; i=i+3) {
                    if (i != index) {
                        radioButtonList.get(i).setEnabled(false);
                    }
                }
                break;

            case ("auto") :
                for (int i = 1; i < 9 ; i=i+3) {
                    if (i != index) {
                        radioButtonList.get(i).setEnabled(false);
                    }
                }
                break;

            case ("notnull") :
                for (int i = 2; i < 9 ; i=i+3) {
                    if (i != index) {
                        radioButtonList.get(i).setEnabled(false);
                    }
                }
                break;
        }
    }

    private void setRelatedRadioButtonsState(int id) {
        tmpBtn = view.findViewById(id);

        if (isItAPrimaryTypeButton()) {
            int index = getIndexOfRadioButton(tmpBtn);
            if (radioButtonList.get(index).isSelected()) {
                turnOffNeedlessRadiobuttons(getTypeOfRadioButton(tmpBtn), index);
            }
            else {
                turnOnNeededRadioButtons(getTypeOfRadioButton(tmpBtn), index);
            }
        }

        else if (isItAnAutoIncrementTypeButton()) {
            int index = getIndexOfRadioButton(tmpBtn);
            int autoIndex = autoIndexRefactoring.get(index);

            if (isSelectedSpinnerAnIntegerType(autoIndex)) {
                if (radioButtonList.get(index).isSelected()) {
                    turnOffNeedlessRadiobuttons(getTypeOfRadioButton(tmpBtn), index);
                    spinnerList.get(autoIndex).setEnabled(false);
                }
                else {
                    turnOnNeededRadioButtons(getTypeOfRadioButton(tmpBtn), index);
                    spinnerList.get(autoIndex).setEnabled(true);
                }
            }
            else {
                tmpBtn.setChecked(false);
            }
        }
    }

    private int getIndexOfRadioButton(RadioButton tmpBtn) {
        int result = -1;
        for (int i = 0; i < radioButtonList.size(); i++) {
            if (tmpBtn.getId() == radioButtonList.get(i).getId()) {
                result = i;
            }
        }
        return result;
    }

    private boolean isSelectedSpinnerAnIntegerType(int autoIndex) {
        return spinnerList.get(autoIndex).getSelectedItem().toString().equals("INT");
    }

    private boolean isItAPrimaryTypeButton() {
        return getTypeOfRadioButton(tmpBtn).equals("primary");
    }

    private boolean isItAnAutoIncrementTypeButton() {
        return getTypeOfRadioButton(tmpBtn).equals("auto");
    }

    private String getTypeOfRadioButton(RadioButton tmpBtn) {

        String result = null;
        for (int i = 0; i < radioButtonList.size(); i++) {
            if((tmpBtn.getId()) == radioButtonList.get(i).getId()) {
                String primaryRadioButtonLocations = "036";
                String autoIncrementRadioButtonLocations = "147";
                if (primaryRadioButtonLocations.contains(""+i)) {
                    result = "primary";
                }
                else if (autoIncrementRadioButtonLocations.contains(""+i)) {
                    result = "auto";
                }
                else if (i >= notnullInterval[0] && i < notnullInterval[1]) {
                    result = "notnull";
                }
            }
        }
        return result;
    }

    private void setToggleButtonBehaviour(int id) {
        tmpBtn = view.findViewById(id);
        if (tmpBtn.isSelected()) {
            tmpBtn.setSelected(false);
            tmpBtn.setChecked(false);
        }
        else {
            tmpBtn.setSelected(true);
            tmpBtn.setChecked(true);
        }
    }

    private List<Boolean> putRadioButtonStatesIntoAList() {
        radioButtonStateList.clear();
        for (int i = 0; i < radioButtonList.size(); i++) {
            radioButtonStateList.add(radioButtonList.get(i).isChecked());
        }
        return radioButtonStateList;
    }

    private String getTableName() {
        return etname.getText().toString();
    }
    //</editor-fold>

    //<editor-fold desc="initializing methods">
    private void initalize() {
        setGui();
        putIndexPairsIntoRefactoringMap();
        putRadiobuttonsIntoAList();
        setSpinners();
        setStatus();
    }

    private void setGui() {
        etcol1 = view.findViewById(R.id.ct_et_col1);
        etcol2 = view.findViewById(R.id.ct_et_col2);
        etcol3 = view.findViewById(R.id.ct_et_col3);
        etname = view.findViewById(R.id.select_et_name);
        spi1 = view.findViewById(R.id.ct_spi_col1);
        spi2 = view.findViewById(R.id.ct_spi_col2);
        spi3 = view.findViewById(R.id.ct_spi_col3);
        Button runButton = view.findViewById(R.id.ct_btn_run);
        runButton.setOnClickListener(this);
        status = view.findViewById(R.id.ct_tv_status);
        rb_col1_pri = view.findViewById(R.id.ct_rb_col1_pri);
        rb_col1_pri.setOnClickListener(this);
        rb_col2_pri = view.findViewById(R.id.ct_rb_col2_pri);
        rb_col2_pri.setOnClickListener(this);
        rb_col3_pri = view.findViewById(R.id.ct_rb_col3_pri);
        rb_col3_pri.setOnClickListener(this);
        rb_col1_notnull = view.findViewById(R.id.ct_rb_col1_notnull);
        rb_col1_notnull.setOnClickListener(this);
        rb_col2_notnull = view.findViewById(R.id.ct_rb_col2_notnull);
        rb_col2_notnull.setOnClickListener(this);
        rb_col3_notnull = view.findViewById(R.id.ct_rb_col3_notnull);
        rb_col3_notnull.setOnClickListener(this);
        rb_col1_auto = view.findViewById(R.id.ct_rb_col1_autoincr);
        rb_col1_auto.setOnClickListener(this);
        rb_col2_auto = view.findViewById(R.id.ct_rb_col2_autoincr);
        rb_col2_auto.setOnClickListener(this);
        rb_col3_auto = view.findViewById(R.id.ct_rb_col3_autoincr);
        rb_col3_auto.setOnClickListener(this);
    }

    private List<String> putEditTextFieldsIntoAList() {
        editTextFields.clear();
        editTextFields.add(etcol1.getText().toString());
        editTextFields.add(etcol2.getText().toString());
        editTextFields.add(etcol3.getText().toString());
        return editTextFields;
    }

    public void putRadiobuttonsIntoAList() {
        radioButtonList.clear();
        radioButtonList.add(rb_col1_pri);
        radioButtonList.add(rb_col1_auto);
        radioButtonList.add(rb_col1_notnull);
        radioButtonList.add(rb_col2_pri);
        radioButtonList.add(rb_col2_auto);
        radioButtonList.add(rb_col2_notnull);
        radioButtonList.add(rb_col3_pri);
        radioButtonList.add(rb_col3_auto);
        radioButtonList.add(rb_col3_notnull);
    }

    private void putIndexPairsIntoRefactoringMap() {
        autoIndexRefactoring.clear();
        autoIndexRefactoring.put(1,0);
        autoIndexRefactoring.put(4,1);
        autoIndexRefactoring.put(7,2);
    }

    private void setSpinners() {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.ct_variables_spinneritems, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi1.setAdapter(spinnerAdapter);
        spi2.setAdapter(spinnerAdapter);
        spi3.setAdapter(spinnerAdapter);
        spinnerList.add(spi1);
        spinnerList.add(spi2);
        spinnerList.add(spi3);
    }

    private List<String> putSpinnersSelectedItemFieldsIntoAList() {
        List<String> result = new ArrayList<>();
        result.add(spi1.getSelectedItem().toString());
        result.add(spi2.getSelectedItem().toString());
        result.add(spi3.getSelectedItem().toString());
        return result;
    }

    private void setStatus() {
        status.setText(Commons.setStatus());
    }

    //</editor-fold>
}