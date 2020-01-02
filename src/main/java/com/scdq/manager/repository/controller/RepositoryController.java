package com.scdq.manager.repository.controller;

import java.util.List;

import com.scdq.manager.repository.model.CommodityCategory;
import com.scdq.manager.repository.model.Commodity;
import com.scdq.manager.repository.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scdq.manager.repository.model.CommodityBrand;

@RestController
@RequestMapping("/goods")
public class RepositoryController {
	@Autowired
	private RepositoryService repositoryService;
	
	@RequestMapping("/getCategories")
	public List<CommodityCategory> getCategories() {
		return repositoryService.getCategories();
	}
	
	@RequestMapping("/addCategory")
	public long addCategory(CommodityCategory category) {
		return repositoryService.addCategory(category);
	}
	
	@RequestMapping("/updateCategory")
	public boolean updateCategory(CommodityCategory category) {
		return repositoryService.updateCategory(category);
	}
	
	@RequestMapping("/deleteCategory")
	public boolean deleteCategory(long categoryId) {
		return repositoryService.deleteCategory(categoryId);
	}
	
	@RequestMapping("/getBrands")
	public List<CommodityBrand> getBrands() {
		return repositoryService.getBrands();
	}
	
	@RequestMapping("/addBrand")
	public long addBrand(CommodityBrand brand) {
		return repositoryService.addBrand(brand);
	}
	
	@RequestMapping("/updateBrand")
	public boolean updateBrand(CommodityBrand brand) {
		return repositoryService.updateBrand(brand);
	}
	
	@RequestMapping("/deleteBrand")
	public boolean deleteBrand(long brandId) {
		return repositoryService.deleteBrand(brandId);
	}
	
	@RequestMapping("/getCommoditiesByBrand")
	public List<Commodity> getCommoditiesByBrand(int brandId) {
		return repositoryService.getCommoditiesByBrand(brandId);
	}
	
	@RequestMapping("/getCommoditiesByCategory")
	public List<Commodity> getGoodsByCategory(int categoryId) {
		return repositoryService.getCommoditiesByCategory(categoryId);
	}
	
	@RequestMapping("/addCommodity")
	public long addCommodity(Commodity commodity) {
		return repositoryService.addCommodity(commodity);
	}
	
	@RequestMapping("/updateCommodity")
	public long updateCommodity(Commodity commodity) {
		return repositoryService.updateCommodity(commodity);
	}
	
	@RequestMapping("/storeCommodity")
	public boolean storeCommodity(long commodityId, int count) {
		return repositoryService.storeCommodity(commodityId, count);
	}
}
