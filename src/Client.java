public class Client extends User{
    private boolean isClubMember;

    public Client(String firstName, String lastName, String userName, String password, boolean isClubMember) {
        super(firstName, lastName, userName, password);
        this.isClubMember = isClubMember;
    }

    public String appealTo(){
        return fullName()+(isClubMember ? " (VIP)" : "");
    }

    public boolean isClubMember() {
        return isClubMember;
    }

}
