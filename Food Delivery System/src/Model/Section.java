package Model;

public class Section {
    private int section_id;
    private int menu_id;
    private String sectionName;

    public Section(int section_id, int menu_id, String sectionName) {
        this.section_id = section_id;
        this.menu_id = menu_id;
        this.sectionName = sectionName;
    }
    public Section(int menu_id, String sectionName) {
        this.menu_id = menu_id;
        this.sectionName = sectionName;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
