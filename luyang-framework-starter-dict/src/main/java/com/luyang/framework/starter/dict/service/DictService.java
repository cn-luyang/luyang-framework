package com.luyang.framework.starter.dict.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.luyang.framework.starter.dict.cache.DictCache;
import com.luyang.framework.starter.dict.model.DictItem;
import com.luyang.framework.starter.dict.repository.DictRepository;

import java.util.List;

/**
 * @author yang.lu
 */
public class DictService {

	private final DictCache dictCache;
	private final DictRepository dictRepository;

	public DictService(DictCache dictCache, DictRepository dictRepository) {
		this.dictCache = dictCache;
		this.dictRepository = dictRepository;
	}

	/**
	 * 添加字典项
	 *
	 * @param dictItem 字典项
	 * @author yang.lu
	 */
	public void createItem(DictItem dictItem) {

		String dictCode = dictItem.getDictCode();
		String itemValue = dictItem.getItemValue();

		// 查看数据库是否存在
		boolean dbExists = dictRepository.existsItem(dictCode, itemValue);
		if (dbExists) {
			throw new IllegalArgumentException("字典项值已存在: " + itemValue);
		}

		int count = dictRepository.insertItem(dictItem);
		if (count > 0) {
			// 添加成功后，清理旧缓存即可，下次查询时重新从数据库加载
			dictCache.removeDict(dictCode);
		}
	}

	/**
	 * 删除字典项
	 *
	 * @param dictCode  字典码
	 * @param itemValue 字典值
	 * @author yang.lu
	 */
	public void deleteItem(String dictCode, String itemValue) {
		// 从数据库删除
		boolean success = dictRepository.removeItem(dictCode, itemValue);
		if (success) {
			// 从缓存删除
			dictCache.removeItem(dictCode, itemValue);
		}
	}

	/**
	 * 获取字典项列表
	 *
	 * @param dictCode 字典码
	 * @return 字典项列表
	 * @author yang.lu
	 */
	public List<DictItem> getItems(String dictCode) {
		if (StrUtil.isBlank(dictCode)) {
			return List.of();
		}

		// 从缓存中获取字典项列表
		List<DictItem> items = dictCache.findItems(dictCode);
		if (CollUtil.isNotEmpty(items)) {
			return items;
		}

		// 缓存为空继续从数据库中获取
		List<DictItem> dbItems = dictRepository.findItems(dictCode);
		if (CollUtil.isEmpty(dbItems)) {
			return List.of();
		}

		// 添加到缓存
		dictCache.putItems(dictCode, dbItems);
		return dbItems;
	}
}
