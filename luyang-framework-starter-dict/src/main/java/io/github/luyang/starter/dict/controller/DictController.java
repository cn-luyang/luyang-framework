package io.github.luyang.starter.dict.controller;

import io.github.luyang.starter.base.common.model.Result;
import io.github.luyang.starter.dict.model.Dict;
import io.github.luyang.starter.dict.model.DictItem;
import io.github.luyang.starter.dict.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
