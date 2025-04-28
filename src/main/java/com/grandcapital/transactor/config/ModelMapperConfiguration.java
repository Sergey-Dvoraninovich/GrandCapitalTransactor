package com.grandcapital.transactor.config;

import com.grandcapital.transactor.DAO.entity.EmailData;
import com.grandcapital.transactor.DAO.entity.PhoneData;
import com.grandcapital.transactor.DAO.entity.User;
import com.grandcapital.transactor.service.model.UserDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    @Qualifier("advancedModelMapper")
    public ModelMapper advancedModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        customizeUserDtoMapper(modelMapper);
        return modelMapper;
    }

    private void customizeUserDtoMapper(ModelMapper modelMapper) {
        modelMapper.addConverter(new Converter<User, UserDTO>() {
            @Override
            public UserDTO convert(MappingContext<User, UserDTO> mappingContext) {
                User entity = mappingContext.getSource();
                UserDTO dto = new UserDTO();
                dto.setId(entity.getId());
                dto.setName(entity.getName());
                dto.setDateOfBirth(entity.getDateOfBirth());
                dto.setPhones(entity.getPhones().stream().map(PhoneData::getPhone).toList());
                dto.setEmails(entity.getEmails().stream().map(EmailData::getEmail).toList());
                return dto;
            }
        });
    }
}
