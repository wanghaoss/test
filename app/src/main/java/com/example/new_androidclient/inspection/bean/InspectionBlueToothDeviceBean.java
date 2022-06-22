package com.example.new_androidclient.inspection.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
/**
 * zq 2.1
 * 原蓝牙需要的实体类，现改为东科提供的测振设备，暂不用
**/
public class InspectionBlueToothDeviceBean extends BaseObservable {

	private String deviceName;
	private String deviceMac;
	private int deviceBand;

	public InspectionBlueToothDeviceBean(String devicename, String devicemac, int deviceband){
		this.setDeviceName(devicename);
		this.setDeviceMac(devicemac);
		this.setDeviceBand(deviceband);
	}

	@Bindable
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Bindable
	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	@Bindable
	public int getDeviceBand() {
		return deviceBand;
	}

	public void setDeviceBand(int deviceBand) {
		this.deviceBand = deviceBand;
	}
}
