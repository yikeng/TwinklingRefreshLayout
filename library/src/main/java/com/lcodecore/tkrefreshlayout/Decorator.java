package com.lcodecore.tkrefreshlayout;

/**
 * Created by lcodecore on 2017/3/3.
 */

public abstract class Decorator implements IDecorator {
    protected IDecorator decorator;
    protected TwinklingRefreshLayout.CoProcessor cp;

    public Decorator(TwinklingRefreshLayout.CoProcessor processor, IDecorator decorator1) {
        cp = processor;
        decorator = decorator1;
    }
}
