package com.alibaba.ocean.rawsdk;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.ocean.rawsdk.client.APIId;
import com.alibaba.ocean.rawsdk.client.AlibabaClientFactory;
import com.alibaba.ocean.rawsdk.client.Request;
import com.alibaba.ocean.rawsdk.client.SDKListener;
import com.alibaba.ocean.rawsdk.client.SyncAPIClient;
import com.alibaba.ocean.rawsdk.client.entity.AuthorizationTokenStore;
import com.alibaba.ocean.rawsdk.client.entity.DefaultAuthorizationTokenStore;
import com.alibaba.ocean.rawsdk.client.policy.ClientPolicy;
import com.alibaba.ocean.rawsdk.client.policy.RequestPolicy;
import com.alibaba.ocean.rawsdk.client.serialize.DeSerializerListener;
import com.alibaba.ocean.rawsdk.client.serialize.SerializerListener;
import com.alibaba.ocean.rawsdk.common.AbstractAPIRequest;

import com.alibaba.ocean.rawsdk.common.BizResultWrapper;

/**
 * The API facade.
 */
public final class ApiExecutor implements SDKListener {
	private String serverHost = "api-be.ele.me";

	private int httpPort = 80;
	private int httpsPort = 443;
	private String appKey;
	private String secKey;

	private AuthorizationTokenStore authorizationTokenStore;

	private Map<Class<? extends SerializerListener>, SerializerListener> serializerListeners = new LinkedHashMap<Class<? extends SerializerListener>, SerializerListener>();
	private Map<Class<? extends DeSerializerListener>, DeSerializerListener> deSerializerListeners = new LinkedHashMap<Class<? extends DeSerializerListener>, DeSerializerListener>();
    private volatile SyncAPIClient syncAPIClient;

    public ApiExecutor(String appKey, String secKey) {
		super();
		this.appKey = appKey;
		this.secKey = secKey;
	}

	public ApiExecutor(String serverHost, int httpPort, int httpsPort, String appKey, String secKey) {
		super();
		this.serverHost = serverHost;
		this.httpPort = httpPort;
		this.httpsPort = httpsPort;
		this.appKey = appKey;
		this.secKey = secKey;
	}

	public void register(SerializerListener serializerListener) {
		serializerListeners.put(serializerListener.getClass(), serializerListener);
	}

	public void register(DeSerializerListener deSerializerListener) {
		deSerializerListeners.put(deSerializerListener.getClass(), deSerializerListener);
	}

	private SyncAPIClient getAPIClient() {
		ClientPolicy clientPolicy = new ClientPolicy(serverHost);
		clientPolicy.setHttpPort(httpPort);
		clientPolicy.setHttpsPort(httpsPort);
		if (appKey != null) {
			clientPolicy.setAppKey(appKey);
		}
		if (secKey != null) {
			clientPolicy.setSigningKey(secKey);
		}
		if (authorizationTokenStore == null) {
			authorizationTokenStore = new DefaultAuthorizationTokenStore();
		}

		if (syncAPIClient == null) {
		    synchronized(this) {
		        if (syncAPIClient == null) {
                    syncAPIClient = new AlibabaClientFactory().createAPIClient(clientPolicy, authorizationTokenStore);
		        }
            }
        }

		return syncAPIClient;
	}

	/**
	 *
	 *
	 * @param code
	 *
	 * @return
	 */
//	public final AuthorizationToken getToken(String code) {
//		try {
//			return getAPIClient().getToken(code);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	/**
	 * refresh the access token with refreshToken
	 *
	 * @param refreshToken
	 *
	 * @return access token object.
	 */
//	public final AuthorizationToken refreshToken(String refreshToken) {
//		try {
//			return getAPIClient().refreshToken(refreshToken);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	/**
	 *
	 * @param apiRequest
	 * @return
	 */
	public final <TResponse> BizResultWrapper<TResponse> execute(AbstractAPIRequest<TResponse> apiRequest) throws IOException {
		RequestPolicy reqPolicy = apiRequest.getOceanRequestPolicy();

		APIId apiId = apiRequest.getOceanApiId();
		Request req = new Request(apiId.getNamespace(), apiId.getName(), apiId.getVersion());

		req.setRequestEntity(apiRequest);

		BizResultWrapper<TResponse> ret = getAPIClient().send(req, apiRequest.getResponseClass(), reqPolicy,
				serializerListeners.values(), deSerializerListeners.values());
		return ret;

	}

	/**
	 *
	 * @param apiRequest
	 * @return
	 */
	public final <TResponse> BizResultWrapper<TResponse> execute(AbstractAPIRequest<TResponse> apiRequest, String accessToken) throws IOException {
		RequestPolicy reqPolicy = apiRequest.getOceanRequestPolicy();

		APIId apiId = apiRequest.getOceanApiId();
		Request req = new Request(apiId.getNamespace(), apiId.getName(), apiId.getVersion());

		req.setRequestEntity(apiRequest);
		req.setAccessToken(accessToken);

		BizResultWrapper<TResponse> ret = getAPIClient().send(req, apiRequest.getResponseClass(), reqPolicy,
				serializerListeners.values(), deSerializerListeners.values());
		return ret;

	}

	public static void main(String args[]){
//		ApiExecutor apiExecutor = new ApiExecutor("{appKey}", "{appSecret}");
//		AuthorizationToken token = apiExecutor.getToken("{the access token}");
//		System.out.println(token);
	}

}
