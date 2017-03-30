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
	
	/** GET���� */
	public static final int METHOD_GET = 1;
	/** POST���� */
	public static final int METHOD_POST = 2;
	
	
	/**
	 * ͨ��httpClient���http������Ӧʵ������ΪUTF-8��
	 * 
	 * @param uri
	 *            �����ַ
	 * @param params
	 *            �������
	 * @param method
	 *            ����ʽ(post/get)
	 * @return ��Ӧʵ��
	 * @throws IOException
	 */
	public static HttpEntity getEntity(String uri,	ArrayList<NameValuePair> params, int method) throws IOException {
		HttpEntity entity = null;
		// �����ͻ���
		DefaultHttpClient client = createHttpClient();
		// ��������
		HttpUriRequest request = null;
		
		//��֧ѡ������ķ�ʽ
		switch (method) { 
		case METHOD_GET:  //get���󷽷�
			StringBuilder sb = new StringBuilder(uri);
			if (params != null && !params.isEmpty()) {
				sb.append("?");
			}
			
			//����������Ϣ
			request = new HttpGet(URLParam.buildParams(params, sb));
			break;

		case METHOD_POST:  //POST�������
			request = new HttpPost(uri);
			
			//�ж���������Ƿ�Ϊ��
			if (params != null && !params.isEmpty()) {
				UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(
						params, "UTF-8");
				//����������Ϣ
				((HttpPost) request).setEntity(reqEntity);
			}
			break;
		}
		// ��һ��һ���ǻ�δ����ֵ������ֵ��SessionId����������
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
		// ִ�����󣬻����Ӧ
		HttpResponse response = client.execute(request);
		// �ж���Ӧ��
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// �����Ӧ��Ϊ200�����ȡ��Ӧʵ��
			entity = response.getEntity();
			List<Cookie> list = client.getCookieStore().getCookies();
			for (Cookie cookie : list) {
				// �����Ƕ�ȡCookie['JSPSESSID']��ֵ���ھ�̬�����У���֤ÿ�ζ���ͬһ��ֵ
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

	// ����HttpClientʵ��
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
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); // ����������������֤
			
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
     * ��ȡ��ϵ��
     * @return
     */
    public static List<WxcBusInfo> getWxcBusInfoListFromFile(String path) throws Exception{
        // �������ļ�·��
        path = "http://192.192.8.233:8080/Test/list.xml";
        HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
        conn.setConnectTimeout(5000);   //���ó�ʱ5��
        conn.setRequestMethod("GET");   //��������ʽ
        if(conn.getResponseCode() == 200){  //���ӳɹ�������200
            return parseXML(conn.getInputStream());
        }
        return null;
    }
	
    
    
    /**
     * ����pull��������xml�ļ����н���
     * 
     * ����һ������·��path��ͨ��URL�����ӣ�ͨ��HttpURLConnection���ӷ���ˣ��õ�������������xml�ļ��ٻ�����ݡ�
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
        int event = pullParser.getEventType();  //ȡ�ÿ�ʼ�ĵ��﷨
        while(event != XmlPullParser.END_DOCUMENT){     //ֻҪ�������ĵ������¼���ѭ������
            switch (event) {
            case XmlPullParser.START_TAG:       //��ʼ��ǩ
                break;                 
            case XmlPullParser.END_TAG:     //������ǩ
                break;
            }
            event = pullParser.next();  //ȥ��һ����ǩ
        }
        return contacts;
    }
    
    
    
    /**
     * ��ȡ����ͼƬ,���ͼƬ�����ڻ����У��ͷ��ظ�ͼƬ������������м��ظ�ͼƬ����������
     * @param path ͼƬ·��
     * @return
     */
    public static Uri getImage(String path, File cacheDir) throws Exception{// path -> MD5 ->32�ַ���.jpg
        File localFile = new File(cacheDir, MD5.getDigest(path)+ path.substring(path.lastIndexOf(".")));
        if(localFile.exists()){
        	System.out.println("ͼƬ�ڻ����д��ڣ�ֱ�Ӵӻ����ȡ");
            return Uri.fromFile(localFile);
        }else{
        	System.out.println("ͼƬ�ڻ����в����ڣ�ֱ�Ӵӷ�������ȡ");        	
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
