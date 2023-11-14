package jpabook.jpashop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.request.CreateMember;
import jpabook.jpashop.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired MockMvc mockMvc;
    @Autowired EntityManager em;
    @Autowired ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach(){
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입")
    void test1() throws Exception{
        CreateMember createMember = CreateMember.builder()
                .name("member1")
                .city("진주")
                .street("사들로")
                .zipCode("157")
                .build();

        String json = objectMapper.writeValueAsString(createMember);
        MvcResult mvcResult = mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print()).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Member member = memberService.findOne(Long.valueOf(contentAsString));
        Assertions.assertThat(member.getName()).isEqualTo("member1");
        Assertions.assertThat(member.getAddress().getCity()).isEqualTo("진주");

    }
}