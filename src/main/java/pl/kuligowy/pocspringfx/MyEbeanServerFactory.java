package pl.kuligowy.pocspringfx;

import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


/**
 * Simple Spring bean factory for creating the EbeanServer.
 */
@Component
public class MyEbeanServerFactory implements FactoryBean<EbeanServer> {
    @Autowired
    @Lazy
    DataSource dataSource;

    public EbeanServer getObject() throws Exception {

        return createEbeanServer();
    }

    public Class<?> getObjectType() {
        return EbeanServer.class;
    }

    public boolean isSingleton() {
        return true;
    }

    /**
     * Create a EbeanServer instance.
     */
    private EbeanServer createEbeanServer() {

        ServerConfig config = new ServerConfig();
        config.setName("pg");

        // load configuration from ebean.properties
        config.loadFromProperties();
        config.setDataSource(dataSource);
        config.setDefaultServer(true);
//    ...
        // other programmatic configuration

        return EbeanServerFactory.create(config);
    }
}

