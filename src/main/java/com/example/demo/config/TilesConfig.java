package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
public class TilesConfig {
    @Bean
	public TilesConfigurer tilesConfigurer() {
		final TilesConfigurer configurer = new TilesConfigurer();
		//configurer.setDefinitions(new String[] {"classpath*:/tiles/tiles.xml"});
		configurer.setDefinitions(new String[] {"/WEB-INF/tiles/tiles.xml"});
		//configurer.setDefinitions(new String[] {"/WEB-INF/tiles/tiles_member.xml","/WEB-INF/tiles/tiles_board.xml"});
		configurer.setCheckRefresh(true);
		return configurer;
	}
	
    @Bean
	public TilesViewResolver tilesViewResolver() {
		final TilesViewResolver resolver = new TilesViewResolver();
		resolver.setViewClass(TilesView.class);
		resolver.setOrder(1);
		return resolver;
	}
}
