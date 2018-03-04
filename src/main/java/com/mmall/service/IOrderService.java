package com.mmall.service;

import java.util.Map;

import com.mmall.common.ServerResponse;

public interface IOrderService {

	ServerResponse pay(Long orderNo, Integer userId, String path);

	ServerResponse aliCallback(Map<String, String> params);

}
