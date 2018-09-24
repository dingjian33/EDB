package nc.edb.Impl;

import nc.vo.pu.m23.entity.ArriveHeaderVO;
import nc.vo.pu.m23.entity.ArriveItemVO;
import nc.vo.pu.m23.entity.ArriveVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.data.IRowSet;
import nc.vo.ic.m45.entity.PurchaseInBodyVO;
import nc.vo.ic.m45.entity.PurchaseInHeadVO;
import nc.vo.ic.m45.entity.PurchaseInVO;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.edb.EDBHelper.EdbGetInfo;
import nc.edb.Helper.CommonHelper;
import nc.edb.Helper.ParseXml;
import nc.edb.Helper.ResponseGetXml;
import nc.edb.itf.MapList;
import nc.edb.itf.ReadFromEDBSyncServiceItf;
import nc.impl.pubapp.pattern.data.bill.BillQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadFromEDBSyncServiceImpl implements ReadFromEDBSyncServiceItf {
	private static final long serialVersionUID = 1586262846504659456L;
	int maxdays = 30;

	@Override
	public void createPurchaseInFromEDB() throws BusinessException {
		// TODO �Զ����ɵķ������

		try {
			// BillInsert<PurchaseInVO> insert = new BillInsert<PurchaseInVO>();
			BillQuery<ArriveVO> arrive = new BillQuery<ArriveVO>(ArriveVO.class);
			StringBuilder builder = new StringBuilder();
			builder.append(" select A.pk_arriveorder from po_arriveorder A ");
			builder.append(" where A.Vdef20='Y' and A.Fbillstatus ='3' and A.bisback ='N' and A.Dr=0");
			ArrayList<String> nos = new ArrayList<String>();

			IRowSet set = CommonHelper.sqlUtil.query(builder.toString());
			while (set.next()) {
				String no = set.getString(0);// ��������
				if (!nos.contains(no)) {
					nos.add(no);
				}
			}
			if (nos != null && nos.size() > 0) {
				ArriveVO[] arr = arrive.query(nos.toArray((new String[nos
						.size()])));
				UFDateTime now = new UFDateTime();

				if (arr != null && arr.length > 0) {
					for (int i = 0; i < arr.length; i++) {
						PurchaseInHeadVO po = new PurchaseInHeadVO();
						ArrayList<PurchaseInBodyVO> bodyvo = new ArrayList<PurchaseInBodyVO>();

						ArriveHeaderVO arriveHVO = arr[i].getHVO();
						po.setPk_org(arriveHVO.getPk_org());
						po.setPk_org_v(arriveHVO.getPk_org_v());
						po.setBillmaker(arriveHVO.getBillmaker());// �Ƶ���
						po.setCbizid(arriveHVO.getPk_pupsndoc());// �ɹ�Ա
						po.setCbiztype(arriveHVO.getPk_busitype());// ҵ������
						// ����ɱ���
						po.setCdptid(arriveHVO.getPk_dept());// �ɹ��������°汾
						po.setCdptvid(arriveHVO.getPk_dept_v());// �ɹ�����

						po.setCfanaceorgoid(arriveHVO.getPk_org());// ���������֯���°汾
						po.setCfanaceorgvid(arriveHVO.getPk_org_v());// ���������֯

						po.setCpayfinorgoid(arriveHVO.getPk_org());// Ӧ��������֯���°汾
						po.setCpayfinorgvid(arriveHVO.getPk_org_v());// Ӧ��������֯

						po.setCorpoid(arriveHVO.getPk_org());// ��˾���°汾
						po.setCorpvid(arriveHVO.getPk_org_v());// ��˾

						po.setCpurorgoid(arriveHVO.getPk_org());// �ɹ���֯
						po.setCpurorgvid(arriveHVO.getPk_org_v());// �ɹ���֯���°汾
						po.setCpayfinorgoid(arriveHVO.getPk_org());// Ӧ��������֯���°汾
						po.setCpayfinorgvid(arriveHVO.getPk_org_v());// Ӧ��������֯
						// ����ʱ�� ����ʱ�� �Ƶ�����
						// po.setNtotalnum(arriveHVO.getNtotalastnum());//������

						po.setCreator(arriveHVO.getBillmaker());// ������
						po.setCtrantypeid(arriveHVO.getCtrantypeid());// ��������
						po.setCvendorid(arriveHVO.getPk_supplier());// ��Ӧ��
						po.setVbillcode(arriveHVO.getVbillcode());// ���ݵ���

						// ���ص�xml�������������������
						String res = EdbGetInfo.edbTradeGet(arriveHVO
								.getVbillcode());
						ArrayList<ResponseGetXml> rg = ParseXml
								.parseGetXMl(res);

						ArriveItemVO[] bvo = arr[i].getBVO();
						for (ArriveItemVO bodys : bvo) {
							po.setCrececountryid(bodys.getCrececountryid());// ����
							po.setCsendcountryid(bodys.getCrececountryid());
							po.setCtaxcountryid(bodys.getCrececountryid());
							po.setCwarehouseid(bodys.getPk_receivestore());// �ֿ�
							String itemid = bodys.getPk_material();// ���ϱ���
							String materialbarcode = CommonHelper
									.GetMaterialbarcode(itemid);
							UFDouble qty = null;
							for (int j = 0; j < rg.size(); j++) {
								if (materialbarcode.equals(rg.get(j)
										.getBar_code())) {
									qty = CommonHelper.ToUFDoubleFromStr(rg
											.get(j).Pro_number);
								}
							}

							po.setNtotalnum(qty);// �����

							PurchaseInBodyVO body = new PurchaseInBodyVO();
							body.setCastunitid(bodys.getCastunitid());// ��λ
							body.setCbodywarehouseid(bodys.getPk_receivestore()); // �ֿ�
							body.setCcurrencyid(bodys.getCcurrencyid());// ���ұ���
							body.setCfanaceorgoid(bodys.getPk_org());// ��֯
							body.setCfirstbillbid(bodys.getCsourcebid());// Դͷ���ݱ�������
							body.setCfirstbillhid(bodys.getCsourceid());// Դͷ���ݱ�ͷ����
							body.setCfirsttranstype(bodys.getVfirsttrantype());// Դͷ���ݽ�������
							body.setCfirsttype(bodys.getCsourcetypecode());// Դͷ��������
							body.setCmaterialoid(bodys.getPk_material());// ����
							body.setCmaterialvid(bodys.getPk_material());// ���ϱ���
							body.setCorigcurrencyid(bodys.getCcurrencyid());// ����
							body.setCreqstoorgoid(bodys.getPk_org());// ��������֯���°汾
							body.setCreqstoorgvid(bodys.getPk_org_v());// ��������֯
							body.setCqtunitid(bodys.getCunitid());// ���۵�λ
							body.setCorpoid(bodys.getPk_org());// ��˾���°汾
							body.setCorpvid(bodys.getPk_org_v());// ��˾
							body.setCrowno(bodys.getCrowno());// �к�
							body.setCsourcebillbid(bodys.getPk_arriveorder_b());// ��Դ���ݱ���������
							body.setCsourcebillhid(bodys.getPk_arriveorder());// ��Դ���ݱ�ͷ����
							body.setCsourcetranstype(arriveHVO.getCtrantypeid()); // ��Դ���ݽ�������
							body.setCsourcetype("23");// ��Դ��������
							// ��Դ�������ͱ���
							body.setCsrcmaterialoid(bodys.getPk_material());// ��Դ����
							body.setCsrcmaterialvid(bodys.getPk_material());// ��Դ���ϱ���
							// ˰��
							body.setCunitid(bodys.getCunitid()); // ����λ
							body.setCvendorid(arriveHVO.getPk_supplier());// ��Ӧ��
							body.setDbizdate(now.getDate());// �������

							// �����漰�������
							body.setNassistnum(qty);// ʵ������
							body.setNcalcostmny(bodys.getNmny()); // �Ƴɱ����
							body.setNcaltaxmny(bodys.getNmny());// ��˰���
							body.setNchangestdrate(bodys.getNexchangerate());// �۱�����
							// body.setNitemdiscountrate();
							// �ۿ�

							body.setNmny(bodys.getNmny());// ������˰���
							body.setNnum(qty);// ʵ��������
							body.setNnetprice(bodys.getNprice());// ��������˰����
							body.setNorigmny(bodys.getNmny());// ��˰���
							body.setNorigprice(bodys.getNorigprice());// ����˰����
							body.setNorignetprice(bodys.getNorigprice());// ����˰����
							body.setNorigtaxmny(bodys.getNorigtaxmny());// ��˰�ϼ�
							body.setNorigtaxnetprice(bodys.getNorigtaxprice());// ����˰����
							body.setNorigtaxprice(bodys.getNorigtaxprice());// ����˰����

							body.setNprice(bodys.getNprice());// ��������˰����
							body.setNqtnetprice(bodys.getNprice());// ������˰����
							body.setNqtorignetprice(bodys.getNprice());// ��˰����
							body.setNqtorigprice(bodys.getNprice());// ��˰����

							body.setNqtorigtaxnetprice(bodys.getNorigtaxprice());// ��˰����
							body.setNqtorigtaxprice(bodys.getNorigtaxprice());// ��˰����

							body.setNqtprice(bodys.getNprice());// ������˰����
							body.setNqttaxnetprice(bodys.getNorigtaxprice());// ���Һ�˰����
							body.setNqttaxprice(bodys.getNorigtaxprice());// ���Һ�˰����

							body.setNqtunitnum(bodys.getNnum());// ��������
							body.setNshouldassistnum(bodys.getNnum());// Ӧ������
							body.setNshouldnum(bodys.getNnum());// Ӧ��������
							// ˰��
							body.setNtaxmny(bodys.getNorigtaxmny());// ���Ҽ�˰�ϼ�
							body.setNtaxnetprice(bodys.getNorigtaxprice());// �����Һ�˰����
							body.setNtaxprice(bodys.getNorigtaxprice());// �����Һ�˰����
							body.setNtaxrate(bodys.getNtaxrate());// ˰��

							body.setPk_creqwareid(bodys.getPk_receivestore());// ����ֿ�
							body.setPk_group(bodys.getPk_group());
							body.setPk_org(bodys.getPk_org());
							body.setPk_org_v(bodys.getPk_org_v());
							body.setVfirstbillcode(bodys.getVfirstcode());// Դͷ���ݺ�
							body.setVfirstrowno(bodys.getVfirstrowno());// Դͷ�����к�
							// ��Դ���ݺź��к�
							body.setStatus(VOStatus.NEW);
							bodyvo.add(body);

						}
						PurchaseInVO[] vos = this.buildBills(po, bodyvo);

						try {
							NCLocator
									.getInstance()
									.lookup(nc.pubitf.ic.m45.api.IPurchaseInMaintainAPI.class)
									.insertBills(vos);
							NCLocator
									.getInstance()
									.lookup(nc.pubitf.ic.m45.api.IPurchaseInMaintainAPI.class)
									.signBills(vos);
							String sql = "update po_arriveorder Set Fbillstatus ='1' where pk_arriveorder='"
									+ arriveHVO.getPk_arriveorder() + "'";
							CommonHelper.getBaseDao().executeUpdate(sql);

						} catch (Exception ex) {
							try {
								if (UFDateTime.getDaysBetween(arr[i].getHVO()
										.getCreationtime(), now) > maxdays) {// ��ֹ��������һֱ��ȡ
									String sql = "update po_arriveorder Set Vdef19='"
											+ arriveHVO.getVdef19()
											+ "' where pk_arriveorder='"
											+ arriveHVO.getPk_arriveorder()
											+ "'";
									CommonHelper.getBaseDao()
											.executeUpdate(sql);
								}
							} catch (Exception e1) {
								Logger.error("����edb�������Ťת״̬Fʱ��������:"
										+ e1.getMessage());
								Logger.error(e1);
							}
							Logger.error("����edb������ݷ�������:" + ex.getMessage());
							Logger.error(ex);
						}

					}
				}
			}

		} catch (Exception ex) {
			Logger.error("��ȡjd������ݷ�������:" + ex.getMessage());
			Logger.error(ex);
		}
	}

	public PurchaseInVO[] buildBills(PurchaseInHeadVO po,
			ArrayList<PurchaseInBodyVO> body) {
		List<PurchaseInVO> lst = new ArrayList<PurchaseInVO>();
		MapList<String, PurchaseInBodyVO> ml = new MapList<String, PurchaseInBodyVO>();
		for (PurchaseInBodyVO bvo : body) {
			String str = bvo.getCgeneralbid();
			ml.put(str, bvo);
		}
		Iterator<String> it = ml.keySet().iterator();
		while (it.hasNext()) {
			String key = ((String) it.next());
			List<PurchaseInBodyVO> bodys = ml.get(key);
			if (bodys != null) {
				PurchaseInVO bill = new PurchaseInVO();
				bill.setParentVO(po);
				bill.setChildrenVO(bodys.toArray(new PurchaseInBodyVO[0]));
				lst.add(bill);
			}
		}
		return lst.toArray(new PurchaseInVO[0]);

	}

}
