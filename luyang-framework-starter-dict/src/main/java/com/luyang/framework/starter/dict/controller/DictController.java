package com.luyang.framework.starter.dict.controller;

import com.luyang.framework.starter.base.api.Result;
import com.luyang.framework.starter.dict.model.DictItem;
import com.luyang.framework.starter.dict.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yang.lu
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dict")
public class DictController {

	private final DictService dictService;

	@PostMapping("/item")
	public Result<Void> createItem(@RequestBody DictItem dictItem) {
		dictService.createItem(dictItem);
		return Result.success();
	}

	@GetMapping("/{dict-code}/items")
	public Result<List<DictItem>> getItems(@PathVariable("dict-code") String dictCode) {
		return Result.success(dictService.getItems(dictCode));
	}
}
