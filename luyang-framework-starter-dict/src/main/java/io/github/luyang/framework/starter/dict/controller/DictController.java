package io.github.luyang.framework.starter.dict.controller;

import io.github.luyang.framework.starter.base.api.Result;
import io.github.luyang.framework.starter.dict.model.Dict;
import io.github.luyang.framework.starter.dict.model.DictItem;
import io.github.luyang.framework.starter.dict.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@PostMapping
	public Result<Void> createDict(@RequestBody Dict dict) {
		dictService.createDict(dict);
		return Result.success();
	}

	@PostMapping("/item")
	public Result<Void> createItem(@RequestBody DictItem dictItem) {
		dictService.createItem(dictItem);
		return Result.success();
	}

	@DeleteMapping("/item")
	public Result<Void> deleteItem(@RequestParam String dictCode, @RequestParam String itemValue) {
		dictService.deleteItem(dictCode, itemValue);
		return Result.success();
	}

	@GetMapping("/{dict-code}/items")
	public Result<List<DictItem>> getItems(@PathVariable("dict-code") String dictCode) {
		return Result.success(dictService.getItems(dictCode));
	}
}
