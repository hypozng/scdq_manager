package com.scdq.manager.repository.controller;

import java.util.List;

import com.scdq.manager.repository.model.Category;
import com.scdq.manager.repository.model.Goods;
import com.scdq.manager.repository.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scdq.manager.repository.model.Brand;

@RestController
@RequestMapping("/goods")
public class GoodsController {
	@Autowired
	private GoodsService goodsService;
	
	@RequestMapping("/getCategories")
	public List<Category> getCategories() {
		return goodsService.getCategories();
	}
	
	@RequestMapping("/addCategory")
	public long addCategory(Category category) {
		return goodsService.addCategory(category);
	}
	
	@RequestMapping("/updateCategory")
	public boolean updateCategory(Category category) {
		return goodsService.updateCategory(category);
	}
	
	@RequestMapping("/deleteCategory")
	public boolean deleteCategory(long categoryId) {
		return goodsService.deleteCategory(categoryId);
	}
	
	@RequestMapping("/getBrands")
	public List<Brand> getBrands() {
		return goodsService.getBrands();
	}
	
	@RequestMapping("/addBrand")
	public long addBrand(Brand brand) {
		return goodsService.addBrand(brand);
	}
	
	@RequestMapping("/updateBrand")
	public boolean updateBrand(Brand brand) {
		return goodsService.updateBrand(brand);
	}
	
	@RequestMapping("/deleteBrand")
	public boolean deleteBrand(long brandId) {
		return goodsService.deleteBrand(brandId);
	}
	
	@RequestMapping("/getGoodsesByBrand")
	public List<Goods> getGoodsesByBrand(int brandId) {
		return goodsService.getGoodsesByBrand(brandId);
	}
	
	@RequestMapping("/getGoodsesByCategory")
	public List<Goods> getGoodsesByCategory(int categoryId) {
		return goodsService.getGoodsesByCategory(categoryId);
	}
	
	@RequestMapping("/addGoods")
	public long addGoods(Goods goods) {
		return goodsService.addGoods(goods);
	}
	
	@RequestMapping("/updateGoods")
	public long updateGoods(Goods goods) {
		return goodsService.updateGoods(goods);
	}
	
	@RequestMapping("/storeGoods")
	public boolean storeGoods(long goodsId, int count) {
		return goodsService.storeGoods(goodsId, count);
	}
}
