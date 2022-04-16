package com.jsm.exptool.ui.configuration.quickComments.addquickcomments;

import static com.jsm.exptool.config.ConfigConstants.MAX_QUICK_COMMENTS;

import android.app.Application;
import android.content.Context;


import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.DeleteActionListener;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.core.utils.ModalMessage;
import com.jsm.exptool.model.QuickCommentsCollection;
import com.jsm.exptool.repositories.QuickCommentsCollectionsRepository;


import java.util.ArrayList;
import java.util.List;

public class ConfigurationAddQuickCommentsCollectionViewModel extends BaseRecyclerViewModel<String, String> implements DeleteActionListener<String> {

    private MutableLiveData<String> quickCommentValue = new MutableLiveData<>("");
    private MutableLiveData<String> collectionNameValue = new MutableLiveData<>("");
    private MutableLiveData<Boolean> addButtonEnabled = new MutableLiveData<>(true);
    private QuickCommentsCollection quickCommentsCollection;


    public ConfigurationAddQuickCommentsCollectionViewModel(Application application, QuickCommentsCollection quickCommentsCollection) {
        super(application);
        List<String> comments = new ArrayList<>();
        if (quickCommentsCollection != null) {
            this.quickCommentsCollection = quickCommentsCollection;
            collectionNameValue.setValue(quickCommentsCollection.getName());
            if (quickCommentsCollection.getQuickComments() != null){
                comments = quickCommentsCollection.getQuickComments();
            }

        } else {
            this.elements.setValue(new ArrayList<>());
        }
        apiResponseRepositoryHolder.setValue(new ListResponse<>(comments));


    }

    public MutableLiveData<String> getQuickCommentValue() {
        return quickCommentValue;
    }

    public void setQuickCommentValue(MutableLiveData<String> quickCommentValue) {
        this.quickCommentValue = quickCommentValue;
    }

    public MutableLiveData<String> getCollectionNameValue() {
        return collectionNameValue;
    }

    public void setCollectionNameValue(MutableLiveData<String> collectionNameValue) {
        this.collectionNameValue = collectionNameValue;
    }

    public MutableLiveData<Boolean> getAddButtonEnabled() {
        return addButtonEnabled;
    }

    @Override
    public List<String> transformResponse(ListResponse<String> response) {
        return response.getResultList();
    }

    @Override
    public void setConstructorParameters(Object... args) {

    }

    @Override
    public void callRepositoryForData() {
    //El origen de datos no viene del repositorio
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {

    }

    public void addQuickComment(Context context) {

        List<String> elementsValue = elements.getValue();
        if (elementsValue != null && elementsValue.size() < MAX_QUICK_COMMENTS && !"".equals(quickCommentValue.getValue())) {
            elementsValue.add(quickCommentValue.getValue());
            elements.setValue(elementsValue);
            quickCommentValue.setValue("");
            textEmptyVisibility.setValue(false);
            addButtonEnabled.setValue(elementsValue.size() < MAX_QUICK_COMMENTS);
        }

    }

    public void delete(String element, Context context) {
        if (element != null) {
            List<String> elementsValue = elements.getValue();
            if(elementsValue != null) {
                elementsValue.remove(element);
                elements.setValue(elementsValue);
                textEmptyVisibility.setValue(elementsValue.size() > 0);
                addButtonEnabled.setValue(true);
            }
        }

    }

    public void saveCollection(Context context) {
        String errorMessage = "";
        if("".equals(collectionNameValue.getValue()) ){
            errorMessage = "El nombre de la colección no puede estar vacío \n";
        }
        if(elements.getValue().size() == 0 ){
            errorMessage += "La colección debe tener al menos un elemento";
        }

        if("".equals(errorMessage)) {
            if (quickCommentsCollection != null) {
                // Es edición
                quickCommentsCollection.setQuickComments(elements.getValue());
                quickCommentsCollection.setName(collectionNameValue.getValue());
                QuickCommentsCollectionsRepository.updateQuickCommentsCollection(quickCommentsCollection);

            }else{
                //Es creación
                quickCommentsCollection = new QuickCommentsCollection(collectionNameValue.getValue(), elements.getValue());
                QuickCommentsCollectionsRepository.registerQuickCommentsCollection(quickCommentsCollection);
            }

            NavController navController = ((BaseActivity) context).getNavController();
            navController.popBackStack();

        }else{
            errorMessage = "Atención, revise los errores para poder guardar la colección: \n" + errorMessage;
            ModalMessage.showError(context, errorMessage, null, null, null, null);
        }

    }

}
