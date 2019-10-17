package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.service.ex.AddressAccessException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.DeleteDataException;
import cn.tedu.store.service.ex.GoodsNumLimitException;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.PasswordFormatException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.RequestArgumentException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UpdateDataException;
import cn.tedu.store.service.ex.UploadFileContentTypeException;
import cn.tedu.store.service.ex.UploadFileSizeLimitException;
import cn.tedu.store.service.ex.UploadIOException;
import cn.tedu.store.service.ex.UploadStateException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;
import cn.tedu.store.service.ex.UsernameFormatException;

/**
 * ��������Ļ���
 */
public abstract class BaseController {

	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	public ResponseResult<Void> handleException(Exception e) {
		// �ж��쳣���ͣ�������
		if (e instanceof RequestArgumentException) {
			// 300-��������쳣
			return new ResponseResult<Void>(300, e);
		} else if (e instanceof UsernameFormatException) {
			// 301-�û�����ʽ����
			return new ResponseResult<Void>(301, e);
		} else if (e instanceof PasswordFormatException) {
			// 302-�����ʽ����
			return new ResponseResult<Void>(302, e);
		} else if (e instanceof UploadFileSizeLimitException) {
			// 303-�ϴ��ļ���С��������
			return new ResponseResult<Void>(303, e);
		} else if (e instanceof UploadFileContentTypeException) {
			// 304-�ϴ��ļ������쳣
			return new ResponseResult<Void>(304, e);
		} else if (e instanceof UploadStateException) {
			// 305-�ϴ�״̬�쳣
			return new ResponseResult<Void>(305, e);
		} else if (e instanceof UploadIOException) {
			// 306-�ϴ��ļ���д�쳣
			return new ResponseResult<Void>(306, e);
		} else if (e instanceof CartNotFoundException) {
			// 307-���ﳵ���ݲ�����
			return new ResponseResult<Void>(307, e);
		} else if (e instanceof GoodsNumLimitException) {
			// 308-���ﳵ����Ʒ���ݳ�������
			return new ResponseResult<Void>(308, e);
		} else if (e instanceof UsernameConflictException) {
			// 401-�û�����ͻ�쳣
			return new ResponseResult<Void>(401, e);
		} else if (e instanceof UserNotFoundException) {
			// 402-�û�������
			return new ResponseResult<Void>(402, e);
		} else if (e instanceof PasswordNotMatchException) {
			// 403-�������
			return new ResponseResult<Void>(403, e);
		} else if (e instanceof AddressNotFoundException) {
			// 404-�ջ���ַ���ݲ�����
			return new ResponseResult<Void>(404, e);
		} else if (e instanceof AddressAccessException) {
			// 405-�ջ���ַ���ݷ����쳣�������ǷǷ�����
			return new ResponseResult<Void>(405, e);
		} else if (e instanceof InsertDataException) {
			// 501-���������쳣
			return new ResponseResult<Void>(501, e);
		} else if (e instanceof UpdateDataException) {
			// 502-���������쳣
			return new ResponseResult<Void>(502, e);
		} else if (e instanceof DeleteDataException) {
			// 503-ɾ�������쳣
			return new ResponseResult<Void>(503, e);
		}
		return null;
	}

	/**
	 * ��Session�л�ȡ��ǰ��¼���û���id
	 * 
	 * @param session HttpSession����
	 * @return ��ǰ��¼���û���id
	 */
	protected Integer getUidFromSession(HttpSession session) {
		return Integer.valueOf(session.getAttribute("uid").toString());
	}

}
