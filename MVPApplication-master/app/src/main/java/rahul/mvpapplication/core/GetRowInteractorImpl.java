package rahul.mvpapplication.core;

import android.support.annotation.NonNull;

import rahul.mvpapplication.interfaces.ApiInterface;
import rahul.mvpapplication.model.ApiResponse;
import rahul.mvpapplication.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRowInteractorImpl implements MainContract.GetRowInteractor {
    @Override
    public void getRowArrayList(final OnFinishedListener onFinishedListener) {


        /* Create handle for the RetrofitInstance interface */
        ApiInterface service = RetrofitInstance.getRetrofitInstance().create(ApiInterface.class);

        /* Call the method with parameter in the interface to get the data */
        Call<ApiResponse> call = service.getData();

        /* API Response callback */
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                try {
                    onFinishedListener.onFinished(response.body().getRows(), response.body().getTitle());
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                onFinishedListener.onFailure(t);
            }

        });

    }

}
