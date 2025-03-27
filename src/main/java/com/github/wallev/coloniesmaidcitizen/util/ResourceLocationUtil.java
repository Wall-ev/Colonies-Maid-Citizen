package com.github.wallev.coloniesmaidcitizen.util;

import com.github.wallev.coloniesmaidcitizen.ColoniesMaidCitizen;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

import static net.minecraft.resources.ResourceLocation.isValidNamespace;
import static net.minecraft.resources.ResourceLocation.isValidPath;

public class ResourceLocationUtil {
    public static boolean isValidResourceLocation(String location) {
        String[] astring = decompose(location, ':');
        return isValidNamespace(StringUtils.isEmpty(astring[0]) ? "minecraft" : astring[0]) && isValidPath(astring[1]);
    }

    protected static String[] decompose(String pLocation, char pSeparator) {
        String[] astring = new String[]{"minecraft", pLocation};
        int i = pLocation.indexOf(pSeparator);
        if (i >= 0) {
            astring[1] = pLocation.substring(i + 1);
            if (i >= 1) {
                astring[0] = pLocation.substring(0, i);
            }
        }

        return astring;
    }

    public static ResourceLocation getResourceLocation(String pLocation) {
        return ResourceLocation.fromNamespaceAndPath(ColoniesMaidCitizen.MOD_ID, pLocation);
    }
}
