package kz.sdu.project.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

@Component
public class ServletContextListenerImpl implements ServletContextListener {

    @Autowired
    private DataSource dataSource;

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
        }
    }
}