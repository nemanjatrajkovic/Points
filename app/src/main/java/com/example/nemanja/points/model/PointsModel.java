package com.example.nemanja.points.model;

import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.example.nemanja.points.Operation;

/**
 * Created by nemanja on 3/6/18.
 */

public class PointsModel extends ViewModel {

    private static final String PLAYER_ONE = "Player One";
    private static final String PLAYER_TWO = "Player Two";
    int[] redSets;
    int[] blueSets;

    String redPlayerName = PLAYER_ONE;
    String bluePlayerName = PLAYER_TWO;

    int selectedSet;
    Operation operation;

    public PointsModel() {
        redSets = new int[3];
        blueSets = new int[3];
        selectedSet = 0;
        operation = Operation.ADD;
    }

    public void clearSets() {
        for (int i = 0; i < redSets.length; i++) {
            redSets[i] = 0;
            blueSets[i] = 0;
        }
    }

    public void clearNames() {
        redPlayerName = PLAYER_ONE;
        bluePlayerName = PLAYER_TWO;
    }

    public void clear() {
        clearSets();
        clearNames();
        selectedSet = 0;
        operation = Operation.ADD;
    }

    public String getRedPlayerName() {
        return redPlayerName;
    }

    public void setRedPlayerName(String redPlayerName) {
        this.redPlayerName = redPlayerName;
    }

    public String getBluePlayerName() {
        return bluePlayerName;
    }

    public void setBluePlayerName(String bluePlayerName) {this.bluePlayerName = bluePlayerName;
    }

    public int[] getRedSets() {
        return redSets;
    }

    public void setRedSets(int[] redSets) {
        this.redSets = redSets;
    }

    public int[] getBlueSets() {
        return blueSets;
    }

    public void setBlueSets(int[] blueSets) {
        this.blueSets = blueSets;
    }

    public int getSelectedSet() {
        return selectedSet;
    }

    public void setSelectedSet(int selectedSet) {
        this.selectedSet = selectedSet;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
