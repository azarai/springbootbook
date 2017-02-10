package de.codeboje.springbootbook.commentstore;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.codeboje.springbootbook.commons.CommentstoreObjectMapper;
import de.codeboje.springbootbook.logging.RequestContextLoggingFilter;

/**
 * Application Configuration and Starter for the commentstore
 * @author Jens Boje
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages= {"de.codeboje.springbootbook"})
//@EnableJpaRepositories(basePackages= {"de.codeboje.springbootbook"})
@EntityScan(basePackages= {"de.codeboje.springbootbook"})
@ImportResource(value={"classpath*:legacy-context.xml"})
public class CommentStoreApp {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(CommentStoreApp.class, args);
    }

    /**
     * Maps the commons logging Filter to all requests; done by spring boot
     * @return
     */
    @Bean
    public Filter initRequestContextLoggingFilter() {
        return new RequestContextLoggingFilter();
    }
    
    @Bean
    @Primary
    public ObjectMapper initObjectMapper() {
        return new CommentstoreObjectMapper();
    }
    
}
