package com.example.NanLinkDemo.bleConnect;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.feasycom.feasymesh.library.ApplicationKey;
import com.feasycom.feasymesh.library.Group;
import com.feasycom.feasymesh.library.MeshManagerApi;
import com.feasycom.feasymesh.library.MeshManagerCallbacks;
import com.feasycom.feasymesh.library.MeshNetwork;
import com.feasycom.feasymesh.library.MeshProvisioningStatusCallbacks;
import com.feasycom.feasymesh.library.MeshStatusCallbacks;
import com.feasycom.feasymesh.library.NetworkKey;
import com.feasycom.feasymesh.library.Provisioner;
import com.feasycom.feasymesh.library.TestSeqCallbacks;
import com.feasycom.feasymesh.library.UnprovisionedBeacon;
import com.feasycom.feasymesh.library.models.SigModelParser;
import com.feasycom.feasymesh.library.provisionerstates.ProvisioningState;
import com.feasycom.feasymesh.library.provisionerstates.UnprovisionedMeshNode;
import com.feasycom.feasymesh.library.transport.ConfigAppKeyAdd;
import com.feasycom.feasymesh.library.transport.ConfigAppKeyStatus;
import com.feasycom.feasymesh.library.transport.ConfigCompositionDataGet;
import com.feasycom.feasymesh.library.transport.ConfigCompositionDataStatus;
import com.feasycom.feasymesh.library.transport.ConfigDefaultTtlGet;
import com.feasycom.feasymesh.library.transport.ConfigDefaultTtlStatus;
import com.feasycom.feasymesh.library.transport.ConfigModelAppStatus;
import com.feasycom.feasymesh.library.transport.ConfigModelPublicationStatus;
import com.feasycom.feasymesh.library.transport.ConfigModelSubscriptionStatus;
import com.feasycom.feasymesh.library.transport.ConfigNetworkTransmitSet;
import com.feasycom.feasymesh.library.transport.ConfigNetworkTransmitStatus;
import com.feasycom.feasymesh.library.transport.ConfigNodeResetStatus;
import com.feasycom.feasymesh.library.transport.ConfigProxyStatus;
import com.feasycom.feasymesh.library.transport.ConfigRelayStatus;
import com.feasycom.feasymesh.library.transport.ControlMessage;
import com.feasycom.feasymesh.library.transport.Element;
import com.feasycom.feasymesh.library.transport.GenericLevelStatus;
import com.feasycom.feasymesh.library.transport.GenericOnOffStatus;
import com.feasycom.feasymesh.library.transport.MeshMessage;
import com.feasycom.feasymesh.library.transport.MeshModel;
import com.feasycom.feasymesh.library.transport.ProvisionedMeshNode;
import com.feasycom.feasymesh.library.transport.ProxyConfigFilterStatus;
import com.feasycom.feasymesh.library.transport.VendorModelMessageStatus;
import com.feasycom.feasymesh.library.utils.MeshAddress;
import com.feasycom.feasymesh.library.utils.MeshParserUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanFilter;
import no.nordicsemi.android.support.v18.scanner.ScanRecord;
import no.nordicsemi.android.support.v18.scanner.ScanResult;
import no.nordicsemi.android.support.v18.scanner.ScanSettings;

@SuppressWarnings("unused")
public class NrfMeshRepository implements MeshProvisioningStatusCallbacks, MeshStatusCallbacks, MeshManagerCallbacks, BleMeshManagerCallbacks {

    private static final String TAG = NrfMeshRepository.class.getSimpleName();
    private static final int ATTENTION_TIMER = 5;
    public static final String EXPORT_PATH = Environment.getExternalStorageDirectory() + File.separator +
            "Feasycom" + File.separator + "FeasyMesh" + File.separator;
    private static final String EXPORTED_PATH = "sdcard" + File.separator + "Feasycom" + File.separator + "FeasyMesh" + File.separator;

    // Connection States Connecting, Connected, Disconnecting, Disconnected etc.
    private final MutableLiveData<Boolean> mIsConnectedToProxy = new MutableLiveData<>();

    // Live data flag containing connected state.
    private MutableLiveData<Boolean> mIsConnected;

    // LiveData to notify when device is ready
    private final MutableLiveData<Void> mOnDeviceReady = new MutableLiveData<>();

    // Updates the connection state while connecting to a peripheral
    private final MutableLiveData<String> mConnectionState = new MutableLiveData<>();

    // Flag to determine if a reconnection is in the progress when provisioning has completed
    private final SingleLiveEvent<Boolean> mIsReconnecting = new SingleLiveEvent<>();
    private final MutableLiveData<UnprovisionedMeshNode> mUnprovisionedMeshNodeLiveData = new MutableLiveData<>();
    private final MutableLiveData<ProvisionedMeshNode> mProvisionedMeshNodeLiveData = new MutableLiveData<>();
    private final SingleLiveEvent<Integer> mConnectedProxyAddress = new SingleLiveEvent<>();

    private boolean mIsProvisioningComplete = false; // Flag to determine if provisioning was completed

    // Holds the selected MeshNode to configure
    private MutableLiveData<ProvisionedMeshNode> mExtendedMeshNode = new MutableLiveData<>();

    // Holds the selected Element to configure
    private MutableLiveData<Element> mSelectedElement = new MutableLiveData<>();

    // Holds the selected mesh model to configure
    private MutableLiveData<MeshModel> mSelectedModel = new MutableLiveData<>();
    // Holds the selected app key to configure
    private MutableLiveData<NetworkKey> mSelectedNetKey = new MutableLiveData<>();
    // Holds the selected app key to configure
    private MutableLiveData<ApplicationKey> mSelectedAppKey = new MutableLiveData<>();
    // Holds the selected provisioner when adding/editing
    private MutableLiveData<Provisioner> mSelectedProvisioner = new MutableLiveData<>();

    private final MutableLiveData<Group> mSelectedGroupLiveData = new MutableLiveData<>();

    // Composition data status
    final SingleLiveEvent<ConfigCompositionDataStatus> mCompositionDataStatus = new SingleLiveEvent<>();

    // App key add status
    final SingleLiveEvent<ConfigAppKeyStatus> mAppKeyStatus = new SingleLiveEvent<>();

    //Contains the MeshNetwork
    private MeshNetworkLiveData mMeshNetworkLiveData = new MeshNetworkLiveData();
    private SingleLiveEvent<String> mNetworkImportState = new SingleLiveEvent<>();
    private SingleLiveEvent<MeshMessage> mMeshMessageLiveData = new SingleLiveEvent<>();

    // Contains the provisioned nodes
    private final MutableLiveData<List<ProvisionedMeshNode>> mProvisionedNodes = new MutableLiveData<>();

    private final MutableLiveData<ProvisionedMeshNode> mPhoneProvisionedNode = new MutableLiveData<>();

    private final MutableLiveData<List<Group>> mGroups = new MutableLiveData<>();

    public MutableLiveData<String> FSCCMD001 = new MutableLiveData<String>();

    private MeshManagerApi mMeshManagerApi;
    private BleMeshManager mBleMeshManager;
    private Handler mHandler;
    private UnprovisionedMeshNode mUnprovisionedMeshNode;
    private ProvisionedMeshNode mProvisionedMeshNode;
    private boolean mIsReconnectingFlag;
    private boolean mIsScanning;
    private boolean mSetupProvisionedNode;
    private MeshNetwork mMeshNetwork;
    private boolean mIsCompositionDataReceived;
    private boolean mIsDefaultTtlReceived;
    private boolean mIsAppKeyAddCompleted;
    private boolean mIsNetworkRetransmitSetCompleted;
    private Uri uri;

    private final Runnable mReconnectRunnable = this::startScan;

    private final Runnable mScannerTimeout = () -> {
        stopScan();
        mIsReconnecting.postValue(false);
    };

    public NrfMeshRepository(final MeshManagerApi meshManagerApi,
                             final BleMeshManager bleMeshManager) {
        //Initialize the mesh api
        mMeshManagerApi = meshManagerApi;
        mMeshManagerApi.setMeshManagerCallbacks(this);
        mMeshManagerApi.setProvisioningStatusCallbacks(this);
        mMeshManagerApi.setMeshStatusCallbacks(this);
        mMeshManagerApi.loadMeshNetwork();
        //Initialize the ble manager
        mBleMeshManager = bleMeshManager;
        mBleMeshManager.setGattCallbacks(this);
        mHandler = new Handler();
    }

    void clearInstance() {
        mBleMeshManager = null;
    }

    /**
     * Returns {@link SingleLiveEvent} containing the device ready state.
     */
    LiveData<Void> isDeviceReady() {
        return mOnDeviceReady;
    }

    /**
     * Returns {@link SingleLiveEvent} containing the device ready state.
     */
    LiveData<String> getConnectionState() {
        return mConnectionState;
    }

    /**
     * Returns {@link SingleLiveEvent} containing the device ready state.
     */
    LiveData<Boolean> isConnected() {

        return mIsConnected;
    }

    /**
     * Returns {@link SingleLiveEvent} containing the device ready state.
     */
    public LiveData<Boolean> isConnectedToProxy() {
        return mIsConnectedToProxy;
    }

    LiveData<Boolean> isReconnecting() {
        return mIsReconnecting;
    }

    boolean isProvisioningComplete() {
        return mIsProvisioningComplete;
    }

    boolean isCompositionDataStatusReceived() {
        return mIsCompositionDataReceived;
    }

    boolean isDefaultTtlReceived() {
        return mIsDefaultTtlReceived;
    }

    boolean isAppKeyAddCompleted() {
        return mIsAppKeyAddCompleted;
    }

    boolean isNetworkRetransmitSetCompleted() {
        return mIsNetworkRetransmitSetCompleted;
    }

    final MeshNetworkLiveData getMeshNetworkLiveData() {
        return mMeshNetworkLiveData;
    }

    LiveData<List<ProvisionedMeshNode>> getNodes() {
        return mProvisionedNodes;
    }

    public LiveData<ProvisionedMeshNode> getPhoneNodes(){ return mPhoneProvisionedNode; }

    void setNodes(ProvisionedMeshNode node){
        Log.e(TAG, "setNodes: 设置 node" );
        mProvisionedNodes.getValue().add(node);
    }

    LiveData<List<Group>> getGroups() {
        return mGroups;
    }

    LiveData<String> getNetworkLoadState() {
        return mNetworkImportState;
    }


    /**
     * Returns the mesh manager api
     *
     * @return {@link MeshManagerApi}
     */
    public MeshManagerApi getMeshManagerApi() {
        return mMeshManagerApi;
    }

    /**
     * Returns the ble mesh manager
     *
     * @return {@link BleMeshManager}
     */
    BleMeshManager getBleMeshManager() {
        return mBleMeshManager;
    }

    LiveData<Group> getSelectedGroup() {
        return mSelectedGroupLiveData;
    }

    /**
     * Reset mesh network
     */
    void resetMeshNetwork() {
        Log.e(TAG, "resetMeshNetwork: 3" );
        disconnect();
        mMeshManagerApi.resetMeshNetwork();
    }

    /**
     * Connect to peripheral
     *
     * @param context          Context
     * @param device           {@link ExtendedBluetoothDevice} device
     * @param connectToNetwork True if connecting to an unprovisioned node or proxy node
     */
    void connect(final Context context, final ExtendedBluetoothDevice device, final boolean connectToNetwork) {
        mMeshNetworkLiveData.setNodeName(device.getName());
        mIsProvisioningComplete = false;
        mIsCompositionDataReceived = false;
        mIsDefaultTtlReceived = false;
        mIsAppKeyAddCompleted = false;
        mIsNetworkRetransmitSetCompleted = false;
//        clearExtendedMeshNode();
//        final LogSession logSession = Logger.newSession(context, null, device.getAddress(), device.getName());
//        mBleMeshManager.setLogger(logSession);
        final BluetoothDevice bluetoothDevice = device.getDevice();
        initIsConnectedLiveData(connectToNetwork);
        mConnectionState.postValue("Connecting....");
        // Added a 1 second delay for connection, mostly to wait for a disconnection to complete before connecting.

        // mHandler.postDelayed(() -> mBleMeshManager.connect(bluetoothDevice).retry(3, 200).enqueue(), 1000);
            
        mHandler.postDelayed(() -> mBleMeshManager.connect(bluetoothDevice).retry(3, 200).enqueue(), 0);
    }

    /**
     * Connect to peripheral
     *
     * @param device bluetooth device
     */
    private void connectToProxy(final ExtendedBluetoothDevice device) {
        initIsConnectedLiveData(true);
        mConnectionState.postValue("Connecting2....");
        mBleMeshManager.connect(device.getDevice()).retry(3, 200).enqueue();
    }

    private void initIsConnectedLiveData(final boolean connectToNetwork) {
        if (connectToNetwork) {
            mIsConnected = new SingleLiveEvent<>();
        } else {
            mIsConnected = new MutableLiveData<>();
        }
    }

    /**
     * Disconnects from peripheral
     */
    void disconnect() {
        Log.e(TAG, "disconnect: 断开连接" );
        clearProvisioningLiveData();
        mIsProvisioningComplete = false;
        mBleMeshManager.disconnect().enqueue();
    }

    void clearProvisioningLiveData() {
        stopScan();
        mHandler.removeCallbacks(mReconnectRunnable);
        mSetupProvisionedNode = false;
        mIsReconnectingFlag = false;
        mUnprovisionedMeshNodeLiveData.setValue(null);
        mProvisionedMeshNodeLiveData.setValue(null);
    }

    private void removeCallbacks() {
        mHandler.removeCallbacksAndMessages(null);
    }

    public void identifyNode(final ExtendedBluetoothDevice device) {
        final UnprovisionedBeacon beacon = (UnprovisionedBeacon) device.getBeacon();
        Log.e(TAG, "identifyNode: beacon is null   ->    "  + (device.getBeacon() != null) );
        if (beacon != null) {
            mMeshManagerApi.identifyNode(beacon.getUuid(), ATTENTION_TIMER);
        } else {
            final byte[] serviceData = getServiceData(device.getScanResult(), BleMeshManager.MESH_PROVISIONING_UUID);
            Log.e(TAG, "identifyNode: serviceData is null   ->    "  + (serviceData != null) );
            if (serviceData != null) {
                final UUID uuid = mMeshManagerApi.getDeviceUuid(serviceData);
                mMeshManagerApi.identifyNode(uuid, ATTENTION_TIMER);
            }
        }
    }

    private void clearExtendedMeshNode() {
        if (mExtendedMeshNode != null) {
            mExtendedMeshNode.postValue(null);
        }
    }

    LiveData<UnprovisionedMeshNode> getUnprovisionedMeshNode() {
        return mUnprovisionedMeshNodeLiveData;
    }

    LiveData<Integer> getConnectedProxyAddress() {
        return mConnectedProxyAddress;
    }

    /**
     * Returns the selected mesh node
     */
    LiveData<ProvisionedMeshNode> getSelectedMeshNode() {
        return mExtendedMeshNode;
    }

    /**
     * Sets the mesh node to be configured
     *
     * @param node provisioned mesh node
     */
    void setSelectedMeshNode(final ProvisionedMeshNode node) {
        mExtendedMeshNode.postValue(node);
    }

    /**
     * Returns the selected element
     */
    LiveData<Element> getSelectedElement() {
        return mSelectedElement;
    }

    /**
     * Set the selected {@link Element} to be configured
     *
     * @param element element
     */
    void setSelectedElement(final Element element) {
        mSelectedElement.postValue(element);
    }

    /**
     * Set the selected model to be configured
     *
     * @param appKey mesh model
     */
    void setSelectedAppKey(@NonNull final ApplicationKey appKey) {
        mSelectedAppKey.postValue(appKey);
    }

    /**
     * Returns the selected mesh model
     */
    LiveData<ApplicationKey> getSelectedAppKey() {
        return mSelectedAppKey;
    }

    /**
     * Selects provisioner for editing or adding
     *
     * @param provisioner {@link Provisioner}
     */
    public void setSelectedProvisioner(@NonNull final Provisioner provisioner) {
        mSelectedProvisioner.postValue(provisioner);
    }

    /**
     * Returns the selected {@link Provisioner}
     */
    public LiveData<Provisioner> getSelectedProvisioner() {
        return mSelectedProvisioner;
    }

    /**
     * Returns the selected mesh model
     */
    LiveData<MeshModel> getSelectedModel() {
        return mSelectedModel;
    }

    /**
     * Set the selected model to be configured
     *
     * @param model mesh model
     */
    void setSelectedModel(final MeshModel model) {
        mSelectedModel.postValue(model);
    }

    @Override
    public void onDataReceived(final BluetoothDevice bluetoothDevice, final int mtu, final byte[] pdu) {
        mMeshManagerApi.handleNotifications(mtu, pdu);
    }

    @Override
    public void onDataSent(final BluetoothDevice device, final int mtu, final byte[] pdu) {
        mMeshManagerApi.handleWriteCallbacks(mtu, pdu);
    }

    @Override
    public void onDeviceConnecting(@NonNull final BluetoothDevice device) {
        mConnectionState.postValue("Connecting....");
    }

    @Override
    public void onDeviceConnected(@NonNull final BluetoothDevice device) {
            mIsConnected.postValue(true);
            mConnectionState.postValue("Discovering services....");
            mIsConnectedToProxy.postValue(true);
    }

    @Override
    public void onDeviceDisconnecting(@NonNull final BluetoothDevice device) {
        if (mIsReconnectingFlag) {
            mConnectionState.postValue("Reconnecting...");
        } else {
            mConnectionState.postValue("Disconnecting...");
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onDeviceDisconnected(@NonNull final BluetoothDevice device) {
        mConnectionState.postValue("");
        if (mIsReconnectingFlag) {
            mIsReconnectingFlag = false;
            mIsReconnecting.postValue(false);
            mIsConnected.postValue(false);
            mIsConnectedToProxy.postValue(false);
        } else {
            mIsConnected.postValue(false);
            mIsConnectedToProxy.postValue(false);
            if (mConnectedProxyAddress.getValue() != null) {
                final MeshNetwork network = mMeshManagerApi.getMeshNetwork();
                network.setProxyFilter(null);
            }
            //clearExtendedMeshNode();
        }
        mSetupProvisionedNode = false;
        mConnectedProxyAddress.postValue(null);
        try{
            if(mMeshManagerApi.getTestSeq()){
                mMeshManagerApi.stopTestSequence(MeshManagerApi.FAILURE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLinkLossOccurred(@NonNull final BluetoothDevice device) {
        mIsConnected.postValue(false);
    }

    @Override
    public void onServicesDiscovered(@NonNull final BluetoothDevice device, final boolean optionalServicesFound) {
        mConnectionState.postValue("Initializing...");
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onDeviceReady(@NonNull final BluetoothDevice device) {
        mOnDeviceReady.postValue(null);
        if (mBleMeshManager.isProvisioningComplete()) {
            if (mSetupProvisionedNode) {
                if (mMeshNetwork.getSelectedProvisioner().getProvisionerAddress() != null) {
                    mHandler.postDelayed(() -> {
                        //Adding a slight delay here so we don't send anything before we receive the mesh beacon message
                        // Log.e(TAG, "onDeviceReady: 2000" );
                        final ProvisionedMeshNode node = mProvisionedMeshNodeLiveData.getValue();
                        if (node != null) {
                            final ConfigCompositionDataGet compositionDataGet = new ConfigCompositionDataGet();
                            mMeshManagerApi.createMeshPdu(node.getUnicastAddress(), compositionDataGet);
                        }
                    //}, 2000);
                    }, 0);
                } else {
                    mSetupProvisionedNode = false;
                    clearExtendedMeshNode();
                }
            }/*else{
                FSCCMD001.postValue(address);
            }*/
            mIsConnectedToProxy.postValue(true);
        }
    }

    @Override
    public void onBondingRequired(@NonNull final BluetoothDevice device) {
        // Empty.
    }

    @Override
    public void onBonded(@NonNull final BluetoothDevice device) {
        // Empty.
    }

    @Override
    public void onBondingFailed(@NonNull final BluetoothDevice device) {
        // Empty.
    }

    @Override
    public void onError(final BluetoothDevice device, @NonNull final String message, final int errorCode) {
        Log.e(TAG, message + " (code: " + errorCode + "), device: " + device.getAddress());
        mConnectionState.postValue(message);
    }

    @Override
    public void onDeviceNotSupported(@NonNull final BluetoothDevice device) {
    }
    @Override
    public void onAddress(String s) {
        ProvisionedMeshNode node = mMeshManagerApi.getMeshNetwork().getNode(Integer.valueOf(s, 16));
        if (node == null){
            // TODO: 断开连接并添加到列表中。
            return;
        }



        FSCCMD001.postValue(s);
        mMeshManagerApi.testSequence(MeshParserUtils.hexToInt(s), new TestSeqCallbacks() {
            @Override
            public void success() {
                Log.e(TAG, "success: 测试seq成功" );
            }

            @Override
            public void failure() {
                Log.e(TAG, "success: 测试seq失败" );
            }

            @Override
            public void nodeIsNull() {
                // 断开连接。
            }

            @Override
            public void notReceiveMeshBeaconMessage() {
                Log.e(TAG, "notReceiveMeshBeaconMessage: 没有收到 ivindex 消息" );
            }
        });
        // disconnect();
    }





    @Override
    public void onIvIndex(int index, boolean ivUpdateActive) {
        Log.e(TAG, "onIvIndex: index  ->  " + index  + "   ivUpdateActive  ->  " + ivUpdateActive );
        mMeshManagerApi.setIvIndex(index, ivUpdateActive);
    }

    @Override
    public void onNetworkLoaded(final MeshNetwork meshNetwork) {
        Log.e(TAG, "onNetworkLoaded: 1" );
        loadNetwork(meshNetwork);
        loadGroups();
    }

    @Override
    public void onNetworkUpdated(final MeshNetwork meshNetwork) {

        loadNetwork(meshNetwork);
        loadGroups();
        updateSelectedGroup();
    }

    @Override
    public void onNetworkLoadFailed(final String error) {
        mNetworkImportState.postValue(error);
    }

    @Override
    public void onNetworkImported(final MeshNetwork meshNetwork) {
        //We can delete the old network after the import has been successful!
        //But let's make sure we don't delete the same network in case someone imports the same network ;)
        final MeshNetwork oldNet = mMeshNetwork;
        if (!oldNet.getMeshUUID().equals(meshNetwork.getMeshUUID())) {
            mMeshManagerApi.deleteMeshNetworkFromDb(oldNet);
        }
        Log.e(TAG, "onNetworkImported: 3" );
        loadNetwork(meshNetwork);
        loadGroups();
        mNetworkImportState.postValue(meshNetwork.getMeshName() + " has been successfully imported.\n" +
                "In order to start sending messages to this network, please change the provisioner address. " +
                "Using the same provisioner address will cause messages to be discarded due to the usage of incorrect sequence numbers " +
                "for this address. However if the network does not contain any nodes you do not need to change the address");
    }

    @Override
    public void onNetworkImportFailed(final String error) {
        mNetworkImportState.postValue(error);
    }

    @Override
    public void sendProvisioningPdu(final UnprovisionedMeshNode meshNode, final byte[] pdu) {
        Log.e("MeshManagerCallbacks", "sendProvisioningPdu: " + MeshParserUtils.bytesToHex(pdu, true));

        // 发送数据
        mBleMeshManager.sendPdu(pdu);
    }

    @Override
    public void onMeshPduCreated(final byte[] pdu) {
        mBleMeshManager.sendPdu(pdu);
    }

    @Override
    public int getMtu() {
        return mBleMeshManager.getMaximumPacketSize();
    }

    @Override
    public void onProvisioningStateChanged(final UnprovisionedMeshNode meshNode, final ProvisioningState.States state, final byte[] data) {
        Log.e(TAG, "onProvisioningStateChanged: ======================" + state.name() + "======================" );
        mUnprovisionedMeshNode = meshNode;
        mUnprovisionedMeshNodeLiveData.postValue(meshNode);
        switch (state) {
            case PROVISIONING_INVITE:
                break;
            case PROVISIONING_FAILED:
                mIsProvisioningComplete = false;
                break;
            case PROVISIONING_CAPABILITIES:
                mMeshManagerApi.startProvisioning(meshNode);
                break;
            case PROVISIONING_COMPLETE:
//                disconnect();
                break;
        }
    }

    @Override
    public void onProvisioningFailed(final UnprovisionedMeshNode meshNode, final ProvisioningState.States state, final byte[] data) {
        mUnprovisionedMeshNode = meshNode;
        mUnprovisionedMeshNodeLiveData.postValue(meshNode);
        if (state == ProvisioningState.States.PROVISIONING_FAILED) {
            mIsProvisioningComplete = false;
        }
    }

    @Override
    public void onProvisioningCompleted(final ProvisionedMeshNode meshNode, final ProvisioningState.States state, final byte[] data) {
        Log.e(TAG, "onProvisioningCompleted: 配置成功 ===========================================" );
        mProvisionedMeshNode = meshNode;
        mUnprovisionedMeshNodeLiveData.postValue(null);
        mProvisionedMeshNodeLiveData.postValue(meshNode);

        if (state == ProvisioningState.States.PROVISIONING_COMPLETE) {
            // mBleMeshManager.disconnect().enqueue();
            onProvisioningCompleted(meshNode);
        }
    }

    @SuppressLint("RestrictedApi")
    private void onProvisioningCompleted(final ProvisionedMeshNode node) {
        Log.e(TAG, "onProvisioningCompleted: 配置成功 ---------------------------------------------" );
        mSetupProvisionedNode = true;

        mIsProvisioningComplete = true;
        mProvisionedMeshNode = node;
        mIsReconnecting.postValue(true);
        BluetoothDevice device = mBleMeshManager.getBluetoothDevice();

        // mBleMeshManager.disconnect().enqueue();
        loadNodes();
        mBleMeshManager.refresh();

        if (mBleMeshManager.mMeshProxyDataInCharacteristic != null){
            final ApplicationKey appKey = mMeshNetworkLiveData.getSelectedAppKey();
            @SuppressLint("RestrictedApi") final int index = node.getAddedNetKeys().get(0).getIndex();
            final NetworkKey networkKey = mMeshNetwork.getNetKeys().get(index);
            final ConfigAppKeyAdd configAppKeyAdd = new ConfigAppKeyAdd(networkKey, appKey);
            Log.e(TAG, "onProvisioningCompleted: 发送配置appkey消息" );
            mMeshManagerApi.createMeshPdu(node.getUnicastAddress(), configAppKeyAdd);
            mMeshManagerApi.addProxy();
        }
    }

    /**
     * Here we load all nodes except the current provisioner. This may contain other provisioner nodes if available
     */
    @SuppressLint("RestrictedApi")
    private void loadNodes() {
        final List<ProvisionedMeshNode> nodes = new ArrayList<>();
        for (final ProvisionedMeshNode node : mMeshNetwork.getNodes()) {
            if (!node.getUuid().equalsIgnoreCase(mMeshNetwork.getSelectedProvisioner().getProvisionerUuid())) {
                nodes.add(node);
            }else {
                mPhoneProvisionedNode.postValue(node);
                // nodes.add(node);
            }
            /*Log.e(TAG, "loadNodes: "  + mMeshNetwork.getSelectedProvisioner().getProvisionerName() );
            nodes.add(node);*/
        }
        mProvisionedNodes.postValue(nodes);
    }

    @Override
    public void onTransactionFailed(final int dst, final boolean hasIncompleteTimerExpired) {
        mProvisionedMeshNode = mMeshNetwork.getNode(dst);
    }

    @Override
    public void onUnknownPduReceived(final int src, final byte[] accessPayload) {
        Log.e(TAG, "onUnknownPduReceived: " + MeshParserUtils.bytesToHex(accessPayload, false) );
        final ProvisionedMeshNode node = mMeshNetwork.getNode(src);
        if (node != null) {
            mProvisionedMeshNode = node;
            updateNode(node);
        }
    }

    @Override
    public void onBlockAcknowledgementProcessed(final int dst, @NonNull final ControlMessage message) {
        final ProvisionedMeshNode node = mMeshNetwork.getNode(dst);
        if (node != null) {
            mProvisionedMeshNode = node;
            if (mSetupProvisionedNode) {
                mProvisionedMeshNodeLiveData.postValue(mProvisionedMeshNode);
            }
        }
    }

    @Override
    public void onBlockAcknowledgementReceived(final int src, @NonNull final ControlMessage message) {
        final ProvisionedMeshNode node = mMeshNetwork.getNode(src);
        if (node != null) {
            mProvisionedMeshNode = node;
            if (mSetupProvisionedNode) {
                mProvisionedMeshNodeLiveData.postValue(node);
            }
        }
    }

    @Override
    public void onMeshMessageProcessed(final int dst, @NonNull final MeshMessage meshMessage) {
        final ProvisionedMeshNode node = mMeshNetwork.getNode(dst);
        if (node != null) {
            mProvisionedMeshNode = node;
            if (meshMessage instanceof ConfigCompositionDataGet) {
                if (mSetupProvisionedNode) {
                    mProvisionedMeshNodeLiveData.postValue(node);
                }
            } else if (meshMessage instanceof ConfigDefaultTtlGet) {
                if (mSetupProvisionedNode) {
                    mProvisionedMeshNodeLiveData.postValue(node);
                }
            } else if (meshMessage instanceof ConfigAppKeyAdd) {
                if (mSetupProvisionedNode) {
                    mProvisionedMeshNodeLiveData.postValue(node);
                }
            } else if (meshMessage instanceof ConfigNetworkTransmitSet) {
                if (mSetupProvisionedNode) {
                    mProvisionedMeshNodeLiveData.postValue(node);
                }
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onMeshMessageReceived(final int src, @NonNull final MeshMessage meshMessage) {
        Log.e(TAG, "onMeshMessageReceived: -------------------------------------" );
        final ProvisionedMeshNode node = mMeshNetwork.getNode(src);
        if (node != null)
            if (meshMessage instanceof ProxyConfigFilterStatus) {
                mProvisionedMeshNode = node;
                setSelectedMeshNode(node);
                final ProxyConfigFilterStatus status = (ProxyConfigFilterStatus) meshMessage;
                final int unicastAddress = status.getSrc();
                Log.v(TAG, "Proxy configuration source: " + MeshAddress.formatAddress(status.getSrc(), false));
                mConnectedProxyAddress.postValue(unicastAddress);
                mMeshMessageLiveData.postValue(status);
            } else if (meshMessage instanceof ConfigCompositionDataStatus) {
                final ConfigCompositionDataStatus status = (ConfigCompositionDataStatus) meshMessage;
                if (mSetupProvisionedNode) {
                    mIsCompositionDataReceived = true;
                    mProvisionedMeshNodeLiveData.postValue(node);
                    mConnectedProxyAddress.postValue(node.getUnicastAddress());
                    mHandler.postDelayed(() -> {
                        final ConfigDefaultTtlGet configDefaultTtlGet = new ConfigDefaultTtlGet();
                        mMeshManagerApi.createMeshPdu(node.getUnicastAddress(), configDefaultTtlGet);
                    //}, 500);
                    }, 0);
                } else {
                    updateNode(node);
                }
            } else if (meshMessage instanceof ConfigDefaultTtlStatus) {
                final ConfigDefaultTtlStatus status = (ConfigDefaultTtlStatus) meshMessage;
                if (mMeshManagerApi.getTestSeq()){
                    mMeshManagerApi.stopTestSequence(MeshManagerApi.SUCCESS);
                }
                if (mSetupProvisionedNode) {
                    mIsDefaultTtlReceived = true;
                    mProvisionedMeshNodeLiveData.postValue(node);
                    mHandler.postDelayed(() -> {
                        final ApplicationKey appKey = mMeshNetworkLiveData.getSelectedAppKey();
                        @SuppressLint("RestrictedApi") final int index = node.getAddedNetKeys().get(0).getIndex();
                        final NetworkKey networkKey = mMeshNetwork.getNetKeys().get(index);
                        final ConfigAppKeyAdd configAppKeyAdd = new ConfigAppKeyAdd(networkKey, appKey);
                        mMeshManagerApi.createMeshPdu(node.getUnicastAddress(), configAppKeyAdd);
                    //}, 1500);
                    }, 0);
                } else {
                    updateNode(node);
                    mMeshMessageLiveData.postValue(status);
                }
            } else if (meshMessage instanceof ConfigAppKeyStatus) {
                final ConfigAppKeyStatus status = (ConfigAppKeyStatus) meshMessage;
                if (mSetupProvisionedNode) {
                    if (status.isSuccessful()) {
                        mIsAppKeyAddCompleted = true;
                        mProvisionedMeshNodeLiveData.postValue(node);
                        mHandler.postDelayed(() -> {
                            final ConfigNetworkTransmitSet networkTransmitSet = new ConfigNetworkTransmitSet(2, 1);
                            mMeshManagerApi.createMeshPdu(node.getUnicastAddress(), networkTransmitSet);
                        // }, 1500);
                        }, 0);
                    }
                } else {
                    updateNode(node);
                    mMeshMessageLiveData.postValue(status);
                }
            } else if (meshMessage instanceof ConfigNetworkTransmitStatus) {
                if (mSetupProvisionedNode) {
                    mSetupProvisionedNode = false;
                    mIsNetworkRetransmitSetCompleted = true;
                } else {
                    updateNode(node);
                    final ConfigNetworkTransmitStatus status = (ConfigNetworkTransmitStatus) meshMessage;
                    mMeshMessageLiveData.postValue(status);
                }
            } else if (meshMessage instanceof ConfigModelAppStatus) {
                if (updateNode(node)) {
                    final ConfigModelAppStatus status = (ConfigModelAppStatus) meshMessage;
                    final Element element = node.getElements().get(status.getElementAddress());
                    if (node.getElements().containsKey(status.getElementAddress())) {
                        mSelectedElement.postValue(element);
                        final MeshModel model = element.getMeshModels().get(status.getModelIdentifier());
                        mSelectedModel.postValue(model);
                    }
                }

            } else if (meshMessage instanceof ConfigModelPublicationStatus) {

                if (updateNode(node)) {
                    final ConfigModelPublicationStatus status = (ConfigModelPublicationStatus) meshMessage;
                    if (node.getElements().containsKey(status.getElementAddress())) {
                        final Element element = node.getElements().get(status.getElementAddress());
                        mSelectedElement.postValue(element);
                        final MeshModel model = element.getMeshModels().get(status.getModelIdentifier());
                        mSelectedModel.postValue(model);
                    }
                }

            } else if (meshMessage instanceof ConfigModelSubscriptionStatus) {

                if (updateNode(node)) {
                    final ConfigModelSubscriptionStatus status = (ConfigModelSubscriptionStatus) meshMessage;
                    if (node.getElements().containsKey(status.getElementAddress())) {
                        final Element element = node.getElements().get(status.getElementAddress());
                        mSelectedElement.postValue(element);
                        final MeshModel model = element.getMeshModels().get(status.getModelIdentifier());
                        mSelectedModel.postValue(model);
                    }
                }

            } else if (meshMessage instanceof ConfigNodeResetStatus) {
                mBleMeshManager.setClearCacheRequired();
                final ConfigNodeResetStatus status = (ConfigNodeResetStatus) meshMessage;
                mExtendedMeshNode.postValue(null);
                loadNodes();
                mMeshMessageLiveData.postValue(status);

            } else if (meshMessage instanceof ConfigRelayStatus) {
                if (updateNode(node)) {
                    final ConfigRelayStatus status = (ConfigRelayStatus) meshMessage;
                    mMeshMessageLiveData.postValue(status);
                }

            } else if (meshMessage instanceof ConfigProxyStatus) {
                if (updateNode(node)) {
                    final ConfigProxyStatus status = (ConfigProxyStatus) meshMessage;
                    mMeshMessageLiveData.postValue(status);
                }

            } else if (meshMessage instanceof GenericOnOffStatus) {
                if (updateNode(node)) {
                    final GenericOnOffStatus status = (GenericOnOffStatus) meshMessage;
                    if (node.getElements().containsKey(status.getSrcAddress())) {
                        final Element element = node.getElements().get(status.getSrcAddress());
                        mSelectedElement.postValue(element);
                        final MeshModel model = element.getMeshModels().get((int) SigModelParser.GENERIC_ON_OFF_SERVER);
                        mSelectedModel.postValue(model);
                    }
                }
            } else if (meshMessage instanceof GenericLevelStatus) {

                if (updateNode(node)) {
                    final GenericLevelStatus status = (GenericLevelStatus) meshMessage;
                    if (node.getElements().containsKey(status.getSrcAddress())) {
                        final Element element = node.getElements().get(status.getSrcAddress());
                        mSelectedElement.postValue(element);
                        final MeshModel model = element.getMeshModels().get((int) SigModelParser.GENERIC_LEVEL_SERVER);
                        mSelectedModel.postValue(model);
                    }
                }

            } else if (meshMessage instanceof VendorModelMessageStatus) {

                if (updateNode(node)) {
                    final VendorModelMessageStatus status = (VendorModelMessageStatus) meshMessage;
                    if (node.getElements().containsKey(status.getSrcAddress())) {
                        final Element element = node.getElements().get(status.getSrcAddress());
                        mSelectedElement.postValue(element);
                        final MeshModel model = element.getMeshModels().get(status.getModelIdentifier());
                        mSelectedModel.postValue(model);
                    }
                }
            }

        if (mMeshMessageLiveData.hasActiveObservers()) {
            mMeshMessageLiveData.postValue(meshMessage);
        }

        //Refresh mesh network live data
        mMeshNetworkLiveData.refresh(mMeshManagerApi.getMeshNetwork());
    }

    @Override
    public void onMessageDecryptionFailed(final String meshLayer, final String errorMessage) {
        Log.e(TAG, "Decryption failed in " + meshLayer + " : " + errorMessage);
    }

    /**
     * Loads the network that was loaded from the db or imported from the mesh cdb
     *
     * @param meshNetwork mesh network that was loaded
     */
    @SuppressLint("RestrictedApi")
    private void loadNetwork(final MeshNetwork meshNetwork) {
        mMeshNetwork = meshNetwork;
        if (mMeshNetwork != null) {

            if (!mMeshNetwork.isProvisionerSelected()) {
                final Provisioner provisioner = meshNetwork.getProvisioners().get(0);
                provisioner.setLastSelected(true);
                mMeshNetwork.selectProvisioner(provisioner);
            }
            //Load live data with mesh network
            mMeshNetworkLiveData.loadNetworkInformation(meshNetwork);
            //Load live data with provisioned nodes
            loadNodes();

            final ProvisionedMeshNode node = getSelectedMeshNode().getValue();
            if (node != null) {
                mExtendedMeshNode.postValue(mMeshNetwork.getNode(node.getUuid()));
            }
        }
    }

    /**
     * We should only update the selected node, since sending messages to group address will notify with nodes that is not on the UI
     */
    private boolean updateNode(@NonNull final ProvisionedMeshNode node) {
        if (mProvisionedMeshNode == null) return false;
        if (mProvisionedMeshNode.getUnicastAddress() == node.getUnicastAddress()) {
            mProvisionedMeshNode = node;
            mExtendedMeshNode.postValue(node);
            return true;
        }
        return false;
    }

    /**
     * Starts reconnecting to the device
     */
    private void startScan() {
        if (mIsScanning) {
            return;
        }

        mIsScanning = true;
        // Scanning settings
        final ScanSettings settings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                // Refresh the devices list every second
                .setReportDelay(0)
                // Hardware filtering has some issues on selected devices
                .setUseHardwareFilteringIfSupported(false)
                // Samsung S6 and S6 Edge report equal value of RSSI for all devices. In this app we ignore the RSSI.
                /*.setUseHardwareBatchingIfSupported(false)*/
                .build();

        // Let's use the filter to scan only for Mesh devices
        final List<ScanFilter> filters = new ArrayList<>();
        filters.add(new ScanFilter.Builder().setServiceUuid(new ParcelUuid((BleMeshManager.MESH_PROXY_UUID))).build());

        final BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
        Log.e(TAG, "startScan: 开始扫描" );
        scanner.startScan(filters, settings, scanCallback);
        mHandler.postDelayed(mScannerTimeout, 20000);
    }

    /**
     * stop scanning for bluetooth devices.
     */
    private void stopScan() {
        mHandler.removeCallbacks(mScannerTimeout);
        final BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
        Log.e(TAG, "stopScan: 停止扫描" );
        scanner.stopScan(scanCallback);
        mIsScanning = false;
    }

    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(final int callbackType, final ScanResult result) {
            Log.e(TAG, "onScanResult: (((((((((((((((((((" );
            //In order to connectToProxy to the correct device, the hash advertised in the advertisement data should be matched.
            //This is to make sure we connectToProxy to the same device as device addresses could change after provisioning.
            final ScanRecord scanRecord = result.getScanRecord();
            if (scanRecord != null) {
                final byte[] serviceData = getServiceData(result, BleMeshManager.MESH_PROXY_UUID);
                if (serviceData != null) {
                    if (mMeshManagerApi.isAdvertisedWithNodeIdentity(serviceData)) {
                        final ProvisionedMeshNode node = mProvisionedMeshNode;
                        if (mMeshManagerApi.nodeIdentityMatches(node, serviceData)) {
                            stopScan();
                            onProvisionedDeviceFound(node, new ExtendedBluetoothDevice(result));
                        }
                    }
                }
            }
        }



        @Override
        public void onBatchScanResults(@NonNull final List<ScanResult> results) {
            // Batch scan is disabled (report delay = 0)
        }

        @Override
        public void onScanFailed(final int errorCode) {
            Log.e(TAG, "onScanFailed: " + errorCode );
        }
    };



    private void onProvisionedDeviceFound(final ProvisionedMeshNode node, final ExtendedBluetoothDevice device) {
        mSetupProvisionedNode = true;
        mProvisionedMeshNode = node;
        mIsReconnectingFlag = true;
        //Added an extra delay to ensure reconnection
        mHandler.postDelayed(() -> connectToProxy(device), 0);
    }

    /**
     * Generates the groups based on the addresses each models have subscribed to
     */
    private void loadGroups() {
        mGroups.postValue(mMeshNetwork.getGroups());
    }

    private void updateSelectedGroup() {
        final Group selectedGroup = mSelectedGroupLiveData.getValue();
        if (selectedGroup != null) {
            mSelectedGroupLiveData.postValue(mMeshNetwork.getGroup(selectedGroup.getAddress()));
        }
    }

    /**
     * Sets the group that was selected from the GroupAdapter.
     */
    void setSelectedGroup(final int address) {
        final Group group = mMeshNetwork.getGroup(address);
        if (group != null) {
            mSelectedGroupLiveData.postValue(group);
        }
    }

    public static byte[] getServiceData(@NonNull final ScanResult result, @NonNull final UUID serviceUuid) {
        final ScanRecord scanRecord = result.getScanRecord();
        if (scanRecord != null) {
            return scanRecord.getServiceData(new ParcelUuid((serviceUuid)));
        }
        return null;
    }

}
