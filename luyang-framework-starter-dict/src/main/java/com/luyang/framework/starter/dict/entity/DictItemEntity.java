package com.luyang.framework.starter.dict.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yang.lu
 */
@Getter
@Setter
public class DictItemEntity {

	private String id;
	private String dictType;
	private String itemName;
	private String itemValue;
	private int itemSort;
	private String remark;
}
