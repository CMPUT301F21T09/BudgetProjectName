package com.cmput301f21t09.budgetprojectname.views.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.controllers.UserController;
import com.cmput301f21t09.budgetprojectname.models.UserModel;
import com.cmput301f21t09.budgetprojectname.services.AuthorizationService;
import com.cmput301f21t09.budgetprojectname.views.lists.UserCustomList;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Fragment that allows the user to search other user
 */
public class SearchFragment extends Fragment {
    /**
     * Search fragment background
     */
    private ConstraintLayout background;

    /**
     * User list view
     */
    private ListView userListView;

    /**
     * Clear search image button
     */
    private ImageButton clearSearch;

    /**
     * Search entry
     */
    private EditText searchUser;

    /**
     * List of users
     */
    private ArrayList<UserModel> users = new ArrayList<>();

    /**
     * Request count
     */
    private int request = 0;

    /**
     * User list adapater
     */
    private UserCustomList userList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        background = view.findViewById(R.id.search_fragment_background);
        userListView = view.findViewById(R.id.user_listview);
        clearSearch = view.findViewById(R.id.clear_text);
        searchUser = view.findViewById(R.id.search_friend_edittext);

        userList = new UserCustomList(getContext(), users);
        userListView.setAdapter(userList);

        searchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString().trim();

                if (!keyword.equals("")) {
                    hideBackground();
                    users.clear();
                    userList.notifyDataSetChanged();
                    getUserById(keyword);
                } else {
                    showBackground();
                }
            }
        });

        clearSearch.setOnClickListener(v -> {
            searchUser.getText().clear();
            showBackground();
        });

    }

    /**
     * Get User by username
     *
     * @param keyword to search user
     */
    private void getUserById(String keyword) {
        final int currentRequest = ++this.request;

        new UserController().readUserByUserName(keyword, user -> {
            if (currentRequest == this.request && user != null && !user.getUID().equals(AuthorizationService.getInstance().getCurrentUserId())) {
                users.add(user);
                users.sort(Comparator.comparing(UserModel::getFirstName));
                userList.notifyDataSetChanged();
            }
        });
    }

    /**
     * Hide views
     */
    private void hideBackground() {
        clearSearch.setVisibility(View.VISIBLE);
        background.setVisibility(View.GONE);
        userListView.setVisibility(View.VISIBLE);
    }

    /**
     * Show Views
     */
    private void showBackground() {
        clearSearch.setVisibility(View.GONE);
        userListView.setVisibility(View.GONE);
        background.setVisibility(View.VISIBLE);
    }
}
