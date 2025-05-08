//package com.luyang.framework.starter.dict.cache;
//
//import cn.hutool.core.collection.CollUtil;
//import com.luyang.framework.starter.dict.model.DictItem;
//import org.redisson.api.RMapCache;
//import org.redisson.api.RedissonClient;
//
//import java.util.List;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
///**
// * @author yang.lu
// */
//public class DictCache {
//
//	private final RedissonClient redissonClient;
//
//	private static final String KEY_PREFIX = "dict:";
//
//	public DictCache(RedissonClient redissonClient) {
//		this.redissonClient = redissonClient;
//	}
//
//	public List<DictItem> findItems(String dictCode) {
//		RMapCache<String, DictItem> itemMap = redissonClient.getMapCache(KEY_PREFIX + dictCode);
//		return itemMap.readAllValues().stream().toList();
//	}
//
//	public void putItems(String dictCode, List<DictItem> dictItems) {
//		if (CollUtil.isEmpty(dictItems)) {
//			return;
//		}
//		RMapCache<String, DictItem> itemMap = redissonClient.getMapCache(KEY_PREFIX + dictCode);
//		itemMap.putAll(dictItems.stream().collect(Collectors.toMap(DictItem::getItemValue, Function.identity())));
//	}
//
//	public void putItem(String dictCode, DictItem dictItem) {
//		if (null == dictItem) {
//			return;
//		}
//
//		RMapCache<String, DictItem> itemMap = redissonClient.getMapCache(KEY_PREFIX + dictCode);
//		itemMap.put(dictItem.getItemValue(), dictItem);
//	}
//
//	public void removeItem(String dictCode, String itemValue) {
//		redissonClient.getMapCache(KEY_PREFIX + dictCode).remove(itemValue);
//	}
//
//	public void removeDict(String dictCode) {
//		redissonClient.getMapCache(KEY_PREFIX + dictCode).delete();
//	}
//}
