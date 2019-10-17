package cn.tedu.store.service;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.PasswordFormatException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateDataException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;
import cn.tedu.store.service.ex.UsernameFormatException;

public interface IUserService {

	/**
	 * �û�ע��
	 * 
	 * @param user �û�����
	 * @return �ɹ�ע����û����ݣ��Ұ����û���id
	 * @throws UsernameConflictException �û�����ռ��
	 * @throws InsertDataException       �������ݴ���
	 * @throws UsernameFormatException   �û�����ʽ����
	 * @throws PasswordFormatException   �����ʽ����
	 */
	User reg(User user)
			throws UsernameConflictException, InsertDataException, UsernameFormatException, PasswordFormatException;

	/**
	 * �û���¼
	 * 
	 * @param username �û���
	 * @param password ����
	 * @return �ɹ���¼���û���Ϣ
	 * @throws UserNotFoundException     �û���������
	 * @throws PasswordNotMatchException �������
	 * @throws UsernameFormatException   �û�����ʽ����
	 * @throws PasswordFormatException   �����ʽ����
	 */
	User login(String username, String password)
			throws UserNotFoundException, PasswordNotMatchException, UsernameFormatException, PasswordFormatException;

	/**
	 * �޸��û�����
	 * 
	 * @param id          �û�id
	 * @param oldPassword ԭ����
	 * @param newPassword ������
	 * @throws UserNotFoundException     �û����ݲ�����
	 * @throws PasswordNotMatchException ԭ���벻ƥ��
	 * @throws PasswordFormatException   �����ʽ����
	 * @throws UpdateDataException       �������ݴ���
	 */
	void changePassword(Integer id, String oldPassword, String newPassword)
			throws UserNotFoundException, PasswordNotMatchException, PasswordFormatException, UpdateDataException;

	/**
	 * �޸��û�����
	 * 
	 * @param user �û�����
	 * @throws UpdateDataException   ���´���
	 * @throws UserNotFoundException �û����ݲ�����
	 */
	void changeInfo(User user) throws UpdateDataException, UserNotFoundException;

	/**
	 * �����û�id��ѯ�û�����
	 * 
	 * @param id �û�id
	 * @return ƥ����û����ݣ����û��ƥ������ݣ��򷵻�null
	 */
	User findUserById(Integer id);

	/**
	 * �޸��û�ͷ��
	 * 
	 * @param id     �û�id
	 * @param avatar �û�ͷ���·��
	 * @throws UserNotFoundException �û����ݲ�����
	 * @throws UpdateDataException   �������ݴ���
	 */
	void changeAvatar(Integer id, String avatar) throws UserNotFoundException, UpdateDataException;

}
