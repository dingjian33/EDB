package nc.edb.Helper;

import java.util.ArrayList;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ParseXml {

	public static ArrayList<ResponseAddXml> parseAddXMl(String xml) {

		ArrayList<ResponseAddXml> px = new ArrayList<ResponseAddXml>();
		try {
			Document document = DocumentHelper.parseText(xml);
			Element rootElement = document.getRootElement();// 获取根元素

			// 循环根节点，获取其子节点
			for (Iterator iter = rootElement.elementIterator(); iter.hasNext();) {
				Element element = (Element) iter.next(); // 获取标签对象
				ResponseAddXml p = new ResponseAddXml();
				String is_success = element.elementText("is_success");
				p.is_success = is_success;
				String response_Code = element.elementText("response_Code");
				p.response_Code = response_Code;
				String response_Msg = element.elementText("response_Msg");
				p.response_Msg = response_Msg;
				String field = element.elementText("field");
				p.field = field;
				px.add(p);
			}
		} catch (DocumentException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return px;
	}

	public static ArrayList<ResponseGetXml> parseGetXMl(String xml) {
		ArrayList<ResponseGetXml> px = new ArrayList<ResponseGetXml>();
		try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();// 获取根元素
			Element total_count = root.element("total_count");
			//String count = root.elementText("total_count");
			for (Iterator it = total_count.elementIterator(); it.hasNext();) {
				Element element = (Element) it.next();
				ResponseGetXml p = new ResponseGetXml();
				String instore_num = element.elementText("instore_num");
				p.instore_num = instore_num;
				String Pro_number = element.elementText("Pro_number");
				p.Pro_number = Pro_number;
				String bar_code = element.elementText("bar_code");
				p.bar_code = bar_code;
				px.add(p);
			}
		} catch (DocumentException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return px;
	}
}