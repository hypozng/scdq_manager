package com.scdq.manager.repository.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scdq.manager.repository.dao.BrandDao;
import com.scdq.manager.repository.dao.CategoryDao;
import com.scdq.manager.repository.dao.GoodsDao;
import com.scdq.manager.repository.model.Brand;
import com.scdq.manager.repository.model.Category;
import com.scdq.manager.repository.model.Goods;

@Service
public class GoodsService {

	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private BrandDao brandDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	/**
	 * 获取商品种类列表
	 * @return
	 */
	public List<Category> getCategories() {
		return categoryDao.findAll();
	}
	
	/**
	 * 添加商品类别
	 * @param category
	 * @return
	 */
	public long addCategory(Category category) {
		return categoryDao.insert(category);
	}
	
	/**
	 * 修改商品类别
	 * @param category
	 * @return
	 */
	public boolean updateCategory(Category category) {
		return categoryDao.update(category) > 0;
	}
	
	/**
	 * 删除商品类别
	 * @param categoryId
	 * @return
	 */
	public boolean deleteCategory(long categoryId) {
		return categoryDao.delete(categoryId) > 0;
	}
	
	/**
	 * 获取商品品牌
	 * @return 
	 */
	public List<Brand> getBrands() {
		return brandDao.findAll();
	}
	
	/**
	 * 新增商品品牌
	 * @param brand
	 * @return
	 */
	public long addBrand(Brand brand) {
		return brandDao.insert(brand);
	}
	
	/**
	 * 修改商品品牌
	 * @param brand
	 * @return
	 */
	public boolean updateBrand(Brand brand) {
		return brandDao.update(brand) > 0;
	}
	
	/**
	 * 删除商品品牌
	 * @param brandId
	 * @return
	 */
	public boolean deleteBrand(long brandId) {
		return brandDao.delete(brandId) > 0;
	}
	
	/**
	 * 根据品牌ID获取该品牌下的商品列表
	 * @param brandId 品牌ID
	 * @return
	 */
	public List<Goods> getGoodsesByBrand(long brandId) {
		return goodsDao.findByBrand(brandId);
	}
	
	/**
	 * 根据类别ID获取该类别下所有商品
	 * @param categoryId
	 * @return
	 */
	public List<Goods> getGoodsesByCategory(long categoryId) {
		List<Goods> list = goodsDao.findByCategory(categoryId);
		fillBrands(list);
		return list;
	}
	
	/**
	 * 填充品牌信息
	 * @param goodses
	 * @return
	 */
	public List<Goods> fillBrands(List<Goods> goodses) {
		if (goodses == null) {
			return goodses;
		}
		Map<Long, Brand> brands = new HashMap<Long, Brand>();
		for (Brand brand : brandDao.findAll()) {
			brands.put(brand.getId(), brand);
		}
		for (Goods goods : goodses) {
			if (goods == null || goods.getBrand() == null) {
				continue;
			}
			goods.setBrand(brands.get(goods.getBrand().getId()));
		}
		return goodses;
	}
	
	/**
	 * 根据商品ID获取商品信息
	 * @param id 商品ID
	 * @return
	 */
	public Goods getGoods(long id) {
		return goodsDao.get(id);
	}
	
	/**
	 * 添加商品信息
	 * @return
	 */
	public long addGoods(Goods goods) {
		if (goods.getBrand() != null && goods.getBrand().getId() == 0) {
			goods.setBrand(null);
		}
		return goodsDao.insert(goods);
	}
	
	/**
	 * 修改商品信息
	 * @param goods
	 * @return
	 */
	public long updateGoods(Goods goods) {
		if (goods == null) {
			return 0;
		}
		return goodsDao.update(goods);
	}
	
	/**
	 * 商品入库
	 * @param goodsId 需要入库的商品的ID
	 * @param count 入库数量
	 * @return 返回true表示入库成功，返回false表示入库失败
	 */
	public boolean storeGoods(long goodsId, int count) {
		if (count == 0) {
			return false;
		}
		Goods goods = goodsDao.get(goodsId);
		if (goods == null) {
			return false;
		}
		return goodsDao.modifyCount(goodsId, goods.getCount() + count) > 0;
	}
}
