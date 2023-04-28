package com.example.accesskeybackend.props;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Getter
@Setter
@ConfigurationProperties("access-key-backend.dns.records")
public class DnsProps {

    private String ipv6;
}
