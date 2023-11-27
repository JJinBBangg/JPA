package study.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
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
				.name("member1")
				.build();
		Member member2 = Member.builder()
				.name("member2")
				.build();

		em.persist(member1);
		em.persist(member2);

		QMember qMember = new QMember("m");

		List<Member> members = query
				.select(qMember)
				.from(qMember)
				.fetch();

		assertEquals(members.get(0), member1,"회원 비교");
		assertEquals(members.get(0).getName(), member1.getName());
//		System.out.println("members.get(0).getName() = " + members.get(0).getName());
//		System.out.println("member.getName() = " + member.getName());
	}

}
