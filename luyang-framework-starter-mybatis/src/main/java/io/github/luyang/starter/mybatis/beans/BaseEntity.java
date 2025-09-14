package io.github.luyang.starter.mybatis.beans;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 *
 * @author yang.lu
 */
@Getter
@Setter
public class BaseEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = -6552230978610685185L;

	/** 创建人 */
	private String createdBy;

	/** 创建时间 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createdTime;

	/** 最后更新人 */
	private String updatedBy;

	/** 更新时间 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updatedTime;

	/** 逻辑删除: [删除:true] [未删除:false] */
	@TableLogic
	private Boolean deleted;
}
