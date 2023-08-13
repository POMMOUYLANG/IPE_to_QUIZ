package Controllers;

public class BookList {

    private String ID;
    private String Name;
    private String Description;
    private String Author;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getPage() {
        return Page;
    }

    public void setPage(String page) {
        Page = page;
    }

    public String getPublic() {
        return Public;
    }

    public void setPublic(String aPublic) {
        Public = aPublic;
    }

    private String Page;
    private String Public;

    public BookList(String id, String name, String description, String author, String page, String aPublic) {
        ID = id;
        Name = name;
        Description = description;
        Author = author;
        Page = page;
        Public = aPublic;
    }
}
