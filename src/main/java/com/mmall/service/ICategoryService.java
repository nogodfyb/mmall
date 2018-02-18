package com.mmall.service;

import java.util.List;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

public interface ICategoryService {

	ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

	ServerResponse addCategory(String categoryName, Integer parentId);

	ServerResponse updateCategoryName(Integer categoryId, String categoryName);

}
