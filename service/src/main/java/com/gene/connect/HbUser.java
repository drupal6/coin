package com.gene.connect;

import com.huobi.client.AsyncRequestClient;
import com.huobi.client.SubscriptionClient;

public class HbUser {
	
	private AsyncRequestClient authAsyncClient;
	
	private AsyncRequestClient asyncClient;
	
	private SubscriptionClient subscriptionClient;
	
	private SubscriptionClient authSubscriptionClient;
	
	public AsyncRequestClient getAuthAsyncClient() {
		return authAsyncClient;
	}

	public void setAuthAsyncClient(AsyncRequestClient authAsyncClient) {
		this.authAsyncClient = authAsyncClient;
	}

	public AsyncRequestClient getAsyncClient() {
		if(authAsyncClient == null) {
			authAsyncClient = AsyncRequestClient.create();
		}
		return asyncClient;
	}

	public void setAsyncClient(AsyncRequestClient asyncClient) {
		this.asyncClient = asyncClient;
	}

	public SubscriptionClient getSubscriptionClient() {
		if(subscriptionClient == null) {
			subscriptionClient = SubscriptionClient.create();
		}
		return subscriptionClient;
	}

	public void setSubscriptionClient(SubscriptionClient subscriptionClient) {
		this.subscriptionClient = subscriptionClient;
	}

	public SubscriptionClient getAuthSubscriptionClient() {
		return authSubscriptionClient;
	}

	public void setAuthSubscriptionClient(SubscriptionClient authSubscriptionClient) {
		this.authSubscriptionClient = authSubscriptionClient;
	}
	
	public void unSub() {
		if(subscriptionClient != null) {
			subscriptionClient.unsubscribeAll();
		}
		if(authSubscriptionClient != null) {
			authSubscriptionClient.unsubscribeAll();
		}
	}
}
