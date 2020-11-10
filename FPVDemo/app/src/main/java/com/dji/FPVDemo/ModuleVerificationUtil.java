package com.dji.FPVDemo;

/**
 * Created by wrs on 2019/6/19,11:53
 * projectName: Ztest5
 * packageName: com.example.admin.ztest.live
 */


//import android.support.annotation.Nullable;

import androidx.annotation.Nullable;
import dji.common.product.Model;
import dji.sdk.accessory.AccessoryAggregation;
import dji.sdk.accessory.beacon.Beacon;
import dji.sdk.accessory.speaker.Speaker;
import dji.sdk.accessory.spotlight.Spotlight;
import dji.sdk.base.BaseProduct;
import dji.sdk.flightcontroller.FlightController;
import dji.sdk.flightcontroller.Simulator;
import dji.sdk.products.Aircraft;
import dji.sdk.products.HandHeld;

/**
 * Created by dji on 16/1/6.
 */
public class ModuleVerificationUtil {
    public static boolean isProductModuleAvailable() {
        return (null != FPVDemoApplication.getProductInstance());
    }

    public static boolean isAircraft() {
        return FPVDemoApplication.getProductInstance() instanceof Aircraft;
    }

    public static boolean isHandHeld() {
        return FPVDemoApplication.getProductInstance() instanceof HandHeld;
    }

    //这个是拍照的
    public static boolean isCameraModuleAvailable() {
        return isProductModuleAvailable() && (null != FPVDemoApplication.getProductInstance().getCamera());
    }

    public static boolean isPlaybackAvailable() {
        return isCameraModuleAvailable() && (null != FPVDemoApplication.getProductInstance()
                .getCamera()
                .getPlaybackManager());
    }

    public static boolean isMediaManagerAvailable() {
        return isCameraModuleAvailable() && (null != FPVDemoApplication.getProductInstance()
                .getCamera()
                .getMediaManager());
    }

    public static boolean isRemoteControllerAvailable() {
        return isProductModuleAvailable() && isAircraft() && (null != FPVDemoApplication.getAircraftInstance()
                .getRemoteController());
    }

    public static boolean isFlightControllerAvailable() {
        return isProductModuleAvailable() && isAircraft() && (null != FPVDemoApplication.getAircraftInstance()
                .getFlightController());
    }

    public static boolean isCompassAvailable() {
        return isFlightControllerAvailable() && isAircraft() && (null != FPVDemoApplication.getAircraftInstance()
                .getFlightController()
                .getCompass());
    }

    public static boolean isFlightLimitationAvailable() {
        return isFlightControllerAvailable() && isAircraft();
    }

    public static boolean isGimbalModuleAvailable() {
        return isProductModuleAvailable() && (null != FPVDemoApplication.getProductInstance().getGimbal());
    }

    public static boolean isAirlinkAvailable() {
        return isProductModuleAvailable() && (null != FPVDemoApplication.getProductInstance().getAirLink());
    }

    public static boolean isWiFiLinkAvailable() {
        return isAirlinkAvailable() && (null != FPVDemoApplication.getProductInstance().getAirLink().getWiFiLink());
    }

    public static boolean isLightbridgeLinkAvailable() {
        return isAirlinkAvailable() && (null != FPVDemoApplication.getProductInstance()
                .getAirLink()
                .getLightbridgeLink());
    }

    public static AccessoryAggregation getAccessoryAggregation() {
        Aircraft aircraft = (Aircraft) FPVDemoApplication.getProductInstance();

        if (aircraft != null && null != aircraft.getAccessoryAggregation()) {
            return aircraft.getAccessoryAggregation();
        }
        return null;
    }

    public static Speaker getSpeaker() {
        Aircraft aircraft = (Aircraft) FPVDemoApplication.getProductInstance();

        if (aircraft != null && null != aircraft.getAccessoryAggregation() && null != aircraft.getAccessoryAggregation().getSpeaker()) {
            return aircraft.getAccessoryAggregation().getSpeaker();
        }
        return null;
    }

    public static Beacon getBeacon() {
        Aircraft aircraft = (Aircraft) FPVDemoApplication.getProductInstance();

        if (aircraft != null && null != aircraft.getAccessoryAggregation() && null != aircraft.getAccessoryAggregation().getBeacon()) {
            return aircraft.getAccessoryAggregation().getBeacon();
        }
        return null;
    }

    public static Spotlight getSpotlight() {
        Aircraft aircraft = (Aircraft) FPVDemoApplication.getProductInstance();

        if (aircraft != null && null != aircraft.getAccessoryAggregation() && null != aircraft.getAccessoryAggregation().getSpotlight()) {
            return aircraft.getAccessoryAggregation().getSpotlight();
        }
        return null;
    }

    @Nullable
    public static Simulator getSimulator() {
        Aircraft aircraft = FPVDemoApplication.getAircraftInstance();
        if (aircraft != null) {
            FlightController flightController = aircraft.getFlightController();
            if (flightController != null) {
                return flightController.getSimulator();
            }
        }
        return null;
    }

    @Nullable
    public static FlightController getFlightController() {
        Aircraft aircraft = FPVDemoApplication.getAircraftInstance();
        if (aircraft != null) {
            return aircraft.getFlightController();
        }
        return null;
    }

    @Nullable
    public static boolean isMavic2Product() {
        BaseProduct baseProduct = FPVDemoApplication.getProductInstance();
        if (baseProduct != null) {
            return baseProduct.getModel() == Model.MAVIC_2_PRO || baseProduct.getModel() == Model.MAVIC_2_ZOOM;
        }
        return false;
    }

}
