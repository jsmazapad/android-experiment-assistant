package com.jsm.exptool.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.providers.MenuProvider;
import com.jsm.exptool.ui.main.MenuElement;

public class MenuRepository {

    /**
     * Obtiene los elementos del menú de manera reactiva
     * @param responseMutableLiveData livedata donde se setean los elementos
     * @param isMainMenu Si es true indica que se están solicitando para el menú principal, si es false indica que se solicitan para el menú lateral
     */
    public static void getMenuElements(MutableLiveData<ListResponse<MenuElement>> responseMutableLiveData, boolean isMainMenu){
        responseMutableLiveData.setValue(new ListResponse<>(MenuProvider.getMenuElements(isMainMenu)));

    }
}
