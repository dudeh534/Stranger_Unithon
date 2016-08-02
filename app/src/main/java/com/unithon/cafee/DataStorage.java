package com.unithon.cafee;

import java.util.List;

/**
 * Created by Youngdo on 2016-07-30.
 */
public class DataStorage {
    private static List<Recycler_item> item_array;

    public static void setItem_array(List<Recycler_item> item_array) {
        DataStorage.item_array = item_array;
    }

    public static List<Recycler_item> getItem_array() {

        return item_array;
    }
}
