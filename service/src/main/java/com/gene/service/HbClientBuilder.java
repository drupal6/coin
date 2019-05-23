package com.gene.service;

import com.huobi.client.AsyncRequestClient;
import com.huobi.client.RequestOptions;
import com.huobi.client.SubscriptionClient;
import com.huobi.client.SubscriptionOptions;
import com.huobi.client.SyncRequestClient;

import io.netty.util.internal.StringUtil;

public class HbClientBuilder {
	
	public static SyncRequestClient syncClient() {
		return syncClient(null, null, null);
	}
	
	public static SyncRequestClient syncClient(String apiKey, String secretKey) {
		return syncClient(apiKey, secretKey, null);
	}
	
	public static SyncRequestClient syncClient(String apiKey, String secretKey, String url) {
		RequestOptions options = null;
		if(false == StringUtil.isNullOrEmpty(url)) {
			options = new RequestOptions();
			options.setUrl(url);
		}
		if(false == StringUtil.isNullOrEmpty(apiKey)) {
			if(options != null) {
				return SyncRequestClient.create(apiKey, secretKey, options);
			} else {
				return SyncRequestClient.create(apiKey, secretKey);
			}
		} else {
			return SyncRequestClient.create();
		}
	}
	
	public static AsyncRequestClient asyncClient() {
		return asyncClient(null, null, null);
	}
	
	public static AsyncRequestClient asyncClient(String apiKey, String secretKey) {
		return asyncClient(apiKey, secretKey, null);
	}
	
	public static AsyncRequestClient asyncClient(String apiKey, String secretKey, String url) {
		RequestOptions options = null;
		if(false == StringUtil.isNullOrEmpty(url)) {
			options = new RequestOptions();
			options.setUrl(url);
		}
		if(false == StringUtil.isNullOrEmpty(apiKey)) {
			if(options != null) {
				return AsyncRequestClient.create(apiKey, secretKey, options);
			} else {
				return AsyncRequestClient.create(apiKey, secretKey);
			}
		} else {
			return AsyncRequestClient.create();
		}
	}
	
	public static SubscriptionClient subClient() {
		return subClient(null, null, null);
	}
	
	public static SubscriptionClient subClient(String apiKey, String secretKey) {
		return subClient(apiKey, secretKey, null);
	}
	
	public static SubscriptionClient subClient(String apiKey, String secretKey, String url) {
		SubscriptionOptions options = null;
		if(false == StringUtil.isNullOrEmpty(url)) {
			options = new SubscriptionOptions();
			options.setUri(url);
		}
		if(false == StringUtil.isNullOrEmpty(apiKey)) {
			if(options != null) {
				return SubscriptionClient.create(apiKey, secretKey, options);
			} else {
				return SubscriptionClient.create(apiKey, secretKey);
			}
		} else {
			return SubscriptionClient.create();
		}
	}
}
