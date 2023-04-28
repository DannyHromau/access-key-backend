package com.example.accesskeybackend.web.service;

import com.example.accesskeybackend.exception.DnsConnectionException;
import com.example.accesskeybackend.props.DirContextProps;
import com.example.accesskeybackend.props.DnsProps;
import com.example.accesskeybackend.web.dto.CheckedIpv6SupportDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import java.net.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

@Log4j2
@Service
@AllArgsConstructor
public class AccessKeyWebService {

    private final DirContextProps dirContextProps;
    private final DnsProps dnsProps;


    public CheckedIpv6SupportDto checkIpv6SupportByUrl(String siteUrl) {

        siteUrl = buildValidUrl(siteUrl);
        boolean success = getAllIPV6ByName(siteUrl, dirContextProps, dnsProps).size() > 0;
        return new CheckedIpv6SupportDto(success);

    }

    public static List<Inet6Address> getAllIPV6ByName(String siteUrl,
                                                      DirContextProps dirContextProps,
                                                      DnsProps dnsProps)
            throws DnsConnectionException {
        try {
            List<Inet6Address> result = new ArrayList<>();
            URI uri = new URI(siteUrl);
            Hashtable<String, Object> env = new Hashtable<>();
            env.put(dirContextProps.getNamingFactory(), dirContextProps.getDnsContextFactory());
            Attributes attrs = new InitialDirContext(env).getAttributes(uri.getHost(), new String[]{dnsProps.getIpv6()});
            Attribute attr = attrs.size() != 0 ? attrs.get(dnsProps.getIpv6()) : null;
            if (attr != null) {
                NamingEnumeration<?> values = attr.getAll();
                while (values.hasMore()) {
                    Object value = values.next();
                    if (value instanceof String) {
                        result.add((Inet6Address) InetAddress.getByName((String) value));
                    }
                }
            }

            return result;
        } catch (Exception e) {
            throw new DnsConnectionException("Method not allowed");
        }
    }

    public static String buildValidUrl(String siteUrl) {
        String httpsPrefix = "https://";
        return siteUrl.contains("http") ? siteUrl : httpsPrefix + siteUrl;
    }
}
