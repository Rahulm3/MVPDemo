package rahul.mvpapplication.core;

import java.util.ArrayList;


import rahul.mvpapplication.model.Row;

public interface MainContract {

    /**
     * Call when user interact with the view and other when view OnDestroy()
     */
    interface presenter {

        void onDestroy();

        void onRefreshButtonClick();

        void requestDataFromServer();

    }

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToRecyclerView and onResponseFailure is fetched from the GetRowInteractorImpl class
     **/
    interface MainView {

        void showProgress();

        void hideProgress();

        //To show data into list
        void setDataToRecyclerView(ArrayList<Row> rowArrayList);

        //To update title of action bar
        void setActionBarTitle(String actionBarTitle);

        void onResponseFailure(Throwable throwable);

    }

    /**
     * Interactors are classes built for fetching data from your database, web services, or any other data source.
     **/
    interface GetRowInteractor {

        void getRowArrayList(OnFinishedListener onFinishedListener);

        interface OnFinishedListener {
            void onFinished(ArrayList<Row> rowArrayList, String title);

            void onFailure(Throwable t);
        }
    }
}
