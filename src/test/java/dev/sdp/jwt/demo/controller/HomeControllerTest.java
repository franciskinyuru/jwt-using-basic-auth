package dev.sdp.jwt.demo.controller;

import dev.sdp.jwt.demo.configs.SecurityConfig;
import dev.sdp.jwt.demo.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest({HomeController.class, AuthController.class})
@Import({SecurityConfig.class, TokenService.class})
class HomeControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    void rootWhenUnauthenticatedThen401()throws Exception{
        this.mvc.perform(get("/")).andExpect(status().isUnauthorized());
    }
    @Test
    void rootWhenAuthenticatedThenSaysHelloUser()throws Exception{
        MvcResult result= this.mvc.perform(post("/token")
                .with(httpBasic("francis","francis"))).
                andExpect(status().isOk()).andReturn();
        String token = result.getResponse().getContentAsString();
        this.mvc.perform(get("/").header("Authorization", "Bearer "+token))
                .andExpect(content().string("Hello my name is francis"));
    }

    @Test
    @WithMockUser
    public void rootWithMockUserStatusIsOk() throws Exception{
        this.mvc.perform(get("/")).andExpect(status().isOk());
    }
}