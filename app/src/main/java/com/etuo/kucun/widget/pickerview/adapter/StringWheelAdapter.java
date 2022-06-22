package com.etuo.kucun.widget.pickerview.adapter;

import java.util.ArrayList;

/**
 * The simple Array wheel adapter
 */
public class StringWheelAdapter implements WheelAdapter {

    /**
     * The default items length
     */
    public static final int DEFAULT_LENGTH = 4;

    // items
    private ArrayList<String> items = new ArrayList<>();
    // length
    private int length;

    /**
     * Constructor
     */
    public StringWheelAdapter(int minValue, int maxValue, String lable) {
        for (int i = minValue; i <= maxValue; i++) {
            items.add(i + lable);
        }
        length = items.size();
    }

    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return "";
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }

    @Override
    public int indexOf(Object o) {
        return items.indexOf(o);
    }

}
