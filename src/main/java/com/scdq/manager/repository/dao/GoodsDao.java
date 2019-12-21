package com.scdq.manager.repository.dao;

import java.util.List;

import com.scdq.manager.common.BasicDao;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.scdq.manager.repository.model.Goods;

@Repository
public interface GoodsDao extends BasicDao {
	String COLS = "category_id, brand_id, model, bar_code, property, purchase_price, sale_price, preferential_price, count, remark";
	
	String COLUMNS = "category_id as `category.id`, brand_id as `brand.id`, model, bar_code as barCode, property,"
			+ "purchase_price as purchasePrice, sale_price as salePrice, preferential_price"
			+ " as preferentialPrice, count, remark, " + COMMON_FIELDS;
	
	String TABLE = "goods";
	
	String SELECT_SQL = "select " + COLUMNS + " from " + TABLE + " where " + NOT_DELETED;
	
	@Select(SELECT_SQL)
	List<Goods> findAll();
	
	@Select(SELECT_SQL + " and brand_id=#{brandId}")
	List<Goods> findByBrand(@Param("brandId") long brandId);
	
	@Select(SELECT_SQL + " and category_id=#{categoryId}")
	List<Goods> findByCategory(@Param("categoryId") long categoryId);
	
	@Select(SELECT_SQL + " and id=#{id}")
	Goods get(@Param("id")long id);
	
	@Insert("insert into " + TABLE + "(" + COLS + ") values(#{goods.category.id}, #{goods.brand.id}, #{goods.model}, #{goods.barCode}"
			+ "#{goods.property}, #{goods.purchasePrice}, #{goods.salePrice}, #{goods.preferentialPrice}, #{goods.count}, #{goods.remark})")
	int insert(@Param("goods") Goods goods);
	
	@Update("<script>update " + TABLE + " set update_time=now()"
			+ "<if test=\"goods.brand!=null and goods.brand.id!=0\">, brand_id=#{goods.brand.id}</if>"
			+ "<if test=\"goods.category!=null and goods.category.id!=0\">, category_id=#{goods.category.id}</if>"
			+ "<if test=\"goods.model!=null\">, model=#{goods.model}</if>"
			+ "<if test=\"goods.barCode!=null\">, bar_code=#{goods.barCode}</if>"
			+ "<if test=\"goods.property!=null\">, property=#{goods.property}</if>"
			+ "<if test=\"goods.purchasePrice!=null\">, purchase_price=#{goods.purchasePrice}</if>"
			+ "<if test=\"goods.salePrice!=null\">, sale_price=#{goods.salePrice}</if>"
			+ "<if test=\"goods.preferentialPrice!=null\">, preferential_price=#{goods.preferentialPrice}</if>"
			+ "<if test=\"goods.remark!=null\">, remark=#{goods.remark}</if>"
			+ " where id=#{goods.id}</script>")
	int update(@Param("goods") Goods goods);
	
	@Update("update " + TABLE + " set count=#{count} where id=#{goodsId}")
	int modifyCount(@Param("goodsId") long goodsId, @Param("count") int count);
	
	@Delete("update " + TABLE + " set " + HAS_DELETED + " where id=#{id}")
	int delete(@Param("id")int id);
}
