package io.github.luyang.starter.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import io.github.luyang.base.util.NetUtil;
import io.github.luyang.starter.base.common.context.CurrentUserAccessor;
import io.github.luyang.starter.mybatis.support.handler.DefaultFieldHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

/**
 * Mybatis plus 配置
 *
 * @author yang.lu
 */

@AutoConfiguration
@PropertySource(value = "classpath:mybatis-plus.properties", encoding = "UTF-8")
public class MybatisPlusAutoConfig {

    /**
     * 插件主体配置
     *
     * @return MybatisPlusInterceptor 插件主体
     * @author yang.lu
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    /**
     * 分页插件配置
     *
     * @return PaginationInnerInterceptor 分页插件
     * @author yang.lu
     */
    private PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInnerInterceptor.setMaxLimit(-1L);
        // 分页合理化
        paginationInnerInterceptor.setOverflow(true);
        return paginationInnerInterceptor;
    }

    /**
     * 配置自动填充处理器
     *
     * @return MetaObjectHandler 自动填充处理器
     * @author yang.lu
     */
    @Bean
    public MetaObjectHandler defaultFieldHandler(ObjectProvider<CurrentUserAccessor> userAccessorProvider) {
        return new DefaultFieldHandler(userAccessorProvider);
    }

    /**
     * 分布式ID生成器（基于主机地址）
     *
     * @return IdentifierGenerator ID生成器
     * @author yang.lu
     */
    @Bean
    public IdentifierGenerator identifierGenerator() {
        return new DefaultIdentifierGenerator(NetUtil.getLocalhost());
    }
}
