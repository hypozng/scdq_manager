package com.scdq.manager.repository.controller;

import java.util.List;

import com.scdq.manager.common.ResponseData;
import com.scdq.manager.repository.model.CommodityCategory;
import com.scdq.manager.repository.model.Commodity;
import com.scdq.manager.repository.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scdq.manager.repository.model.CommodityBrand;

@RestController
@RequestMapping("/repository")
public class RepositoryController {
	@Autowired
	private RepositoryService repositoryService;
	
	@RequestMapping("/getCategories")
	public List<CommodityCategory> getCategories() {
		return repositoryService.getCategories();
	}
	
	@RequestMapping("/saveCategory")
	public ResponseData<CommodityCategory> saveCategory(CommodityCategory category) {
		return repositoryService.saveCategory(category);
	}

	@RequestMapping("/deleteCategory")
	public ResponseData<CommodityCategory> deleteCategory(String id) {
		return repositoryService.deleteCategory(id);
	}
	
	@RequestMapping("/getBrands")
	public List<CommodityBrand> getBrands() {
		return repositoryService.getBrands();
	}
	
	@RequestMapping("/saveBrand")
	public ResponseData<CommodityBrand> saveBrand(CommodityBrand brand) {
		return repositoryService.saveBrand(brand);
	}

	@RequestMapping("/deleteBrand")
	public ResponseData<CommodityBrand> deleteBrand(String id) {
		return repositoryService.deleteBrand(id);
	}
	
	@RequestMapping("/getCommoditiesByBrand")
	public List<Commodity> getCommoditiesByBrand(String brandId) {
		return repositoryService.getCommoditiesByBrand(brandId);
	}
	
	@RequestMapping("/getCommoditiesByCategory")
	public List<Commodity> getGoodsByCategory(String categoryId) {
		return repositoryService.getCommoditiesByCategory(categoryId);
	}
	
	@RequestMapping("/saveCommodity")
	public ResponseData<Commodity> saveCommodity(Commodity commodity) {
		return repositoryService.saveCommodity(commodity);
	}

	@RequestMapping("/storeCommodity")
	public boolean storeCommodity(String commodityId, int count) {
		return repositoryService.storeCommodity(commodityId, count);
	}
}
