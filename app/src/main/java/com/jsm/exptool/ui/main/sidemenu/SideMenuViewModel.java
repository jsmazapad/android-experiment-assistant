package com.jsm.exptool.ui.main.sidemenu;

import android.app.Application;
import android.content.Context;

import androidx.navigation.NavController;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.data.repositories.MenuRepository;
import com.jsm.exptool.ui.main.MenuElement;

import java.util.List;


public class SideMenuViewModel extends BaseRecyclerViewModel<MenuElement, MenuElement> {

    public SideMenuViewModel(Application app) {
        super(app);
    }

    @Override
    public List<MenuElement> transformResponse(ListResponse<MenuElement> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context context) {
        MenuElement element = elements.getValue().get(position);

        if (element.getMenuAction() == null) {
            navController.navigate(element.getNavDestiny());
        } else {
            element.getMenuAction().execute(context, navController);
        }
    }

    @Override
    public void setConstructorParameters(Object... args) {

    }

    @Override
    public void callRepositoryForData() {
        MenuRepository.getMenuElements(apiResponseRepositoryHolder, false);
    }


}
