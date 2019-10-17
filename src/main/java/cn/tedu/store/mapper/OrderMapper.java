package cn.tedu.store.mapper;

import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.vo.OrderVO;

public interface OrderMapper {

	/**
	 * ��������
	 * @param order
	 * @return
	 */
	Integer insertOrder(Order order);

	/**
	 * ����������Ʒ
	 * @param orderItem
	 * @return
	 */
	Integer insertOrderItem(OrderItem orderItem);
	
	/**
	 * ���ݶ���id��ѯ��������
	 * @param orderId
	 * @return
	 */
	OrderVO getOrderById(Integer orderId);
	
}
