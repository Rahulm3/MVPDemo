package rahul.mvpapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



import rahul.mvpapplication.R;
import rahul.mvpapplication.adapter.MyRowRecyclerViewAdapter;
import rahul.mvpapplication.core.GetRowInteractorImpl;
import rahul.mvpapplication.core.MainContract;
import rahul.mvpapplication.core.MainPresenterImpl;
import rahul.mvpapplication.model.Row;
import rahul.mvpapplication.utils.InternetConnection;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment implements MainContract.MainView,
        SwipeRefreshLayout.OnRefreshListener {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private final List<Row> rowList = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.textView_error)
    TextView textViewError;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private View rootView;
    private MainContract.presenter presenter;
    private MyRowRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MainFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, 0);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onRefresh() {
        if (rowList.size() > 0) {
            rowList.clear();
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        callAPI();
    }

    public void callAPI() {
        if (getActivity() != null && isAdded()) {
            if (InternetConnection.checkConnection(getActivity())) {
                presenter = new MainPresenterImpl(this, new GetRowInteractorImpl());
                presenter.requestDataFromServer();
            } else {
                Toast.makeText(getActivity(), getString(R.string.no_internet_error), Toast.LENGTH_SHORT).show();

                if (textViewError.getVisibility() == View.GONE) {
                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText(getString(R.string.no_internet_error));
                }

                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }

                recyclerView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_row_list, container, false);
        initializeView();
        callAPI();
        return rootView;
    }

    private void initializeView() {
        ButterKnife.bind(this, rootView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adapter = new MyRowRecyclerViewAdapter(rowList, getActivity());
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void showProgress() {
        if (swipeRefreshLayout != null && !swipeRefreshLayout.isRefreshing()) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setActionBarTitle(String actionBarTitle) {
        if (getActivity() != null && isAdded() && actionBarTitle != null) {
            getActivity().setTitle(actionBarTitle);
        }
    }

    @Override
    public void hideProgress() {
        if (progressBar != null && progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setDataToRecyclerView(ArrayList<Row> rowArrayList) {
        if (getActivity() != null && isAdded()) {
            rowList.addAll(rowArrayList);
            if (adapter == null) {
                adapter = new MyRowRecyclerViewAdapter(rowList, getActivity());
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }

            //Stop Swipe Refresh Layout
            if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }

            //Error message when there is no data
            if (rowList == null || rowList.size() == 0) {
                if (textViewError.getVisibility() == View.GONE) {
                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText(getString(R.string.no_data_error));
                }
                recyclerView.setVisibility(View.GONE);
            } else {
                textViewError.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        if (getActivity() != null && isAdded()) {
            if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            if (textViewError.getVisibility() == View.GONE) {
                textViewError.setVisibility(View.VISIBLE);
                textViewError.setText(getString(R.string.no_data_error));
            }
            recyclerView.setVisibility(View.GONE);
        }
    }

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

}
