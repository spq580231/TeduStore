package cn.tedu.store.mapper;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;

public interface UserMapper {
	
	/**
	 * �����û�����
	 * @param user �û�����
	 * @return ��Ӱ�������
	 */
	Integer insert(User user);

	/**
	 * �����û�����ѯ�û�����
	 * @param username �û���
	 * @return ƥ����û����ݣ����û��ƥ������ݣ��򷵻�null
	 */
	User findUserByUsername(String username);
	
	/**
	 * �����û�id��ѯ�û�����
	 * @param id �û�id
	 * @return ƥ����û����ݣ����û��ƥ������ݣ��򷵻�null
	 */
	User findUserById(Integer id);

	/**
	 * ��������
	 * @param id �û�id
	 * @param password ������
	 * @return ��Ӱ�������
	 */
	Integer updatePassword(
			@Param("id") Integer id, 
			@Param("password") String password);

	/**
	 * �����û�����
	 * @param user �û����ݶ���
	 * @return ��Ӱ�������
	 */
	Integer updateInfo(User user);
	
	/**
	 * �����û�ͷ��
	 * @param id �û�id
	 * @param avatar �û�ͷ���ڷ������˵�·��
	 * @return ��Ӱ�������
	 */
	Integer updateAvatar(
			@Param("id") Integer id,
			@Param("avatar") String avatar);
}




