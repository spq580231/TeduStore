package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.service.IOrderService;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {
	
	@Autowired
	private IOrderService orderService;
	
	@RequestMapping(value="/create.do", 
			method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> createOrder(
		@RequestParam("address_id") Integer addressId,
		@RequestParam("cart_ids") Integer[] cartIds,
		HttpSession session) {
		// ��ȡuid
		Integer uid = getUidFromSession(session);
		// ����ҵ���ʵ�ֹ���
		orderService.createOrder(uid, addressId, cartIds);
		// ����
		return new ResponseResult<Void>();
	}

}
