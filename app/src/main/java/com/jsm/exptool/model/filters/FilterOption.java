package com.jsm.exptool.model.filters;

import androidx.annotation.StringRes;

public class FilterOption<FilterOptionType, ValueType> {

    private final @StringRes int  titleTranslatableRes;
    private final FilterOptionType filterVar;
    private final ValueType filterValue;

    public FilterOption(int titleTranslatableRes, FilterOptionType filterVar, ValueType filterValue) {
        this.titleTranslatableRes = titleTranslatableRes;
        this.filterVar = filterVar;
        this.filterValue = filterValue;
    }

    public int getTitleTranslatableRes() {
        return titleTranslatableRes;
    }

    public FilterOptionType getFilterVar() {
        return filterVar;
    }

    public ValueType getFilterValue() {
        return filterValue;
    }
}
