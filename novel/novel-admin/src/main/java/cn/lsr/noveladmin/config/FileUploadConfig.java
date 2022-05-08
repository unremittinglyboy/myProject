package cn.lsr.noveladmin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//解决图片需要重启才能访问的问题
@Configuration
public class FileUploadConfig implements WebMvcConfigurer {
    @Value("${web.upload-path}")
    private String path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + path + "/");
    }
}
