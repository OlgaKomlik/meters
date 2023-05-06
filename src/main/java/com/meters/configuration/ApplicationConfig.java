package com.meters.configuration;

import com.meters.entities.mappers.ManagerMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
@Bean
    public ModelMapper modelMapper() {return new ModelMapper();}

}
