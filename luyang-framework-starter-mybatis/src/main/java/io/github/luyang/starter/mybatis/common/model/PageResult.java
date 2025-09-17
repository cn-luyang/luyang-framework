package io.github.luyang.starter.mybatis.common.model;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页结果
 *
 * @author yang.lu
 */
public class PageResult<T> implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/** 总记录数 */
	private long total;

	/** 数据列表 */
	private List<T> rows;

	public PageResult(long total, List<T> rows) {
		this.rows = rows;
		this.total = total;
	}

	public PageResult(Long total) {
		this.rows = new ArrayList<>();
		this.total = total;
	}

	public static <T> PageResult<T> empty() {
		return new PageResult<>(0L);
	}

	public static <T> PageResult<T> empty(Long total) {
		return new PageResult<>(total);
	}

	public static <T> PageResult<T> build(IPage<T> page) {
		return new PageResult<>(page.getTotal(), page.getRecords());
	}

	public static <T> PageResult<T> build(List<T> list) {
		return new PageResult<>(list.size(), list);
	}

	public static <T> PageResult<T> build(List<T> list, Long total) {
		return new PageResult<>(total, list);
	}

	public static <T, S> PageResult<T> build(IPage<S> page, Function<S, T> convertor) {
		List<S> source = Optional.ofNullable(page.getRecords()).orElse(Collections.emptyList());
		List<T> target = source.stream().map(convertor).collect(Collectors.toList());
		return new PageResult<>(page.getTotal(), target);
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
