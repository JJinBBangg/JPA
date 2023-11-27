package study.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class QuerydslApplicationTests {

	EntityManager em;

	JPAQueryFactory query;


	@Autowired
	public QuerydslApplicationTests(EntityManager em) {
		this.em = em;
		this.query = new JPAQueryFactory(em);
	}


	@Test
	@DisplayName("Querydsl 세팅 테스트")
	void contextLoads() {
		Member member1 = Member.builder()
				.username("member1")
				.build();
		Member member2 = Member.builder()
				.username("member2")
				.build();

		em.persist(member1);
		em.persist(member2);

		QMember qMember = new QMember("m");

		List<Member> members = query
				.select(qMember)
				.from(qMember)
				.fetch();

		assertEquals(members.get(0), member1,"회원 생성");
		assertEquals(members.get(0).getUsername(), member1.getUsername(),"회원이름 비교");
	}

}
