package com.arcbees.gaestudio.server.util;
public class PackageHelper {

    public static String getLeafPackageName(Package pack) {
        String[] subPackages = pack.getName().split("\\.");
        return subPackages[subPackages.length - 1];
    }
}
