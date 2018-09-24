package com.victor.wang.bigCrab.sharedObject.lov;

public enum CardStatus
{
	/**
	 * 未使用
	 */
	UNUSED,
	/**
	 * 用户已兑换，已填写物流信息
	 */
	REDEEMED,
	/**
	 * 快递员已发货
	 */
	DELIVERED,
	/**
	 * 收货人已收货
	 */
	RECEIVED,
	/**
	 * 卡被冻结
	 */
	FROZEN,
	/**
	 * 已被电话预约
	 */
	PHONED;

//	public static String getName(CardStatus status){
//		if(status == null){
//			return "";
//		}
//		if(status == CardStatus.UNUSED)
//	}
}
