package study.datajpa.repository;


public interface NameOnly {
    String getName();
    TeamInfo getTeam();
    interface TeamInfo {
        String getName();
    }
}
