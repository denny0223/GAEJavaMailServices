package com.denny.mailservices.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String address, String content, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
