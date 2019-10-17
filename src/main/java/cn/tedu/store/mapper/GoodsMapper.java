package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Goods;

public interface GoodsMapper {
	
	/**
	 * ����id��ѯ��Ʒ����
	 * @param id ��Ʒ��id
	 * @return ƥ�����Ʒ���ݣ����û��ƥ������ݣ��򷵻�null
	 */
	Goods findGoodsById(String id);

	/**
	 * ��ȡ��Ʒ�б�
	 * @param where ��ѯ����е�WHERE�Ӿ䣬������WHERE�ؼ���
	 * @param orderBy ��ѯ����е�ORDER BY�Ӿ䣬������ORDER BY�ؼ���
	 * @param offset ��ѯʱ�������еĲ�ѯ����У�����ǰ�������ٿ�ʼ��ȡ����
	 * @param count ����ѯ����������
	 * @return ��Ʒ�б�
	 */
	List<Goods> getList(
		@Param("where") String where,
		@Param("orderBy") String orderBy,
		@Param("offset") Integer offset,
		@Param("count") Integer count
	);
	
}
