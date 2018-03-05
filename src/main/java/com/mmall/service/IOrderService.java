package com.mmall.service;

import java.util.Map;

import com.mmall.common.ServerResponse;

public interface IOrderService {

	ServerResponse pay(Long orderNo, Integer userId, String path);

	ServerResponse aliCallback(Map<String, String> params);

	ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);

	ServerResponse createOrder(Integer userId, Integer shippingId);

}
