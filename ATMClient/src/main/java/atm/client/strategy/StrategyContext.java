package atm.client.strategy;

import java.util.List;

public class StrategyContext {
    private List<Strategy> strategies;

    public StrategyContext(List<Strategy> strategies) {
        this.strategies = strategies;
    }

    public Strategy select(String opt) {
        for (Strategy strategy : strategies) {
            if (strategy.getType().name().equalsIgnoreCase(opt)) {
                return strategy;
            }
        }
        return null;
    }
}
