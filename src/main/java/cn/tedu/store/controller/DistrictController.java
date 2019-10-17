package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.entity.District;
import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.service.IDistrictService;

@Controller
@RequestMapping("/district")
public class DistrictController {

	@Autowired
	private IDistrictService districtService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	public ResponseResult<List<District>> getList(
		@RequestParam("parent") String parent) {
		// ��ȡ����
		List<District> list
			= districtService.getList(parent);
		// ��������ֵ����
		ResponseResult<List<District>> rr
			= new ResponseResult<List<District>>();
		// �ڷ��ض����з�װ����
		rr.setData(list);
		// ִ�з���
		return rr;
	}
}








