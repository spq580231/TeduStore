package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface ICartService {

	/**
	 * 
	 * @param cart
	 */
	void addToCart(Cart cart);

	
	/**
	 * 
	 * @param uid
	 * @return
	 */
	List<CartVO> getCartListByUid(Integer uid);
	
	/**
	 * 
	 * @param uid
	 * @param ids
	 * @return
	 */
	List<CartVO> getListByIds(Integer uid, Integer[] ids);
	
	/**
	 * 
	 * @param id
	 */
	void addNum(Integer id);
	
	/**
	 * 
	 * @param id
	 */
	void reduceNum(Integer id);

	void delFromCart(Integer id);
}
