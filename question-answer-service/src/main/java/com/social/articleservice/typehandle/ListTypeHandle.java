package com.social.articleservice.typehandle;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class ListTypeHandle extends BaseTypeHandler<List<?>> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<?> objects, JdbcType jdbcType) throws SQLException {
        String tag = JSON.toJSONString(objects);
        preparedStatement.setString(i, tag);
    }

    @Override
    public List<?> getNullableResult(ResultSet resultSet, String s) throws SQLException {

        return JSON.parseArray(resultSet.getString(s), Object.class);
    }

    @Override
    public List<?> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return JSON.parseArray(resultSet.getString(i), Object.class);
    }

    @Override
    public List<?> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return JSON.parseArray(callableStatement.getString(i), Object.class);
    }
}
