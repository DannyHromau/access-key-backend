package com.example.accesskeybackend.props;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Getter
@Setter
@ConfigurationProperties("access-key-backend.dir-context.env")
public class DirContextProps {

    private String namingFactory;
    private String dnsContextFactory;
}
