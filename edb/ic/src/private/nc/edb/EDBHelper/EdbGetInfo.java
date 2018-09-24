package nc.edb.EDBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import nc.edb.Helper.CommonHelper;

public class EdbGetInfo {
	
	/**
	 * �����ⵥ
	 * 
	 */
	public static String edbInStoreAdd(nc.vo.pu.m23.entity.ArriveVO data){
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbInStoreAdd");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder builder = new StringBuilder();
		builder.append("<info>");
		builder.append("<orderInfo>");
		builder.append("<instorage_no>'"+data.getHVO().getVbillcode()+"'</instorage_no>");//��������
		builder.append("<instorage_type>�������</instorage_type>");//�������
		builder.append("<instorage_time>'"+df.format(new Date())+"'</instorage_time>");//��ǰʱ��
		//builder.append("<storage_no>1</storage_no>");//�ֿ⵵�����Զ�����ֵ2
		builder.append("<supplier_no>7</supplier_no>");//ֱ����Ĭ��ֵ��7 �����ƣ���E
		builder.append("<instorage_reason>�ɹ����</instorage_reason>");//Ĭ�ϣ��ɹ����
		builder.append("<inStorage_remark>'"+data.getHVO().getVmemo()+"'</inStorage_remark>");//����������ͷ��ע
		builder.append("<import_sign>δ����</import_sign>");//Ĭ��ֵ;δ����
		builder.append("<Source_no>'"+data.getHVO().getVbillcode()+"'</Source_no>");//��������	
		builder.append("<tax>16</tax>");//Ĭ�ϣ�16
		builder.append("</orderInfo>");
		
        for(int i=0;i<data.getBVO().length;i++){
        	
		builder.append("<product_info>");
		builder.append("<product_item>");
		
		builder.append("<instorage_no>'"+data.getHVO().getVbillcode()+"'</instorage_no>");//NCÿ�����׵�������ǰ����Ϣ��һ��  ��������
		builder.append("<cost>'"+data.getBVO()[i].getNtaxprice()+"'</cost>");//�����������Һ�˰����
		
		String code = CommonHelper.GetMaterialcode(data.getBVO()[i].getPk_srcmaterial());
		
		builder.append("<productItem_no>'"+code+"'</productItem_no>");//���ϻ�������
		builder.append("<instorage_num>'"+data.getBVO()[i].getNastnum()+"'</instorage_num>"); //��������
		builder.append("<storage_no>'"+data.getBVO()[i].getVbdef2()+"'</storage_no>");//���������ֿ��Զ�����2
		builder.append("<instorage_remark>'"+data.getBVO()[i].getVmemob()+"'</instorage_remark>");//�б�ע
		
		String materialbarcode = CommonHelper.GetMaterialbarcode(data.getBVO()[i].getPk_srcmaterial());
		
		builder.append("<bar_code>'"+materialbarcode+"'</bar_code>");//���ϵ�����������
		
		builder.append("</product_item>");
		builder.append("</product_info>");
        }	
		builder.append("</info>");

		params.put("xmlValues", builder.toString());
		String res = edb.edbRequstPost(params);
		return res;
	}
	
	
	
	
	
	/**
	 * ��ȡ����
	 */
	public static String edbTradeGet(String vbillcode) {
		//date_type��begin_time��end_time�������ʹ�ã����� date_type=�������ڣ�begin_time=2017-05-01 00:00:00,end_time=2017-07-01 00:00:00 ��ʾ��ѯ����������2017-05-01 00:00:00��2017-07-01 00:00:00֮��Ķ���

		//��ѯʱ���������ĵ��ݿ����кܶ�����Ϊ�˻�ȡ���з��������ĵ���:
		//page_no��1��ʼ�飬�����صĵ��ݸ�����json�Ļ�item����ĳ��ȣ�xml�Ļ�Rows��ǩ�ĸ���������page_size,�����page_no���Ų飬ֱ�����صĶ�������С��page_size

		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbTradeGet");
		params.put("type", "8");//�������
		params.put("date_type", "create_time");//��������--�������in_time,�Ƶ�����create_time,�������examine_time
		//params.put("begin_time", "2018-05-03 00:00:00");
		//params.put("end_time", "2018-05-11 15:02:33");
		params.put("in_store_tid", vbillcode);
		params.put("status", "2");
		//params.put("page_no", "1");
		//params.put("order_status", "�ѷ���");
		//params.put("page_size", "10");
		//params.put("payment_status", "�Ѹ���");
		//params.put("productInfo_type", "3");
		//params.put("fields", "storage_id,tid,is_bill,invoice_name,taobao_delivery_status,taobao_delivery_method,order_process_time,is_break,breaker,break_time,break_explain,plat_send_status,plat_type,is_adv_sale,provinc_code,city_code,area_code,express_code,last_returned_time,last_refund_time,deliver_centre,deliver_station,is_pre_delivery_notice,jd_delivery_time,Sorting_code,cod_settlement_vouchernumber,total_num,big_marker,three_codes,distributing_centre_name,send_site_name,:child_storage_id,:child_tid,:child_out_tid,:child_pro_detail_code,:child_pro_name,:child_specification,:child_barcode,:child_combine_barcode,:child_iscancel,:child_isscheduled,:child_stock_situation,:child_isbook_pro,:child_iscombination,:child_isgifts,:child_gift_num,:child_book_storage,:child_pro_num,:child_send_num,:child_refund_num,:child_refund_renum,:child_inspection_num,:child_timeinventory,:child_cost_price,:child_sell_price");
		String res = edb.edbRequstPost(params);
        return res;
	}

	/**
	 * �����ⵥ
	 */
	public static void edbInStoreAdd() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbInStoreAdd");
		StringBuilder builder = new StringBuilder();
		builder.append("<info>");
		builder.append("<orderInfo>");
		builder.append("<instorage_no>20549778</instorage_no>");
		builder.append("<instorage_type>�������</instorage_type>");
		builder.append("<instorage_time>2016-10-20 09:54</instorage_time>");
		builder.append("<storage_no>1</storage_no>");
		builder.append("<supplier_no>1</supplier_no>");
		builder.append("<delivery_no>EMS1265489</delivery_no>");
		builder.append("<cost>20</cost>");
		builder.append("<procure_cost>10</procure_cost>");
		builder.append("<other_cost>5</other_cost>");
		builder.append("<pact_totalAmount>100</pact_totalAmount>");
		builder.append("<out_pactNo>cs390233837</out_pactNo>");
		builder.append("<WL_company>�޾�����</WL_company>");
		builder.append("<express_no>1235345345</express_no>");
		builder.append("</orderInfo>");

		builder.append("<orderInfo>");
		builder.append("<instorage_no>20549779</instorage_no>");
		builder.append("<instorage_type>�������</instorage_type>");
		builder.append("<instorage_time>2016-10-20 09:54</instorage_time>");
		builder.append("<storage_no>1</storage_no>");
		builder.append("<supplier_no>1</supplier_no>");
		builder.append("<delivery_no>EMS1265489</delivery_no>");
		builder.append("<cost>20</cost>");
		builder.append("<procure_cost>10</procure_cost>");
		builder.append("<other_cost>5</other_cost>");
		builder.append("<pact_totalAmount>100</pact_totalAmount>");
		builder.append("<out_pactNo>cs390233837</out_pactNo>");
		builder.append("<WL_company>�޾�����</WL_company>");
		builder.append("<express_no>1235345345</express_no>");
		builder.append("</orderInfo>");

		builder.append("<product_info>");
		builder.append("<product_item>");
		builder.append("<instorage_no>20549778</instorage_no>");
		builder.append("<productItem_no>1</productItem_no>");
		builder.append("<instorage_num>10</instorage_num>");
		builder.append("<storage_no>1</storage_no>");
		builder.append("<batch>10000</batch>");
		builder.append("<expire_Time>2017-06-20</expire_Time>");
		builder.append("</product_item>");

		builder.append("<product_item>");
		builder.append("<instorage_no>20549779</instorage_no>");
		builder.append("<productItem_no>2</productItem_no>");
		builder.append("<instorage_num>2</instorage_num>");
		builder.append("<storage_no>1</storage_no>");
		builder.append("<batch>100001</batch>");
		builder.append("<expire_Time>2017-06-01</expire_Time>");
		builder.append("</product_item>");

		builder.append("</product_info>");
		builder.append("</info>");

		params.put("xmlValues", builder.toString());
		String res = edb.edbRequstPost(params);

	}

	/**
	 * ��Ӷ���
	 */
	public static void edbTradeAdd() {
		
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbTradeAdd");
		StringBuilder builder = new StringBuilder();
		builder.append("<info>");
		builder.append("<orderInfo>");
		builder.append("<out_tid>WZ2017121900003</out_tid>");//�ⲿƽ̨���ţ�ƽ̨������ţ�������Ҫ����Ψһ�����������Լ���������ƽ̨���ظ��ı�����ϵ����Щ12345���Ǽ������ģ������淶�ģ���������ƽ̨�У����Ұɣ���Ź���AO201605090000001����������׷��
		builder.append("<shop_id>17</shop_id>");//���̱�ţ�E�걦�ͻ��˵���������������������������в鿴�������û��ȡappkey�󶨵ĵ���
		builder.append("<storage_id>4</storage_id>");//�ֿ���루E�걦�ͻ��˵���������ֿ⵵�����ֿ������в鿴�������û��ȡ�������̵�Ĭ�ϲֿ�
		builder.append("<buyer_id>��ԭһ���</buyer_id>");//���ID��������վע����˺����ƣ����û�����ջ��˰�
		builder.append("<buyer_msg>,9.28�Ƽ��ȷ��ְ�Ľϣ����+�������PO201709280103</buyer_msg>");//�������
		builder.append("<seller_remark/>");//�ͷ���ע
		builder.append("<consignee><![CDATA[��<��]]></consignee>");//�ջ�����ʵ����
		builder.append("<telephone>13288888888</telephone>");//��ϵ�绰
		builder.append("<mobilPhone>13288888888</mobilPhone>");//��ϵ�ֻ����ֻ��͵绰����һ����Ϊ�գ�
		builder.append("<privince>����ʡ</privince>");//�ջ���ʡ��
		builder.append("<city>������</city>");//�ջ��˳���
		builder.append("<area>������</area>");//�ջ��˵���
		builder.append("<address>����ʡ�����и������ɰ���·46�ų�ʢ�Ƽ���ҵ԰</address>");//�ջ���ַ�������ʡ����
		builder.append("<actual_freight_get>0</actual_freight_get>");//ʵ���˷ѣ�������ҵ��˷ѣ������������0
		builder.append("<is_COD>0</is_COD>");//�Ƿ��������ǵĻ�һ��Ҫ��1����Ȼ��ʧ����
		builder.append("<order_totalMoney>138</order_totalMoney>");//�����ܽ����ʵ��֧���Ľ�������һ�80����һ��100�ֿ�ȯ����80Ҫ���ȥ���������������͵�30Ԫ�ֿ�ȯ����30��Ҫ���ȥ���������������һ��ʵ�յĽ���Ҫ�����
		builder.append("<product_totalMoney>138</product_totalMoney>");//��Ʒ�ܽ�ʵ�յĲ�Ʒ�ܽ��������˷ѣ��������Ʒ��sum����������*�ɽ����ۣ���Ӧ
		builder.append("<favorable_money>0</favorable_money>");//�Żݽ��:�������Żݽ���Ʒ�ϵ��Żݲ��������ڣ�����һ������һ����Ʒ999������680��������һ���ؼ�ֻҪ499��319��999-680����������Ż��ϣ�181��680-499��������
		builder.append("<terminal_type>�ֻ�</terminal_type>");//�ն�����(����-�ֻ�-�绰)���������ն����ʹ����ģ�����������ö�ָ�����͵Ķ�����������
		builder.append("<pay_method>΢��֧��</pay_method>");//֧����ʽ��֧�������Ƶ�ͨ���ֽ��
		builder.append("<out_payNo>20549778_02081654</out_payNo>");//�ⲿƽ̨����ţ�����֧������ˮ�ţ�һ����õ�����֧���ӿڶ����ܵõ������ˮ�ţ��������
		builder.append("<pay_date>2017-12-19 08:16</pay_date>");//��������
		builder.append("<order_date>2017-12-19 08:16</order_date>");//�������ڣ��������ھ൱ǰʱ�䲻�ɳ���һ���£�
		//builder.append("<express>�ϴ�</express>");//��ݹ�˾���ƣ�E�걦�ͻ��˵���������ֿ⵵������ݹ�˾���ò鿴�����������дȡ�������̰󶨵�Ĭ�Ͽ��
		builder.append("<pay_status>�Ѹ���</pay_status>");//����״̬�����׹ر�,δ����и��������Ʋ�����δ�������Ҫ����������ɹ��Ķ����Ҳ��������û�����㣩,�Ѹ���,�������,�ѷ���,Ԥ�˿Ĭ�ϣ�δ�������ǹ���������˿�/�˻��������״̬����Ԥ�˿�����˻��Ĳ�Ʒ�����ϼ��ϡ��˿��Ʒ�������ǣ�������л��ɴ��˿���˿�/���˿�ȫ���˿���ֶ����ڲֿ���ҵ���ھͿ���������
		builder.append("<invoice_type>����</invoice_type>");//��Ʊ����
        builder.append("<invoice_title>��Ʊ̧ͷ</invoice_title>");//��Ʊ̧ͷ��������д���������ʾ�Ƿ񿪷�Ʊ�̣�
        builder.append("<invoice_msg>��Ʊ����</invoice_msg>");//��Ʊ����
		builder.append("</orderInfo>");

		/*
		builder.append("<orderInfo>");
		builder.append("<out_tid>WZ2017080800006</out_tid>");
		builder.append("<shop_id>16</shop_id>");
		builder.append("<storage_id>25</storage_id>");
		builder.append("<buyer_id>��ݼ��</buyer_id>");
		builder.append("<buyer_msg>��������: Τ�ֺ졿Ҫ�������������鷳ȷ��������лл</buyer_msg>");
		builder.append("<seller_remark/>");
		builder.append("<consignee>��ݼ��</consignee>");
		builder.append("<telephone>13588217105</telephone>");
		builder.append("<privince></privince>");
		builder.append("<city></city>");
		builder.append("<area></area>");
		builder.append("<address>����ʡ�����и������ɰ���·46�ų�ʢ�Ƽ���ҵ԰</address>");
		builder.append("<actual_freight_get>0</actual_freight_get>");
		builder.append("<is_COD>0</is_COD>");
		builder.append("<order_totalMoney>138</order_totalMoney>");
		builder.append("<product_totalMoney>138</product_totalMoney>");
		builder.append("<is_invoiceOpened>0</is_invoiceOpened>");
		builder.append("<favorable_money>0</favorable_money>");
		builder.append("<terminal_type>΢��</terminal_type>");
		builder.append("<pay_method>΢��֧��</pay_method>");
		builder.append("<out_payNo>20549778_02081653</out_payNo>");
		builder.append("<pay_date>2017-09-21 08:16</pay_date>");
		builder.append("<order_date>2017-09-21 08:16</order_date>");
		builder.append("<express>�ϴ�</express>");
		builder.append("<pay_status>�Ѹ���</pay_status>");
		builder.append("</orderInfo>");
*/
		builder.append("<product_info>");
		builder.append("<product_item>");	
		//��������YST100,SYJ100
		builder.append("<barCode>911911888308057</barCode>");//�����룬������Ӧ�������Ԥ�õĲ�Ʒ������������������������������д�����������������쳣�������ӿ��޷��������˵�����Ҳ�������ȥ�쳣������
		builder.append("<product_title>�ֵ�Ѽ�࣬�����ڵ��人ԭζ����Ʒ������ʮ���Զ</product_title>");//��Ʒ����
		builder.append("<standard>��</standard>");//��Ʒ���ԣ�һ����sku����������XL��ɫ��L��ɫ��û�п�Ĭ�Ͼ��롢����ֻ������
		builder.append("<favorite_money>0</favorite_money>");//�Żݽ��:��Ʒ���Żݽ�ע���Ǹ�����Ʒ�����Żݽ�����һ�����Ż�
		builder.append("<orderGoods_Num>1</orderGoods_Num>");//��������
		builder.append("<cost_Price>46</cost_Price>");//�ɽ�����
		builder.append("<out_tid>WZ2017121900003</out_tid>");//��ӦorderInfo�е�out_tid��ʾ������ϸ
		builder.append("</product_item>");
		
		/*
		builder.append("<product_item>");
		builder.append("<out_tid>WZ2017080800005</out_tid>");//��ӦorderInfo�е�out_tid��ʾ������ϸ
		builder.append("<barCode>CP12012GG2</barCode>");
		builder.append("<product_title>��ը�������������û�����͵������һ����һ��û׼���»�</product_title>");
		builder.append("<standard>���㣨4����X1��</standard>");
		builder.append("<favorite_money>0</favorite_money>");
		builder.append("<orderGoods_Num>1</orderGoods_Num>");
		builder.append("<cost_Price>46</cost_Price>");		
		builder.append("</product_item>");

		builder.append("<product_item>");		
		builder.append("<barCode>911911888308057</barCode>");
		builder.append("<product_title>�ֵ�Ѽ�࣬�����ڵ��人ԭζ����Ʒ������ʮ���Զ</product_title>");
		builder.append("<standard>������4����X2��</standard>");
		builder.append("<favorite_money>0</favorite_money>");
		builder.append("<orderGoods_Num>1</orderGoods_Num>");
		builder.append("<cost_Price>46</cost_Price>");
		builder.append("<out_tid>WZ2017080800006</out_tid>");//��ӦorderInfo�е�out_tid��ʾ������ϸ
		builder.append("</product_item>");
		builder.append("<product_item>");
		builder.append("<out_tid>WZ2017080800006</out_tid>");//��ӦorderInfo�е�out_tid��ʾ������ϸ
		builder.append("<barCode>CP12012GG2</barCode>");
		builder.append("<product_title>��ը�������������û�����͵������һ����һ��û׼���»�</product_title>");
		builder.append("<standard>���㣨4����X1��</standard>");
		builder.append("<favorite_money>0</favorite_money>");
		builder.append("<orderGoods_Num>1</orderGoods_Num>");
		builder.append("<cost_Price>46</cost_Price>");		
		builder.append("</product_item>");
*/
		builder.append("</product_info>");
		builder.append("</info>");

		params.put("xmlValues", builder.toString());
		String res = edb.edbRequstPost(params);
		// String res = edb.name(params);
	}
	/**
	 * ��������
	 */
	public static void edbTradeUpdate() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbTradeUpdate");
		StringBuilder builder = new StringBuilder();
		builder.append("<info>");
		builder.append("<orderInfo>");
		builder.append("<tid>S1611110013488</tid>");
		builder.append("<express>Բͨ���</express>");
		builder.append("<express_no>809304336767</express_no>");
		builder.append("<printer>edb_a80258</printer>");
		builder.append("<print_time>2016-11-12 10:50:55</print_time>");
		builder.append("<inspecter>edb_a80258</inspecter>");
		builder.append("<inspect_time>2016-11-12 10:50:55</inspect_time>");
		builder.append("<delivery_operator>edb_a80258</delivery_operator>");
		builder.append("<delivery_time>2016-11-12 10:50:55</delivery_time>");
		builder.append("<GrossWeight>1.0</GrossWeight>");
		builder.append("</orderInfo>");

		builder.append("<product_info>");
		builder.append("<product_item>");
		builder.append("<tid>S1611110013488</tid>");
		builder.append("<barCode>34210531064402</barCode>");
		builder.append("<inspection_num>1</inspection_num>");		
		builder.append("</product_item>");

		builder.append("<product_item>");
		builder.append("<tid>S1611110013488</tid>");
		builder.append("<barCode>44210530960602</barCode>");
		builder.append("<inspection_num>1</inspection_num>");
		builder.append("</product_item>");
		builder.append("</product_info>");
		builder.append("</info>");

		params.put("xmlValues", builder.toString());
		String res = edb.edbRequstPost(params);
		// String res = edb.name(params);
	}
	
	/**
	 * ������������ 
	 */
	public static void edbTradeDeliveryBatch() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbTradeDeliveryBatch");
		StringBuilder builder = new StringBuilder();
		builder.append("<info>");
		//��1������
		builder.append("<orderInfo>");
		builder.append("<OrderCode>S1611110013488</OrderCode>");
		builder.append("<express>Բͨ���</express>");
		builder.append("<express_no>809304336767</express_no>");
		builder.append("<delivery_time>2016-11-12 10:50:55</delivery_time>");
		builder.append("<weight>1.0</weight>");
		builder.append("</orderInfo>");
		//��2������
		builder.append("<orderInfo>");
		builder.append("<OrderCode>S1611110013489</OrderCode>");
		builder.append("<express>Բͨ���</express>");
		builder.append("<express_no>809304336768</express_no>");
		builder.append("<delivery_time>2016-11-12 10:50:55</delivery_time>");
		builder.append("<weight>1.0</weight>");
		builder.append("</orderInfo>");
		
		
		builder.append("</info>");

		params.put("xmlValues", builder.toString());
		String res = edb.edbRequstPost(params);
	}
	/**
	 * ����̵㵥 
	 */
	public static void edbInventoryAdd() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbInventoryAdd");
		StringBuilder builder = new StringBuilder();
		builder.append("<info>");
		builder.append("<orderInfo>");
		builder.append("<checkOrderCode>PD23659</checkOrderCode>");
		builder.append("<storage_no>30</storage_no>");
		builder.append("<checkTime>2016-11-12 10:50:55</checkTime>");
		builder.append("<checkType>�����̵�</checkType>");
		builder.append("<remark>�̵�˵��</remark>");
		builder.append("</orderInfo>");

		builder.append("<product_info>");
		builder.append("<product_item>");
		builder.append("<checkOrderCode>PD23659</checkOrderCode>");
		builder.append("<barCode>ALB015A</barCode>");
		builder.append("<quantity>1</quantity>");		
		builder.append("</product_item>");

		builder.append("<product_item>");
		builder.append("<checkOrderCode>PD23659</checkOrderCode>");
		builder.append("<barCode>ALB016A</barCode>");
		builder.append("<quantity>1</quantity>");
		builder.append("</product_item>");
		builder.append("</product_info>");
		builder.append("</info>");

		params.put("xmlValues", builder.toString());
		String res = edb.edbRequstPost(params);
		// String res = edb.name(params);
	}
	
	/**
	 * ��ݸ��£��ѹ�ʱ��
	 */
	public static void edbExpressUpdate() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbExpressUpdate");
		StringBuilder builder = new StringBuilder();
		builder.append("<info>");
		builder.append("<orderInfo>");
		builder.append("<Orderid>S1707270002564</Orderid>");
		builder.append("<Express>2</Express>");
		builder.append("<storage_id>10</storage_id>");
		builder.append("<Csmemo>��è����</Csmemo>");
		builder.append("</orderInfo>");
		builder.append("</info>");

		params.put("xmlValues", builder.toString());
		String res = edb.edbRequstPost(params);
		// String res = edb.name(params);
	}
	/**
	 * ��������  
	 */
	public static void edbTradeCancel() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbTradeCancel");
		StringBuilder builder = new StringBuilder();
		builder.append("<order>");
		builder.append("<orderInfo><tid>S1709090055178</tid></orderInfo>");//tid �� EDB������ţ������ⲿ������
		//builder.append("<orderInfo><tid>S1212030600004</tid></orderInfo>");
		builder.append("</order>");
		params.put("xmlValues", builder.toString());
		
		String res = edb.edbRequstPost(params);
		String s = "";
	}

	/**
	 * ��ȡ�˻�������Ϣ
	 */
	public static void edbTradReturnGet() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbTradReturnGet");
		// params.put("date_type", "�˻���������");
		params.put("start_time", "20160101");
		params.put("end_time", "20160928");
		// params.put("import_mark", "δ����");
		// params.put("process_status", "������");
		// params.put("return_status", "�˻���");
		// params.put("refund_status", "δ�˿�");
		params.put("page_no", "1");
		params.put("page_size", "5");
		String res = edb.edbRequstPost(params);
	}
	/**
	 * ��ȡ��Ʊ��Ϣ
	 */
	public static void edbInvoiceGet() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbInvoiceGet");
		//params.put("begin_time", "2015-07-29 23:24:40");
		//params.put("end_time", "2015-07-29 23:24:40");
		//params.put("page_no", "1");
		//params.put("page_size", "5");
		String res = edb.edbRequstPost(params);
	}
	/**
	 * ���Ӳ�Ʒ��ϸ��Ϣ
	 */
	public static void edbProductDetailAdd() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbProductDetailAdd");
		StringBuilder builder = new StringBuilder();
		builder.append("<info>");
		builder.append("<orderInfo>");
		builder.append("<brand_name>�󸻵�</brand_name>");
		builder.append("<sort_name>èʪ��SUNNY PATTAYA����ŵ���</sort_name>");
		builder.append("<supplier>ɽ���󸻵�ó�׷�չ���޹�˾</supplier>");
		//builder.append("<productNo>LWGW00010</productNo>");
		builder.append("<product_name>����ŵ���Ũ֭������+����</product_name>");
		builder.append("<market_price>0</market_price>");
		builder.append("<retail_price>0</retail_price>");
		builder.append("<product_intro>1</product_intro>");
		builder.append("<factory_item>1</factory_item>");
		builder.append("<wfpid>192.168.0.10</wfpid>");
		builder.append("</orderInfo>");

		builder.append("<detailInfo>");
		builder.append("<detail_item>");
		builder.append("<bar_code>6958862109529</bar_code>");
		builder.append("<specification>60g</specification>");
		//builder.append("<color>��</color>");
		builder.append("<size>1.0</size>");
		builder.append("<unit>��</unit>");
		builder.append("<weight>0.06</weight>");
		builder.append("<contrast_purchase_price>1.0</contrast_purchase_price>");
		//builder.append("<product_status>����</product_status>");
		builder.append("<sell_price>0</sell_price>");

		builder.append("</detail_item>");
		builder.append("</detailInfo>");
		builder.append("</info>");

		params.put("xmlValues", builder.toString());
		String res = edb.edbRequstPost(params);

	}

	/**
	 * ��ȡ��Ʒ������Ʒ��Ϣ ���ݲ�Ʒ���õĿ�ʼ����ʱ��Ͳ�Ʒ���룬��ŵ���Ϣ��ȡ��Ʒ������Ϣ�����������
	 */
	public static void edbProductBaseInfoGet() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbProductBaseInfoGet");
		params.put("BarCode", "Edb0092");

		String res = edb.edbRequstPost(params);
		String s = "";
	}
	/**
	 * ��ȡE�걦���õ�Ʒ����Ϣ 
	 */
	public static void edbProductBrandGet() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbProductBrandGet");
		//params.put("brand_code", "W60508");
		//params.put("brand_name", "W60508");
		//params.put("enable", "W60508");

		String res = edb.edbRequstPost(params);
		String s = "";
	}
	/**
	 * ��ȡ��Ʒ������Ϣ 
	 */
	public static void edbProductClassGet() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbProductClassGet");
		//params.put("class_code", "98");//EDBϵͳ������
		params.put("class_name", "ʳƷ����");
		//params.put("sort_type", "��Ʒ");
		//params.put("is_suit", "0");		
		//params.put("store_name", "ETOP˳���");		
		//params.put("get_child_nodes", "true");		
		String res = edb.edbRequstPost(params);
		String s = "";
	}
	/**
	 * ��ȡ��Ʒ�����Ϣ  
	 * û�а����䶯ʱ���ѯ�ķ�����һ��ҵ����ÿ��5~10������Ҫ��ѯһ��������Ʒ�Ŀ��
	 */
	public static void edbProductGet() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbProductGet");
		params.put("date_type", "��������");//�������Ӱ��begin_time ��  end_time
		params.put("begin_time", "2012-01-01 00:00"); //date_type ���ڵ���begin_time
		params.put("end_time", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));//date_type ���ڵ���end_time
		//params.put("product_no", "PN000036");		
		//params.put("brand_no", "28");	//EDBϵͳƷ�Ʊ��	
		//params.put("standard", "���");		
		params.put("bar_code", "SKYWORTH-00000030");		
		//params.put("sort_no", "96");	//	EDBϵͳ������
		//params.put("store_id", "1,2,3");		
//		params.put("isuit", "0");		
//		params.put("iscut_store", "1");		
		params.put("page_no", "1");		
		params.put("page_size", "10");		
		String res = edb.edbRequstPost(params);
		String s = "";
	}
	
	/**
	 * ��������Ʒ��Ϣ 
	 */
	public static void edbProductBaseInfoUpdate() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbProductBaseInfoUpdate");
		params.put("productId", "436");
		params.put("brand_name", "��Ԫ");
		params.put("productNo", "LWH-0006");
		params.put("product_name", "���Ь");
		String res = edb.edbRequstPost(params);
		String s = "";
	}
	/**
	 * ���µ��ݵ�����Ϊ �ѵ���
	 */
	public static void edbTradeImportStatusUpdate() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbTradeImportStatusUpdate");
		params.put("num_id", "S1512020000001,S1512020000002:��ע");
		params.put("tid_type", "Order");

		String res = edb.edbRequstPost(params);
		String s = "";
	}
	/**
	 * 	����������Ϣ 
	 */
	public static void edbLogisticsCompaniesUpdate() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbLogisticsCompaniesUpdate");
		params.put("tid", "S1512020000001");//E�걦������
		params.put("express", "Բͨ���");
		params.put("express_deliveryno", "809173667727");
		params.put("delivery_time", "2016-11-13 18:20:10");
		params.put("weight", "0");

		String res = edb.edbRequstPost(params);
		String s = "";
	}
	
	/**
	 * ����һ����Ʒ����
	 */
	public static void edbProductClassAdd(){
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbProductClassAdd");
		StringBuilder builder = new StringBuilder();
		builder.append("<info>");
		builder.append("<orderInfo>");
		builder.append("<sort_name>�ն�Ʒ</sort_name>");
		builder.append("<OutSortNO>HC-037</OutSortNO>");
		builder.append("<sort_type>��Ʒ</sort_type>");
		builder.append("</orderInfo>");		
		builder.append("</info>");

		params.put("xmlValues", builder.toString());
		String res = edb.edbRequstPost(params);
		String s = "";
	}
	/**
	 * ����һ����ƷƷ��
	 */
	public static void edbProductBrandAdd(){
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbProductBrandAdd");
		StringBuilder builder = new StringBuilder();
		builder.append("<order>");
		builder.append("<orderInfo>");
		builder.append("<brand_name>Zimmerli������2016</brand_name>");//Ʒ������
		builder.append("<OutBrandNO>Z005</OutBrandNO>");
		builder.append("<Cdescript></Cdescript>");
		builder.append("<Cremark></Cremark>");
		builder.append("<Is_active>1</Is_active>");
		builder.append("</orderInfo>");		
		builder.append("</order>");

		params.put("xmlValues", builder.toString());
		String res = edb.edbRequstPost(params);
		String s = "";
	}
	/**
	 * ���²�Ʒ����
	 */
	public static void edbProductClassUpdate() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbProductClassUpdate");
		params.put("sort_name", "S1512020000001");
		params.put("sort_no", "10");
		// params.put("parent_sort_no", "Order");
		params.put("packing_charges", "1");
		params.put("rough_weight_ratio", "0");
		params.put("sort_type ", "��Ʒ");
		params.put("storeId ", "2");
		params.put("is_pack ", "0");
		params.put("is_suit ", "0");
		String res = edb.edbRequstPost(params);
		String s = "";
	}

	/**
	 * �˻�������ӿ�
	 */
	public static void edbReturnStoreAdd() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbReturnStoreAdd");
		StringBuilder builder = new StringBuilder();
		builder.append("<corder>");
		builder.append("<corderInfo>");
		builder.append("<ordernum>20549778</ordernum>");
		builder.append("<wresingnum>89642354865122</wresingnum>");
		builder.append("<stoaffirm>0</stoaffirm>");
		builder.append("<expressnum>981632</expressnum>");
		builder.append("<expresscom>Բͨ</expresscom>");
		builder.append("<retime>2017-06-10 10:00</retime>");		
		builder.append("</corderInfo>");

		builder.append("<corderInfo>");
		builder.append("<ordernum>20549779</ordernum>");
		builder.append("<wresingnum>89642354865123</wresingnum>");
		builder.append("<stoaffirm>0</stoaffirm>");
		builder.append("<expressnum>981632</expressnum>");
		builder.append("<expresscom>Բͨ</expresscom>");
		builder.append("<retime>2017-06-10 10:00</retime>");		
		builder.append("</corderInfo>");

		builder.append("<rproductInfo>");
		builder.append("<rproduct_item>");
		builder.append("<barcode>20549778</barcode>");
		builder.append("<wresingnum>89642354865122</wresingnum>");
		builder.append("<pronum>1</pronum>");
		builder.append("<reamount>100.00</reamount>");
		builder.append("</rproduct_item>");

		builder.append("<rproduct_item>");
		builder.append("<barcode>20549778</barcode>");
		builder.append("<wresingnum>89642354865123</wresingnum>");
		builder.append("<pronum>1</pronum>");
		builder.append("<reamount>120.00</reamount>");
		builder.append("</rproduct_item>");

		builder.append("</rproductInfo>");
		builder.append("</corder>");

		params.put("xmlValues", builder.toString());
		String res = edb.edbRequstPost(params);

	}
	/**
	 * ȷ����ⵥ
	 */
	public static void edbInStoreConfirm() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbInStoreConfirm");
		params.put("inStorage_no", "7026899788-702689996");
		params.put("freight", "5.00");

		String res = edb.edbRequstPost(params);
		String s = "";
	}
	
	/**
	 * �������������
	 */
	public static void edbReadySynDeliveries() {
		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbReadySynDeliveries");
		StringBuilder builder = new StringBuilder();
		builder.append("<order>");
		builder.append("<orderInfo>");
		builder.append("<order_no>SO1708070002056</order_no>");
		builder.append("<express>�ϴ�</express>");
		builder.append("<express_no>SO1708070002056</express_no>");
		builder.append("<consignee>��ݼ��</consignee>");
		builder.append("<delivery_time>2017-08-08 10:00</delivery_time>");
		builder.append("<package_num>1</package_num>");		
		builder.append("</orderInfo>");

//		builder.append("<orderInfo>");
//		builder.append("<order_no>SO1708080001778</order_no>");
//		builder.append("<express>�ϴ�</express>");
//		builder.append("<express_no>SO1708080001778</express_no>");
//		builder.append("<consignee>��ݼ��</consignee>");
//		builder.append("<delivery_time>2017-08-08 14:00</delivery_time>");
//		builder.append("<package_num>1</package_num>");			
//		builder.append("</orderInfo>");
		
		builder.append("</order>");

		params.put("xmlValues", builder.toString());
		String res = edb.edbRequstPost(params);

	}
	/*
	 * ��ѯ������������Ϣ
	 */
	public static void edbGetSynDeliveries() {
		//��ѯʱ���������ĵ��ݿ����кܶ�����Ϊ�˻�ȡ���з��������ĵ���:
		//page_no��1��ʼ�飬�����صĵ��ݸ�����json�Ļ�item����ĳ��ȣ�xml�Ļ�Rows��ǩ�ĸ���������page_size,�����page_no���Ų飬ֱ�����صĶ�������С��page_size

		EdbLib edb = new EdbLib();
		Map<String, String> params = edb.edbGetCommonParams("edbGetSynDeliveries");
		params.put("begin_time", "2017-01-01");
		params.put("end_time", "2017-08-15");
		params.put("page_no", "1");
		params.put("page_size", "200");
		//params.put("order_nos", "S1512020000110,S1702080001139");//EDB����(�ܳ���2000���ַ�)EDB������ƽ̨���Ŷ���ʱֻȡEDB����	
		params.put("out_order_nos", "99998887776777,170206-356617403521");//ƽ̨����(�ܳ���2000���ַ�)EDB������ƽ̨���Ŷ���ʱֻȡEDB����	
		//params.put("fields", "order_no,out_order_no");//ָ�������ֶ�		
		String res = edb.edbRequstPost(params);

	}
	/// <summary>
    /// ֪ͨ����
    /// </summary>
    private static void edbNotifyTrade()
    {
        EdbLib edb = new EdbLib();
        Map<String, String>params = edb.edbGetCommonParams("edbNotifyTrade");
        StringBuilder builder = new StringBuilder();
        builder.append("<info>");
        builder.append("<orderInfo>");
        builder.append("<bizOrderId>WZ201804270001</bizOrderId>");//���¿ⵥ��
        builder.append("<bizOrderPushType>�����˿�</bizOrderPushType>");//������������
        builder.append("</orderInfo>");
        //��2��
        builder.append("<orderInfo>");
        builder.append("<bizOrderId>WZ201804270002</bizOrderId>");//���¿ⵥ��
        builder.append("<bizOrderPushType>��ַ���</bizOrderPushType>");//������������
        builder.append("</orderInfo>");   
        builder.append("</info>");
        params.put("xmlValues", builder.toString());
        String res = edb.edbRequstPost(params);

    }    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//edbReadySynDeliveries();
		//edbGetSynDeliveries();
//		edbExpressUpdate();
//		edbProductGet();
		edbTradeAdd();
		// edbInStoreAdd();
		 //edbTradeGet();
		// edbTradReturnGet();
//		edbInvoiceGet();//��ȡ��Ʊ��Ϣ
		 //edbProductBaseInfoGet();
		// edbTradeImportStatusUpdate();
		//edbProductClassAdd();
		//edbProductClassGet();//��ȡ��Ʒ������Ϣ
		// edbProductClassUpdate();
		 //edbProductDetailAdd();
		//edbInStoreConfirm();
//		edbTradeCancel();
		// edbLogisticsCompaniesUpdate();
		 //edbInventoryAdd();
//		 edbProductBrandGet();
		 //edbTradeDeliveryBatch();
		//edbReturnStoreAdd();
//		edbProductBrandAdd();
	}

}
