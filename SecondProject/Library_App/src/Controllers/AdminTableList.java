package Controllers;

public class AdminTableList {

    private String Name;
    private String Email;
    private String ID;
    private String Department;
    private String Duties;

    public String getName() {
        return Name;
    }
    public String getEmail() {
        return Email;
    }
    public String getID() {
        return ID;
    }
    public String getDepartment() {
        return Department;
    }
    public String getDuties() {
        return Duties;
    }
    public void setName(String name) {
        Name = name;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public void setID(String iD) {
        ID = iD;
    }
    public void setDepartment(String department) {
        Department = department;
    }
    public void setDuties(String duties) {
        Duties = duties;
    }

    public AdminTableList( String Name, String Email, String ID, String Department, String Duties) {

        this.Name = Name;
        this.Email = Email;
        this.ID = ID;
        this.Department = Department;
        this.Duties = Duties;
    }


}
