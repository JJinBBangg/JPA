package study.querydsl.controller;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Profile("local")
@RequiredArgsConstructor
public class InitData {

    private final InitMemberService initMemberService;

    @PostConstruct
    // @Transactional 과 함께 사용할 수 없어서 주입받아서 사용 
    void init(){
        initMemberService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitMemberService {

        private final EntityManager em;

        @Transactional
        public void init() {
            Team teamA = Team.builder()
                    .name("teamA")
                    .build();
            Team teamB = Team.builder()
                    .name("teamB")
                    .build();
            em.persist(teamA);
            em.persist(teamB);

            IntStream.range(1, 101).forEach(i -> {
                Team selectedTeam = (i % 2) != 0 ? teamA : teamB;
                em.persist(Member.builder()
                        .username("member" + i)
                        .age(i)
                        .team(selectedTeam)
                        .build());
            });
        }
    }
}
