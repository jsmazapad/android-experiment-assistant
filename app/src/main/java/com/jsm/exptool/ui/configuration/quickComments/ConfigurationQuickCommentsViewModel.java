package com.jsm.exptool.ui.configuration.quickComments;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.DeleteActionListener;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModelListener;
import com.jsm.exptool.core.utils.ModalMessage;
import com.jsm.exptool.model.QuickCommentsCollection;
import com.jsm.exptool.repositories.QuickCommentsCollectionsRepository;

import java.util.List;

public class ConfigurationQuickCommentsViewModel extends BaseRecyclerViewModelListener<QuickCommentsCollection, QuickCommentsCollection> implements DeleteActionListener<QuickCommentsCollection> {



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

    public void saveSuggestion(QuickCommentsCollection element) {

        QuickCommentsCollectionsRepository.registerQuickCommentsCollection(element);
            callRepositoryForData();
    }

    @Override
    public void delete(QuickCommentsCollection element, Context context) {
        if(element != null) {
            List<QuickCommentsCollection> elementsList = elements.getValue();
            int index = elementsList.indexOf(element);
            if (index > -1){
                ModalMessage.showModalMessage(context, context.getString(R.string.default_warning_title),
                        String.format(context.getString(R.string.warning_delete_quickcomment_cllection_format), element.getName()),
                        null, (dialog, which) -> {
                            QuickCommentsCollectionsRepository.deleteQuickCommentsCollection(elementsList.get(index));
                            callRepositoryForData();
                        }, context.getString(R.string.default_modal_cancelButton), null);

            }

        }
    }

    @Override
    public void initObservers(LifecycleOwner owner) {
        super.initObservers(owner);

    }
}