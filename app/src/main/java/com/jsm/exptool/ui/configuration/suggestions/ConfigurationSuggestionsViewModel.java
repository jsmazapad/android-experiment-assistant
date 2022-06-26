package com.jsm.exptool.ui.configuration.suggestions;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.DeleteActionListener;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.core.libs.ModalMessage;
import com.jsm.exptool.entities.CommentSuggestion;
import com.jsm.exptool.data.repositories.CommentSuggestionsRepository;

import java.util.List;

public class ConfigurationSuggestionsViewModel extends BaseRecyclerViewModel<CommentSuggestion, CommentSuggestion> implements DeleteActionListener<CommentSuggestion> {

    private MutableLiveData<String> suggestion = new MutableLiveData<>();

    public ConfigurationSuggestionsViewModel(Application app) {
        super(app);
    }

    public MutableLiveData<String> getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(MutableLiveData<String> suggestion) {
        this.suggestion = suggestion;
    }

    @Override
    public List<CommentSuggestion> transformResponse(ListResponse<CommentSuggestion> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {

    }

    @Override
    public void setConstructorParameters(Object... args) {
        suggestion = new MutableLiveData<>();
    }

    @Override
    public void callRepositoryForData() {
        CommentSuggestionsRepository.getCommentSuggestions(apiResponseRepositoryHolder, suggestion.getValue());
    }

    public void saveSuggestion(Context context) {

        if (CommentSuggestionsRepository.checkIfCommentSuggestionExists(suggestion.getValue()).size() == 0 && suggestion.getValue() != null && !"".equals(suggestion.getValue())) {

            CommentSuggestionsRepository.registerCommentSuggestion(new CommentSuggestion(0, suggestion.getValue()));
            callRepositoryForData();
        }else{
            ModalMessage.showModalMessage(context, context.getString(R.string.default_warning_title), context.getString(R.string.error_suggestion_already_exists), null, null, null, null);
        }

    }

    @Override
    public void delete(CommentSuggestion element, Context context) {
        if(element != null) {
            List<CommentSuggestion> elementsList = elements.getValue();
            int index = elementsList.indexOf(element);
            if (index > -1){
                ModalMessage.showModalMessage(context, context.getString(R.string.default_warning_title),
                        String.format(context.getString(R.string.warning_delete_comment_suggestion_format), element.getComment()),
                        null, (dialog, which) -> {
                            CommentSuggestionsRepository.deleteCommentSuggestion(elementsList.get(index));
                            //suggestion.setValue("");
                            callRepositoryForData();
                        }, context.getString(R.string.default_modal_cancelButton), null);

            }

        }
    }

    @Override
    public void initObservers(LifecycleOwner owner) {
        super.initObservers(owner);
        suggestion.observe(owner, value->{
            callRepositoryForData();
        });
    }
}