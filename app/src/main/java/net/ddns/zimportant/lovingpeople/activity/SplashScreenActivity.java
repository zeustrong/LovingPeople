package net.ddns.zimportant.lovingpeople.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import net.ddns.zimportant.lovingpeople.service.Constant;
import net.ddns.zimportant.lovingpeople.service.utils.AppUtils;

import io.realm.ObjectServerError;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

public class SplashScreenActivity extends BaseActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (SyncUser.current() != null) {
			MainActivity.open(this);
		} else {
			WelcomeActivity.open(this);
		}

		/*
		TaskStackBuilder.create(this)
				.addNextIntentWithParentStack(new Intent(this, MainActivity.class))
				.addNextIntent(new Intent(this, IntroActivity.class))
				.startActivities();
				*/
	}
}
