package com.jsm.exptool.ui.main;

import android.content.Context;

import androidx.navigation.NavController;

public interface MenuAction {
    void execute(Context context, NavController navController);
}
