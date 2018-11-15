package pro.onlyou.apache.shiro.boot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class DruidConfiguration {

    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean registrationBean =
                new ServletRegistrationBean(new StatViewServlet());
        // 白名单
        registrationBean.addInitParameter("allow","127.0.0.1");
        // 黑名单
        registrationBean.addInitParameter("deny","192.168.1.100");
        // 监控台用户信息
        registrationBean.addInitParameter("loginUsername","druid");
        registrationBean.addInitParameter("loginPassword","druid");
        // 是否能够重置数据
        registrationBean.addInitParameter("resetEnable","false");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean statFilter(){
        FilterRegistrationBean registrationBean =
                new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则
        registrationBean.addUrlPatterns("/*");
        // 添加不需要
        registrationBean.addInitParameter("exclusions","*.js,*.gif,*.css,*.jpg,*.png,*.jpeg,*.ico,/druid/*");

        return registrationBean;
    }

    @Bean
    PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(){
        return new PersistenceExceptionTranslationPostProcessor();
    }


    @Bean("dataSource")
    @Primary
    @ConfigurationProperties(
            prefix = "spring.datasource"
    )
    public DataSource dataSource(){
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }


    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dataSource") DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean =
                new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resourcePatternResolver =
                new PathMatchingResourcePatternResolver();
        try {
            sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath:mappers/*.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sqlSessionFactoryBean;
    }
}
