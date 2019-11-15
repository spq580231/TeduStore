package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.vo.CartVO;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {

	@Autowired
	private ICartService cartService;

	@RequestMapping(value = "/add.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> addToCart(@RequestParam("goods_id") String goodsId,
			@RequestParam("goods_num") Integer goodsNum, HttpSession session) {
		// ��session�л�ȡuid
		Integer uid = getUidFromSession(session);
		// ����Cart���͵Ķ���
		Cart cart = new Cart(uid, goodsId, goodsNum);
		// ����ҵ��㷽��
		cartService.addToCart(cart);
		// ����
		return new ResponseResult<Void>();
	}


	@RequestMapping("/list.do")
	@ResponseBody
	public ResponseResult<List<CartVO>> getCartListByUid(HttpSession session) {
		// ��ȡuid
		Integer uid = getUidFromSession(session);
		// ִ��
		List<CartVO> list = cartService.getCartListByUid(uid);
		// ��������ֵ����
		ResponseResult<List<CartVO>> rr = new ResponseResult<List<CartVO>>();
		// ��װ����
		rr.setData(list);
		// ����
		return rr;
	}

	@RequestMapping("/list_by_ids.do")
	@ResponseBody
	public ResponseResult<List<CartVO>> getCartListByIds(@RequestParam("ids") Integer[] ids, HttpSession session) {
		// ��ȡuid
		Integer uid = getUidFromSession(session);
		// ִ��
		List<CartVO> list = cartService.getListByIds(uid, ids);
		// ��������ֵ����
		ResponseResult<List<CartVO>> rr = new ResponseResult<List<CartVO>>();
		// ��װ����
		rr.setData(list);
		// ����
		return rr;
	}

	@RequestMapping("add_num.do")
	@ResponseBody
	public ResponseResult<Void> addGoodsNum(@RequestParam("id") Integer id) {
		cartService.addNum(id);
		return new ResponseResult<Void>();
	}

	@RequestMapping("reduce_num.do")
	@ResponseBody
	public ResponseResult<Void> reduceGoodsNum(@RequestParam("id") Integer id) {
		cartService.reduceNum(id);
		return new ResponseResult<Void>();
	}

}
