package com.frostnerd.dnschangerlight.API;

import android.app.ActivityManager;
import android.content.Context;

import com.frostnerd.dnschangerlight.DNSVpnService;

import java.math.BigInteger;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * Copyright Daniel Wolf 2017
 * All rights reserved.
 * <p>
 * development@frostnerd.com
 */
public class API {
    public static final String BROADCAST_SERVICE_STATUS_CHANGE = "com.frostnerd.dnschanger.VPN_SERVICE_CHANGE";
    private static final SecureRandom random = new SecureRandom();
    private static final Pattern ipv4Pattern = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"),
            domainPattern = Pattern.compile("^(([a-zA-Z]{1})|([a-zA-Z]{1}[a-zA-Z]{1})|([a-zA-Z]{1}[0-9]{1})|([0-9]{1}[a-zA-Z]{1})|([a-zA-Z0-9][a-zA-Z0-9-_]{1,61}[a-zA-Z0-9]))\\.([a-zA-Z]{2,6}|[a-zA-Z0-9-]{2,30}\\.[a-zA-Z]{2,3})$");

    public static boolean checkVPNServiceRunning(Context c){
        ActivityManager am = (ActivityManager)c.getSystemService(Context.ACTIVITY_SERVICE);
        String name = DNSVpnService.class.getName();
        for(ActivityManager.RunningServiceInfo service: am.getRunningServices(Integer.MAX_VALUE)){
            if(name.equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }

    public static final String randomString(final int bits){
        return new BigInteger(bits, random).toString(32);
    }

    public static boolean isIP(String s){
        return isIP(s,false);
    }

    public static boolean isIP(String s, boolean ipv6){
        return ipv4Pattern.matcher(s).matches()|| (ipv6 && isIPv6(s));
    }

    private static boolean isIPv6(String addr){
        try{
            InetAddress a = InetAddress.getByName(addr);
            return a instanceof Inet6Address;
        }catch(Exception e){}
        return false;
    }

    public static boolean isDomain(String s){
        return domainPattern.matcher(s).matches();
    }

    public static boolean isInteger(String s){
        try {
            Integer.parseInt(s);
            return true;
        }catch(Exception e){

        }
        return false;
    }

    public static boolean between(int i,int min,int max){
        return i >= min && i <= max;
    }
}
