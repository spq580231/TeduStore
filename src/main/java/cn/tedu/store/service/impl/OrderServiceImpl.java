package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.mapper.OrderMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.vo.CartVO;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {

	@Autowired 
	private IAddressService addressService;
	
	@Autowired 
	private ICartService cartService;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Transactional
	public void createOrder(Integer uid, Integer addressId, Integer[] cartIds) {
		// ��ȡ��ǰʱ��
		Date now = new Date();
		
		// ����cartIds��ȡ��Ʒ�������
		List<CartVO> carts = cartService.getListByIds(uid, cartIds);
		// �����ܽ��
		Long pay = 0L;
		for (CartVO cartVO : carts) {
			pay += cartVO.getGoodsPrice() * cartVO.getGoodsNum();
		}

		// ����addressId��ȡ�ջ���ַ����
		Address address = addressService.getAddressById(addressId);
		// ׼�����붩������
		Order order = new Order();
		order.setUid(uid);
		order.setRecvName(address.getRecvName());
		order.setRecvAddress(address.getRecvAddress());
		order.setRecvDistrict(address.getRecvDistrict());
		order.setRecvPhone(address.getRecvPhone());
		order.setRecvZip(address.getRecvZip());
		order.setStatus(0); // 0-δ֧��
		order.setOrderTime(now);
		order.setPayTime(null);
		order.setPay(pay);

		// ִ�У����붩������
		insertOrder(order);

		// ִ�У����붩����Ʒ����
		for (CartVO cartVO : carts) {
			OrderItem item = new OrderItem();
			item.setOrderId(order.getId());
			item.setGoodsId(cartVO.getGoodsId());
			item.setGoodsImage(cartVO.getGoodsImage());
			item.setGoodsNum(cartVO.getGoodsNum());
			item.setGoodsPrice(cartVO.getGoodsPrice());
			item.setGoodsTitle(cartVO.getGoodsTitle());
			insertOrderItem(item);
		}
		
		// TODO ���ݲ���Integer[] cartIds��ȡ����goods_id��goods_num������t_goods������Ʒ�Ŀ��

		// TODO ���ݲ���Integer[] cartIdsɾ�����ﳵ�ж�Ӧ������
	}
	
	private void insertOrder(Order order) {
		Integer rows
			= orderMapper.insertOrder(order);
		if (rows != 1) {
			throw new InsertDataException(
				"");
		}
	}

	private void insertOrderItem(OrderItem orderItem) {
		Integer rows
			= orderMapper.insertOrderItem(orderItem);
		if (rows != 1) {
			throw new InsertDataException(
				"");
	}
	}

}
