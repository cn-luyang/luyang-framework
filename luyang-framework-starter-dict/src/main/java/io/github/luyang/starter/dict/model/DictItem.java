package io.github.luyang.starter.dict.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yang.lu
 */
@Getter
@Setter
public class DictItem{

    private String id;
    private String dictCode;
    private String itemName;
    private String itemValue;
    private int sortOrder;
    private String remark;
}
