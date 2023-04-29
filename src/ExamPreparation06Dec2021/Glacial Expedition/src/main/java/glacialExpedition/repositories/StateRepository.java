package glacialExpedition.repositories;

import glacialExpedition.models.states.State;

import java.util.*;

public class StateRepository implements Repository<State> {
    private Map<String, State> stateMap;

    public StateRepository() {
        stateMap = new LinkedHashMap<>();
    }

    @Override
    public Collection<State> getCollection() {
        return Collections.unmodifiableCollection(stateMap.values());
    }

    @Override
    public void add(State state) {
        this.stateMap.put(state.getName(), state);
    }

    @Override
    public boolean remove(State state) {
        return this.stateMap.remove(state.getName()) != null;
    }

    @Override
    public State byName(String name) {
        return this.stateMap.get(name);
    }
}
