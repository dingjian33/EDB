package nc.edb.Plugin;

import java.util.ArrayList;

import nc.bs.businessevent.BusinessEvent;
import nc.bs.businessevent.IBusinessEvent;
import nc.bs.ic.general.businessevent.ICGeneralCommonEvent;
import nc.edb.EDBHelper.EdbGetInfo;
import nc.edb.Helper.CommonHelper;
import nc.edb.Helper.ParseXml;
import nc.edb.Helper.ResponseAddXml;
import nc.vo.pu.m23.entity.ArriveVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ValueObject;
import nc.vo.pub.lang.UFBoolean;

public class PurchaseInAfterAdd implements
		nc.bs.businessevent.IBusinessListener {

	@Override
	public void doAction(IBusinessEvent event) throws BusinessException {
		// TODO 自动生成的方法存根
		try {
			AggregatedValueObject[] value = getVOs(event);
			try {
				ArrayList<nc.vo.pu.m23.entity.ArriveVO> datas = new ArrayList<nc.vo.pu.m23.entity.ArriveVO>();
				for (int i = 0; i < value.length; i++) {
					datas.add((ArriveVO) value[i]);
				}

				for (int i = 0; i < value.length; i++) {
					ArriveVO data = datas.get(i);
					UFBoolean isedb = CommonHelper.GetWhIsEdb(data.getHVO()
							.getPk_arriveorder());// 是否发送edb
					if (CommonHelper.GetBoolean(isedb)
							&& !CommonHelper.GetBoolean(data.getHVO()
									.getBisback())) {// 退货
						String s = EdbGetInfo.edbInStoreAdd(data);
						ResponseAddXml px = ParseXml.parseAddXMl(s).get(0);
						if ("200".equals(px.getResponse_Code())) {
							data.getHVO().setVdef19("Y");
							String sql = "update po_arriveorder Set Vdef19='"
									+ data.getHVO().getVdef19()
									+ "' where pk_arriveorder='"
									+ data.getHVO().getPk_arriveorder() + "'";
							CommonHelper.getBaseDao().executeUpdate(sql);
						} else {
							throw new Exception("增加入库传给EDB发生异常："
									+ px.getResponse_Msg());
						}
					}
				}
			} catch (Exception ex) {
				throw ex;
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	private AggregatedValueObject[] getVOs(IBusinessEvent event)
			throws BusinessException {
		ValueObject obj = (ValueObject) event.getUserObject();
		AggregatedValueObject[] vos = null;
		if ((obj instanceof ICGeneralCommonEvent.ICGeneralCommonUserObj)) {
			ICGeneralCommonEvent.ICGeneralCommonUserObj objs = (ICGeneralCommonEvent.ICGeneralCommonUserObj) obj;
			vos = (AggregatedValueObject[]) objs.getNewObjects(); // 新增的时候NewObject为空，修改的时候改变NewObject,OldObject不变
		} else {
			BusinessEvent.BusinessUserObj objs = (BusinessEvent.BusinessUserObj) obj;
			vos = (AggregatedValueObject[]) objs.getUserObj();
		}
		if ((vos == null) || (vos.length == 0)) {
			return null;
		}
		return vos;
	}
}
