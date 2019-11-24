package ca.ubc.cs304.delegates;

public interface QueryDelegate<T> {
    void insert(String tableName, T data);
    void delete(String tableName, T data);
    void update(String tableName, T data);
    void viewAll(String tableName);
}
