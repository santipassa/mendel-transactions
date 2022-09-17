package com.mendel.transactions.repositories;


import java.util.List;

public interface Repository<T> {
    T save(T element);
    List<T> findAll();
}
