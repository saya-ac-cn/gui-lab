package ac.cn.saya.lab.tools;

import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.alibaba.fastjson.JSON;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.SSLInitializationException;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @Title: HttpRequestUtils
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author Administrator
 * @Date: 2020/3/31 0031 09:55
 * @Description: 支持POST和GET请求, 支持SSL
 * @description HttpClient 4.5.2
 * @description fastjson 1.2.31
 *
 * <dependency>
 * <groupId>org.apache.httpcomponents</groupId>
 * <artifactId>httpclient</artifactId>
 * <version>4.5.2</version>
 * </dependency>
 *
 * <dependency>
 * <groupId>com.alibaba</groupId>
 * <artifactId>fastjson</artifactId>
 * <version>1.2.31</version>
 * </dependency>
 */
public class HttpRequestUtils {

    /**
     * 全局的tontext
     */
    private static HttpClientContext clientContext;


    /**
     * 连接池
     */
    private static PoolingHttpClientConnectionManager connManager;

    /**
     * 编码
     */
    private static final String ENCODING = "UTF-8";

    /**
     * 出错返回结果
     */
    private static final String RESULT = "-1";

    public static HttpClientContext getClientContext() {
        return clientContext;
    }

    /**
     * 初始化连接池管理器,配置SSL
     */
    static {
        if (connManager == null) {
            try {
                // 创建ssl安全访问连接
                // 获取创建ssl上下文对象
                /**
                 * 使用带证书的定制SSL访问
                 */
                //File authFile = new File("C:/Users/lynch/Desktop/my.keystore");
                //SSLContext sslContext = getSSLContext(false, authFile, "mypassword");

                // 注册
                Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.INSTANCE)
                        //.register("https", new SSLConnectionSocketFactory(sslContext))
                        .build();

                // ssl注册到连接池
                connManager = new PoolingHttpClientConnectionManager(registry);
                connManager.setMaxTotal(1000);  // 连接池最大连接数
                connManager.setDefaultMaxPerRoute(20);  // 每个路由最大连接数
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (null == clientContext){
            try {
                clientContext = HttpClientContext.create();
                CookieStore cookieStore = new BasicCookieStore();
                clientContext.setCookieStore(cookieStore);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取客户端连接对象
     *
     * @param timeOut 超时时间
     * @return
     */
    private static CloseableHttpClient getHttpClient(Integer timeOut) {

        // 配置请求参数
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeOut).
                        setConnectTimeout(timeOut).
                        setSocketTimeout(timeOut).
                        build();
        // 配置超时回调机制
        HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 3) {// 如果已经重试了3次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return true;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(retryHandler)
                .build();

        return httpClient;

    }

    /**
     * 获取SSL上下文对象,用来构建SSL Socket连接
     *
     * @param isDeceive 是否绕过SSL
     * @param creFile   整数文件,isDeceive为true 可传null
     * @param crePwd    整数密码,isDeceive为true 可传null, 空字符为没有密码
     * @return SSL上下文对象
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws CertificateException
     */
    private static SSLContext getSSLContext(boolean isDeceive, File creFile, String crePwd) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, FileNotFoundException, IOException {
        SSLContext sslContext = null;
        if (isDeceive) {
            sslContext = SSLContext.getInstance("SSLv3");
            // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
            X509TrustManager x509TrustManager = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
            };
            sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
        } else {
            if (null != creFile && creFile.length() > 0) {
                if (null != crePwd) {
                    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                    keyStore.load(new FileInputStream(creFile), crePwd.toCharArray());
                    sslContext = SSLContexts.custom().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy()).build();
                } else {
                    throw new SSLHandshakeException("整数密码为空");
                }
            }
        }
        return sslContext;
    }

    /**
     * post请求,支持SSL
     *
     * @param url           请求地址
     * @param headers       请求头信息
     * @param params        请求参数
     * @param timeOut       超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param isStream      是否以流的方式获取响应信息
     * @param clientContext Http请求客户端上下文对象，包含Cookie
     * @return 响应信息
     * @throws UnsupportedEncodingException
     */
    public static String httpPost(String url, Map<String, Object> headers, Map<String, Object> params, Integer timeOut, boolean isStream, HttpClientContext clientContext) throws UnsupportedEncodingException {
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);
        // 添加请求头信息
        if (null != headers) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        // 添加请求参数信息
        if (null != params) {
            httpPost.setEntity(new UrlEncodedFormEntity(covertParams2NVPS(params), ENCODING));
        }
        return getResult(httpPost, timeOut, isStream, clientContext);
    }

    /**
     * post请求,支持SSL
     *
     * @param url           请求地址
     * @param params        请求参数
     * @param timeOut       超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param clientContext Http请求客户端上下文对象，包含Cookie
     * @return 响应信息
     * @throws UnsupportedEncodingException
     */
    public static String httpPost(String url, Map<String, Object> params, Integer timeOut, HttpClientContext clientContext) throws UnsupportedEncodingException {
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);
        // 添加请求参数信息
        if (null != params) {
            httpPost.setEntity(new UrlEncodedFormEntity(covertParams2NVPS(params), ENCODING));
        }
        return getResult(httpPost, timeOut, true, clientContext);
    }

    /**
     * post请求,支持SSL
     *
     * @param url           请求地址
     * @param headers       请求头信息
     * @param params        请求参数
     * @param timeOut       超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param isStream      是否以流的方式获取响应信息
     * @param clientContext Http请求客户端上下文对象，包含Cookie
     * @return 响应信息
     * @throws UnsupportedEncodingException
     */
    public static String httpPost(String url, JSONObject headers, JSONObject params, Integer timeOut, boolean isStream, HttpClientContext clientContext) throws UnsupportedEncodingException {
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头 发送的是json数据格式
        httpPost.setHeader("Content-type", "application/json;charset=utf-8");
        httpPost.setHeader("Connection", "Close");
        // 添加请求头信息
        if (null != headers) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        // 添加请求参数信息
        if (null != params) {
            StringEntity entity = new StringEntity(JSON.toJSONString(params), ENCODING);
            //设置编码格式
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            entity.setContentType("application/json");
            //把请求消息实体塞进去
            httpPost.setEntity(entity);
        }
        return getResult(httpPost, timeOut, isStream, clientContext);

    }

    /**
     * post请求,支持SSL
     *
     * @param url           请求地址
     * @param params        请求参数
     * @param timeOut       超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param clientContext Http请求客户端上下文对象，包含Cookie
     * @return 响应信息
     * @throws UnsupportedEncodingException
     */
    public static String httpPost(String url, JSONObject params, Integer timeOut, HttpClientContext clientContext) throws UnsupportedEncodingException {
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);
        // 添加请求参数信息
        if (null != params) {
            httpPost.setEntity(new UrlEncodedFormEntity(covertParams2NVPS(params), ENCODING));
        }
        return getResult(httpPost, timeOut, true, clientContext);
    }

    /**
     * get请求,支持SSL
     *
     * @param url           请求地址
     * @param headers       请求头信息
     * @param params        请求参数
     * @param timeOut       超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param isStream      是否以流的方式获取响应信息
     * @param clientContext Http请求客户端上下文对象，包含Cookie
     * @return 响应信息
     * @throws URISyntaxException
     */
    public static String httpGet(String url, Map<String, Object> headers, Map<String, Object> params, Integer timeOut, boolean isStream, HttpClientContext clientContext) throws URISyntaxException {
        // 构建url
        URIBuilder uriBuilder = new URIBuilder(url);
        // 添加请求参数信息
        if (null != params) {
            uriBuilder.setParameters(covertParams2NVPS(params));
        }
        // 创建post请求
        HttpGet httpGet = new HttpGet(url);
        // 添加请求头信息
        if (null != headers) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        return getResult(httpGet, timeOut, isStream, clientContext);

    }

    /**
     * get请求,支持SSL
     *
     * @param url           请求地址
     * @param headers       请求头信息
     * @param params        请求参数
     * @param timeOut       超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param isStream      是否以流的方式获取响应信息
     * @param clientContext Http请求客户端上下文对象，包含Cookie
     * @return 响应信息
     * @throws URISyntaxException
     */
    public static String httpGet(String url, JSONObject headers, JSONObject params, Integer timeOut, boolean isStream, HttpClientContext clientContext) throws URISyntaxException {
        // 构建url
        URIBuilder uriBuilder = new URIBuilder(url);
        // 添加请求参数信息
        if (null != params) {
            uriBuilder.setParameters(covertParams2NVPS(params));
        }
        URI uri = uriBuilder.build();
        // 创建post请求
        HttpGet httpGet = new HttpGet(uri);
        // 设置请求头 发送的是json数据格式
        httpGet.setHeader("Content-type", "application/json;charset=utf-8");
        httpGet.setHeader("Connection", "Close");
        // 添加请求头信息
        if (null != headers) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        return getResult(httpGet, timeOut, isStream, clientContext);
    }

    /**
     * 文件下载,支持SSL
     *
     * @param url           请求地址
     * @param headers       请求头信息
     * @param params        请求参数
     * @param savePath      保存路径
     * @param timeOut       超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param isStream      是否以流的方式获取响应信息
     * @param clientContext Http请求客户端上下文对象，包含Cookie
     * @return 响应信息
     * @throws URISyntaxException
     */
    public static boolean httpDownload(String url, JSONObject headers, JSONObject params, String savePath, Integer timeOut, boolean isStream, HttpClientContext clientContext) throws URISyntaxException {
        // 构建url
        URIBuilder uriBuilder = new URIBuilder(url);
        // 添加请求参数信息
        if (null != params) {
            uriBuilder.setParameters(covertParams2NVPS(params));
        }
        boolean result = false;
        URI uri = uriBuilder.build();
        // 创建post请求
        HttpGet httpGet = new HttpGet(uri);
        // 设置请求头 发送的是json数据格式
        httpGet.setHeader("Content-type", "application/json;charset=utf-8");
        httpGet.setHeader("Connection", "Close");
        // 添加请求头信息
        if (null != headers) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        InputStream is = null;
        FileOutputStream fileOut = null;
        // 获取连接客户端
        CloseableHttpClient httpClient = getHttpClient(timeOut);
        try(
                CloseableHttpResponse response = (null != clientContext)?httpClient.execute(httpGet, clientContext):httpClient.execute(httpGet);
                ) {
            /// 发起请求
            ///if (null != clientContext) {
            ///    response = httpClient.execute(httpGet, clientContext);
            ///} else {
            ///    response = httpClient.execute(httpGet);
            ///}
            // 获取状态码
            int respCode = response.getStatusLine().getStatusCode();
            if (200 == respCode){
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                File file = new File(savePath);
                file.getParentFile().mkdirs();
                fileOut = new FileOutputStream(file);
                /**
                 * 根据实际运行效果 设置缓冲区大小
                 */
                byte[] buffer = new byte[4096];
                int ch = 0;
                while ((ch = is.read(buffer)) != -1) {
                    fileOut.write(buffer, 0, ch);
                }
                fileOut.flush();
                result = true;
            }
            response.close();
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        } finally {
            if (null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fileOut){
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

    }

    /**
     * get请求,支持SSL
     *
     * @param url           请求地址
     * @param params        请求参数
     * @param timeOut       超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param clientContext Http请求客户端上下文对象，包含Cookie
     * @return 响应信息
     * @throws URISyntaxException
     */
    public static String httpGet(String url, Map<String, Object> params, Integer timeOut, HttpClientContext clientContext) throws URISyntaxException {
        // 构建url
        URIBuilder uriBuilder = new URIBuilder(url);
        // 添加请求参数信息
        if (null != params) {
            uriBuilder.setParameters(covertParams2NVPS(params));
        }
        // 创建post请求
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet, timeOut, true, clientContext);
    }


    /**
     * get请求,支持SSL
     *
     * @param url           请求地址
     * @param params        请求参数
     * @param timeOut       超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param clientContext Http请求客户端上下文对象，包含Cookie
     * @return 响应信息
     * @throws URISyntaxException
     */
    public static String httpGet(String url, JSONObject params, Integer timeOut, HttpClientContext clientContext) throws URISyntaxException {
        // 构建url
        URIBuilder uriBuilder = new URIBuilder(url);
        // 添加请求参数信息
        if (null != params) {
            uriBuilder.setParameters(covertParams2NVPS(params));
        }
        // 创建post请求
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet, timeOut, true, clientContext);
    }

    /**
     * Put请求,支持SSL
     *
     * @param url           请求地址
     * @param headers       请求头信息
     * @param params        请求参数
     * @param timeOut       超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param isStream      是否以流的方式获取响应信息
     * @param clientContext Http请求客户端上下文对象，包含Cookie
     * @return 响应信息
     * @throws UnsupportedEncodingException
     */
    public static String httpPut(String url, JSONObject headers, JSONObject params, Integer timeOut, boolean isStream, HttpClientContext clientContext) throws UnsupportedEncodingException {
        // 创建Put请求
        HttpPut httpPut = new HttpPut(url);
        // 设置请求头 发送的是json数据格式
        httpPut.setHeader("Content-type", "application/json;charset=utf-8");
        httpPut.setHeader("Connection", "Close");
        // 添加请求头信息
        if (null != headers) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpPut.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        // 添加请求参数信息
        if (null != params) {
            StringEntity entity = new StringEntity(JSON.toJSONString(params), ENCODING);
            //设置编码格式
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            entity.setContentType("application/json");
            //把请求消息实体塞进去
            httpPut.setEntity(entity);
        }
        return getResult(httpPut, timeOut, isStream, clientContext);

    }

    /**
     * delete请求,支持SSL
     *
     * @param url           请求地址
     * @param headers       请求头信息
     * @param params        请求参数
     * @param timeOut       超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param isStream      是否以流的方式获取响应信息
     * @param clientContext Http请求客户端上下文对象，包含Cookie
     * @return 响应信息
     * @throws URISyntaxException
     */
    public static String httpDelete(String url, JSONObject headers, JSONObject params, Integer timeOut, boolean isStream, HttpClientContext clientContext) throws URISyntaxException {
        // 构建url
        URIBuilder uriBuilder = new URIBuilder(url);
        // 添加请求参数信息
        if (null != params) {
            uriBuilder.setParameters(covertParams2NVPS(params));
        }
        URI uri = uriBuilder.build();
        // 创建Deletet请求
        HttpDelete httpDeletet = new HttpDelete(uri);
        // 设置请求头 发送的是json数据格式
        httpDeletet.setHeader("Content-type", "application/json;charset=utf-8");
        httpDeletet.setHeader("Connection", "Close");
        // 添加请求头信息
        if (null != headers) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpDeletet.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        return getResult(httpDeletet, timeOut, isStream, clientContext);
    }

    private static String getResult(HttpRequestBase httpRequest, Integer timeOut, boolean isStream, HttpClientContext clientContext) {
        // 响应结果
        StringBuilder sb = null;
        //CloseableHttpResponse response = null;
        // 获取连接客户端
        CloseableHttpClient httpClient = getHttpClient(timeOut);
        try( // 发起请求
             CloseableHttpResponse response = (null != clientContext)?(httpClient.execute(httpRequest, clientContext)):httpClient.execute(httpRequest);
        ) {
            // 发起请求(使用try with resource 关闭资源代替try catch finally，只要资源集成实现Closeable即可)
            /// if (null != clientContext) {
            ///     response = httpClient.execute(httpRequest, clientContext);
            /// } else {
            ///     response = httpClient.execute(httpRequest);
            /// }
            int respCode = response.getStatusLine().getStatusCode();
            // 如果是重定向
            if (302 == respCode) {
                String locationUrl = response.getLastHeader("Location").getValue();
                return getResult(new HttpPost(locationUrl), timeOut, isStream, clientContext);
            }
            if (401 == respCode){
                // 鉴权失败
                Platform.runLater(() -> {
                    NoticeUtils.show("操作提示","登录失效，请重新登录");
                });
                return "{\"code\":-7,\"msg\":\"请登录\"}";
            }
            // 正确响应
            if (200 == respCode) {
                // 获得响应实体
                HttpEntity entity = response.getEntity();
                sb = new StringBuilder();
                // 如果是以流的形式获取
                if (isStream) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), ENCODING));
                    String len = "";
                    while ((len = br.readLine()) != null) {
                        sb.append(len);
                    }
                } else {
                    sb.append(EntityUtils.toString(entity, ENCODING));
                    if (sb.length() < 1) {
                        sb.append("-1");
                    }
                }
            }
            response.close();
            return sb == null ? RESULT : ("".equals(sb.toString().trim()) ? "-1" : sb.toString());
        } catch (ConnectionPoolTimeoutException e) {
            /// System.err.println("从连接池获取连接超时!!!");
            Platform.runLater(() -> {
                NoticeUtils.show("操作提示","获取连接资源超时，请稍后重试");
            });
            e.printStackTrace();
            return "{\"code\":-5001,\"msg\":\"从连接池获取连接超时\"}";
        } catch (SocketTimeoutException e) {
            /// System.err.println("响应超时");
            Platform.runLater(() -> {
                NoticeUtils.show("操作提示","响应超时，请稍后重试");
            });
            e.printStackTrace();
            return "{\"code\":-5002,\"msg\":\"响应超时\"}";
        } catch (ConnectTimeoutException e) {
            /// System.err.println("请求超时");
            Platform.runLater(() -> {
                NoticeUtils.show("操作提示","请求超时，请稍后重试");
            });
            e.printStackTrace();
            return "{\"code\":-5003,\"msg\":\"请求超时\"}";
        } catch (ClientProtocolException e) {
            /// System.err.println("http协议错误");
            Platform.runLater(() -> {
                NoticeUtils.show("操作提示","http协议错误，请稍后重试");
            });
            e.printStackTrace();
            return "{\"code\":-5004,\"msg\":\"http协议错误\"}";
        } catch (UnsupportedEncodingException e) {
            /// System.err.println("不支持的字符编码");
            Platform.runLater(() -> {
                NoticeUtils.show("操作提示","不支持的字符编码，请稍后重试");
            });
            e.printStackTrace();
            return "{\"code\":-5005,\"msg\":\"不支持的字符编码\"}";
        } catch (UnsupportedOperationException e) {
            /// System.err.println("不支持的请求操作");
            Platform.runLater(() -> {
                NoticeUtils.show("操作提示","不支持的请求操作，请稍后重试");
            });
            e.printStackTrace();
            return "{\"code\":-5006,\"msg\":\"不支持的请求操作\"}";
        } catch (ParseException e) {
            /// System.err.println("解析错误");
            Platform.runLater(() -> {
                NoticeUtils.show("操作提示","解析错误，请稍后重试");
            });
            e.printStackTrace();
            return "{\"code\":-5007,\"msg\":\"解析错误\"}";
        } catch (ConnectException e) {
            /// System.err.println("IO错误");
            Platform.runLater(() -> {
                NoticeUtils.show("操作提示","服务器连接异常，请稍后重试");
            });
            e.printStackTrace();
            return "{\"code\":-5008,\"msg\":\"文件流异常\"}";
        }catch (IOException e) {
            /// System.err.println("IO错误");
            Platform.runLater(() -> {
                NoticeUtils.show("操作提示","文件流异常，请稍后重试");
            });
            e.printStackTrace();
            return "{\"code\":-5009,\"msg\":\"文件流异常\"}";
        } catch (Exception e) {
            /// System.err.println("其它错误");
            Platform.runLater(() -> {
                NoticeUtils.show("操作提示","接口请求错误，请稍后重试");
            });
            e.printStackTrace();
            return "{\"code\":-5010,\"msg\":\"接口请求错误\"}";
        }
    }

    /**
     * Map转换成NameValuePair List集合
     *
     * @param params map
     * @return NameValuePair List集合
     */
    public static List<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
        List<NameValuePair> paramList = new LinkedList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        return paramList;
    }

    public static void main(String[] args) throws Exception {
        HttpClientContext clientContext = HttpClientContext.create();
        CookieStore cookieStore = new BasicCookieStore();
        clientContext.setCookieStore(cookieStore);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("account","admin");
        jsonObject.put("password","111111");
        /** 登录 */
        System.out.println(httpPost("http://172.20.11.66:8055/erp/account/login", null, jsonObject, 6000, false, clientContext));
        /** 验证是否登录 */
        //System.out.println(httpPost("http://localhost/auth/isLogin", null, null, 6000, false, clientContext));
        /** 退出登录 */
        //System.out.println(httpPost("http://localhost/auth/logout", null, null, 6000, false, clientContext));

    }

}
