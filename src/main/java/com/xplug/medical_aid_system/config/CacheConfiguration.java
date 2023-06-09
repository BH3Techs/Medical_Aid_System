package com.xplug.medical_aid_system.config;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.redisson.Redisson;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;

    @Bean
    public javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration(JHipsterProperties jHipsterProperties) {
        MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();

        URI redisUri = URI.create(jHipsterProperties.getCache().getRedis().getServer()[0]);

        Config config = new Config();
        if (jHipsterProperties.getCache().getRedis().isCluster()) {
            ClusterServersConfig clusterServersConfig = config
                .useClusterServers()
                .setMasterConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .addNodeAddress(jHipsterProperties.getCache().getRedis().getServer());

            if (redisUri.getUserInfo() != null) {
                clusterServersConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        } else {
            SingleServerConfig singleServerConfig = config
                .useSingleServer()
                .setConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
                .setConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .setAddress(jHipsterProperties.getCache().getRedis().getServer()[0]);

            if (redisUri.getUserInfo() != null) {
                singleServerConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        }
        jcacheConfig.setStatisticsEnabled(true);
        jcacheConfig.setExpiryPolicyFactory(
            CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, jHipsterProperties.getCache().getRedis().getExpiration()))
        );
        return RedissonConfiguration.fromInstance(Redisson.create(config), jcacheConfig);
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cm) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cm);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer(javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
        return cm -> {
            createCache(cm, com.xplug.medical_aid_system.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.User.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Authority.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.PlanCategory.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.PlanCategory.class.getName() + ".plans", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Plans.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Plans.class.getName() + ".currencies", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Plans.class.getName() + ".planBenefits", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Plans.class.getName() + ".planBillingCycles", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Plans.class.getName() + ".policies", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Currency.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Currency.class.getName() + ".tariffs", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Currency.class.getName() + ".wallets", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Currency.class.getName() + ".tarrifClaims", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.PlanBenefit.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.PlanBenefit.class.getName() + ".benefitLimits", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Benefit.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Benefit.class.getName() + ".tariffs", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.BenefitType.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.BenefitType.class.getName() + ".benefits", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.BenefitType.class.getName() + ".planBenefits", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Tariff.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.BenefitLimit.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.BenefitLimitType.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.BenefitLimitType.class.getName() + ".benefitLimits", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.BenefitClaimTracker.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Policy.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Policy.class.getName() + ".claims", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Policy.class.getName() + ".nextOfKins", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Policy.class.getName() + ".plans", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Document.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.DocumentType.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.DocumentType.class.getName() + ".documents", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Individual.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Group.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.NextOfKin.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.ContactDetails.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Address.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.BankingDetails.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Wallet.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Claim.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Claim.class.getName() + ".tarrifClaims", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.TarrifClaim.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.ServiceProvider.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.ServiceProvider.class.getName() + ".claims", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Invoice.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.InvoiceLine.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.RiskProfile.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.RiskProfile.class.getName() + ".conditions", jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.Condition.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.SponsorAdministration.class.getName(), jcacheConfiguration);
            createCache(cm, com.xplug.medical_aid_system.domain.PlanBillingCycle.class.getName(), jcacheConfiguration);
            // jhipster-needle-redis-add-entry
        };
    }

    private void createCache(
        javax.cache.CacheManager cm,
        String cacheName,
        javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
