package com.mendel.transactions.repositories.datasources;

import com.mendel.transactions.models.Model;

public interface DataSource<T extends Model, D> {
    T save(T element);
    D getData();
}
