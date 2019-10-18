package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.RequestArgumentException;
import cn.tedu.store.service.ex.UploadFileContentTypeException;
import cn.tedu.store.service.ex.UploadFileSizeLimitException;
import cn.tedu.store.service.ex.UploadIOException;
import cn.tedu.store.service.ex.UploadStateException;
import cn.tedu.store.util.TextValidator;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	/**
	 * �û��ϴ���ͷ������ߴ磬��λ���ֽ�
	 */
	private static final long AVATAR_MAX_SIZE = 1 * 1024 * 1024;
	/**
	 * ͷ�����Ͱ�����
	 */
	private static final List<String> AVATAR_TYPE_WHITE_LIST = new ArrayList<String>();

	@PostConstruct
	public void init() {
		AVATAR_TYPE_WHITE_LIST.add("image/jpeg");
		AVATAR_TYPE_WHITE_LIST.add("image/png");
	}

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/handle_reg.do", method = RequestMethod.POST)
	@ResponseBody
	// ��ǰ�����ķ���ֵ�еķ��ͱ�ʾ��Ҫ���ͻ��˵Ľ���У����˲���״̬����ʾ��Ϣ���⣬����ʲô����
	public ResponseResult<Void> handleReg(User user,HttpSession session) {
		// ��֤���ݸ�ʽ����������ϣ���ֱ����Ӧ����ʾ����
		
		if (!TextValidator.checkUsername(user.getUsername())) {
			return new ResponseResult<Void>(301, "�û�����ʽ����ȷ��");

		}
		if (!TextValidator.checkPassword(user.getPassword())) {
			return new ResponseResult<Void>(302, "�����ʽ����ȷ��");
		}

		// ����ҵ������ʵ��ע��
		userService.reg(user);
		
		session.setAttribute("uid", user.getId());
		// ִ�з���
		return new ResponseResult<Void>();
	}

	@RequestMapping(value = "/handle_login.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleLogin(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpSession session) {
		// ��֤���ݸ�ʽ����������ϣ���ֱ����Ӧ����ʾ����
		if (!TextValidator.checkUsername(username)) {
			return new ResponseResult<Void>(301, "�û�����ʽ����ȷ��");
		}
		if (!TextValidator.checkPassword(password)) {
			return new ResponseResult<Void>(302, "�����ʽ����ȷ��");
		}

		// ����ҵ�������login()����������ȡ����ֵ
		User user = userService.login(username, password);
		// ���û�id���û�����װ��session��
		session.setAttribute("uid", user.getId());
		//session.setAttribute("username", user.getUsername());
		// ����
		return new ResponseResult<Void>();
	}

	@RequestMapping(value = "/change_password.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleChangePassword(@RequestParam("old_password") String oldPassword,
			@RequestParam("new_password") String newPassword, HttpSession session) {
		// ��֤�����ʽ
		if (!TextValidator.checkPassword(oldPassword)) {
			return new ResponseResult<Void>(302, "ԭ�����ʽ����");
		}
		if (!TextValidator.checkPassword(newPassword)) {
			return new ResponseResult<Void>(302, "�������ʽ����");
		}

		// ��Session�л�ȡ��ǰ�û���id
		Integer id = getUidFromSession(session);
		// ͨ��ҵ��ִ���޸�����
		userService.changePassword(id, oldPassword, newPassword);
		// ����
		return new ResponseResult<Void>();
	}

	@RequestMapping(value = "/change_info.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleChangeInfo(User user, HttpSession session) {
		// ��ȡid
		Integer uid = getUidFromSession(session);
		// ִ��
		user.setId(uid);
		userService.changeInfo(user);
		// ����
		return new ResponseResult<Void>();
	}

	@RequestMapping(value = "/info.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<User> getInfo(HttpSession session) {
		// ��ȡuid
		Integer uid = getUidFromSession(session);
		// ��ѯ
		User user = userService.findUserById(uid);
		// ��������ֵ����
		ResponseResult<User> rr = new ResponseResult<User>();
		// �Ѳ�ѯ�����װ������ֵ�����data������
		rr.setData(user);
		// ����
		return rr;
	}

	@RequestMapping(value = "/delSession.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> delSession(HttpSession session) {
		session.removeAttribute("uid");
		return null;
	}

	@RequestMapping(value = "/upload.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> handleUpload(HttpServletRequest request, HttpSession session,
			@RequestParam("file") CommonsMultipartFile file) {
		// ����Ƿ��ϴ����ļ�
		if (file.isEmpty()) {
			throw new RequestArgumentException("û��ѡ���ϴ����ļ������ϴ����ļ�������Ϊ�գ�");
		}
		// ����ļ���С
		long fileSize = file.getSize();
		if (fileSize > AVATAR_MAX_SIZE) {
			throw new UploadFileSizeLimitException("�ϴ����ļ���С�������ƣ�����ֵΪ" + (AVATAR_MAX_SIZE / 1024) + "KByte��");
		}
		// ����ļ�����
		String contentType = file.getContentType();
		if (!AVATAR_TYPE_WHITE_LIST.contains(contentType)) {
			throw new UploadFileContentTypeException("�ϴ��ļ����ʹ���������ļ����ͣ�" + AVATAR_TYPE_WHITE_LIST);
		}

		// ��ȡ��ǰ��¼���û���id
		Integer id = getUidFromSession(session);

		// �û��ϴ����ļ��洢�����ļ��е�����
		String uploadDirName = "upload";
		// �û��ϴ����ļ��洢�����ļ��е�·��
		String parentDirPath = request.getServletContext().getRealPath(uploadDirName);
		// �û��ϴ����ļ��洢�����ļ���
		File parentDir = new File(parentDirPath);
		// ȷ���ļ��д���
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}

		// ��ȡԭʼ�ļ���
		String originalFileName = file.getOriginalFilename();
		// ��ȡԭʼ�ļ�����չ��
		int beginIndex = originalFileName.lastIndexOf(".");
		String suffix = originalFileName.substring(beginIndex);
		// �û��ϴ����ļ��洢���ļ���
		String fileName = getFileName(id) + suffix;
		// ȷ���û��ϴ����ļ��ڷ������˵�·��
		String avatar = uploadDirName + "/" + fileName;

		// �û��ϴ����ļ��洢���������˵��ļ�����
		File dest = new File(parentDir, fileName);

		// ���û��ϴ����ļ��洢��ָ���ļ���
		try {
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			throw new UploadStateException("��ȡ�ļ��жϣ��ļ�·�������Ѿ������仯��");
		} catch (IOException e) {
			throw new UploadIOException("��ȡ���ݳ����ļ������ѱ��ƶ���ɾ���������������жϣ�");
		}
		// ���û���ͷ�����ݸ��µ����ݱ�
		userService.changeAvatar(id, avatar);

		// ����
		ResponseResult<String> rr = new ResponseResult<String>();
		rr.setData(avatar);
		return rr;
	}

	/**
	 * ��ȡ�ϴ��ļ����ļ������ļ��������������ǣ�uid-yyyyMMddHHmmss
	 * 
	 * @param uid �û�id
	 * @return ƥ���ʽ���ַ���
	 */
	private String getFileName(Integer uid) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return uid + "-" + sdf.format(date);
	}

}
