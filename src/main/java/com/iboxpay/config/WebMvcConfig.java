package com.iboxpay.config;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.removeIf(converter -> {
      if (converter instanceof MappingJackson2HttpMessageConverter) {
        return true;
      }

      return false;
    });

    FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
    List<MediaType> mediaTypes = new ArrayList<>();
    // mediaTypes.add(new MediaType("text", "html", StandardCharsets.UTF_8));
    mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
    mediaTypes.add(new MediaType("application", "*+json", StandardCharsets.UTF_8));
    fastJsonConverter.setSupportedMediaTypes(mediaTypes);
    converters.add(fastJsonConverter);

  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.enableContentNegotiation(new MappingJackson2JsonView());
    registry.jsp("/WEB-INF/pages/", ".jsp");
  }

}
