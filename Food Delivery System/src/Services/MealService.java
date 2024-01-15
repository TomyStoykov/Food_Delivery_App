package Services;

import Model.Meal;
import Repositories.MealRepository;
import java.util.Collections;
import java.util.List;

public class MealService {
    private final Repositories.MealRepository MealRepository;

    public MealService(MealRepository MealRepository) {
        this.MealRepository = MealRepository;
    }

    public List<Meal> getMealsBySection(int sectionId) {
        return MealRepository.getMealsBySectionId(sectionId);
    }

    public List<Meal> getMealsForSection(int sectionId) {
        List<Meal> meals = getMealsBySection(sectionId);
        if (meals.isEmpty()) {
            System.out.println("No meals available for this section at the moment.");
            return Collections.emptyList();
        }

        for (int i = 0; i < meals.size(); i++) {
            System.out.println((i + 1) + ". " + meals.get(i).getDescription() + " - $" + meals.get(i).getPrice());
        }
        return meals;
    }
}

