package com.scdq.manager.repository.dao;

import java.util.List;

import com.scdq.manager.common.BasicDao;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.scdq.manager.repository.model.Commodity;

@Repository
public interface CommodityDao extends BasicDao {
    String TABLE = "commodity";

    String INSERT_COLS = "category_id, brand_id, model, bar_code, property, purchase_price, sale_price, preferential_price, count, remark";
	
	String SELECT_COLS = "category_id as `category.id`, brand_id as `brand.id`, model, bar_code as barCode, property,"
			+ "purchase_price as purchasePrice, sale_price as salePrice, preferential_price"
			+ " as preferentialPrice, count, remark, " + COMMON_FIELDS;

	String SELECT_SQL = "select " + SELECT_COLS + " from " + TABLE + " where " + NOT_DELETED;
	
	@Select(SELECT_SQL)
	List<Commodity> findAll();

    @Select(SELECT_SQL + " and id=#{id}")
    Commodity get(@Param("id") long id);

    @Delete("update " + TABLE + " set " + HAS_DELETED + " where id=#{id}")
    int delete(@Param("id") int id);

    @Insert("insert into " + TABLE + "(" + INSERT_COLS + ") values(#{commodity.category.id}, #{commodity.brand.id}, #{commodity.model}, #{commodity.barCode}"
            + "#{commodity.property}, #{commodity.purchasePrice}, #{commodity.salePrice}, #{commodity.preferentialPrice}, #{commodity.count}, #{commodity.remark})")
    int insert(@Param("commodity") Commodity commodity);

    @Update("<script>update " + TABLE + " set update_time=now()"
            + "<if test=\"commodity.brand!=null and commodity.brand.id!=0\">, brand_id=#{commodity.brand.id}</if>"
            + "<if test=\"commodity.category!=null and commodity.category.id!=0\">, category_id=#{commodity.category.id}</if>"
            + "<if test=\"commodity.model!=null\">, model=#{commodity.model}</if>"
            + "<if test=\"commodity.barCode!=null\">, bar_code=#{commodity.barCode}</if>"
            + "<if test=\"commodity.property!=null\">, property=#{commodity.property}</if>"
            + "<if test=\"commodity.purchasePrice!=null\">, purchase_price=#{commodity.purchasePrice}</if>"
            + "<if test=\"commodity.salePrice!=null\">, sale_price=#{commodity.salePrice}</if>"
            + "<if test=\"commodity.preferentialPrice!=null\">, preferential_price=#{commodity.preferentialPrice}</if>"
            + "<if test=\"commodity.remark!=null\">, remark=#{commodity.remark}</if>"
            + " where id=#{commodity.id}</script>")
    int update(@Param("commodity") Commodity commodity);

    @Select(SELECT_SQL + " and brand_id=#{brandId}")
	List<Commodity> findByBrand(@Param("brandId") long brandId);
	
	@Select(SELECT_SQL + " and category_id=#{categoryId}")
	List<Commodity> findByCategory(@Param("categoryId") long categoryId);
	
	@Update("update " + TABLE + " set count=#{count} where id=#{goodsId}")
	int modifyCount(@Param("goodsId") long goodsId, @Param("count") int count);
}
