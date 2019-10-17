package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.PasswordFormatException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateDataException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;
import cn.tedu.store.service.ex.UsernameFormatException;
import cn.tedu.store.util.TextValidator;

/**
 * �û����ݵ�ҵ���
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	public User reg(User user) throws UsernameConflictException, InsertDataException {
		// ��֤�û���������ĸ�ʽ�Ƿ���ȷ
		if (!TextValidator.checkUsername(user.getUsername())) {
			throw new UsernameFormatException("���Ե�¼���û���(" + user.getUsername() + ")��ʽ����ȷ��");
		}
		if (!TextValidator.checkPassword(user.getPassword())) {
			throw new PasswordFormatException("���Ե�¼�������ʽ����ȷ��");
		}

		// * ��λ����֯ҵ�񣬱�֤���ݵİ�ȫ��
		// ���ݳ���ע����û�����ѯ�û�����
		User data = findUserByUsername(user.getUsername());
		// �ж��Ƿ��ѯ������
		if (data != null) {
			// �ǣ���ѯ�����ݣ����û�����ռ�ã����׳�UsernameConflictException�쳣
			throw new UsernameConflictException("����ע����û���(" + user.getUsername() + ")�Ѿ���ռ�ã�");
		} else {
			// ��û�в�ѯ�����ݣ����û���û�б�ռ�ã���ִ�в����û����ݣ���ȡ����ֵ
			User result = insert(user);
			// ִ�з���
			return result;
		}
	}

	public User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException {
		// ��֤�û���������ĸ�ʽ�Ƿ���ȷ
		if (!TextValidator.checkUsername(username)) {
			throw new UsernameFormatException("���Ե�¼���û���(" + username + ")��ʽ����ȷ��");
		}
		if (!TextValidator.checkPassword(password)) {
			throw new PasswordFormatException("���Ե�¼�������ʽ����ȷ��");
		}

		// �����û�����ѯ�û�����
		User user = findUserByUsername(username);
		// �ж��Ƿ��ѯ������
		if (user != null) {
			// �ǣ���ѯ�����û���ƥ������ݣ���ȡ��ֵ
			String salt = user.getSalt();
			// ���ڲ�����������ֵ���м���
			String md5Password = getEncrpytedPassword(password, salt);
			// �жϼ��ܽ�����û������е������Ƿ�ƥ��
			if (user.getPassword().equals(md5Password)) {
				// �ǣ������û�����
				user.setPassword(null);
				user.setSalt(null);
				return user;
			} else {
				// �����벻��ȷ���׳�PasswordNotMacthException�쳣
				throw new PasswordNotMatchException("�������");
			}
		} else {
			// ��û�����û���ƥ������ݣ����׳�UserNotFoundException�쳣
			throw new UserNotFoundException("���Ե�¼���û���(" + username + ")�����ڣ�");
		}
	}

	public void changePassword(Integer id, String oldPassword, String newPassword)
			throws UserNotFoundException, PasswordNotMatchException, PasswordFormatException, UpdateDataException {
		// ��֤���ݸ�ʽ
		if (!TextValidator.checkPassword(oldPassword)) {
			throw new PasswordFormatException("ԭ�����ʽ����");
		}
		if (!TextValidator.checkPassword(newPassword)) {
			throw new PasswordFormatException("�������ʽ����");
		}

		// ����id��ѯ�û�����
		User user = findUserById(id);
		// �ж��û������Ƿ���ڣ������û���¼�����ݱ�ɾ����
		if (user != null) {
			// �ǣ��û����ݴ��ڣ���ȡ��ֵ
			String salt = user.getSalt();
			// ��oldPassword����
			String oldMd5Password = getEncrpytedPassword(oldPassword, salt);
			// �����ܺ�����룬��ղŲ�ѯ����е�����Ա�
			if (user.getPassword().equals(oldMd5Password)) {
				// �ǣ������κ�newPassword����
				String newMd5Password = getEncrpytedPassword(newPassword, salt);
				// ��������
				updatePassword(id, newMd5Password);
			} else {
				// ��ԭ��������׳�PasswordNotMatchException
				throw new PasswordNotMatchException("ԭ�������");
			}
		} else {
			// ���û����ݲ����ڣ��׳�UserNotFoundException
			throw new UserNotFoundException("���Է��ʵ��û����ݲ����ڣ������Ѿ���ɾ����");
		}
	}

	public void changeInfo(User user) throws UpdateDataException, UserNotFoundException {
		// ����Ƿ���id
		if (user.getId() == null) {
			throw new UpdateDataException("�����û�����ʧ�ܣ�ȱ�ٱ�Ҫ�������û�id��");
		}

		// ����ֻ����롢�����ʼ��ĸ�ʽ

		// ����û������Ƿ����
		User data = findUserById(user.getId());
		if (data == null) {
			throw new UserNotFoundException("���Է��ʵ��û����ݲ����ڣ�");
		}

		// ��ȫ����
		user.setModifiedTime(new Date());
		user.setModifiedUser(data.getUsername());

		// ִ�и���
		updateInfo(user);
	}

	public void changeAvatar(Integer id, String avatar) throws UserNotFoundException, UpdateDataException {
		if (findUserById(id) == null) {
			throw new UserNotFoundException("���Է��ʵ��û����ݲ����ڣ�");
		}
		updateAvatar(id, avatar);
	}

	public User findUserById(Integer id) {
		return userMapper.findUserById(id);
	}

	/**
	 * �����û�����ѯ�û�����
	 * 
	 * @param username �û���
	 * @return ƥ����û����ݣ����û��ƥ������ݣ��򷵻�null
	 */
	private User findUserByUsername(String username) {
		return userMapper.findUserByUsername(username);
	}

	/**
	 * �����û�����
	 * 
	 * @param user �û�����
	 * @return �ɹ�������û�����
	 */
	private User insert(User user) {
		// �ڲ���user�з�װ��Щ�����ⲿ�ṩ�����ݣ�
		// 1. ��������Σ�����װ��user��
		String salt = UUID.randomUUID().toString().toUpperCase();
		user.setSalt(salt);
		// 2. ȡ��user��ԭ����ִ�м��ܣ�����װ��user��
		String oldPassword = user.getPassword();
		String md5Password = getEncrpytedPassword(oldPassword, salt);
		user.setPassword(md5Password);
		// 3. ����isDeleteΪ0
		user.setIsDelete(0);
		// 4. ��־��4��
		Date now = new Date();
		user.setCreatedTime(now);
		user.setModifiedTime(now);
		user.setCreatedUser("[System]");
		user.setModifiedUser("[System]");

		// �����û�����
		Integer rows = userMapper.insert(user);
		if (rows == 1) {
			return user;
		} else {
			throw new InsertDataException("�����û�����ʱ����δ֪��������ϵϵͳ����Ա��");
		}
	}

	/**
	 * ��������
	 * 
	 * @param id       �û�id
	 * @param password ������
	 * @return ��Ӱ�������
	 */
	private Integer updatePassword(Integer id, String password) {
		Integer rows = userMapper.updatePassword(id, password);
		if (rows == 1) {
			return 1; // return rows;
		} else {
			throw new UpdateDataException("��������ʱ����δ֪��������ϵϵͳ����Ա��");
		}
	}

	/**
	 * �����û�����
	 * 
	 * @param user �û�����
	 */
	private void updateInfo(User user) {
		Integer rows = userMapper.updateInfo(user);
		if (rows != 1) {
			throw new UpdateDataException("�����û�����ʱ����δ֪��������ϵϵͳ����Ա��");
		}
	}

	/**
	 * �����û�ͷ��
	 * 
	 * @param id     �û�id
	 * @param avatar �û�ͷ���·��
	 */
	private void updateAvatar(Integer id, String avatar) {
		Integer rows = userMapper.updateAvatar(id, avatar);
		if (rows != 1) {
			throw new UpdateDataException("�����û�����ʱ����δ֪��������ϵϵͳ����Ա��");
		}
	}

	/**
	 * ��ȡ���ܺ������
	 * 
	 * @param password ����ԭ��
	 * @param salt     ��ֵ
	 * @return ���ܺ������
	 */
	private String getEncrpytedPassword(String password, String salt) {
		// ��ԭ�������
		String str1 = DigestUtils.md5Hex(password).toUpperCase();
		// ���μ���
		String str2 = DigestUtils.md5Hex(salt).toUpperCase();
		// ������2�����ܽ��ƴ��
		String result = str1 + str2;
		// ѭ��5�μ���
		for (int i = 0; i < 5; i++) {
			result = DigestUtils.md5Hex(result).toUpperCase();
		}
		// ����
		return result;
	}

}
