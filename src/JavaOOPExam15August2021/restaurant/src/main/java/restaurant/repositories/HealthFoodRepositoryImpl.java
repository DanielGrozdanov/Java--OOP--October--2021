package restaurant.repositories;

import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.repositories.interfaces.HealthFoodRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HealthFoodRepositoryImpl implements HealthFoodRepository<HealthyFood> {

    private Map<String, HealthyFood> healthyFoods;

    public HealthFoodRepositoryImpl() {
        healthyFoods = new HashMap<>();
    }

    @Override
    public HealthyFood foodByName(String name) {
        return healthyFoods.get(name);
    }

    @Override
    public Collection<HealthyFood> getAllEntities() {
        return healthyFoods.values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void add(HealthyFood entity) {
        healthyFoods.put(entity.getName(), entity);
    }
}
