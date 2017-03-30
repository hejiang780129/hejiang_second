package com.hbjr.washcheapp.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;


import android.net.Uri;
import android.util.Xml;

import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.util.MD5;

public class WxcHttpConnect {
	
	/** GET请求 */
	public static final int METHOD_GET = 1;
	/** POST请求 */
	public static final int METHOD_POST = 2;
	
	
	/**
	 * 通过httpClient获得http请求响应实，编码为UTF-8。
	 * 
	 * @param uri
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param method
	 *            请求方式(post/get)
	 * @return 响应实体
	 * @throws IOException
	 */
	public static HttpEntity getEntity(String uri,	ArrayList<NameValuePair> params, int method) throws IOException {
		HttpEntity entity = null;
		// 创建客户端
		DefaultHttpClient client = createHttpClient();
		// 创建请求
		HttpUriRequest request = null;
		
		//分支选择请求的方式
		switch (method) { 
		case METHOD_GET:  //get请求方法
			StringBuilder sb = new StringBuilder(uri);
			if (params != null && !params.isEmpty()) {
				sb.append("?");
			}
			
			//发送请求信息
			request = new HttpGet(URLParam.buildParams(params, sb));
			break;

		case METHOD_POST:  //POST请求参数
			request = new HttpPost(uri);
			
			//判断请求参数是否为空
			if (params != null && !params.isEmpty()) {
				UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(
						params, "UTF-8");
				//发送请求信息
				((HttpPost) request).setEntity(reqEntity);
			}
			break;
		}
		// 第一次一般是还未被赋值，若有值则将SessionId发给服务器
		if (null != ConstantS.JSESSIONID) {
			request.setHeader("Cookie", "JSESSIONID=" + ConstantS.JSESSIONID);
		}
		request.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);
		request.getParams().setParameter(HTTP.CHARSET_PARAM, HTTP.UTF_8);
		request.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET,
				HTTP.UTF_8);
		// request.getParams().setParameter(HTTP.DEFAULT_CONTENT_CHARSET,
		// HTTP.UTF_8);
		request.getParams().setParameter("http.protocol.content-type-charset",
				HTTP.UTF_8);
		// 执行请求，获得响应
		HttpResponse response = client.execute(request);
		// 判断响应码
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// 如果响应码为200，则获取响应实体
			entity = response.getEntity();
			List<Cookie> list = client.getCookieStore().getCookies();
			for (Cookie cookie : list) {
				// 这里是读取Cookie['JSPSESSID']的值存在静态变量中，保证每次都是同一个值
				if ("JSESSIONID".equals(cookie.getName())) {
					ConstantS.JSESSIONID = cookie.getValue();
					break;
				}
			}
			// Log.i("info", "entity: " + entity.getContentType().getName() +
			// "="
			// + entity.getContentType().getValue());
		}
		return entity;
	}

	// 创建HttpClient实例
	public static DefaultHttpClient createHttpClient() {
		try {
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams.setHttpElementCharset(params, HTTP.UTF_8);

			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory sf = new HttpConnection.SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); // 允许所有主机的验证
			
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schReg.register(new Scheme("https", sf, 443));
			ClientConnectionManager conman = new ThreadSafeClientConnManager(params, schReg);

			return new DefaultHttpClient(conman, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static class SSLSocketFactoryEx extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public SSLSocketFactoryEx(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);
			TrustManager tm = new X509TrustManager() {

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
				}
			};
			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host,
					port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}
	
	
	
	
	  /**
     * 获取联系人
     * @return
     */
    public static List<WxcBusInfo> getWxcBusInfoListFromFile(String path) throws Exception{
        // 服务器文件路径
        path = "http://192.192.8.233:8080/Test/list.xml";
        HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
        conn.setConnectTimeout(5000);   //设置超时5秒
        conn.setRequestMethod("GET");   //设置请求方式
        if(conn.getResponseCode() == 200){  //连接成功返回码200
            return parseXML(conn.getInputStream());
        }
        return null;
    }
	
    
    
    /**
     * 利用pull解析器对xml文件进行解析
     * 
     * 传递一个网络路径path，通过URL打开连接，通过HttpURLConnection连接服务端，得到输入流，解析xml文件再获得数据。
     * 
     * @param xml
     * @return
     * @throws Exception
     */
    private static List<WxcBusInfo> parseXML(InputStream xml) throws Exception{
        List<WxcBusInfo> contacts = new ArrayList<WxcBusInfo>();
        android.provider.ContactsContract.Contacts contact = null;
        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(xml, "UTF-8");  
        int event = pullParser.getEventType();  //取得开始文档语法
        while(event != XmlPullParser.END_DOCUMENT){     //只要不等于文档结束事件，循环解析
            switch (event) {
            case XmlPullParser.START_TAG:       //开始标签
                break;                 
            case XmlPullParser.END_TAG:     //结束标签
                break;
            }
            event = pullParser.next();  //去下一个标签
        }
        return contacts;
    }
    
    
    
    /**
     * 获取网络图片,如果图片存在于缓存中，就返回该图片，否则从网络中加载该图片并缓存起来
     * @param path 图片路径
     * @return
     */
    public static Uri getImage(String path, File cacheDir) throws Exception{// path -> MD5 ->32字符串.jpg
        File localFile = new File(cacheDir, MD5.getDigest(path)+ path.substring(path.lastIndexOf(".")));
        if(localFile.exists()){
        	System.out.println("图片在缓存中存在，直接从混存中取");
            return Uri.fromFile(localFile);
        }else{
        	System.out.println("图片在缓存中不存在，直接从服务器上取");        	
            HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if(conn.getResponseCode() == 200){
                FileOutputStream outStream = new FileOutputStream(localFile);
                InputStream inputStream = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while( (len = inputStream.read(buffer)) != -1){
                    outStream.write(buffer, 0, len);
                }
                inputStream.close();
                outStream.close();
                return Uri.fromFile(localFile);
            }
        }
        return null;
    }
    

}
