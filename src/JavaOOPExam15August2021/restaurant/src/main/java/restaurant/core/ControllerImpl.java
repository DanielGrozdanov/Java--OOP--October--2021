package restaurant.core;

import restaurant.common.ExceptionMessages;
import restaurant.common.OutputMessages;
import restaurant.core.interfaces.Controller;
import restaurant.entities.drinks.Fresh;
import restaurant.entities.drinks.Smoothie;
import restaurant.entities.healthyFoods.Salad;
import restaurant.entities.healthyFoods.VeganBiscuits;
import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.entities.tables.Indoors;
import restaurant.entities.tables.InGarden;
import restaurant.entities.tables.interfaces.Table;
import restaurant.repositories.interfaces.*;

import java.util.Collection;

public class ControllerImpl implements Controller {
    private HealthFoodRepository<HealthyFood> healthyFoodRepository;
    private BeverageRepository<Beverages> beverageRepository;
    private TableRepository<Table> tableRepository;
    private double totalIncome;

    public ControllerImpl(HealthFoodRepository<HealthyFood> healthFoodRepository,
                          BeverageRepository<Beverages> beverageRepository,
                          TableRepository<Table> tableRepository) {
        this.healthyFoodRepository = healthFoodRepository;
        this.beverageRepository = beverageRepository;
        this.tableRepository = tableRepository;
    }

    @Override
    public String addHealthyFood(String type, double price, String name) {
        HealthyFood typeOfFood = type.equals("Salad") ? new Salad(name, price) : new VeganBiscuits(name, price);

        HealthyFood food = healthyFoodRepository.foodByName(typeOfFood.getName());
        if (food == null) {
            this.healthyFoodRepository.add(typeOfFood);
            return String.format(OutputMessages.FOOD_ADDED, name);
        }
        throw new IllegalArgumentException(String.format(ExceptionMessages.FOOD_EXIST, name));
    }

    @Override
    public String addBeverage(String type, int counter, String brand, String name) {

        Beverages typeOfBeverage = type.equals("Fresh") ? new Fresh(name, counter, brand) : new Smoothie(name, counter, type);

        Beverages beverages = beverageRepository.beverageByName(typeOfBeverage.getName(), typeOfBeverage.getBrand());
        if (beverages == null) {
            beverageRepository.add(typeOfBeverage);
            return String.format(OutputMessages.BEVERAGE_ADDED, type, brand);
        }
        throw new IllegalArgumentException(String.format(ExceptionMessages.BEVERAGE_EXIST, name));
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {

        Table typeOfTable = type.equals("Indoors") ? new Indoors(tableNumber, capacity) : new InGarden(tableNumber, capacity);

        Table table = tableRepository.byNumber(tableNumber);
        if (table == null) {
            tableRepository.add(typeOfTable);
            return String.format(OutputMessages.TABLE_ADDED, tableNumber);
        }
        throw new IllegalArgumentException(String.format(ExceptionMessages.TABLE_IS_ALREADY_ADDED, tableNumber));
    }

    @Override
    public String reserve(int numberOfPeople) {
        Collection<Table> tables = this.tableRepository.getAllEntities();

        Table table = tables.stream()
                .filter(t -> !t.isReservedTable() && t.getSize() >= numberOfPeople)
                .findFirst()
                .orElse(null);

        String message = String.format(OutputMessages.RESERVATION_NOT_POSSIBLE, numberOfPeople);

        if (table != null) {
            table.reserve(numberOfPeople);
            message = String.format(OutputMessages.TABLE_RESERVED, table.getTableNumber(), numberOfPeople);
        }

        return message;
    }

    @Override
    public String orderHealthyFood(int tableNumber, String healthyFoodName) {

        Table table = tableRepository.byNumber(tableNumber);
        HealthyFood healthyFood = healthyFoodRepository.foodByName(healthyFoodName);

        String message = String.format(OutputMessages.WRONG_TABLE_NUMBER, tableNumber);
        if (table != null) {
            if (healthyFood != null) {
                table.orderHealthy(healthyFood);
                return String.format(OutputMessages.FOOD_ORDER_SUCCESSFUL, healthyFoodName, tableNumber);
            }
            message = String.format(OutputMessages.NONE_EXISTENT_FOOD, healthyFoodName);
        }
        return message;
    }

    @Override
    public String orderBeverage(int tableNumber, String name, String brand) {

        Table table = tableRepository.byNumber(tableNumber);
        Beverages beverages = this.beverageRepository.beverageByName(name, brand);

        String message = String.format(OutputMessages.WRONG_TABLE_NUMBER, tableNumber);
        if (table != null) {
            if (beverages != null) {
                table.orderBeverages(beverages);
                return String.format(OutputMessages.BEVERAGE_ORDER_SUCCESSFUL, name, tableNumber);
            }
            message = String.format(OutputMessages.NON_EXISTENT_DRINK, name, brand);
        }
        return message;
    }

    @Override
    public String closedBill(int tableNumber) {

        Table table = this.tableRepository.byNumber(tableNumber);
        double bill = table.bill();
        table.clear();
        totalIncome += bill;
        return String.format(OutputMessages.BILL, tableNumber, bill);
    }


    @Override
    public String totalMoney() {

        return String.format(OutputMessages.TOTAL_MONEY,totalIncome);
    }
}
