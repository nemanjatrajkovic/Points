package com.example.nemanja.points.model;

import android.support.v4.app.FragmentActivity;

/**
 * Created by nemanja on 3/7/18.
 */

public class PointsViewModel {

    public PointsViewModel instance(FragmentActivity activity) {
        return new PointsViewModel();
    }
}
