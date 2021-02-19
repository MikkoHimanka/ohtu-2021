
package ohtu;

public class Player {
    private String name;
    private String team;
    private int assists;
    private int goals;
    private String nationality;


    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String result;
        result = String.format("%-20s %-6s %-2d + %-2d = %-3d",
                this.name, this.team, this.goals, this.assists, this.goals + this.assists);
        return result;
    }
}
