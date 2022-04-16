package com.jsm.exptool.ui.configuration.quickComments;

import static com.jsm.exptool.config.ConfigConstants.CONFIG_SAVED_ARG;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.ItemManagementActionsListener;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.core.utils.ModalMessage;
import com.jsm.exptool.model.QuickCommentsCollection;
import com.jsm.exptool.repositories.QuickCommentsCollectionsRepository;
import com.jsm.exptool.ui.configuration.ConfigurationFragmentDirections;
import com.jsm.exptool.ui.main.MainActivity;

import java.util.List;

public class ConfigurationQuickCommentsViewModel extends BaseRecyclerViewModel<QuickCommentsCollection, QuickCommentsCollection> implements ItemManagementActionsListener<QuickCommentsCollection> {



    public ConfigurationQuickCommentsViewModel(Application app) {
        super(app);
    }


    @Override
    public List<QuickCommentsCollection> transformResponse(ListResponse<QuickCommentsCollection> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {

    }

    @Override
    public void setConstructorParameters(Object... args) {

    }

    @Override
    public void callRepositoryForData() {
        QuickCommentsCollectionsRepository.getQuickCommentsCollections(apiResponseRepositoryHolder);
    }

    public void navigateToQuickCommentsCollectionManagement(Context context) {
        NavController navController = ((BaseActivity) context).getNavController();
        navController.navigate(ConfigurationQuickCommentsFragmentDirections.actionNavQuickCommentsConfigurationToNavManageQuickCommentsConfiguration(null));
    }


    @Override
    public void delete(QuickCommentsCollection element, Context context) {
        if(element != null) {
            List<QuickCommentsCollection> elementsList = elements.getValue();
            int index = elementsList.indexOf(element);
            if (index > -1){
                ModalMessage.showModalMessage(context, context.getString(R.string.default_warning_title),
                        String.format(context.getString(R.string.warning_delete_quick_comment_collection_format), element.getName()),
                        null, (dialog, which) -> {
                            QuickCommentsCollectionsRepository.deleteQuickCommentsCollection(elementsList.get(index));
                            callRepositoryForData();
                        }, context.getString(R.string.default_modal_cancelButton), null);

            }

        }
    }

    @Override
    public void edit(QuickCommentsCollection element, Context context) {
        NavController navController = ((BaseActivity) context).getNavController();
        navController.navigate(ConfigurationQuickCommentsFragmentDirections.actionNavQuickCommentsConfigurationToNavManageQuickCommentsConfiguration(element));

    }

    protected void initBackStackEntryObserver(Context context, LifecycleOwner owner) {
        SavedStateHandle savedStateHandle = ((MainActivity) context).getNavController().getCurrentBackStackEntry().getSavedStateHandle();
        savedStateHandle.getLiveData(CONFIG_SAVED_ARG).observe(owner, configValue -> {
            if (configValue != null) {
                if((Boolean) configValue) {
                    callRepositoryForData();
                }
                //Eliminamos para no leerlo dos veces
                savedStateHandle.remove(CONFIG_SAVED_ARG);
            }
        });
    }

}