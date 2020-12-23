package ru.arkaleks.telptracker.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.arkaleks.telptracker.controllers.mapper.EmployeeMapper;
import ru.arkaleks.telptracker.controllers.mapper.MessageMapper;
import ru.arkaleks.telptracker.controllers.mapper.TaskMapper;

@Configuration
public class MapperConfig {
    @Bean
    EmployeeMapper employeeMapper() {
        return Mappers.getMapper(EmployeeMapper.class);
    }

    @Bean
    TaskMapper taskMapper() {
        return Mappers.getMapper(TaskMapper.class);
    }

    @Bean
    MessageMapper messageMapper() {
        return Mappers.getMapper(MessageMapper.class);
    }
}
