package org.example.dao.filters;


import org.bson.conversions.Bson;

public interface UsersFilterStrategy {
    Bson toBson();
}
