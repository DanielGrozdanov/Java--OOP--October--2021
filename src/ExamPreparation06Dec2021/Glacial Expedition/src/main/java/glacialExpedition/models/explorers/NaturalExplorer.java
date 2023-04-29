package glacialExpedition.models.explorers;

public class NaturalExplorer extends BaseExplorer {
    private final static double INITIAL_ENERGY = 60;

    public NaturalExplorer(String name) {
        super(name, INITIAL_ENERGY);
    }

    @Override
    public void search() {
        if (super.getEnergy() <= 7) {
            super.setEnergy(0);
        } else {
            super.setEnergy(this.getEnergy() - 7);
        }
    }
}
