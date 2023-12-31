package Services;

import Database.DatabaseManager;
import Model.Section;
import Repositories.SectionRepository;


import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SectionService {
    private final SectionRepository sectionRepository;
    private final Scanner scanner = new Scanner(System.in);

    public SectionService(DatabaseManager databaseManager) {
        this.sectionRepository = new SectionRepository(databaseManager);
    }

    public void createSectionForMenu(int menuId) {
        System.out.println("Enter the name of the new section:");
        String sectionName = scanner.nextLine();

        Section newSection = new Section(0, menuId, sectionName);
        if (sectionRepository.createSection(newSection)) {
            System.out.println("Model.Section added successfully!");
        } else {
            System.out.println("Failed to add section.");
        }
    }

    public List<Section> displaySectionsForMenu(int menuId) {
        List<Section> sections = sectionRepository.getSectionsByMenuId(menuId);

        if (sections.isEmpty()) {
            System.out.println("No sections available for this menu at the moment.");
            return Collections.emptyList();
        }

        System.out.println("Available sections:");
        for (int i = 0; i < sections.size(); i++) {
            System.out.println((i + 1) + ". " + sections.get(i).getSectionName());
        }
        return sections;
    }
}
