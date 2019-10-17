package cn.tedu.store.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.mapper.CartMapper;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.GoodsNumLimitException;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.UpdateDataException;
import cn.tedu.store.vo.CartVO;

@Service("cartService")
public class CartServiceImpl implements ICartService {
	
	@Autowired
	private CartMapper cartMapper;

	public void addToCart(Cart cart) {
		// ����cart�����з�װ��uid��goodsIdִ�в�ѯ
		Cart data = findCartByUidAndGoodsId(
				cart.getUid(), 
				cart.getGoodsId());
		// �жϽ���Ƿ�Ϊnull
		if (data == null) {
			// �ǣ����û���ǰû����Ӹ���Ʒ����ִ�в�������
			insert(cart);
		} else {
			// �񣺸��û��Ѿ���Ӹ���Ʒ����ȡԭ������
			Integer n = data.getGoodsNum();
			// ��cart�����л�ȡ�˴ε�������������õ��µ�����
			Integer newGoodsNum = cart.getGoodsNum() + n;
			// ������Ʒ����
			updateGoodsNum(data.getId(), newGoodsNum);
		}
	}

	public List<CartVO> getCartListByUid(Integer uid) {
		return getList(uid);
	}
	
	public List<CartVO> getListByIds(
			Integer uid,
			Integer[] ids) {
		List<CartVO> list = getListByIds(ids);
		Iterator<CartVO> it = list.iterator();
		while(it.hasNext()) {
			if (!it.next().getUid().equals(uid)) {
				it.remove();
			}
		}
		return list;
	}
	
	private List<CartVO> getListByIds(Integer[] ids) {
		return cartMapper.getListByIds(ids);
	}
	
	public void addNum(Integer id) {
		Cart cart = findCartById(id);
		if (cart == null) {
			throw new CartNotFoundException(
				"���Է��ʵĹ��ﳵ���ݲ����ڣ�");
		}
		Integer num = cart.getGoodsNum() + 1;
		updateGoodsNum(id, num);
	}
	
	public void reduceNum(Integer id) {
		Cart cart = findCartById(id);
		if (cart == null) {
			throw new CartNotFoundException(
				"���Է��ʵĹ��ﳵ���ݲ����ڣ�");
		}
		if(cart.getGoodsNum() <= 1) {
			throw new GoodsNumLimitException(
				"�����޸ĵĹ��ﳵ���ݵ���Ʒ�����г����ƣ�");
		}
		Integer num = cart.getGoodsNum() - 1;
		updateGoodsNum(id, num);
	}

	/**
	 * ��������
	 * @param cart
	 * @return
	 */
	private Cart insert(Cart cart) {
		Integer rows = cartMapper.insert(cart);
		if (rows == 1) {
			return cart;
		} else {
			throw new InsertDataException(
				"���ﳵ�������������ʱ����δ֪��������ϵϵͳ����Ա��");
		}
	}

	/**
	 * ��ѯ
	 * @param uid
	 * @param goodsId
	 * @return
	 */
	private Cart findCartByUidAndGoodsId(
		Integer uid, String goodsId) {
		return cartMapper.findCartByUidAndGoodsId(
				uid, goodsId);
	}

	/**
	 * �޸�����
	 * @param id
	 * @param goodsNum
	 * @return
	 */
	private void updateGoodsNum(
		Integer id, Integer goodsNum) {
		Integer rows = cartMapper.updateGoodsNum(id, goodsNum);
		if (rows != 1) {
			throw new UpdateDataException(
					"�޸Ĺ��ﳵ�е���Ʒ����ʱ����δ֪��������ϵϵͳ����Ա��");
		}
	}

	private List<CartVO> getList(Integer uid) {
		return cartMapper.getList(uid);
	}
	
	private Cart findCartById(Integer id) {
		return cartMapper.findCartById(id);
	}

	
}
