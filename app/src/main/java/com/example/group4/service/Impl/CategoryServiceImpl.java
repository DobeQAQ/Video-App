package com.example.group4.service.Impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.group4.entity.CategoryEntity;
import com.example.group4.service.CategoryService;
import com.example.group4.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private DBHelper dbHelper;

    public CategoryServiceImpl(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    @Override
    public List<CategoryEntity> listCategory() {
        List<CategoryEntity> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from category";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setCategoryId(cursor.getInt(cursor.getColumnIndex("category_id")));
            categoryEntity.setCategoryName(cursor.getString(cursor.getColumnIndex("category_name")));
            list.add(categoryEntity);
        }
        return list;
    }
}
