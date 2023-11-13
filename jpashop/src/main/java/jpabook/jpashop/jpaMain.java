package jpabook.jpashop;

import jakarta.persistence.*;
import jpabook.jpashop.domain.*;

import java.util.List;

public class jpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
//            System.out.println("===================");
//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Team teamB = new Team();
//            team.setName("teamB");
//            em.persist(teamB);
//
//            Member findMember = new Member();
//            findMember.setName("findMember");
//            findMember.setTeam(team);
//            em.persist(findMember);
//
//            Member member2 = new Member();
//            member2.setName("member2");
//            member2.setTeam(teamB);
//            em.persist(member2);
//
//            em.flush();
//            em.clear();
//
//            System.out.println("===================");
//            Member findMember = em.find(Member.class, member2.getId());
//            System.out.println("findMember.getTeam().getClass() = " + findMember.getTeam().getClass());
//            // team의 정보조회 없이 team.getClass(); 조회시 Proxy 타입으로 조회됨
////            System.out.println("findMember.getTeam().getName() = " + findMember.getTeam().getName());
//            // 위 team을 불러오게되면 team을 join해서 조회
//
//            //EAGER설정 시 즉시조회
////            Member findMember2 = em.find(Member.class, member2.getId());
////            List<Member> resultMembers = em.createQuery("select m from Member m", Member.class).getResultList();
//            List<Member> resultMembers = em.createQuery("select m from Member m join fetch m.team", Member.class).getResultList();
//            // 위처럼 JPQL 를 사용하는 경우 member만 조회 하는 쿼리를 만들어서 조회했지만 EAGER설정으로
//            // team조회하는 쿼리가 한번더 나가게됨
//            // 위같은 N+1 문제를 해결하기 위해서 LAZY설정을 권장(실무에서 반드시)
//            // 경우에 따라 member정보와 team정보를 같이 가져오고 싶은경우 fetch join 이라는 기능 사용

//            Child child1 = new Child();
//            Child child2 = new Child();
//
//            Parent parent = new Parent();
//            parent.addChild(child1);
//            parent.addChild(child2);
//
//            em.persist(parent); //cascade 의 영향받음(ALL, PERSIST)
////            em.persist(child1);
////            em.persist(child2);
//
//            em.flush();
//            em.clear();
//
//            Parent findParent = em.find(Parent.class, parent.getId());
//            findParent.getChildList().remove(0);
//            // 컬렉션에서 삭제했을 뿐인데 영속컨텍스트에 삭제함(orphanRemoval =true) 설정
//
//            Member member1 = new Member();
//            Member member2 = new Member();
//
//            member1.setName("newMember1");
//            member2.setName("newMember2");
//            Address buildAddress = new Address.Builder()
//                    .city("jinju")
//                    .street("sadlo")
//                    .zipCode("1234")  //없어도 null 로 들어가고 컴파일 오류발생하지 않음 builder pattern 사용
//                    .build();
//
//            member1.getHomeAddress();
//            Address buildAddress2 = new Address.Builder()
//                    .city("jinju1")
//                    .street("sadlo")
//                    .zipCode("12344")  //없어도 null 로 들어가고 컴파일 오류발생하지 않음 builder pattern 사용
//                    .build();
//            member1.setHomeAddress(buildAddress2);
//
//            member1.getAddressList().add(buildAddress);
//            // 수정하고싶어도 값타입인경우 아래처럼 새로운 객체로 이어서 수정하게 구조를 만들어야함
//            // Address 의 setter를 모두 없애고 builder pattern 활용하여 수정이 불가능하여
//            // 수정이 필요한 시점에 새로운 객채를 만들어서 넣어야함
//            // 다른맴버의 Address를 불러와서 set하는 방법은 막을 수 없을까?
//            member2.setHomeAddress(member1.getHomeAddress());
//            member2.getAddressList().add(member2.getHomeAddress());
//            // 위처럼 저장할 수는 있으나
//            // 결국 member1의 값을 수정하려 하면 새로운 객체로 갈아끼워야하기때문에 member2에 영향을 미치치 않는가?
////            member1.setHomeAddress(new Address.Builder().city("44").street("55").zipCode("66").build());
//            // 위에서 입력되는 값이 기존값과 비교하거나 필요시 저장하지 않는 로직은 사용자 편의메서드 사용하여 로직 구성
//            // 결과는 member1의 값을 수정하더라도 새로운객체로 갈아끼웠기때문에 member2의 homeAddress값에는 영향을 미치지않음
//            // 불변성 문제 해결!
//            member1.getAddressList().add(member1.getHomeAddress());
//            member1.getFavoriteFood().add("피자");
//            member1.getFavoriteFood().add("햄버거");
//            member1.getFavoriteFood().add("토스트");
//
//            em.persist(member1);
//            em.persist(member2);
//            // 피자를 지우고 백숙으로 바꾸고싶으면
//            // 자료구조상 관계없을 뿐아니라 테이블 상 member_id 와 피자를 PK로 사용하기때문에
//            // 별도로 삭제하는 방법밖에 없음
//            member1.getFavoriteFood().remove("피자");
//            member1.getFavoriteFood().add("백숙");
//            // 여기서 주목할 점은 FavoriteFood나 AddressList 의 경우 별도의 테이블로 구성되어있지만
//            // @ElementCollection과 @CollectionTable으로 만들어져서
//            // cascadeType.ALL과 고아객체의 성격을 지니고있으며
//            // fetch속성은 LAZY로 default값을 가지게 됨
//
//            //member1에서 기존의 buildAddress 값을 지우고싶은경우 같은형태의 값을 넣으면
//            // equals 로 비교해서 지워줌 하지만 반드시 equals 와 hashCode override 하고 사용 권장
//
//            em.flush();
//            em.clear();
//
//
//            Member findMember1 = em.find(Member.class, member1.getId());
//            Member findMember2 = em.find(Member.class, member2.getId());
//
//            List<Address> addressList = findMember1.getAddressList();
//            for (Address address : addressList) {
//                System.out.println("address = " + address.getCity());
//                System.out.println("street = " + address.getStreet());
//                System.out.println("zipCode = " + address.getZipCode());
//                System.out.println("==========================");
//            }
//
//            findMember1.getAddressList().remove(buildAddress);
//            findMember1.getAddressList().remove(buildAddress2);
//
//            findMember1.getAddressList().add(new Address.Builder()
//                    .city("44")
//                    .street("55")
//                    .zipCode("66")
//                    .build());
////            findMember1.getAddressList().add(new Address.Builder()
////                    .city("44")
////                    .street("55")
////                    .zipCode("66")
////                    .build());
////            findMember1.getAddressList().add(new Address.Builder()
////                    .city("44")
////                    .street("55")
////                    .zipCode("66")
////                    .build());
//            //collection 실무사용 X list 같은경우 기존 pk값인 MEMBER_ID에 걸리 모든 데이터를 지우고
//            //현재 컬렉션 list에 남아있는 모든값을 다시 밀어넣음 (절대사용하면안되는상태)
//            // 해결방법은 @OrderColumn 사용 하면되기는하나 별도의 컬럼에 순서를 매기개되면
//            // Entity를 사용하는것과 별반 다르지 않으니 사용하지않음
//
//            findMember1.setHomeAddress(findMember1.getAddressList().get(0));
//            String city1 = findMember1.getHomeAddress().getCity();
//            System.out.println("city1 = " + city1);
//            String city2 = findMember2.getHomeAddress().getCity();
//            System.out.println("city2 = " + city2);
            Member member = new Member();
            member.setName("member1");
            member.setHomeAddress(new Address.Builder()
                            .street(":")
                            .city(":")
                            .zipCode(":")
                            .build());

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
