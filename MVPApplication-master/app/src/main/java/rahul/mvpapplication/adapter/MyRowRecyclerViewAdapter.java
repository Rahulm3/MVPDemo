package rahul.mvpapplication.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;



import butterknife.BindView;
import butterknife.ButterKnife;
import rahul.mvpapplication.R;
import rahul.mvpapplication.model.Row;


public class MyRowRecyclerViewAdapter extends RecyclerView.Adapter<MyRowRecyclerViewAdapter.ViewHolder> {

    private final List<Row> mValues;
    private Activity activity;

    public MyRowRecyclerViewAdapter(List<Row> items, Activity context) {
        mValues = items;
        activity = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //Title
        if (mValues.get(position).getTitle() != null &&
                !mValues.get(position).getTitle().trim().isEmpty()) {
            holder.titleTextView.setText(mValues.get(position).getTitle().trim());
            holder.mainLayout.setVisibility(View.VISIBLE);
        } else {
            holder.titleTextView.setText("");
        }

        //Description
        if (mValues.get(position).getDescription() != null &&
                !mValues.get(position).getDescription().trim().isEmpty()) {
            holder.descriptionTextView.setText(mValues.get(position).getDescription().trim());
            holder.mainLayout.setVisibility(View.VISIBLE);
        } else {
            holder.descriptionTextView.setText("");
        }


        if (mValues.get(position).getImageHref() != null &&
                !mValues.get(position).getImageHref().isEmpty()
                ) {
            Picasso.get()
                    .load(mValues.get(position).getImageHref())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                        }

                    });
        }

        if (mValues.get(position).getTitle() == null && mValues.get(position).getDescription() == null &&
                mValues.get(position).getImageHref() == null) {

            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_textView)
        TextView titleTextView;
        @BindView(R.id.description_textView)
        TextView descriptionTextView;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.main_layout)
        LinearLayout mainLayout;

        private ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
