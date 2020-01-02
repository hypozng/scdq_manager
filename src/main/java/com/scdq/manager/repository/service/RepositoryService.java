package com.scdq.manager.repository.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private CommodityBrandDao commodityBrandDao;
	
	@Autowired
	private CommodityCategoryDao CommodityCategoryDao;
	
	/**
	 * 获取商品种类列表
	 * @return
	 */
	public List<CommodityCategory> getCategories() {
		return CommodityCategoryDao.findAll();
	}
	
	/**
	 * 添加商品类别
	 * @param category
	 * @return
	 */
	public long addCategory(CommodityCategory category) {
		return CommodityCategoryDao.insert(category);
	}
	
	/**
	 * 修改商品类别
	 * @param category
	 * @return
	 */
	public boolean updateCategory(CommodityCategory category) {
		return CommodityCategoryDao.update(category) > 0;
	}
	
	/**
	 * 删除商品类别
	 * @param categoryId
	 * @return
	 */
	public boolean deleteCategory(long categoryId) {
		return CommodityCategoryDao.delete(categoryId) > 0;
	}
	
	/**
	 * 获取商品品牌
	 * @return 
	 */
	public List<CommodityBrand> getBrands() {
		return commodityBrandDao.findAll();
	}
	
	/**
	 * 新增商品品牌
	 * @param brand
	 * @return
	 */
	public long addBrand(CommodityBrand brand) {
		return commodityBrandDao.insert(brand);
	}
	
	/**
	 * 修改商品品牌
	 * @param brand
	 * @return
	 */
	public boolean updateBrand(CommodityBrand brand) {
		return commodityBrandDao.update(brand) > 0;
	}
	
	/**
	 * 删除商品品牌
	 * @param brandId
	 * @return
	 */
	public boolean deleteBrand(long brandId) {
		return commodityBrandDao.delete(brandId) > 0;
	}
	
	/**
	 * 根据品牌ID获取该品牌下的商品列表
	 * @param brandId 品牌ID
	 * @return
	 */
	public List<Commodity> getCommoditiesByBrand(long brandId) {
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
	public List<Commodity> getCommoditiesByCategory(long categoryId) {
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
		Map<Long, CommodityBrand> brands = new HashMap<>();
		for (CommodityBrand brand : commodityBrandDao.findAll()) {
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
        Map<Long, CommodityCategory> categories = new HashMap<>();
        for (CommodityCategory category : CommodityCategoryDao.findAll()) {
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
	public Commodity getCommodity(long id) {
		return commodityDao.get(id);
	}
	
	/**
	 * 添加商品信息
	 * @return
	 */
	public long addCommodity(Commodity goods) {
		if (goods.getBrand() != null && goods.getBrand().getId() == 0) {
			goods.setBrand(null);
		}
		return commodityDao.insert(goods);
	}
	
	/**
	 * 修改商品信息
	 * @param commodity
	 * @return
	 */
	public long updateCommodity(Commodity commodity) {
		if (commodity == null) {
			return 0;
		}
		return commodityDao.update(commodity);
	}
	
	/**
	 * 商品入库
	 * @param commodityId 需要入库的商品的ID
	 * @param count 入库数量
	 * @return 返回true表示入库成功，返回false表示入库失败
	 */
	public boolean storeCommodity(long commodityId, int count) {
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
