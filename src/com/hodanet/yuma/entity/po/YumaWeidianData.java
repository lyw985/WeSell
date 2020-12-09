package com.hodanet.yuma.entity.po;

import java.text.ParseException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;

import com.hodanet.common.util.DateConverterUtil;
import com.hodanet.common.util.StringUtil;
import com.hodanet.yuma.constant.SyncStatus;

@Entity
@Table(name = "yuma_weidian_data")
public class YumaWeidianData {

	private final static Logger logger = Logger.getLogger(YumaWeidianData.class);

	/** 主键 */
	@Id
	@GeneratedValue(generator = "incrementGenerator")
	@GenericGenerator(name = "incrementGenerator", strategy = "increment")
	private Integer id;

	// 订单编号 810210048495003
	@Column(name = "order_number")
	private String orderNumber;

	// 订单状态 已关闭，已完成
	@Column(name = "order_status")
	private String orderStatus;

	// 订单类型 担保交易
	@Column(name = "order_type")
	private String orderType;

	// 下单时间 2018-11-07 16:08:47
	@Column(name = "order_create_time")
	private Date orderCreateTime;

	// 付款时间
	@Column(name = "order_pay_time")
	private Date orderPayTime;

	// 商品名称 东海小眼睛油带鱼
	@Column(name = "item_name")
	private String itemName;

	// 商品型号 带鱼4条2.65斤
	@Column(name = "item_model_name")
	private String itemModelName;

	// 商品id 2208505590
	@Column(name = "item_id")
	private String itemId;

	// 商品编码
	@Column(name = "item_number")
	private String itemNumber;

	// 购买数量 4
	@Column(name = "item_count")
	private String itemCount;

	// 商品价格 0.01
	@Column(name = "item_price")
	private String itemModelPrice;

	// 商品积分
	@Column(name = "item_integral")
	private String itemIntegral;

	// 发货状态
	@Column(name = "send_status")
	private String sendStatus;

	// 退款状态
	@Column(name = "refund_status")
	private String refundStatus;

	// 退款金额 0.01
	@Column(name = "refund_price")
	private String refundPrice;

	// 客服指派退款
	@Column(name = "service_refund")
	private String serviceRefund;

	// 商品原价 0.01
	@Column(name = "original_price")
	private String originalPrice;

	// 运费退款 0.01
	@Column(name = "freight_refund")
	private String freightRefund;

	// 收件人姓名 张三
	@Column(name = "receiver_name")
	private String receiverName;

	// 收件人手机 1300000000
	@Column(name = "receiver_phone")
	private String receiverPhone;

	// 省 上海
	@Column(name = "receiver_province")
	private String receiverProvince;

	// 市 黄浦区
	@Column(name = "receiver_city")
	private String receiverCity;

	// 区 城区
	@Column(name = "receiver_area")
	private String receiverArea;

	// 收货详细地址 上海 黄浦区 城区 斜土路1号
	@Column(name = "receiver_address")
	private String receiverAddress;

	// 订单描述 2年左右放山老母鸡 老母鸡一只(净重3.8~4.5斤) [数量:1]
	@Column(name = "order_detail")
	private String orderDetail;

	// 买家留言
	@Column(name = "buyer_remark")
	private String buyerRemark;

	// 下单模板信息
	@Column(name = "order_template")
	private String orderTemplate;

	// 卖家留言
	@Column(name = "seller_remark")
	private String sellerRemark;

	// 分销商店铺ID
	@Column(name = "distribution_storeid")
	private String distributionStoreId;

	// 分销商注册姓名
	@Column(name = "distribution_name")
	private String distributionName;

	// 分销商手机号
	@Column(name = "distribution_phone")
	private String distributionPhone;

	// 下单账号
	@Column(name = "order_phone")
	private String orderPhone;

	// 是否已成团
	@Column(name = "group_status")
	private String groupStatus;

	// 身份证号
	@Column(name = "identification_card")
	private String identificationCard;

	// 支付方式
	@Column(name = "pay_type")
	private String payType;

	// 是否自提
	@Column(name = "self_mention")
	private String selfMention;

	// 数据状态
	@Column(name = "sync_status")
	private Integer syncStatus;

	public YumaWeidianData() {
	}

	public YumaWeidianData(String orderNumber, String orderStatus, String orderType, String orderCreateTimeStr,
			String orderPayTimeStr, String itemName, String itemModelName, String itemId, String itemNumber,
			String itemCount, String itemModelPrice, String itemIntegral, String sendStatus, String refundStatus,
			String refundPrice, String serviceRefund, String originalPrice, String freightRefund, String receiverName,
			String receiverPhone, String receiverProvince, String receiverCity, String receiverArea,
			String receiverAddress, String orderDetail, String buyerRemark, String orderTemplate, String sellerRemark,
			String distributionStoreId, String distributionName, String distributionPhone, String orderPhone,
			String groupStatus, String identificationCard, String payType, String selfMention) {
		this.orderNumber = orderNumber;
		this.orderStatus = orderStatus;
		this.orderType = orderType;
		try {
			if (StringUtil.isNotBlank(orderCreateTimeStr)) {
				this.orderCreateTime = DateConverterUtil.parse(orderCreateTimeStr, DateConverterUtil.yyyy_MM_ddHHmmss);
			}
			if (StringUtil.isNotBlank(orderPayTimeStr)) {
				this.orderPayTime = DateConverterUtil.parse(orderPayTimeStr, DateConverterUtil.yyyy_MM_ddHHmmss);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.itemName = itemName;
		this.itemModelName = itemModelName;
		this.itemId = itemId;
		this.itemNumber = itemNumber;
		this.itemCount = itemCount;
		this.itemModelPrice = itemModelPrice;
		this.itemIntegral = itemIntegral;
		this.sendStatus = sendStatus;
		this.refundStatus = refundStatus;
		this.refundPrice = refundPrice;
		this.serviceRefund = serviceRefund;
		this.originalPrice = originalPrice;
		this.freightRefund = freightRefund;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.receiverProvince = receiverProvince;
		this.receiverCity = receiverCity;
		this.receiverArea = receiverArea;
		this.receiverAddress = receiverAddress;
		this.orderDetail = orderDetail;
		this.buyerRemark = buyerRemark;
		this.orderTemplate = orderTemplate;
		this.sellerRemark = sellerRemark;
		this.distributionStoreId = distributionStoreId;
		this.distributionName = distributionName;
		this.distributionPhone = distributionPhone;
		this.orderPhone = orderPhone;
		this.groupStatus = groupStatus;
		this.identificationCard = identificationCard;
		this.payType = payType;
		this.selfMention = selfMention;
		this.syncStatus = SyncStatus.INIT.getValue();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public Date getOrderPayTime() {
		return orderPayTime;
	}

	public void setOrderPayTime(Date orderPayTime) {
		this.orderPayTime = orderPayTime;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemModelName() {
		return itemModelName;
	}

	public void setItemModelName(String itemModelName) {
		this.itemModelName = itemModelName;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getItemCount() {
		return itemCount;
	}

	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}

	public String getItemModelPrice() {
		return itemModelPrice;
	}

	public void setItemModelPrice(String itemModelPrice) {
		this.itemModelPrice = itemModelPrice;
	}

	public String getItemIntegral() {
		return itemIntegral;
	}

	public void setItemIntegral(String itemIntegral) {
		this.itemIntegral = itemIntegral;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(String refundPrice) {
		this.refundPrice = refundPrice;
	}

	public String getServiceRefund() {
		return serviceRefund;
	}

	public void setServiceRefund(String serviceRefund) {
		this.serviceRefund = serviceRefund;
	}

	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getFreightRefund() {
		return freightRefund;
	}

	public void setFreightRefund(String freightRefund) {
		this.freightRefund = freightRefund;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverProvince() {
		return receiverProvince;
	}

	public void setReceiverProvince(String receiverProvince) {
		this.receiverProvince = receiverProvince;
	}

	public String getReceiverCity() {
		return receiverCity;
	}

	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}

	public String getReceiverArea() {
		return receiverArea;
	}

	public void setReceiverArea(String receiverArea) {
		this.receiverArea = receiverArea;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(String orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getBuyerRemark() {
		return buyerRemark;
	}

	public void setBuyerRemark(String buyerRemark) {
		this.buyerRemark = buyerRemark;
	}

	public String getOrderTemplate() {
		return orderTemplate;
	}

	public void setOrderTemplate(String orderTemplate) {
		this.orderTemplate = orderTemplate;
	}

	public String getSellerRemark() {
		return sellerRemark;
	}

	public void setSellerRemark(String sellerRemark) {
		this.sellerRemark = sellerRemark;
	}

	public String getDistributionStoreId() {
		return distributionStoreId;
	}

	public void setDistributionStoreId(String distributionStoreId) {
		this.distributionStoreId = distributionStoreId;
	}

	public String getDistributionName() {
		return distributionName;
	}

	public void setDistributionName(String distributionName) {
		this.distributionName = distributionName;
	}

	public String getDistributionPhone() {
		return distributionPhone;
	}

	public void setDistributionPhone(String distributionPhone) {
		this.distributionPhone = distributionPhone;
	}

	public String getOrderPhone() {
		return orderPhone;
	}

	public void setOrderPhone(String orderPhone) {
		this.orderPhone = orderPhone;
	}

	public String getGroupStatus() {
		return groupStatus;
	}

	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}

	public String getIdentificationCard() {
		return identificationCard;
	}

	public void setIdentificationCard(String identificationCard) {
		this.identificationCard = identificationCard;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getSelfMention() {
		return selfMention;
	}

	public void setSelfMention(String selfMention) {
		this.selfMention = selfMention;
	}

	public Integer getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}

	public String toString() {
		return this.itemName + "\t" + this.itemModelName + "\t" + this.itemNumber + "\t" + this.itemCount + "\t"
				+ this.itemModelPrice + "\t" + this.itemIntegral + "\t" + this.sendStatus + "\t" + this.refundStatus
				+ "\t" + this.refundPrice + "\t" + this.serviceRefund + "\t" + this.originalPrice + "\t"
				+ this.freightRefund + "\t" + this.receiverName + "\t" + this.receiverPhone + "\t"
				+ this.receiverProvince + "\t" + this.receiverCity + "\t" + this.receiverArea + "\t"
				+ this.receiverAddress + "\t" + this.orderDetail + "\t" + this.buyerRemark + "\t" + this.orderTemplate
				+ "\t" + this.sellerRemark + "\t" + this.distributionStoreId + "\t" + this.distributionName + "\t"
				+ this.distributionPhone + "\t" + this.orderPhone + "\t" + this.groupStatus + "\t"
				+ this.identificationCard + "\t" + this.payType + "\t" + this.selfMention;
	}

	public static YumaWeidianData getYumaWeidianDataFromXlsData(String[] titleStrs, String[] values) {
		String orderNumber = null, orderStatus = null, orderType = null, orderCreateTimeStr = null,
				orderPayTimeStr = null, itemName = null, itemModelName = null, itemId = null, itemNumber = null,
				itemCount = null, itemModelPrice = null, itemIntegral = null, sendStatus = null, refundStatus = null,
				refundPrice = null, serviceRefund = null, originalPrice = null, freightRefund = null,
				receiverName = null, receiverPhone = null, receiverProvince = null, receiverCity = null,
				receiverArea = null, receiverAddress = null, orderDetail = null, buyerRemark = null,
				orderTemplate = null, sellerRemark = null, distributionStoreId = null, distributionName = null,
				distributionPhone = null, orderPhone = null, groupStatus = null, identificationCard = null,
				payType = null, selfMention = null;
		for (int i = 0; i < titleStrs.length; i++) {
			String title = titleStrs[i];
			String value = null;
			if (values.length > i) {
				value = values[i];
			}
			if (StringUtil.isNotBlank(value)) {
				value = value.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
			}
			if ("订单编号".equals(title)) {
				orderNumber = value;
				continue;
			}
			if ("订单状态".equals(title)) {
				orderStatus = value;
				continue;
			}
			if ("订单类型".equals(title)) {
				orderType = value;
				continue;
			}
			if ("下单时间".equals(title)) {
				orderCreateTimeStr = value;
				continue;
			}
			if ("付款时间".equals(title) || "支付时间".equals(title)) {
				orderPayTimeStr = value;
				continue;
			}
			if ("商品名称".equals(title)) {
				itemName = value;
				continue;
			}
			if ("商品型号".equals(title)) {
				itemModelName = value;
				continue;
			}
			if ("商品id".equals(title) || "商品ID".equals(title)) {
				itemId = value;
				continue;
			}
			if ("商品编码".equals(title)) {
				itemNumber = value;
				continue;
			}
			if ("购买数量".equals(title) || "商品件数".equals(title)) {
				itemCount = value;
				continue;
			}
			if ("商品价格".equals(title) || "单品折扣后价格".equals(title)) {
				itemModelPrice = value;
				continue;
			}
			if ("商品积分".equals(title)) {
				itemIntegral = value;
				continue;
			}
			if ("发货状态".equals(title)) {
				sendStatus = value;
				continue;
			}
			if ("退款状态".equals(title)) {
				refundStatus = value;
				continue;
			}
			if ("退款金额".equals(title)) {
				refundPrice = value;
				continue;
			}
			if ("客服指派退款".equals(title)) {
				serviceRefund = value;
				continue;
			}
			if ("商品原价".equals(title) || "商品原始单价".equals(title)) {
				originalPrice = value;
				continue;
			}
			if ("运费退款".equals(title)) {
				freightRefund = value;
				continue;
			}
			if ("收件人姓名".equals(title)||"收件人/提货人姓名".equals(title)) {
				receiverName = value;
				continue;
			}
			if ("收件人手机".equals(title)||"收件人/提货人手机号".equals(title)) {
				receiverPhone = value;
				continue;
			}
			if ("省".equals(title)) {
				receiverProvince = value;
				continue;
			}
			if ("市".equals(title)) {
				receiverCity = value;
				continue;
			}
			if ("区".equals(title)) {
				receiverArea = value;
				continue;
			}
			if ("收货详细地址".equals(title) || "收货/提货详细地址".equals(title)) {
				receiverAddress = value;
				continue;
			}
			if ("订单描述".equals(title)) {
				orderDetail = value;
				continue;
			}
			if ("买家留言".equals(title)) {
				buyerRemark = value;
				continue;
			}
			if ("下单模板信息".equals(title)) {
				orderTemplate = value;
				continue;
			}
			if ("备注".equals(title)) {
				sellerRemark = value;
				continue;
			}
			if ("分销商店铺ID".equals(title)) {
				distributionStoreId = value;
				continue;
			}
			if ("分销商注册姓名".equals(title)) {
				distributionName = value;
				continue;
			}
			if ("分销商手机号".equals(title)) {
				distributionPhone = value;
				continue;
			}
			if ("下单账号".equals(title)) {
				orderPhone = value;
				continue;
			}
			if ("是否已成团".equals(title)) {
				groupStatus = value;
				continue;
			}
			if ("身份证号".equals(title)) {
				identificationCard = value;
				continue;
			}
			if ("支付方式".equals(title)) {
				payType = value;
				continue;
			}
			if ("是否自提".equals(title)) {
				selfMention = value;
				continue;
			}
			if ("分销店铺名称".equals(title)) {
				continue;
			}
			if ("分销佣金".equals(title)) {
				continue;
			}
			logger.warn("--------------- 不存在对应的字段title:[" + title + "] -----------------------");
		}

		// 202006微店新版本文件更新
		if (itemModelName == null) {
			int rightIndex = itemName.lastIndexOf(")");
			int leftIndex = itemName.lastIndexOf("(");
			String back = itemName.substring(leftIndex + 1, rightIndex);
			String front = itemName.substring(0, leftIndex);
			while (back.lastIndexOf(")") != -1) {
				rightIndex = back.lastIndexOf(")") + leftIndex + 1;
				leftIndex = front.lastIndexOf("(");
				back =  itemName.substring(leftIndex + 1, rightIndex);
				front =  itemName.substring(0, leftIndex);
			}
			itemModelName=  itemName.substring(leftIndex + 1, itemName.lastIndexOf(")"));
			itemName =  itemName.substring(0, leftIndex);
		}
		
		return new YumaWeidianData(orderNumber, orderStatus, orderType, orderCreateTimeStr, orderPayTimeStr, itemName,
				itemModelName, itemId, itemNumber, itemCount, itemModelPrice, itemIntegral, sendStatus, refundStatus,
				refundPrice, serviceRefund, originalPrice, freightRefund, receiverName, receiverPhone, receiverProvince,
				receiverCity, receiverArea, receiverAddress, orderDetail, buyerRemark, orderTemplate, sellerRemark,
				distributionStoreId, distributionName, distributionPhone, orderPhone, groupStatus, identificationCard,
				payType, selfMention);

	}
}
