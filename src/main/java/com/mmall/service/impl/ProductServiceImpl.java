package com.mmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductListVo;

@Service
public class ProductServiceImpl implements IProductService {
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private ProductMapper productMapper;
	
	@SuppressWarnings("rawtypes")
	@Override
	public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum,
			int pageSize, String orderBy) {
		if (StringUtils.isBlank(keyword) && categoryId == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
					ResponseCode.ILLEGAL_ARGUMENT.getDesc());
		}
		List<Integer> categoryIdList = new ArrayList<Integer>();

		if (categoryId != null) {
			Category category = categoryMapper.selectByPrimaryKey(categoryId);
			if (category == null && StringUtils.isBlank(keyword)) {
				// 没有该分类,并且还没有关键字,这个时候返回一个空的结果集,不报错
				PageHelper.startPage(pageNum, pageSize);
				List<ProductListVo> productListVoList = Lists.newArrayList();

				PageInfo pageInfo = new PageInfo(productListVoList);
				return ServerResponse.createBySuccess(pageInfo);
			}
			categoryIdList = (List<Integer>) categoryService.selectCategoryAndChildrenById(category.getId()).getData();
		}
		if (StringUtils.isNotBlank(keyword)) {
			keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
		}

		PageHelper.startPage(pageNum, pageSize);
		// 排序处理
		if (StringUtils.isNotBlank(orderBy)) {
			if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
				String[] orderByArray = orderBy.split("_");
				PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
			}
		}
		List<Product> productList = productMapper.selectByNameAndCategoryIds(
				StringUtils.isBlank(keyword) ? null : keyword, categoryIdList.size() == 0 ? null : categoryIdList);

		List<ProductListVo> productListVoList = Lists.newArrayList();
		for (Product product : productList) {
			ProductListVo productListVo = assembleProductListVo(product);
			productListVoList.add(productListVo);
		}

		PageInfo pageInfo = new PageInfo(productList);
		pageInfo.setList(productListVoList);
		return ServerResponse.createBySuccess(pageInfo);
	}
	
    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }

}
