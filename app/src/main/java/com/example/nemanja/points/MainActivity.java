package com.example.nemanja.points;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nemanja.points.model.PointsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String ZERO_ZERO = "00";
    List<LinearLayout> setLayoutList = new ArrayList<>();
    List<TextView> setListRed = new ArrayList<>();
    List<TextView> setListBlue = new ArrayList<>();

    private View.OnClickListener operationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView op = (TextView) view;
            Operation current = (Operation) op.getTag();
            current.swapOperation(op);
            saveDataFromUi();
        }
    };
    private View.OnClickListener setClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view instanceof LinearLayout) enableSet(setLayoutList.indexOf(view));
        }
    };;
    private View.OnClickListener playerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (R.id.nextSet == view.getId()) {
                final int index = getSelectedSetIndex();
                int next = (index + 1) % 3;
                enableSet(next);
            } else if (R.id.clear == view.getId()) {
                clearCurrentSet();
            } else {
                changeSelectedSet(view);
            }
            saveDataFromUi();
        }
    };

    private View.OnLongClickListener longClickListener= new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            if (R.id.nextSet == view.getId()) {
                showDialogFragment();
            } else if (R.id.clear == view.getId()) {
                clearAllSets();
            }
            return true;
        }
    };

    private boolean clearAllSets() {
        boolean clear = getPointsModel().getOperation() == Operation.SUBTRACT;
        if (clear) {
            getPointsModel().clearSets();
            setUiFromData();
        } else {
            showNeedDeleteOperationSnack(true);
        }
        return clear;
    }

    private void showDialogFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = manager.findFragmentByTag(PlayerNameDialogFragment.TAG);
        if (fragment != null) { transaction.remove(fragment); }
        transaction.addToBackStack(null);
        PlayerNameDialogFragment playerDialog = PlayerNameDialogFragment.instance();
        playerDialog.show(transaction, PlayerNameDialogFragment.TAG);
    }

    private CoordinatorLayout coordinatorLayout;
    private View clearData;
    private Button redButton;
    private Button blueButton;

    private void clearCurrentSet() {
        Operation op = (Operation) operation.getTag();
        if (op == Operation.SUBTRACT) {
            final int selected = getSelectedSetIndex();
            setListRed.get(selected).setText(ZERO_ZERO);
            setListBlue.get(selected).setText(ZERO_ZERO);
        } else {
            showNeedDeleteOperationSnack(true);
        }
    }

    private void showNeedDeleteOperationSnack(boolean... all) {
        String text = all.length == 1 && all[0]
                ? "Set operation to \"-\" to delete all sets."
                : "Set operation to \"-\" to delete set.";
        Snackbar.make(clearData, text, Snackbar.LENGTH_SHORT).show();
    }

    private TextView operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main_plain);
        coordinatorLayout = findViewById(R.id.coordinatorRoot);

        setLayoutList.add((LinearLayout) findViewById(R.id.setOne));
        setLayoutList.add((LinearLayout) findViewById(R.id.setTwo));
        setLayoutList.add((LinearLayout) findViewById(R.id.setThree));

        for (View view: setLayoutList) {
            view.setOnClickListener(setClickListener);
        }

        TextView setOneRed = findViewById(R.id.setOneRed);
        TextView setTwoRed = findViewById(R.id.setTwoRed);
        TextView setThreeRed = findViewById(R.id.setThreeRed);
        setListRed.add(setOneRed);
        setListRed.add(setTwoRed);
        setListRed.add(setThreeRed);

        TextView setOneBlue = findViewById(R.id.setOneBlue);
        TextView setTwoBlue = findViewById(R.id.setTwoBlue);
        TextView setThreeBlue = findViewById(R.id.setThreeBlue);
        setListBlue.add(setOneBlue);
        setListBlue.add(setTwoBlue);
        setListBlue.add(setThreeBlue);

        operation = findViewById(R.id.addSubtract);
        operation.setTag(Operation.ADD);
        operation.setOnClickListener(operationClickListener);

        redButton = findViewById(R.id.redPlayerAct);
        redButton.setOnClickListener(playerClickListener);
        blueButton = findViewById(R.id.bluePlayerAct);
        blueButton.setOnClickListener(playerClickListener);

        Button nextSet = findViewById(R.id.nextSet);
        nextSet.setOnClickListener(playerClickListener);
        nextSet.setOnLongClickListener(longClickListener);

        clearData = findViewById(R.id.clear);
        clearData.setOnClickListener(playerClickListener);
        clearData.setOnLongClickListener(longClickListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUiFromData();
    }

    @Override
    protected void onPause() {
        saveDataFromUi();
        super.onPause();
    }

    private void enableSet(int selectedSet) {
        for (int i = 0; i < setLayoutList.size(); i++) {
            setBackground(setLayoutList.get(i), i == selectedSet);
        }
    }

    private void setBackground(View view,  boolean enabled) {
//        int backgroundColor = enabled ? view.getResources().getColor(R.color.lightViolet) : 0;
//        view.setBackgroundColor(backgroundColor);

        view.setBackgroundResource(enabled ? R.drawable.rounded_button : 0);
        view.setTag(enabled);
    }

    private int getSelectedSetIndex() {
        int index = 0 ;
        for (int i = 0; i < setLayoutList.size(); i++) {
            boolean enabled = (boolean) setLayoutList.get(i).getTag();
            if (enabled) return i;
        }
        return index;
    }

    private void setValue(View view, int value) {
        TextView textView = (TextView) view;
        textView.setText(String.format(Locale.getDefault(), "%02d", value));
    }


    private void changeSelectedSet(View view) {
        final Operation op = (Operation) operation.getTag();
        final int selectedSet = getSelectedSetIndex();
        TextView text = view.getId() ==
                R.id.redPlayerAct ? setListRed.get(selectedSet) : setListBlue.get(selectedSet);
        op.operateByOne(text);
    }

    private void saveDataFromUi() {
        final PointsModel pointsModel = getPointsModel();
        for (int i = 0; i < setListRed.size(); i++) {
            saveValue(i, setListRed, pointsModel.getRedSets());
            saveValue(i, setListBlue, pointsModel.getBlueSets());
        }
        pointsModel.setOperation((Operation) operation.getTag());
        pointsModel.setSelectedSet(getSelectedSetIndex());
    }

    private void saveValue(int i, List<TextView> setList, int[] setModel) {
        final int value = Integer.parseInt(setList.get(i).getText().toString());
        setModel[i] = value;
    }

    void setUiFromData() {
        final PointsModel pointsModel = getPointsModel();
        for (int i = 0; i < pointsModel.getRedSets().length; i++) {
            setValue(setListRed.get(i), pointsModel.getRedSets()[i]);
            setValue(setListBlue.get(i), pointsModel.getBlueSets()[i]);
        }
        pointsModel.getOperation().setAsCurrent(operation);
        enableSet(pointsModel.getSelectedSet());
        redButton.setText(pointsModel.getRedPlayerName());
        blueButton.setText(pointsModel.getBluePlayerName());
    }

    public PointsModel getPointsModel() {
        ViewModelProvider viewModelProvider = ViewModelProviders.of(this);
        return viewModelProvider.get(PointsModel.class);
    }
}
