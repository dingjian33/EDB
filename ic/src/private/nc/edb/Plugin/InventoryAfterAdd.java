package nc.edb.Plugin;

import nc.bs.businessevent.IBusinessEvent;
import nc.bs.businessevent.IEventType;
import nc.bs.businessevent.bd.BDCommonEvent;
import nc.vo.pub.BusinessException;

public class InventoryAfterAdd implements nc.bs.businessevent.IBusinessListener {

	@Override
	public void doAction(IBusinessEvent event) throws BusinessException {
		// TODO 自动生成的方法存根
		try {
			// AggregatedsValueObject[] value = getVOs(event);
			if (IEventType.TYPE_INSERT_AFTER.equals(event.getEventType())
					|| IEventType.TYPE_UPDATE_AFTER
							.equals(event.getEventType())) {
				BDCommonEvent e = (BDCommonEvent) event;
				Object[] value;
				if ((IEventType.TYPE_INSERT_AFTER.equals(event.getEventType())))
					value = e.getNewObjs();
				else
					value = e.getNewObjs();
				if (null == value) {
					return;
				}
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
}
