package com.jsm.exptool.config;

import com.jsm.exptool.R;
import com.jsm.exptool.ui.main.MenuElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Configuración de menú inicial
 */
public class MenuConfig {
    /**
     * elementos que se mostrarán en menú
     */
    public static List<MenuElement> menuElements = Arrays.asList(
            new MenuElement(R.string.title_experiments_fragment, "", R.color.white, MenuElement.ElementGravity.RIGHT, R.drawable.ic_baseline_developer_board_24, R.drawable.ic_baseline_developer_board_24, R.id.nav_experiments, true, false, null),
            new MenuElement(R.string.title_experiments_create_fragment, "", R.color.white, MenuElement.ElementGravity.RIGHT, R.drawable.ic_baseline_build_24, R.drawable.ic_baseline_build_24, R.id.nav_experiment_create, true, false, null),
            new MenuElement(R.string.title_configuration_fragment, "", R.color.white, MenuElement.ElementGravity.RIGHT, R.drawable.ic_baseline_settings_24, R.drawable.ic_baseline_settings_24, R.id.nav_configuration, true, false, null),
            new MenuElement(R.string.title_info_fragment, "", R.color.white, MenuElement.ElementGravity.RIGHT, R.drawable.ic_baseline_settings_24, R.drawable.ic_baseline_perm_device_information_24, R.id.nav_about, true, false, null)
    );
}
