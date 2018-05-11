package com.test.chexian.api.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 车辆的数据交互对象
 * 
 * @author Lyu Yang
 *
 */
@Getter
@Setter
public class CarDto {

	/**
	 * 车辆号牌
	 */
	private String vehiclePlate;
	
	/**
	 * 车辆使用性质
	 * <p>
	 * 暂时仅支持家庭自用车,无需对外暴露
	 */
	private Byte vehicleUseCharacter = 1;
	
	/**
	 * 是否是一年内的过户车
	 */
	private Boolean isTransferVehicle;
	
	/**
	 * 过户日期
	 * <p>
	 * 当车辆为一年内的过户车时,此属性不能为空
	 * <p>
	 * example:2017-01-02
	 */
	private Date vehicleTransferDate;
	
	/**
	 * 品牌型号{长度为6-50位.中文,字母,数字,下划线,中短线,空格以及它们之间的组合}
	 */
	private String vehicleModel;
	
	/**
	 * 车架号{长度为17位.字母,数字以及它们之间的组合}
	 */
	private String vehicleVin;
	
	/**
	 * 发动机号{长度为6-20位.字母,数字,中短线以及它们之间的组合}
	 */
	private String vehicleEngineNo;
	
	/**
	 * 车辆注册日期
	 * <p>
	 * 已上牌车辆此属性不能为空
	 * <p>
	 * example:2017-01-02
	 */
	private Date vehicleRegDate;
	
	/**
	 * 车辆座位数,即核定载客数{数字,仅支持9座及以下}
	 */
	private String vehicleSeat;
	
	/*---------- 以下属性需要通过车型查询接口获取得到 ----------*/
	
	/**
	 * 车型唯一编码
	 */
	private String vehicleId;
	
	/**
	 * 车型正式名称
	 */
	private String vehicleName;
	
	/**
	 * 车型种类
	 */
	private String vehicleClass;
	
	/**
	 * 新车购置价{不包含小数的数字,单位为元}
	 * <p>
	 * example:74400
	 */
	private Integer vehiclePrice;
	
	/**
	 * 车辆整备质量{包含小数的数字}
	 * <p>
	 * example:1.00 or 1.355
	 */
	private String vehicleCurbWeight;
	
	/**
	 * 发动机功率{数字}
	 * <p>
	 * example:93 or 95.5
	 */
	private String vehicleEnginePower;
	
	/**
	 * 排气量{数字}
	 * <p>
	 * example:1.598
	 */
	private String vehicleDisplacement;
	
	/**
	 * 产地类型
	 */
	private String vehicleOriginType;
	
	/**
	 * 上市年份{数字}
	 * <p>
	 * example:2017
	 */
	private String vehicleYear;
	
	public static CarDto convert(CarDto carDto) {
		CarDto copy = null;
		if (carDto != null) {
			copy = new CarDto();
			copy.setVehicleRegDate(carDto.getVehicleRegDate());
			copy.setIsTransferVehicle(carDto.getIsTransferVehicle());
			copy.setVehicleClass(carDto.getVehicleClass());
			copy.setVehicleCurbWeight(carDto.getVehicleCurbWeight());
			copy.setVehicleDisplacement(carDto.getVehicleDisplacement());
			copy.setVehicleEngineNo(carDto.getVehicleEngineNo());
			copy.setVehicleEnginePower(carDto.getVehicleEnginePower());
			copy.setVehicleId(carDto.getVehicleId());
			copy.setVehicleModel(carDto.getVehicleModel());
			copy.setVehicleName(carDto.getVehicleName());
			copy.setVehicleOriginType(carDto.getVehicleOriginType());
			copy.setVehiclePlate(carDto.getVehiclePlate());
			copy.setVehicleSeat(carDto.getVehicleSeat());
			copy.setVehicleTransferDate(carDto.getVehicleTransferDate());
			copy.setVehicleUseCharacter(carDto.getVehicleUseCharacter());
			copy.setVehicleVin(carDto.getVehicleVin());
			copy.setVehicleYear(carDto.getVehicleYear());
			copy.setVehiclePrice(carDto.getVehiclePrice());
		}
		return copy;
	}
}
