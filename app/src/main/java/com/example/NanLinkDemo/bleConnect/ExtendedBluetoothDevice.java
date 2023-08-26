/*
 * Copyright (c) 2018, Nordic Semiconductor
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.example.NanLinkDemo.bleConnect;

import static com.google.gson.internal.bind.TypeAdapters.UUID;

import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.NanLinkDemo.Application.MyApplication;
import com.example.NanLinkDemo.util.TransformUtil;
import com.feasycom.feasymesh.library.MeshBeacon;

import no.nordicsemi.android.support.v18.scanner.ScanRecord;
import no.nordicsemi.android.support.v18.scanner.ScanResult;


public class ExtendedBluetoothDevice implements Parcelable {
    private final BluetoothDevice device;
    private final ScanResult scanResult;
    private String name = "Unknown";
    private int rssi;
    private MeshBeacon beacon;
    private String manufacturer = "FEASY";
    private boolean selected;
    private int CH;
    private String DEVICE_ID;
    private String DEVICE_NAME = "Unknown";

    private byte[] uuid;

    private int TYPE = 0;
    private String manufacturer_ID = "78";
    private int contentVersion = 1;
    private int agreementVersion = 0;


    public ExtendedBluetoothDevice(final ScanResult scanResult, final MeshBeacon beacon) {
        this.scanResult = scanResult;
        this.device = scanResult.getDevice();
        final ScanRecord scanRecord = scanResult.getScanRecord();
        if (scanRecord != null) {
            this.name = scanRecord.getDeviceName();
            // this.name = scanResult.getDevice().getName();
        }
        this.rssi = scanResult.getRssi();
        this.beacon = beacon;
    }

    public ExtendedBluetoothDevice(final ScanResult scanResult) {
        this.scanResult = scanResult;
        this.device = scanResult.getDevice();
        final ScanRecord scanRecord = scanResult.getScanRecord();
        if (scanRecord != null) {
            this.name = scanRecord.getDeviceName();
            // this.name = scanResult.getDevice().getName();
        }
        this.rssi = scanResult.getRssi();
    }

    protected ExtendedBluetoothDevice(Parcel in) {
        device = in.readParcelable(BluetoothDevice.class.getClassLoader());
        scanResult = in.readParcelable(ScanResult.class.getClassLoader());
        name = in.readString();
        rssi = in.readInt();
        beacon = in.readParcelable(MeshBeacon.class.getClassLoader());
        manufacturer = in.readString();
        selected = in.readByte() != 0;
        CH = in.readInt();
        DEVICE_ID = in.readString();
        DEVICE_NAME = in.readString();
        uuid = in.createByteArray();
        TYPE = in.readInt();
        manufacturer_ID = in.readString();
        contentVersion = in.readInt();
        agreementVersion = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(device, flags);
        dest.writeParcelable(scanResult, flags);
        dest.writeString(name);
        dest.writeInt(rssi);
        dest.writeParcelable(beacon, flags);
        dest.writeString(manufacturer);
        dest.writeByte((byte) (selected ? 1 : 0));
        dest.writeInt(CH);
        dest.writeString(DEVICE_ID);
        dest.writeString(DEVICE_NAME);
        dest.writeByteArray(uuid);
        dest.writeInt(TYPE);
        dest.writeString(manufacturer_ID);
        dest.writeInt(contentVersion);
        dest.writeInt(agreementVersion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ExtendedBluetoothDevice> CREATOR = new Creator<ExtendedBluetoothDevice>() {
        @Override
        public ExtendedBluetoothDevice createFromParcel(Parcel in) {
            return new ExtendedBluetoothDevice(in);
        }

        @Override
        public ExtendedBluetoothDevice[] newArray(int size) {
            return new ExtendedBluetoothDevice[size];
        }
    };

    public BluetoothDevice getDevice() {
        return device;
    }

    public MeshBeacon getBeacon() {
        return beacon;
    }

    public String getAddress() {
        return device.getAddress();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
        if (manufacturer.equals("USER")) {
            setCH(Integer.parseInt(name.substring(5, 8)));
            setDEVICE_ID(name.substring(9, 15));
            if (!MyApplication.getDeviceHashMap().isEmpty()) {
                setDEVICE_NAME(MyApplication.getDeviceHashMap().get(DEVICE_ID).getDeviceName());
            }
        }
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(final int rssi) {
        this.rssi = rssi;
    }

    public ScanResult getScanResult() {
        return scanResult;
    }

    // Parcelable implementation

    public boolean matches(final ScanResult scanResult) {
        return device.getAddress().equals(scanResult.getDevice().getAddress());
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof ExtendedBluetoothDevice) {
            final ExtendedBluetoothDevice that = (ExtendedBluetoothDevice) o;
            return device.getAddress().equals(that.device.getAddress());
        }
        return super.equals(o);
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        if (manufacturer.equals("USER")) {
            setName(name);
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getCH() {
        return CH;
    }

    public void setCH(int CH) {
        this.CH = CH;
    }

    public String getDEVICE_ID() {
        return DEVICE_ID;
    }

    public void setDEVICE_ID(String DEVICE_ID) {
        this.DEVICE_ID = DEVICE_ID;
    }

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    public String getManufacturer_ID() {
        return manufacturer_ID;
    }

    public void setManufacturer_ID(String manufacturer_ID) {
        this.manufacturer_ID = manufacturer_ID;
    }

    public int getContentVersion() {
        return contentVersion;
    }

    public void setContentVersion(int contentVersion) {
        this.contentVersion = contentVersion;
    }

    public int getAgreementVersion() {
        return agreementVersion;
    }

    public void setAgreementVersion(int agreementVersion) {
        this.agreementVersion = agreementVersion;
    }


    public byte[] getUuid() {
        return uuid;
    }

    public void setUuid(byte[] uuid) {
        this.uuid = uuid;
        if (uuid[7] >= 0) {
            setCH(uuid[6] * 256 + uuid[7]);
        } else {
            setCH((uuid[6] + 1) * 256 + uuid[7]);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TransformUtil.byte2bit(uuid[8]).substring(7));
        stringBuilder.append(TransformUtil.byte2bit(uuid[9]));
        setAgreementVersion(TransformUtil.bit2int(stringBuilder.toString()));
        stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%02X", uuid[10]));
        stringBuilder.append(String.format("%02X", uuid[11]));
        stringBuilder.append(String.format("%02X", uuid[12]));
        setDEVICE_ID(stringBuilder.toString());
        if (!MyApplication.getDeviceHashMap().isEmpty()){
            setDEVICE_NAME(MyApplication.getDeviceHashMap().get(DEVICE_ID).getDeviceName());
        }
        String bit = TransformUtil.byte2bit(uuid[13]);
        setTYPE(TransformUtil.bit2int(bit.substring(7)));
        int contentVersion = TransformUtil.bit2int(bit.substring(1,7));
        if (contentVersion <= 1){
            contentVersion = 1;
        }
        setContentVersion(contentVersion);
        setManufacturer_ID(String.valueOf(uuid[14]));
    }

    public String getDEVICE_NAME() {
        return DEVICE_NAME;
    }

    public void setDEVICE_NAME(String DEVICE_NAME) {
        this.DEVICE_NAME = DEVICE_NAME;
    }
}
