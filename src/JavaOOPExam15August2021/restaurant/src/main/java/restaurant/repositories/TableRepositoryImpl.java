package restaurant.repositories;

import restaurant.entities.tables.interfaces.Table;
import restaurant.repositories.interfaces.TableRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TableRepositoryImpl implements TableRepository<Table> {

    private Map<Integer, Table> tables;

    public TableRepositoryImpl() {
        tables = new HashMap<>();
    }

    @Override
    public Collection<Table> getAllEntities() {
        return tables.values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void add(Table entity) {
        tables.putIfAbsent(entity.getTableNumber(), entity);
    }

    @Override
    public Table byNumber(int number) {
        return tables.get(number);
    }
}
