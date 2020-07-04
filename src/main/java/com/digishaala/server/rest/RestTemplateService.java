package com.digishaala.server.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientResponseException;

@Component
public class RestTemplateService {
	public String restTemplateAuthToken(String url, String username, String password) throws IOException {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("username",username);
		map.add("password",password);

		System.out.println("POSTing to "+url);
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			return response.getHeaders().toSingleValueMap().toString();
		} catch(RestClientResponseException e) {
			return e.getResponseBodyAsString();
		}
		//System.out.println(Integer.toString(response.getStatusCodeValue()));
	}
	private int RETRY_COUNT = 2;
	private int TIME_OUT_SECONDS = 11*60*1000;
	public String httpClientAuthToken(String url, String username, String password) throws IOException {
		LayeredConnectionSocketFactory ssl =  new SSLConnectionSocketFactory(createSSLContentext(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Registry<ConnectionSocketFactory> sfr = RegistryBuilder
			.<ConnectionSocketFactory> create()
			.register("http", PlainConnectionSocketFactory.getSocketFactory())
			.register("https", ssl != null ? ssl : SSLConnectionSocketFactory.getSocketFactory()).build();

		CloseableHttpClient httpClient = HttpClientBuilder.create()
			.setDefaultRequestConfig(RequestConfig.custom().setConnectionRequestTimeout(TIME_OUT_SECONDS).setSocketTimeout(TIME_OUT_SECONDS).setConnectTimeout(TIME_OUT_SECONDS).build())
			.setConnectionManager(new PoolingHttpClientConnectionManager(sfr)).setRetryHandler(new HttpRequestRetryHandler() {
					@Override
					public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
					if(executionCount > RETRY_COUNT){
					return false;
					}else{
					return true;
					}
					}
					}).setMaxConnTotal(200).setMaxConnPerRoute(100).build();

		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		httpPost.setEntity(new UrlEncodedFormEntity(params));
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

		BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine);
		}
		reader.close();

		System.out.println(response.toString());
		httpClient.close();
		return(response.toString());
	}
	private SSLContext createSSLContentext(){
		try {
			SSLContext sslcontext;
			sslcontext = SSLContext.getInstance(SSLConnectionSocketFactory.TLS); 
			sslcontext.init(null, getTrustsCerts(), new SecureRandom());
			return sslcontext;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private TrustManager[] getTrustsCerts() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };
		return trustAllCerts;
	}
}
