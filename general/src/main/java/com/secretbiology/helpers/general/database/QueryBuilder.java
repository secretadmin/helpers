package com.secretbiology.helpers.general.database;

import java.util.List;

@Deprecated
public class QueryBuilder {

    private String query = "";


    public QueryBuilder() {
    }

    public static String buildTableQuery(String tableName, List<String[]> columns) {
        String query = "CREATE TABLE " + tableName + " ( ";
        for (int i = 0; i < columns.size() - 1; i++) {
            query += columns.get(i)[0].trim() + " " + columns.get(i)[1].trim() + " ,";
        }
        query += columns.get(columns.size() - 1)[0].trim() + " " + columns.get(columns.size() - 1)[1].trim() + " )";
        return query;
    }

    public String build() {
        return query;
    }

    public QueryBuilder select() {
        query = query.trim();
        query += " SELECT ";
        return this;
    }

    public QueryBuilder use(String string) {
        query = query.trim();
        query += " " + string + " ";
        return this;
    }

    public QueryBuilder limit(int value) {
        query = query.trim();
        query += " LIMIT " + String.valueOf(value) + " ";
        return this;
    }

    public QueryBuilder offset(int value) {
        query = query.trim();
        query += " OFFSET " + String.valueOf(value) + " ";
        return this;
    }

    public QueryBuilder where() {
        query = query.trim();
        query += " WHERE ";
        return this;
    }

    public QueryBuilder exists() {
        query = query.trim();
        query += " EXISTS ";
        return this;
    }

    public QueryBuilder openBracket() {
        query = query.trim();
        query += " ( ";
        return this;
    }

    public QueryBuilder closeBracket() {
        query = query.trim();
        query += " ) ";
        return this;
    }

    public QueryBuilder column(String columnName) {
        query = query.trim();
        query += " " + columnName + " ";
        return this;
    }

    public QueryBuilder selectMax(String columnName) {
        query = query.trim();
        query += " SELECT MAX(" + columnName + ") ";
        return this;
    }

    public QueryBuilder max(String columnName) {
        query = query.trim();
        query += " MAX(" + columnName + ") ";
        return this;
    }

    public QueryBuilder whereColumn(String columnName) {
        query = query.trim();
        query += " WHERE " + columnName + " ";
        return this;
    }

    public QueryBuilder isEqualTo(String value) {
        query = query.trim();
        query += " = '" + value + "' ";
        return this;
    }

    public QueryBuilder selectAll() {
        query = query.trim();
        query += " SELECT * ";
        return this;
    }

    public QueryBuilder selectColumn(String columnName) {
        query = query.trim();
        query += " SELECT " + columnName + " ";
        return this;
    }

    public QueryBuilder fromTable(String tableName) {
        query = query.trim();
        query += " FROM " + tableName + " ";
        return this;
    }

    public QueryBuilder whereColumnIsEqual(String columnName, String value) {
        query = query.trim();
        query += " WHERE " + columnName + " ='" + value + "' ";
        return this;
    }

    public QueryBuilder columnIsEqual(String columnName, String value) {
        query = query.trim();
        query += " " + columnName + " ='" + value + "' ";
        return this;
    }

    public QueryBuilder and() {
        query = query.trim();
        query += " AND ";
        return this;
    }

    public QueryBuilder or() {
        query = query.trim();
        query += " OR ";
        return this;
    }

    public QueryBuilder having() {
        query = query.trim();
        query += " HAVING ";
        return this;
    }

    public QueryBuilder groupBy() {
        query = query.trim();
        query += " GROUP BY ";
        return this;
    }

    public QueryBuilder orderBy() {
        query = query.trim();
        query += " ORDER BY ";
        return this;
    }

    public QueryBuilder in() {
        query = query.trim();
        query += " IN ";
        return this;
    }

    public QueryBuilder update() {
        query = query.trim();
        query += " UPDATE ";
        return this;
    }

    public QueryBuilder set() {
        query = query.trim();
        query += " SET ";
        return this;
    }

    public QueryBuilder distinct() {
        query = query.trim();
        query += " DISTINCT ";
        return this;
    }

    public QueryBuilder limit() {
        query = query.trim();
        query += " LIMIT ";
        return this;
    }

    public QueryBuilder ascending() {
        query = query.trim();
        query += " ASC ";
        return this;
    }

    public QueryBuilder descending() {
        query = query.trim();
        query += " DESC ";
        return this;
    }

    public QueryBuilder delete() {
        query = query.trim();
        query += " DELETE ";
        return this;
    }

    public QueryBuilder insert() {
        query = query.trim();
        query += " INSERT ";
        return this;
    }

    public QueryBuilder insertInto() {
        query = query.trim();
        query += " INSERT INTO ";
        return this;
    }

    public QueryBuilder alterTableAdd(String tableName, String newColumn, String typeOfColumn) {
        query = query.trim();
        query += " ALTER TABLE " + tableName + " ADD " + newColumn + " " + typeOfColumn + " ";
        return this;
    }

    public QueryBuilder alterTableRename(String oldTableName, String newTableName) {
        query = query.trim();
        query += " ALTER TABLE " + oldTableName + " RENAME TO " + newTableName + " ";
        return this;
    }


    public QueryBuilder columnList(String... allColumns) {
        query = query.trim();
        query += " ";
        for (int i = 0; i < allColumns.length - 1; i++) {
            query += allColumns[i] + " , ";
        }
        query += allColumns[allColumns.length - 1] + " ";
        return this;
    }

}
