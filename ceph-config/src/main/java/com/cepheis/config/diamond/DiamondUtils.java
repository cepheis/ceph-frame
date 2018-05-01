package com.cepheis.config.diamond;

import com.taobao.diamond.client.impl.DiamondEnvRepo;
import com.taobao.diamond.common.Constants;
import com.taobao.diamond.manager.ManagerListener;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class DiamondUtils {

    public static void main(String[] args) {
        getContent("123", "123");
    }

    public static String getContent(String group, String dataId) {
        String config;
        try {
            config = DiamondEnvRepo.defaultEnv.getConfig(dataId, group, Constants.GETCONFIG_LOCAL_SERVER_SNAPSHOT, 1000);
        } catch (IOException e) {
            throw new RuntimeException("获取diamong配置失败", e);
        }
        if (config == null) {
            throw new RuntimeException("获取diamong配置失败[" + group + "." + dataId + "]未配置");
        }
        return config;
    }

    public static String getContentAndAddListener(final String group, final String dataId, final Consumer<String> consumer) {
        String config;
        try {
            config = DiamondEnvRepo.defaultEnv.getConfig(dataId, group, Constants.GETCONFIG_LOCAL_SERVER_SNAPSHOT, 1000);
        } catch (IOException e) {
            throw new RuntimeException("获取diamong配置失败", e);
        }
        if (config == null) {
            throw new RuntimeException("获取diamong配置失败[" + group + "." + dataId + "]未配置");
        }
        addListener(group, dataId, consumer);
        return config;
    }

    public static void addListener(final String group, final String dataId, final Consumer<String> consumer) {
        List<ManagerListener> ls = getListeners(group, dataId);
        if (ls == null || ls.size() == 0) {
            DiamondEnvRepo.defaultEnv.addListeners(dataId, group, (ManagerListenerAdapter) config -> consumer.accept(config));
        }
    }

    private static List<ManagerListener> getListeners(String group, String dataId) {
        return DiamondEnvRepo.defaultEnv.getListeners(dataId, group);
    }
}
