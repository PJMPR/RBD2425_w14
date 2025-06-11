package org.example.dao.filters;


import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;

public class FilterByName implements UsersFilterStrategy {
    private String name;

    public FilterByName(String name) {
        this.name = name;
    }

    public Bson toBson() {
        return Filters.eq("name", name);
    }
}
