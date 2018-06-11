package net.ddns.zimportant.lovingpeople.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.ddns.zimportant.lovingpeople.R;
import net.ddns.zimportant.lovingpeople.service.common.model.UserChat;
import net.ddns.zimportant.lovingpeople.service.helper.UserHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.OrderedRealmCollection;
import io.realm.RealmList;
import io.realm.RealmRecyclerViewAdapter;

import static net.ddns.zimportant.lovingpeople.service.helper.UserHelper.joinRealmListString;

public class CounselorsRecyclerAdapter extends
		RealmRecyclerViewAdapter<UserChat, CounselorsRecyclerAdapter.UserChatViewHolder> {

	public CounselorsRecyclerAdapter(@Nullable OrderedRealmCollection<UserChat> data) {
		super(data, true);
	}

	@NonNull
	@Override
	public UserChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_counselor, parent, false);
		return new UserChatViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull UserChatViewHolder holder, int position) {
		holder.setItem(getItem(position));
	}

	class UserChatViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.civ_avatar)
		CircleImageView avatar;
		@BindView(R.id.tv_name)
		TextView textViewName;
		@BindView(R.id.tv_field)
		TextView textViewField;
		@BindView(R.id.onlineIndicator)
		ImageView imageViewOnlineIndicator;

		UserChat item;

		UserChatViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		void setItem(UserChat item) {
			this.item = item;
			updateAvatar();
			updateStatus();
			updateName();
			updateField();
		}

		private void updateAvatar() {
			Picasso.get()
					.load(item.getAvatarUrl())
					.into(avatar);
		}

		private void updateStatus() {
			imageViewOnlineIndicator.setImageResource(
					UserHelper.getOnlineIndicatorResource(item.getStatus())
			);
		}

		private void updateName() {
			textViewName.setText(item.getName());
		}

		private void updateField() {
			textViewField.setText(joinRealmListString(item.getFields()));
		}
	}
}