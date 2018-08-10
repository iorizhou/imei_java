package com.imei.app.entity;

public class Item {
	private long id;
	private String name;
	private String cover;
	private String doctorName;
	private long doctorId;
	private int jumpType;
	private String jumpUrl;
	private int sortOrder;
	private String detailsUrl;
	private long hospitalId;
	private String city;
	private long typeId;
	private long parentTypeId;
	private long parentParentTypeId;
	private long discountPrice;
	private long origPrice;
	private String relateItemid;   //该项目关联的兄弟项目.比如某个医院发了4个瘦脸针的项目， 2 3 4项目则是这个1项目的关联项目,APP上显示为服务规格 .id已，连接
	
	
	public String getRelateItemid() {
		return relateItemid;
	}
	public void setRelateItemid(String relateItemid) {
		this.relateItemid = relateItemid;
	}
	public long getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(long discountPrice) {
		this.discountPrice = discountPrice;
	}
	public long getOrigPrice() {
		return origPrice;
	}
	public void setOrigPrice(long origPrice) {
		this.origPrice = origPrice;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}
	public int getJumpType() {
		return jumpType;
	}
	public void setJumpType(int jumpType) {
		this.jumpType = jumpType;
	}
	public String getJumpUrl() {
		return jumpUrl;
	}
	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getDetailsUrl() {
		return detailsUrl;
	}
	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}
	public long getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(long hospitalId) {
		this.hospitalId = hospitalId;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	public long getParentTypeId() {
		return parentTypeId;
	}
	public void setParentTypeId(long parentTypeId) {
		this.parentTypeId = parentTypeId;
	}
	public long getParentParentTypeId() {
		return parentParentTypeId;
	}
	public void setParentParentTypeId(long parentParentTypeId) {
		this.parentParentTypeId = parentParentTypeId;
	}
	public Item(String name, String cover, String doctorName, long doctorId, int jumpType, String jumpUrl,
			int sortOrder, String detailsUrl, long hospitalId, long typeId, long parentTypeId,
			long parentParentTypeId,String city) {
		super();
		this.name = name;
		this.cover = cover;
		this.doctorName = doctorName;
		this.doctorId = doctorId;
		this.jumpType = jumpType;
		this.jumpUrl = jumpUrl;
		this.sortOrder = sortOrder;
		this.detailsUrl = detailsUrl;
		this.hospitalId = hospitalId;
		this.typeId = typeId;
		this.parentTypeId = parentTypeId;
		this.parentParentTypeId = parentParentTypeId;
		this.city = city;
	}
	
	
	
	
	

}
