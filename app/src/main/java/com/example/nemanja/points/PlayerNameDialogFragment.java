package com.example.nemanja.points;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nemanja.points.model.PointsModel;

/**
 * Created by nemanja on 3/7/18.
 */

public class PlayerNameDialogFragment extends DialogFragment {

    public static final String TAG = PlayerNameDialogFragment.class.getSimpleName();

    private TextInputLayout redInputLayout;
    private TextInputLayout blueInputLayout;

    private TextView redPlayerEdit;
    private TextView bluePlayerEdit;

    private Button setButton;
    private Button cancelButton;

    public static PlayerNameDialogFragment instance() {
        return new PlayerNameDialogFragment();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (R.id.done == view.getId()) {
                saveDataFromUi();
            }
            getFragmentManager().popBackStackImmediate();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            getDialog().setTitle("Set Player names");
        }
        final View view = inflater.inflate(R.layout.player_name_fragment, container, false);
        redInputLayout = view.findViewById(R.id.redInputLayout);
        blueInputLayout = view.findViewById(R.id.blueInputLayout);
        redPlayerEdit = view.findViewById(R.id.redPlayerName);
        bluePlayerEdit = view.findViewById(R.id.bluePlayerName);

        setButton = view.findViewById(R.id.done);
        setButton.setOnClickListener(clickListener);
//        cancelButton = view.findViewById(R.id.cancel);
//        cancelButton.setOnClickListener(clickListener);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUiFromData();
    }

    @Override
    public void onPause() {
        saveDataFromUi();
        super.onPause();
    }

    MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    private void setUiFromData() {
        PointsModel pointsModel = getMainActivity().getPointsModel();
        redPlayerEdit.setText(pointsModel.getRedPlayerName());
        bluePlayerEdit.setText(pointsModel.getBluePlayerName());

    }

    private void saveDataFromUi() {
        PointsModel pointsModel = getMainActivity().getPointsModel();
        final String redPlayer = redPlayerEdit.getText().toString();
        final String bluePlayer = bluePlayerEdit.getText().toString();
        if(!TextUtils.isEmpty(redPlayer) && !TextUtils.isEmpty(bluePlayer)) {
            pointsModel.setRedPlayerName(redPlayer);
            pointsModel.setBluePlayerName(bluePlayer);
            getMainActivity().setUiFromData();
        }
    }
}
