package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Major;
import com.example.projetjee.util.DatabaseManager;
import com.example.projetjee.util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MajorDAO {
    private static final String MAJOR_TABLE = "major";
    private static final String MAJOR_ID = "majorId";
    private static final String MAJOR_NAME = "majorName";

    public static List<Major> getAllMajor() {
        List<Major> majors = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "SELECT * FROM " + MAJOR_TABLE;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Major major = new Major();

                major.setMajorId(resultSet.getInt(MAJOR_ID));
                major.setMajorName(resultSet.getString(MAJOR_NAME));

                majors.add(major);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return majors;
    }

}
