package com.gglabs.materna.ViewController.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gglabs.materna.Model.Story;
import com.gglabs.materna.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GG on 18/02/2018.
 */

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {

    private static final String TAG = "StoriesAdapter";
    private Context context;

    private RecyclerView rv;
    private List<Story> stories;

    private View.OnClickListener onClickListener;

    public StoriesAdapter(Context context, RecyclerView rv) {
        this.context = context;
        this.rv = rv;

        stories = new ArrayList<>();
        onClickListener = new OnClickListener();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.list_item_story, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, final int position) {
        Story story = stories.get(vh.getLayoutPosition());
        vh.onBind(story);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void add(Story story, boolean scrollTo) {
        stories.add(story);
        notifyItemInserted(stories.size());
        //if (scrollTo) rv.smoothScrollToPosition(getItemCount());
    }

    public void add(int position, Story story) {
        stories.add(story);
        notifyItemInserted(position);

        rv.scrollToPosition(getItemCount() - 1);
    }

    public void remove(Story story) {
        int position = stories.indexOf(story);

        stories.remove(position);
        notifyItemRemoved(position);
    }

    public void update(int position, Story newStory) {
        stories.set(position, newStory);
        notifyItemChanged(position);
    }

    private void toggleExpand(View content, TextView btnToggle, View readMoreText, boolean expand) {
        if (!expand) {
            content.setVisibility(View.GONE);
            btnToggle.setText(context.getString(R.string.read_more));
            readMoreText.setVisibility(View.VISIBLE);
        } else {
            content.setVisibility(View.VISIBLE);
            btnToggle.setText(context.getString(R.string.close));
            readMoreText.setVisibility(View.INVISIBLE);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View rootLayout;
        private TextView tvTitle, tvStoryShort, tvStoryLong, tvBtnToggleExpand, tvMoreContent;

        private ViewHolder(View itemView) {
            super(itemView);
            rootLayout = (View) itemView.findViewById(R.id.rootLayout);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvStoryShort = (TextView) itemView.findViewById(R.id.tv_story_short);
            tvStoryLong = (TextView) itemView.findViewById(R.id.tv_story_long);
            tvBtnToggleExpand = (TextView) itemView.findViewById(R.id.tv_btn_expand);
            tvMoreContent = (TextView) itemView.findViewById(R.id.tv_more_content);
        }

        public void onBind(Story story) {
            toggleExpand(tvStoryLong, tvBtnToggleExpand, tvMoreContent, false);

            tvTitle.setText(story.getTitle());
            tvStoryShort.setText(story.getShortStory());
            tvStoryLong.setText(story.getLongStory());

            tvBtnToggleExpand.setOnClickListener(onClickListener);
            tvMoreContent.setOnClickListener(onClickListener);
        }
    }


/*    public void toggleExpand(int position, boolean scrollTo) {
        ViewHolder vh = (ViewHolder) rv.findViewHolderForAdapterPosition(position);
        toggleExpand(vh.tvStoryLong, vh.tvBtnToggleExpand, true);
        if (scrollTo) rv.smoothScrollToPosition(position);
    }*/

/*    private void toggleExpand(View content, ImageView icon) {
        if (content.getVisibility() == View.VISIBLE) {
            content.setVisibility(View.GONE);
            icon.setImageResource(R.drawable.ic_expand_more_black);
        } else {
            content.setVisibility(View.VISIBLE);
            icon.setImageResource(R.drawable.ic_expand_less_black);
        }
        TransitionManager.beginDelayedTransition(rv,
                new android.support.transition.ChangeBounds().setDuration(70));
    }*/

    private class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ViewHolder vh = (ViewHolder) rv.findContainingViewHolder(view);

            int id = view.getId();
            if (id == R.id.tv_btn_expand || id == R.id.tv_more_content) {
                if (vh != null) {
                    toggleExpand(vh.tvStoryLong, vh.tvBtnToggleExpand, vh.tvMoreContent, vh.tvStoryLong.getVisibility() != View.VISIBLE);
                }
            }
        }
    }

}

