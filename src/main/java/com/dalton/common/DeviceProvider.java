package com.dalton.common;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dalton on 22/12/17.
 */

@Component
public class DeviceProvider {

    public Device getCurrentDevice(HttpServletRequest request) {
        return DeviceUtils.getCurrentDevice(request);
    }
}
