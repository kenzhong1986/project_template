package org.lzs.config;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = {"org.lzs.golddata.dao.mapper"}, sqlSessionFactoryRef = "datasource2SqlSessionFactory")
public class DataSource2 {
    @Autowired
    @Qualifier("datasource2")
    private DataSource dataSource2;

    @Bean
    public SqlSessionFactory datasource2SqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource2); //
        //分页插件
        Interceptor interceptor = new PageHelper();
        Properties properties = new Properties();
        //数据库

        properties.setProperty("dialect", "sqlserver");
        //是否将参数offset作为PageNum使用
        properties.setProperty("offsetAsPageNum", "true");
        //是否进行count查询
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("pageSizeZero", "true");
        //是否分页合理化
        properties.setProperty("reasonable", "false");
        properties.setProperty("params","pageNum=pageHelperStart;pageSize=pageHelperRows;");
        properties.setProperty("supportMethodsArguments","true");
        properties.setProperty("returnPageInfo","none");
        interceptor.setProperties(properties);
        factoryBean.setPlugins(new Interceptor[] {interceptor});
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate datasource2SqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(datasource2SqlSessionFactory()); // 使用上面配置的Factory
        return template;
    }

    @Bean
    public PlatformTransactionManager datasource2TransactionManager(@Qualifier("datasource2") DataSource prodDataSource) {
        return new DataSourceTransactionManager(prodDataSource);
    }

    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        //数据库
        properties.setProperty("helperDialect", "sqlserver");
        //是否将参数offset作为PageNum使用
        properties.setProperty("offsetAsPageNum", "true");
        //是否进行count查询
        properties.setProperty("rowBoundsWithCount", "true");
        //是否分页合理化
        properties.setProperty("reasonable", "false");
        pageHelper.setProperties(properties);
        return pageHelper;
    }


}
