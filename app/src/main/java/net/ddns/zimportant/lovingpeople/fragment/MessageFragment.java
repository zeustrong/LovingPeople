package net.ddns.zimportant.lovingpeople.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.ddns.zimportant.lovingpeople.R;
import net.ddns.zimportant.lovingpeople.activity.ConversationActivity;
import net.ddns.zimportant.lovingpeople.activity.SearchCounselorsActivity;
import net.ddns.zimportant.lovingpeople.adapter.ChatRoomsRecyclerAdapter;
import net.ddns.zimportant.lovingpeople.service.common.model.ChatRoom;
import net.ddns.zimportant.lovingpeople.service.common.model.UserChat;
import net.ddns.zimportant.lovingpeople.service.interfaces.OnCreateConversation;
import net.ddns.zimportant.lovingpeople.service.interfaces.OnCreateConversationCounselor;
import net.ddns.zimportant.lovingpeople.service.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.SyncUser;

import static net.ddns.zimportant.lovingpeople.service.Constant.COUNSELOR_ID;
import static net.ddns.zimportant.lovingpeople.service.Constant.ERR_USER_CANNOT_CHANGE_STATUS;
import static net.ddns.zimportant.lovingpeople.service.Constant.ERR_USER_CHAT_OTHER;
import static net.ddns.zimportant.lovingpeople.service.Constant.STORYTELLER_ID;
import static net.ddns.zimportant.lovingpeople.service.common.model.UserChat.COUNSELOR;
import static net.ddns.zimportant.lovingpeople.service.common.model.UserChat.STORYTELLER;
import static net.ddns.zimportant.lovingpeople.service.common.model.UserChat.USER_BUSY;
import static net.ddns.zimportant.lovingpeople.service.common.model.UserChat.USER_OFFLINE;
import static net.ddns.zimportant.lovingpeople.service.common.model.UserChat.USER_ONLINE;

public abstract class MessageFragment extends BaseFragment {

	@BindView(R.id.tb_message)
	Toolbar toolbar;
	@BindView(R.id.rv_message)
	RecyclerView chatRoomRecyclerView;
	@BindView(R.id.fab_message)
	FloatingActionButton fab;

	Realm realm;
	RecyclerView.LayoutManager layoutManager;
	String chatRoomRoleId;
	UserChat currentUser;
	boolean isShowFab;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
	                         @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_message, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ButterKnife.bind(this, view);
		super.setUpToolbar(toolbar);
		setUpRealm();
		setUpCurrentUser();
		setUpMessage();
	}

	private void setUpRealm() {
		realm = getMainActivity().getRealm();
	}

	private void setUpCurrentUser() {
		currentUser = realm
				.where(UserChat.class)
				.equalTo("id", SyncUser.current().getIdentity())
				.findFirst();
	}

	private void setUpMessage() {
		if (currentUser == null || !currentUser.isValid()) {
			getMainActivity().logOutRealm();
			return;
		}
		setUpInformation();
		setUpRecyclerView();
		setUpFab();
	}

	protected abstract void setUpInformation();

	private void setUpRecyclerView() {
		RealmResults<ChatRoom> items = getRealmResults();
		ChatRoomsRecyclerAdapter chatRoomsRecyclerAdapter = new ChatRoomsRecyclerAdapter(items);

		layoutManager = new LinearLayoutManager(getContext());
		chatRoomRecyclerView.setLayoutManager(layoutManager);
		chatRoomRecyclerView.setAdapter(chatRoomsRecyclerAdapter);
	}

	private RealmResults<ChatRoom> getRealmResults() {
		return realm
				.where(ChatRoom.class)
				.equalTo(chatRoomRoleId, currentUser.getId())
				.findAllAsync();
	}

	private void setUpFab() {
		if (isShowFab) {
			fab.setVisibility(View.VISIBLE);
			fab.setOnClickListener(view -> {
				SearchCounselorsActivity.open(getContext());
			});
		} else {
			fab.setVisibility(View.GONE);
		}
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		//inflater.inflate(R.menu.menu_message, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
