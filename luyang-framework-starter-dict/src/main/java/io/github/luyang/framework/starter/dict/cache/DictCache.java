package io.github.luyang.framework.starter.dict.cache;

import cn.hutool.core.collection.CollUtil;
import io.github.luyang.framework.starter.dict.model.DictItem;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 字典相关缓存
 *
 * @author yang.lu
 */
public class DictCache {

	private final RedissonClient redissonClient;

	private static final String KEY_PREFIX = "dict:";

	public DictCache(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
	}

	/**
	 * 查找字典项列表
	 *
	 * @param dictCode 字典码
	 * @return 字典项列表
	 * @author yang.lu
	 */
	public List<DictItem> findItems(String dictCode) {
		RMapCache<String, DictItem> itemMap = redissonClient.getMapCache(KEY_PREFIX + dictCode);
		return itemMap.readAllValues().stream().toList();
	}

	/**
	 * 字典项是否存在
	 *
	 * @param dictCode  字典码
	 * @param itemValue 字典值
	 * @return true存在 false不存在
	 * @author yang.lu
	 */
	public boolean existsItem(String dictCode, String itemValue) {
		List<DictItem> items = findItems(dictCode);
		return items.stream().anyMatch(item -> item.getItemValue().equals(itemValue));
	}

	/**
	 * 添加字典项列表
	 *
	 * @param dictCode  字典码
	 * @param dictItems 字典项列表
	 * @author yang.lu
	 */
	public void putItems(String dictCode, List<DictItem> dictItems) {
		if (CollUtil.isEmpty(dictItems)) {
			return;
		}
		RMapCache<String, DictItem> itemMap = redissonClient.getMapCache(KEY_PREFIX + dictCode);
		itemMap.putAll(dictItems.stream().collect(Collectors.toMap(DictItem::getItemValue, Function.identity())));
	}

	/**
	 * 添加字典项
	 *
	 * @param dictItem 字典项
	 * @author yang.lu
	 */
	public void putItem(DictItem dictItem) {
		if (null == dictItem) {
			return;
		}

		RMapCache<String, DictItem> itemMap = redissonClient.getMapCache(KEY_PREFIX + dictItem.getDictCode());
		itemMap.put(dictItem.getItemValue(), dictItem);
	}

	/**
	 * 删除字典项
	 *
	 * @param dictCode  字典码
	 * @param itemValue 字典值
	 * @author yang.lu
	 */
	public void removeItem(String dictCode, String itemValue) {
		redissonClient.getMapCache(KEY_PREFIX + dictCode).fastRemove(itemValue);
	}

	/**
	 * 删除字典
	 *
	 * @param dictCode 字典码
	 * @author yang.lu
	 */
	public void removeDict(String dictCode) {
		redissonClient.getMapCache(KEY_PREFIX + dictCode).delete();
	}
}
