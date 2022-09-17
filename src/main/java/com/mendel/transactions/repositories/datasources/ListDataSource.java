package com.mendel.transactions.repositories.datasources;

import com.mendel.transactions.models.Model;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ListDataSource<T extends Model> implements DataSource<T, List<T>> {
    private final List<T> data = new ArrayList<>();

    @Override
    public synchronized T save(T element) {
        data.add(element);
        return element;
    }

    @Override
    public List<T> getData() {
        return data;
    }
}
