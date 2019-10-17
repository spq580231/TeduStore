package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.service.ex.AddressAccessException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.DeleteDataException;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.UpdateDataException;

@Service("addressService")
public class AddressServiceImpl 
	implements IAddressService {
	
	@Autowired
	private AddressMapper addressMapper;
	@Autowired
	private IDistrictService districtService;

	public Address addnew(Address address) {
		// �������ݣ�recv_district
		String recvDistrict
			= getRecvDistrictByCode(
				address.getRecvProvince(), 
				address.getRecvCity(), 
				address.getRecvArea());
		address.setRecvDistrict(recvDistrict);
		
		// �������ݣ�is_default
		Integer count = getCountByUid(
				address.getUid());
		address.setIsDefault(
				count == 0 ? 1 : 0);
		
		// ִ�в������ݣ���ȡ����ֵ
		Address result = insert(address);
		// ִ�з���
		return result;
	}
	
	public List<Address> getList(Integer uid) {
		return addressMapper.getList(uid);
	}

	@Transactional
	public void setDefaultAddress(Integer id, Integer uid) throws AddressNotFoundException, UpdateDataException {
		// ��1����������Ƿ�����û�
		Address address = findAddressById(id);
		if (address == null) {
			throw new AddressNotFoundException(
				"���Է��ʵ��ջ���ַ���ݲ����ڣ�");
		}
		if (!address.getUid().equals(uid)) {
			throw new AddressAccessException(
				"���Է��ʵ��ջ���ַ���ݹ�������");
		}
		// ��2�������û������е�ַ���÷�Ĭ��
		setNonDefault(uid);
		// ��3����ָ��id�ĵ�ַ����ΪĬ��
		setDefault(id);
	}
	
	@Transactional
	public void delete(Integer id, Integer uid)
			throws AddressNotFoundException, AddressAccessException, DeleteDataException {
		// ����id��ѯ����
		Address data = findAddressById(id);
		// ��������Ƿ����
		if (data != null) {
		    // ���ڣ�������ݵ�uid����
		    if (data.getUid().equals(uid)) {
		        // ����������ִ��ɾ��
		    	deleteById(id);
		    	// �жϸղ�ɾ���ĵ�ַ�Ƿ���Ĭ��
		    	if (data.getIsDefault() == 1) {
		            // �ǣ���ǰ����û���ջ���ַ���������٣�
		    		Integer count = getCountByUid(uid);
		    		if (count > 0) {
		                // ��Ϊ0����id������������ΪĬ�ϵ�ַ
		    			Integer maxId = getMaxId(uid);
		    			setDefault(maxId);
		    		}
		    	}
		    } else {
		        // ���������׳��쳣AddressAccessException
		    	throw new AddressAccessException(
		    		"����ɾ�����ջ���ַ���ݹ�������");
		    }
		} else {
		    // �����ڣ��׳��쳣AddressNotFoundException
			throw new AddressNotFoundException(
				"����ɾ�����ջ���ַ���ݲ����ڣ�");
		}
	}

	public Address getAddressById(Integer id) {
		return findAddressById(id);
	}
	
	/**
	 * �����ջ���ַ����
	 * @param address �ջ���ַ����
	 * @return �ɹ�������ջ���ַ���ݣ��������ݵ�id
	 */
	private Address insert(Address address) {
		Integer rows
			= addressMapper.insert(address);
		if (rows != 1) {
			throw new InsertDataException("�����ջ���ַ����ʱ����δ֪��������ϵϵͳ����Ա��");
		} else {
			return address;
		}
	}
	
	/**
	 * �����û�id��ȡ���û����ջ���ַ���ݵ�����
	 * @param uid �û�id
	 * @return ��Ӧ���û����ջ���ַ���ݵ�����
	 */
	private Integer getCountByUid(Integer uid) {
		return addressMapper.getCountByUid(uid);
	}
	
	/**
	 * ��ȡ�ջ���ַ��ʡ���С���
	 * @param provinceCode ʡ�Ĵ���
	 * @param cityCode �еĴ���
	 * @param areaCode ���Ĵ���
	 * @return �ջ���ַ��ʡ���С��������磺�ӱ�ʡ, ʯ��ׯ��, ������
	 */
	private String getRecvDistrictByCode(
			String provinceCode,
			String cityCode, 
			String areaCode) {
		District province = districtService.getDistrictByCode(provinceCode);
		District city = districtService.getDistrictByCode(cityCode);
		District area = districtService.getDistrictByCode(areaCode);
		
		StringBuffer str = new StringBuffer();
		str.append(province == null ? "Null" : province.getName());
		str.append(", ");
		str.append(city == null ? "Null" : city.getName());
		str.append(", ");
		str.append(area == null ? "Null" : area.getName());
		
		return str.toString();
	}

	/**
	 * ��ĳ�û��������ջ���ַ����Ϊ��Ĭ���ջ���ַ
	 * @param uid �û�id
	 * @return ��Ӱ�������
	 */
	private void setNonDefault(Integer uid) {
		Integer rows = addressMapper.setNonDefault(uid);
		if (rows < 1) {
			throw new UpdateDataException(
				"�����ջ���ַ��Ϣʱ����δ֪��������ϵϵͳ����Ա��");
		}
	}

	/**
	 * ��ָ��id���ջ���ַ����ΪĬ���ջ���ַ
	 * @param id �ջ���ַ����id
	 * @return ��Ӱ�������
	 */
	private void setDefault(Integer id) {
		Integer rows = addressMapper.setDefault(id);
		if (rows < 1) {
			throw new UpdateDataException(
				"�����ջ���ַ��Ϣʱ����δ֪��������ϵϵͳ����Ա��");
		}
	}

	/**
	 * ����id��ȡ�ջ���ַ����
	 * @param id �ջ���ַ����id
	 * @return �ջ���ַ���ݣ����û��ƥ������ݣ��򷵻�null��
	 */
	private Address findAddressById(Integer id) {
		return addressMapper.findAddressById(id);
	}

	private void deleteById(Integer id) {
		Integer rows
			= addressMapper.deleteById(id);
		if (rows != 1) {
			throw new DeleteDataException(
				"ɾ���ջ���ַʱ����δ֪��������ϵϵͳ����Ա��");
		}
	}

	private Integer getMaxId(Integer uid) {
		return addressMapper.getMaxId(uid);
	}
	
}





