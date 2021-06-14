package com.codex.shop.controller;


import com.codex.shop.dto.UserDto;
import com.codex.shop.entity.User;
import com.codex.shop.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class RegisterControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  public void registerUser() throws Exception {
    String userName= "name";
    String userEmail = "email@email.com";
    String userPassword = "password";
    User userMock = new User(userName, userPassword, userEmail);
    UserDto userDtoMock = new UserDto(1L, userName, userEmail);
    when(userService.registerUser(userMock)).thenReturn(userDtoMock);
    this.mockMvc
        .perform(post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"name\": \"name\", \"email\": \"email@email.com\", \"password\": \"password\"}"))
        .andExpect(status().isCreated())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("name"))
        .andExpect(jsonPath("$.email").value("email@email.com"));
  }
}

