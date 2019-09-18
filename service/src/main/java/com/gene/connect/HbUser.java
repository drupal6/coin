package com.gene.connect;

import com.huobi.client.AsyncRequestClient;
import com.huobi.client.SubscriptionClient;

public class HbUser {
	
	private final User user;
	
	private AsyncRequestClient authAsyncRequestClient;
	
	private SubscriptionClient authSubscriptionClient;

	public HbUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

	public SubscriptionClient getAuthSubscriptionClient() {
		return authSubscriptionClient;
	}

	public void setAuthSubscriptionClient(SubscriptionClient authSubscriptionClient) {
		this.authSubscriptionClient = authSubscriptionClient;
	}
	
	public AsyncRequestClient getAuthAsyncRequestClient() {
		return authAsyncRequestClient;
	}

	public void setAuthAsyncRequestClient(AsyncRequestClient authAsyncRequestClient) {
		this.authAsyncRequestClient = authAsyncRequestClient;
	}

	public void unSub() {
		if(authSubscriptionClient != null) {
			authSubscriptionClient.unsubscribeAll();
		}
	}
}
