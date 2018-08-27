package com.imei.app.entity;

//医院红包
public class YYRedPacket {
	private long id;
	private String name;
	private String useCondition;  //红包使用规则说明 
	private long userId;
	private String startDate;
	private String endDate;
	private String source;  //获取来源
	private long hospitalId;
	private long amount;  //红包金额
	private long conditionAmount;   //使用基线。  满xx才可使用
	private int status;  //状态  0未使用 1已使用 2已过期
	private String useDate;  //使用时间(若该红包已被使用)
	private long userOrderId; //使用在哪个订单上
	
	public String getUseDate() {
		return useDate;
	}
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	public long getUserOrderId() {
		return userOrderId;
	}
	public void setUserOrderId(long userOrderId) {
		this.userOrderId = userOrderId;
	}
	public long getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(long hospitalId) {
		this.hospitalId = hospitalId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUseCondition() {
		return useCondition;
	}
	public void setUseCondition(String useCondition) {
		this.useCondition = useCondition;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public long getConditionAmount() {
		return conditionAmount;
	}
	public void setConditionAmount(long conditionAmount) {
		this.conditionAmount = conditionAmount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public YYRedPacket(String name, String useCondition, long userId, String startDate, String endDate, String source,
			long hospitalId, long amount, long conditionAmount, int status) {
		super();
		this.name = name;
		this.useCondition = useCondition;
		this.userId = userId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.source = source;
		this.hospitalId = hospitalId;
		this.amount = amount;
		this.conditionAmount = conditionAmount;
		this.status = status;
	}
	public YYRedPacket() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
