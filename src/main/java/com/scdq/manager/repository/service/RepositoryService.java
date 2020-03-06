package com.scdq.manager.repository.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scdq.manager.common.ResponseData;
import com.scdq.manager.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scdq.manager.repository.dao.CommodityBrandDao;
import com.scdq.manager.repository.dao.CommodityCategoryDao;
import com.scdq.manager.repository.dao.CommodityDao;
import com.scdq.manager.repository.model.CommodityBrand;
import com.scdq.manager.repository.model.CommodityCategory;
import com.scdq.manager.repository.model.Commodity;

@Service
public class RepositoryService {

	@Autowired
	private CommodityDao commodityDao;
	
	@Autowired
	private CommodityBrandDao brandDao;
	
	@Autowired
	private CommodityCategoryDao categoryDao;
	
	/**
	 * 获取商品种类列表
	 * @return
	 */
	public List<CommodityCategory> getCategories() {
		return categoryDao.findAll();
	}
	
	/**
	 * 保存商品类别信息
	 * @param category
	 * @return
	 */
	public ResponseData<CommodityCategory> saveCategory(CommodityCategory category) {
		if (category == null) {
			return new ResponseData<>("信息不能为空");
		}
		int rows = StringUtils.isNullOrEmpty(category.getId())
				? categoryDao.insert(category)
				: categoryDao.update(category);
		if (rows == 0) {
			return new ResponseData<>("数据库操作失败");
		}
		return new ResponseData<>(category);
	}
	
	/**
	 * 删除商品类别信息
	 * @param id
	 * @return
	 */
	public ResponseData<CommodityCategory> deleteCategory(String id) {
		if (StringUtils.isNullOrEmpty(id)) {
			return new ResponseData<>("信息不能为空");
		}
		CommodityCategory category = categoryDao.get(id);
		if (category == null) {
			return new ResponseData<>("未找到相关数据");
		}
		int rows = categoryDao.delete(category.getId());
		if (rows == 0) {
			return new ResponseData<>("数据库操作失败");
		}
		return new ResponseData<>(category);
	}
	
	/**
	 * 获取商品品牌
	 * @return 
	 */
	public List<CommodityBrand> getBrands() {
		return brandDao.findAll();
	}
	
	/**
	 * 保存品牌信息
	 * @param brand
	 * @return
	 */
	public ResponseData<CommodityBrand> saveBrand(CommodityBrand brand) {
		if (brand == null) {
			return new ResponseData<>("数据不能为空");
		}
		int rows = StringUtils.isNullOrEmpty(brand.getId())
				? brandDao.insert(brand)
				: brandDao.update(brand);
		if (rows == 0) {
			return new ResponseData<>("数据库操作失败");
		}
		return new ResponseData<>(brand);
	}
	
	/**
	 * 删除商品品牌
	 * @param id
	 * @return
	 */
	public ResponseData<CommodityBrand> deleteBrand(String id) {
		if (StringUtils.isNullOrEmpty(id)) {
			return new ResponseData<>("信息不能为空");
		}
		CommodityBrand brand = brandDao.get(id);
		if (brand == null) {
			return new ResponseData<>("未找到相关信息");
		}
		int rows = brandDao.delete(brand.getId());
		if (rows == 0) {
			return new ResponseData<>("数据库操作失败");
		}
		return new ResponseData<>(brand);
	}
	
	/**
	 * 根据品牌ID获取该品牌下的商品列表
	 * @param brandId 品牌ID
	 * @return
	 */
	public List<Commodity> getCommoditiesByBrand(String brandId) {
        List<Commodity> commodities = commodityDao.findByBrand(brandId);
        fillBrands(commodities);
        fillCategories(commodities);
		return commodityDao.findByBrand(brandId);
	}
	
	/**
	 * 根据类别ID获取该类别下所有商品
	 * @param categoryId
	 * @return
	 */
	public List<Commodity> getCommoditiesByCategory(String categoryId) {
		List<Commodity> commodities = commodityDao.findByCategory(categoryId);
		fillBrands(commodities);
        fillCategories(commodities);
		return commodities;
	}
	
	/**
	 * 填充品牌信息
	 * @param commodities
	 * @return
	 */
	public List<Commodity> fillBrands(List<Commodity> commodities) {
		if (commodities == null) {
			return commodities;
		}
		Map<String, CommodityBrand> brands = new HashMap<>();
		for (CommodityBrand brand : brandDao.findAll()) {
			brands.put(brand.getId(), brand);
		}
		for (Commodity good : commodities) {
			if (good == null || good.getBrand() == null) {
				continue;
			}
			good.setBrand(brands.get(good.getBrand().getId()));
		}
		return commodities;
	}

    /**
     * 填充商品分类信息
     * @param commodities
     * @return
     */
    public List<Commodity> fillCategories(List<Commodity> commodities) {
        if (commodities == null) {
            return commodities;
        }
        Map<String, CommodityCategory> categories = new HashMap<>();
        for (CommodityCategory category : categoryDao.findAll()) {
            categories.put(category.getId(), category);
        }
        for (Commodity commodity : commodities) {
            if (commodity == null || commodity.getCategory() == null) {
                continue;
            }
            commodity.setCategory(categories.get(commodity.getCategory().getId()));
        }
        return commodities;
    }
	
	/**
	 * 根据商品ID获取商品信息
	 * @param id 商品ID
	 * @return
	 */
	public Commodity getCommodity(String id) {
		return commodityDao.get(id);
	}
	
	/**
	 * 添加商品信息
	 * @return
	 */
	public ResponseData<Commodity> saveCommodity(Commodity commodity) {
		if (commodity == null) {
			return new ResponseData<>("信息不能为空");
		}
		int rows = StringUtils.isNullOrEmpty(commodity.getId())
				? commodityDao.insert(commodity)
				: commodityDao.update(commodity);
		if (rows == 0) {
			return new ResponseData<>("数据库操作失败");
		}
		return new ResponseData<>(commodity);
	}
	
	/**
	 * 商品入库
	 * @param commodityId 需要入库的商品的ID
	 * @param count 入库数量
	 * @return 返回true表示入库成功，返回false表示入库失败
	 */
	public boolean storeCommodity(String commodityId, int count) {
		if (count == 0) {
			return false;
		}
		Commodity commodity = commodityDao.get(commodityId);
		if (commodity == null) {
			return false;
		}
		return commodityDao.modifyCount(commodityId, commodity.getCount() + count) > 0;
	}
}
