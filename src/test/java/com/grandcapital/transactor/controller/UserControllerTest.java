package com.grandcapital.transactor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grandcapital.transactor.DAO.EmailDataRepository;
import com.grandcapital.transactor.DAO.PhoneDataRepository;
import com.grandcapital.transactor.DAO.UsersRepository;
import com.grandcapital.transactor.DAO.entity.EmailData;
import com.grandcapital.transactor.DAO.entity.PhoneData;
import com.grandcapital.transactor.DAO.entity.User;
import com.grandcapital.transactor.controller.model.EmailRequest;
import com.grandcapital.transactor.controller.model.UpdateEmailRequest;
import com.grandcapital.transactor.service.IJwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    EmailDataRepository emailDataRepository;

    @Autowired
    PhoneDataRepository phoneDataRepository;

    @Autowired
    IJwtService jwtService;

    @Test
    void emailModifications() throws Exception {
        User user = new User();
        user.setName("Ben_Zimmermann");
        user.setDateOfBirth(LocalDate.of(2002, 2, 2));
        user.setPassword("$2a$10$OvFROh3eYlAxtxL6NO800ORcmwYlWCBHCGv/yhHTslBuJEofxWFCu");
        usersRepository.save(user);

        Long userId = user.getId();

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail("zimmermann@gmail.com");

        EmailRequest secondEmailRequest = new EmailRequest();
        secondEmailRequest.setEmail("ben@gmail.com");

        EmailRequest incorrectEmailRequest = new EmailRequest();
        incorrectEmailRequest.setEmail("incorrect.ema");

        UpdateEmailRequest updateEmailRequest = new UpdateEmailRequest();
        updateEmailRequest.setOldEmail("zimmermann@gmail.com");
        updateEmailRequest.setNewEmail("zimmermann2002@gmail.com");

        String token = jwtService.generateToken(user);

        mockMvc.perform(put("/users/" + userId + "/email")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(emailRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/users/" + userId + "/email")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(secondEmailRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/users/" + userId + "/email")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(incorrectEmailRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(put("/users/" + userId + "/email")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(emailRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(patch("/users/" + userId + "/email")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(updateEmailRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/users/" + userId + "/email")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(secondEmailRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(put("/users/" + userId + "/email")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(secondEmailRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        User otherUser = new User();
        otherUser.setName("Bob Artov");
        otherUser.setDateOfBirth(LocalDate.of(1992, 2, 2));
        otherUser.setPassword("$2a$10$OvFROh3eYlAxtxL6NO800ORcmwYlWCBHCGv/yhHTslBuJEofxWFCu");
        usersRepository.save(otherUser);

        Long otherUserId = otherUser.getId();
        mockMvc.perform(put("/users/" + otherUserId + "/email")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(emailRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void usersSearch() throws Exception {
        User user = new User();
        user.setName("Bob Zimmermann");
        user.setDateOfBirth(LocalDate.of(2002, 2, 2));
        user.setPassword("$2a$10$OvFROh3eYlAxtxL6NO800ORcmwYlWCBHCGv/yhHTslBuJEofxWFCu");
        usersRepository.save(user);

        EmailData emailData = new EmailData();
        emailData.setUser(user);
        emailData.setEmail("bob.zimmermann@gmail.com");
        emailDataRepository.save(emailData);

        PhoneData phoneData = new PhoneData();
        phoneData.setUser(user);
        phoneData.setPhone("+375299970111");
        phoneDataRepository.save(phoneData);


        User user2 = new User();
        user2.setName("Ingrid Svendsdatter");
        user2.setDateOfBirth(LocalDate.of(2001, 11, 1));
        user2.setPassword("$2a$10$OvFROh3eYlAxtxL6NO800ORcmwYlWCBHCGv/yhHTslBuJEofxWFCu");
        usersRepository.save(user2);

        EmailData emailData2 = new EmailData();
        emailData2.setUser(user2);
        emailData2.setEmail("ingrid.svendsdatter@gmail.com");
        emailDataRepository.save(emailData2);

        PhoneData phoneData2 = new PhoneData();
        phoneData2.setUser(user2);
        phoneData2.setPhone("+375299970222");
        phoneDataRepository.save(phoneData2);

        MvcResult result = mockMvc.perform(get("/users?email=ingrid.svendsdatter@gmail.com")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String name = objectMapper.readTree(responseBody)
                .get("content").get(0).get("name").asText();
        Assertions.assertEquals("Ingrid Svendsdatter", name);


        result = mockMvc.perform(get("/users?phone=+375299970111")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk())
                .andReturn();

        responseBody = result.getResponse().getContentAsString();
        name = objectMapper.readTree(responseBody)
                .get("content").get(0).get("name").asText();
        Assertions.assertEquals("Bob Zimmermann", name);

    }

}
