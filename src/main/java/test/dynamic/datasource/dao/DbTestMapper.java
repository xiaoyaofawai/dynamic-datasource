package test.dynamic.datasource.dao;

import java.util.List;
import test.dynamic.datasource.model.DbTest;

public interface DbTestMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table db_test
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table db_test
     *
     * @mbg.generated
     */
    int insert(DbTest record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table db_test
     *
     * @mbg.generated
     */
    DbTest selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table db_test
     *
     * @mbg.generated
     */
    List<DbTest> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table db_test
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DbTest record);
}