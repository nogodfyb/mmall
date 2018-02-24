package com.mmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mmall.pojo.Cart;

public interface CartMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Cart record);

	int insertSelective(Cart record);

	Cart selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Cart record);

	int updateByPrimaryKey(Cart record);

	Cart selectCartByUserIdProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

	List<Cart> selectCartByUserId(Integer userId);

	int selectCartProductCheckedStatusByUserId(Integer userId);

	int deleteByUserIdProductIds(@Param("userId") Integer userId, @Param("productIdList") List<String> productIdList);

	int checkedOrUncheckedProduct(@Param("userId") Integer userId, @Param("productId") Integer productId,
			@Param("checked") Integer checked);

	Integer selectCartProductCount(Integer userId);
}