package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.Address;

public interface AddressMapper {
	
	/**
	 * �����ջ���ַ����
	 * @param address �ջ���ַ����
	 * @return ��Ӱ�������
	 */
	Integer insert(Address address);
	
	/**
	 * ��ȡĳ�û����ջ���ַ���ݵ�����
	 * @param uid �û���id
	 * @return �û����ջ���ַ���ݵ�����
	 */
	Integer getCountByUid(Integer uid);

	/**
	 * ��ȡĳ�û����ջ���ַ�б�
	 * @param uid �û���id
	 * @return �ջ���ַ�б����û��ƥ������ݣ��򷵻���Ԫ�صĿ��б�
	 */
	List<Address> getList(Integer uid);
	
	/**
	 * ��ĳ�û��������ջ���ַ����Ϊ��Ĭ���ջ���ַ
	 * @param uid �û�id
	 * @return ��Ӱ�������
	 */
	Integer setNonDefault(Integer uid);

	/**
	 * ��ָ��id���ջ���ַ����ΪĬ���ջ���ַ
	 * @param id �ջ���ַ����id
	 * @return ��Ӱ�������
	 */
	Integer setDefault(Integer id);

	/**
	 * ����id��ȡ�ջ���ַ����
	 * @param id �ջ���ַ����id
	 * @return �ջ���ַ���ݣ����û��ƥ������ݣ��򷵻�null��
	 */
	Address findAddressById(Integer id);
	
	Integer deleteById(Integer id);

	Integer getMaxId(Integer uid);
}




