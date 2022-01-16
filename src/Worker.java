public class Worker extends User{
    private int rank;
    private String[] rankTypes;

    public Worker(String firstName, String lastName, String userName, String password, int rank) {
        super(firstName, lastName, userName, password);
        this.rank = rank;
        this.rankTypes = new String[]{"Regular worker", "Director", "Member of the management team"};
    }

    public String appealTo(){
        return fullName()+" ("+rankTypes[rank-1]+")";
    }

    public int getRank() {
        return rank;
    }
}
