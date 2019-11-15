package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

public interface CartMapper {

	/**
	 * ��������
	 * @param cart
	 * @return
	 */
	Integer insert(Cart cart);

	/**
	 * ��ѯ
	 * @param uid
	 * @param goodsId
	 * @return
	 */
	Cart findCartByUidAndGoodsId(
		@Param("uid") Integer uid, 
		@Param("goodsId") String goodsId);

	/**
	 * �޸�����
	 * @param id
	 * @param goodsNum
	 * @return
	 */
	Integer updateGoodsNum(@Param("id") Integer id, @Param("goodsNum") Integer goodsNum);

	Integer deleteGoodsFromCart(Integer id);

	/**
	 * 
	 * @param uid
	 * @return
	 */
	List<CartVO> getList(Integer uid);

	/**
	 * 
	 * @param id
	 * @return
	 */
	Cart findCartById(Integer id);
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	List<CartVO> getListByIds(Integer[] ids);
}



