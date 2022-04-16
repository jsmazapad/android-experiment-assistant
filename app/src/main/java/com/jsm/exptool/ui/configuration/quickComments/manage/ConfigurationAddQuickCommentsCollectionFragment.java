package com.jsm.exptool.ui.configuration.quickComments.manage;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerFragment;
import com.jsm.exptool.databinding.ConfigurationAddQuickCommentsCollectionFragmentBinding;
import com.jsm.exptool.model.QuickCommentsCollection;

public class ConfigurationAddQuickCommentsCollectionFragment extends BaseRecyclerFragment<ConfigurationAddQuickCommentsCollectionFragmentBinding, ConfigurationAddQuickCommentsCollectionViewModel> {


    @Override
    protected ConfigurationAddQuickCommentsCollectionFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.configuration_add_quick_comments_collection_fragment, container, false);
    }


    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new ConfigurationAddQuickCommentsCollectionAdapter(getContext(), viewModel, viewModel.getElements(), ((BaseActivity)getActivity()).getNavController(), getListItemResourceId(), viewModel);
    }

    @Override
    protected ConfigurationAddQuickCommentsCollectionViewModel createViewModel() {
        QuickCommentsCollection quickCommentsCollection = ConfigurationAddQuickCommentsCollectionFragmentArgs.fromBundle(getArguments()).getQuickCommentsCollection();
        return new ViewModelProvider(this, new ConfigurationAddQuickCommentsCollectionViewModelFactory(getActivity().getApplication(), quickCommentsCollection )).get(ConfigurationAddQuickCommentsCollectionViewModel.class);
    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.generic_cloud_tag_with_deletion_list_item;
    }

    @Override
    protected void setupRecyclerView() {
        super.setupRecyclerView();
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        recyclerView.setLayoutManager(layoutManager);
    }
}
