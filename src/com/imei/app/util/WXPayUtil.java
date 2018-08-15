package com.imei.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

public class WXPayUtil {
	public static SortedMap<Object, Object> getWXPrePayID()
    {
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("appid", Constants.WXPAY_APPID);
        parameters.put("mch_id", Constants.WXPAY_MCHID);
        parameters.put("nonce_str", createNoncestr());
        parameters.put("fee_type", "CNY");
        parameters.put("notify_url", Constants.WXPAY_NOTIFY_RECEIVER);
        parameters.put("trade_type", "APP");
        return parameters;
    }
	
	
	public static String createNoncestr()
    {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < 16; i++)
        {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }
	
	public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters)
    {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k))
            {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + Constants.WXPAY_KEY);
        String sign = MD5Util.md5(sb.toString()).toUpperCase();
        return sign;
    }
	
	public static String getRequestXml(SortedMap<Object, Object> parameters)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k))
            {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else
            {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
	
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr)
    {
        try
        {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm =
            { new TrustManagerUtil() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            // conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr)
            {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null)
            {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce)
        {
            // log.error("连接超时：{}", ce);
        } catch (Exception e)
        {
            // log.error("https请求异常：{}", e);
        }
        return null;
    }
	
	public static SortedMap<Object, Object> startWXPay(String result)
    {
        try
        {
            Map<String, String> map = XMLUtils.Dom2Map(result);
            if (map==null) {
				return null;
			}
            SortedMap<Object, Object> parameterMap = new TreeMap<Object, Object>();
            parameterMap.put("appid", Constants.WXPAY_APPID);
            parameterMap.put("partnerid", Constants.WXPAY_MCHID);
            parameterMap.put("prepayid", map.get("prepay_id"));
            parameterMap.put("package", "Sign=WXPay");
            parameterMap.put("noncestr", createNoncestr());
            // 本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
            parameterMap.put("timestamp",
                    Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0, 10)));
            String sign = createSign("UTF-8", parameterMap);
            parameterMap.put("sign", sign);
            return parameterMap;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
	
	public static String reciverWx(HttpServletRequest request) throws IOException
    {
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null)
        {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        return sb.toString();
    }
	
	public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams)
    {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v))
            {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + Constants.WXPAY_KEY);
        // 算出摘要
        String mysign = MD5Util.md5(sb.toString()).toLowerCase();
        String tenpaySign = ((String) packageParams.get("sign")).toLowerCase();
        // System.out.println(tenpaySign + " " + mysign);
        return tenpaySign.equals(mysign);
    }
}
