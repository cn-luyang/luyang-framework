package com.luyang.framework.starter.dict.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.luyang.framework.starter.dict.model.DictItem;
import com.luyang.framework.starter.dict.repository.DictRepository;

import java.util.List;

/**
 * @author yang.lu
 */
public class DictService {

//	private final DictCache dictCache;
	private final DictRepository dictRepository;

	public DictService(/*DictCache dictCache, */DictRepository dictRepository) {
//		this.dictCache = dictCache;
		this.dictRepository = dictRepository;
	}

	public void createItem(DictItem dictItem) {
		dictRepository.insertItem(dictItem);
	}

	public List<DictItem> getItems(String dictCode) {
		if (StrUtil.isBlank(dictCode)) {
			return List.of();
		}

//		List<DictItem> items = dictCache.findItems(dictCode);
//		if (CollUtil.isNotEmpty(items)) {
//			return items;
//		}

		List<DictItem> dbItems = dictRepository.findItems(dictCode);
		if (CollUtil.isEmpty(dbItems)) {
			return List.of();
		}

//		dictCache.putItems(dictCode, dbItems);
		return dbItems;
	}
}
