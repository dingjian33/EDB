package nc.edb.EDBHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class EdbLib {
	 
    
     private String dbhost = "edb_a87897";// ���˺�(��ʽʱ��EDB����)
     private String appkey = "7670dec6";// (��ʽʱ��EDB����)
     private String appscret = "4b950f6e31494b9cbf398884e266133b";// (��ʽʱ��EDB����)
     private String token = "0b34169b82824b05aa269fd74219027f";// (��ʽʱ��EDB����)        
     private String serviceUrl = "http://59.111.98.152/rest/index.aspx";// ��������ַ(��ʽʱ��EDB����)
	//����E�걦�ӿ��õ�����Ϣ��λ�ȡ�� --> http://vip13.edb08.com.cn/mongolog/EAOPHandler.ashx?_id=94b0473980374bfb9c8d395aec73dc67
	/**
	 * ��ȡ��������
	 * 
	 * @param method
	 *            �ӿ�����
	 * @return ������
	 */
	public Map<String, String> edbGetCommonParams(String method) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("method", method);// �ӿ�����
		map.put("dbhost", this.dbhost);
		map.put("appkey", this.appkey);
//		map.put("format", "JSON");// ���ص����ݸ�ʽ
		map.put("format", "XML");// ���ص����ݸ�ʽ
		map.put("timestamp", new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));// timestamp
//		map.put("timestamp", "201612251416");// timestamp
																						// ȫСд
		map.put("v", "2.0");// �汾��
		map.put("slencry", "1");//
		map.put("ip", "192.168.1.153");// ����ip
		return map;
	}

	/**
	 * ��ʼ����
	 * 
	 * @param params
	 *            ����(��Ҫ����appscret��token)
	 * @return �����Ӧ
	 */
	public String edbRequstPost(Map<String, String> params) {		
		HttpURLConnection connection = null;
		try {
			StringBuilder builder = new StringBuilder();
			for (String key : params.keySet()) {
				String val="xmlValues".equalsIgnoreCase(key)?URLEncoder.encode(params.get(key), "UTF-8"):params.get(key);
				builder.append(String.format("%1$s=%2$s&", key,  URLEncoder.encode(val, "UTF-8")));
			}
			builder.append("sign=" + edbSignature(params));

			System.out.println("�����ַ :" + this.serviceUrl);
			System.out.println("�������� :" + URLDecoder.decode(URLDecoder.decode(builder.toString(), "utf-8"), "utf-8"));
			System.out.println("ʵ�ʷ��� :" + builder);
			connection = (HttpURLConnection) new URL(this.serviceUrl).openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
			out.write(builder.toString());
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			builder.setLength(0);
			String line = "";
			while ((line = reader.readLine()) != null)
				builder.append(line);
			reader.close();
			System.out.println("��Ӧ���� :" + builder);
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			if (connection != null)
				connection.disconnect();
		}
	}

	
	/**
	 * ǩ��
	 * 
	 * @param params
	 *            ����
	 * @return ǩ�����
	 */
	private String edbSignature(Map<String, String> params) {
		Map<String, String> treeMap = new TreeMap<String, String>(comparator);
		treeMap.putAll(params);
		treeMap.put("appscret", this.appscret);
		treeMap.put("token", this.token);
		// ƴ��Ҫǩ�����ַ���
		StringBuilder builder = new StringBuilder(this.appkey);
		for (String key : treeMap.keySet()) {
			if ("".equals(key) || "".equals(treeMap.get(key)))
				continue;
			builder.append(key).append(treeMap.get(key));
		}
		System.out.println("ǩ������:" + builder);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(builder.toString().getBytes("utf-8"));
			builder.setLength(0);
			for (byte b : bytes) {
				String hx = Integer.toHexString(b & 0XFF);
				builder.append(hx.length() == 1 ? "0" + hx : hx);
			}
			return builder.toString().toUpperCase();
		} catch (Exception e) {
			return "ǩ���쳣";
		}
	}

	/**
	 * �Ƚ���
	 */
	private static Comparator<String> comparator = new Comparator<String>() {
		@Override
		public int compare(String k1, String k2) {
			return k1.compareToIgnoreCase(k2);
		}
	};
}
