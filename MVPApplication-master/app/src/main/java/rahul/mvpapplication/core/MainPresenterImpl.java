package rahul.mvpapplication.core;

import java.util.ArrayList;

import rahul.mvpapplication.model.Row;

public class MainPresenterImpl implements MainContract.presenter, MainContract.GetRowInteractor.OnFinishedListener {

    private MainContract.MainView mainView;
    private MainContract.GetRowInteractor getRowInteractor;

    public MainPresenterImpl(MainContract.MainView mainView, MainContract.GetRowInteractor getRowInteractor) {
        this.mainView = mainView;
        this.getRowInteractor = getRowInteractor;
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onRefreshButtonClick() {
        if (mainView != null) {
            mainView.showProgress();
        }
        getRowInteractor.getRowArrayList(this);

    }

    @Override
    public void requestDataFromServer() {
        if (mainView != null) {
            mainView.showProgress();
        }
        getRowInteractor.getRowArrayList(this);
    }


    @Override
    public void onFinished(ArrayList<Row> rowArrayList, String title) {
        if (mainView != null) {
            mainView.setDataToRecyclerView(rowArrayList);
            mainView.setActionBarTitle(title);
            mainView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (mainView != null) {
            mainView.onResponseFailure(t);
            mainView.hideProgress();
        }
    }
}
