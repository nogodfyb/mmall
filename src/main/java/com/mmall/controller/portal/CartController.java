package com.mmall.controller.portal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import com.mmall.vo.CartVo;

@Controller
@RequestMapping("cart/")
public class CartController {

	@Autowired
	private ICartService cartService;

	@RequestMapping("add.do")
	@ResponseBody
	public ServerResponse<CartVo> add(HttpSession session, Integer count, Integer productId) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
					ResponseCode.NEED_LOGIN.getDesc());
		}
		return cartService.add(user.getId(), productId, count);
	}

	@RequestMapping("update.do")
	@ResponseBody
	public ServerResponse<CartVo> update(HttpSession session, Integer count, Integer productId) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
					ResponseCode.NEED_LOGIN.getDesc());
		}
		return cartService.update(user.getId(), productId, count);
	}

	@RequestMapping("list.do")
	@ResponseBody
	public ServerResponse<CartVo> list(HttpSession session) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
					ResponseCode.NEED_LOGIN.getDesc());
		}
		return cartService.list(user.getId());
	}

	@RequestMapping("delete_product.do")
	@ResponseBody
	public ServerResponse<CartVo> deleteProduct(HttpSession session, String productIds) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
					ResponseCode.NEED_LOGIN.getDesc());
		}
		return cartService.deleteProduct(user.getId(), productIds);
	}

	@RequestMapping("select.do")
	@ResponseBody
	public ServerResponse<CartVo> select(HttpSession session, Integer productId) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
					ResponseCode.NEED_LOGIN.getDesc());
		}
		return cartService.selectOrUnSelect(user.getId(), productId, Const.Cart.CHECKED);
	}

	@RequestMapping("un_select.do")
	@ResponseBody
	public ServerResponse<CartVo> unSelect(HttpSession session, Integer productId) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
					ResponseCode.NEED_LOGIN.getDesc());
		}
		return cartService.selectOrUnSelect(user.getId(), productId, Const.Cart.UN_CHECKED);
	}

	@RequestMapping("get_cart_product_count.do")
	@ResponseBody
	public ServerResponse<Integer> getCartProductCount(HttpSession session) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createBySuccess(0);
		}
		return cartService.getCartProductCount(user.getId());
	}

	@RequestMapping("select_all.do")
	@ResponseBody
	public ServerResponse<CartVo> selectAll(HttpSession session) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
					ResponseCode.NEED_LOGIN.getDesc());
		}
		return cartService.selectOrUnSelect(user.getId(), null, Const.Cart.CHECKED);
	}
	
    @RequestMapping("un_select_all.do")
    @ResponseBody
    public ServerResponse<CartVo> unSelectAll(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(),null,Const.Cart.UN_CHECKED);
    }
}
