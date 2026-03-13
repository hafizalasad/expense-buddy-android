package com.hafiz.expense.buddy.data;

import com.hafiz.expense.buddy.R;

import java.util.ArrayList;
import java.util.List;

public enum CategoryIconEnum {

    BABY(R.drawable.ic_baby),
    VEGETABLE(R.drawable.ic_vegetable),
    FISH(R.drawable.ic_fish),
    MEAT(R.drawable.ic_meat),
    FRUIT(R.drawable.ic_fruit),
    ;
    private final int icon;

    CategoryIconEnum(int icon) {
        this.icon = icon;

    }

    public int getIcon() {
        return icon;
    }


    public static CategoryIconEnum findByIcon(int pIcon) {

        for (CategoryIconEnum enumItem : CategoryIconEnum.values()) {
            if (enumItem.icon == pIcon) {
                return enumItem;
            }
        }
        return null;
    }


}
