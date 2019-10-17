package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.District;

/**
 * ʡ���С���������ҵ���
 */
public interface IDistrictService {

	/**
	 * ����ʡ���С����Ĵ��Ż�ȡ��ϸ����
	 * @param code ʡ���С����Ĵ���
	 * @return ʡ���С�������ϸ����
	 */
	District getDistrictByCode(String code);
	
	/**
	 * ��ȡʡ���б�/ĳʡ���е��б�/ĳ�е������б�
	 * @param parent ������λ�Ĵ��ţ������Ҫ��ȡʡ���б��򸸼���λ�Ĵ��Ź̶���86
	 * @return ʡ�б�/���б�/���б�
	 */
	List<District> getList(String parent);
	
}
