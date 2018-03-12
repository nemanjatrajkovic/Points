package com.example.nemanja.points;

import android.widget.TextView;

import java.util.Locale;

/**
 * Created by nemanja on 3/6/18.
 */




public enum Operation {
    ADD("+"), SUBTRACT("-");

    private String operationSign;

    Operation(String operationSign) {
        this.operationSign = operationSign;
    }

    public String getSign() { return operationSign; }

    public void setAsCurrent(TextView op) {
        op.setText(operationSign);
        op.setTag(this);
    }

    public void swapOperation(TextView op) {
        if (!(op.getTag() instanceof Operation)) return;
        Operation operation = (Operation) op.getTag();
        swap(operation).setAsCurrent(op);
    }

    public Operation swap(Operation operation) {
        return operation == ADD ? SUBTRACT : ADD;
    }

    public int operateByOne(int a) {
        if (this == ADD) {
            return a + 1;
        } else {
            return (a > 0) ? a - 1 : 0;
        }
    }

    public void operateByOne(TextView textView) {
        final int value = Integer.parseInt(textView.getText().toString());
        textView.setText(String.format(Locale.getDefault(), "%02d", operateByOne(value)));
    }
}
