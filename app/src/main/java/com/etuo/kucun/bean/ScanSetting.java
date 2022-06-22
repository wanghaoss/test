package com.etuo.kucun.bean;
import java.util.List;

/**
 * Created by UglyB
 * 0n 2019/5/8
 *  <property name="model" value="T52"/>
 *         <property name="openService_reboot" value = "false"/>
 *         <property name="SerialPortName" value="/dev/ttyMT1"/>
 *         <property name="FileNodePowerOn" value = "/sys/devices/soc/10003000.keypad/scan_leveltrigger_enable"/>
 *         <property name="scanVoice" value = "false"/>
 *         <property name="scanVibrate" value = "false"/>
 *         <property name="HIDChoose" value = "false"/>
 *         <property name="EnterChoose" value = "false"/>
 *         <property name="TabChoose" value = "false"/>
 */


public class ScanSetting {
    private String model;
    private boolean openService_reboot;
    private boolean scanVoice;
    private String SerialPortName;
    private String FileNodePowerOn;
    private boolean scanVibrate;
    private boolean HIDChoose;
    private boolean EnterChoose;
    private String TabChoose;
    private String ScanModel;
    private String ScreenDirection;
    private String FileNodeCloseOpen;
    private boolean ClipBoardChoose;
    private int ReaderID = 2;

    @Override
    public String toString() {
        return "ScanSetting{" +
                "model='" + model + '\'' +
                ", openService_reboot=" + openService_reboot +
                ", scanVoice=" + scanVoice +
                ", SerialPortName='" + SerialPortName + '\'' +
                ", FileNodePowerOn='" + FileNodePowerOn + '\'' +
                ", scanVibrate=" + scanVibrate +
                ", HIDChoose=" + HIDChoose +
                ", EnterChoose=" + EnterChoose +
                ", TabChoose=" + TabChoose +
                ", ScanModel='" + ScanModel + '\'' +
                ", ScreenDirection='" + ScreenDirection + '\'' +
                ", FileNodeCloseOpen='" + FileNodeCloseOpen + '\'' +
                ", ClipBoardChoose=" + ClipBoardChoose +
                ", brocastButtons=" + brocastButtons +
                ", readid=" + ReaderID +
                '}';
    }


    public int getReaderID() {
        return ReaderID;
    }

    public void setReaderID(int readerID) {
        ReaderID = readerID;
    }

    /*@Convert(columnType = String.class, converter = BrocastButtonConverter.class)*/
    private List<BrocastButton> brocastButtons;

    public boolean isClipBoardChoose() {
        return ClipBoardChoose;
    }

    public void setClipBoardChoose(boolean clipBoardChoose) {
        ClipBoardChoose = clipBoardChoose;
    }

    public String getScreenDirection() {
        return ScreenDirection;
    }

    public void setScreenDirection(String screenDirection) {
        ScreenDirection = screenDirection;
    }

    public String getFileNodeCloseOpen() {
        return FileNodeCloseOpen;
    }

    public void setFileNodeCloseOpen(String fileNodeCloseOpen) {
        FileNodeCloseOpen = fileNodeCloseOpen;
    }



    public List<BrocastButton> getBrocastButtons() {
        return brocastButtons;
    }

    public void setBrocastButtons(List<BrocastButton> brocastButtons) {
        this.brocastButtons = brocastButtons;
    }

    public String getScanModel() {
        return ScanModel;
    }

    public void setScanModel(String scanModel) {
        ScanModel = scanModel;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isOpenService_reboot() {
        return openService_reboot;
    }

    public void setOpenService_reboot(boolean openService_reboot) {
        this.openService_reboot = openService_reboot;
    }

    public boolean isScanVoice() {
        return scanVoice;
    }

    public void setScanVoice(boolean scanVoice) {
        this.scanVoice = scanVoice;
    }

    public String getSerialPortName() {
        return SerialPortName;
    }

    public void setSerialPortName(String serialPortName) {
        SerialPortName = serialPortName;
    }

    public String getFileNodePowerOn() {
        return FileNodePowerOn;
    }

    public void setFileNodePowerOn(String fileNodePowerOn) {
        FileNodePowerOn = fileNodePowerOn;
    }

    public boolean isScanVibrate() {
        return scanVibrate;
    }

    public void setScanVibrate(boolean scanVibrate) {
        this.scanVibrate = scanVibrate;
    }

    public boolean isHIDChoose() {
        return HIDChoose;
    }

    public void setHIDChoose(boolean HIDChoose) {
        this.HIDChoose = HIDChoose;
    }

    public boolean isEnterChoose() {
        return EnterChoose;
    }

    public void setEnterChoose(boolean enterChoose) {
        EnterChoose = enterChoose;
    }

    public void setTabChoose(String tabChoose) {
        TabChoose = tabChoose;
    }

    public boolean getOpenService_reboot() {
        return this.openService_reboot;
    }

    public boolean getScanVoice() {
        return this.scanVoice;
    }

    public boolean getScanVibrate() {
        return this.scanVibrate;
    }

    public boolean getHIDChoose() {
        return this.HIDChoose;
    }

    public boolean getEnterChoose() {
        return this.EnterChoose;
    }

    public String getTabChoose() {
        return TabChoose;
    }


}
