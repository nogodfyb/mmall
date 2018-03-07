package com.mmall.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.vo.OrderVo;

public interface IOrderService {

	ServerResponse pay(Long orderNo, Integer userId, String path);

	ServerResponse aliCallback(Map<String, String> params);

	ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);

	ServerResponse createOrder(Integer userId, Integer shippingId);

	ServerResponse getOrderCartProduct(Integer userId);

	ServerResponse getOrderList(Integer userId, int pageNum, int pageSize);

	ServerResponse getOrderDetail(Integer userId, Long orderNo);

	ServerResponse cancel(Integer userId, Long orderNo);

	ServerResponse<PageInfo> manageList(int pageNum, int pageSize);

	ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize);

	ServerResponse<OrderVo> manageDetail(Long orderNo);

	ServerResponse<String> manageSendGoods(Long orderNo);

}
