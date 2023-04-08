package vip.floatationdevice.progmgr.sqlmapper;

import org.apache.ibatis.annotations.*;
import vip.floatationdevice.progmgr.data.ProgramData;

import java.util.List;

@SuppressWarnings("unused")
@Mapper
public interface ProgramDataMapper
{

// ==================== READ RELATED FUNCTIONS ====================

    // data count
    @Select("SELECT COUNT(*) FROM programs")
    int getDataCount();

    // list all data
    @Select("SELECT * FROM programs")
    List<ProgramData> getAllData();

    // list data by page
    @Select("SELECT * FROM programs LIMIT #{offset}, 20")
    List<ProgramData> getPagedData(@Param("offset") int offset);

    // check existence
    @Select("SELECT COUNT(*) FROM programs WHERE id = #{id}")
    int hasData(@Param("id") int id);

    // get data
    @Select("SELECT * FROM programs WHERE id = #{id}")
    ProgramData getData(@Param("id") int id);

    // find data
    @Select({
            "<script>",
            "SELECT * FROM programs",
            "<where>",
            "<if test='typeName != null'>",
            "AND typeName = #{typeName}",
            "</if>",
            "<if test='actorCount != null'>",
            "AND actorCount = #{actorCount}",
            "</if>",
            "<if test='name != null'>",
            "AND name LIKE #{name}",
            "</if>",
            "</where>",
            "</script>"
    })
    List<ProgramData> findData(
            @Param("typeName") String typeName,
            @Param("actorCount") Integer actorCount,
            @Param("name") String name
    );

// ==================== WRITE RELATED FUNCTIONS ====================

    // insert data
    @Insert("INSERT INTO programs(name, typeName, actorList, actorCount, view) VALUES(#{name}, #{typeName}, #{actorList}, #{actorCount}, #{view})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertData(ProgramData p);

    // update data
    @Update("UPDATE programs SET name = #{name}, typeName = #{typeName}, actorList = #{actorList}, actorCount = #{actorCount}, view = #{view} WHERE id = #{id}")
    int updateData(ProgramData p);

    // delete data
    @Delete("DELETE FROM programs WHERE id = #{id}")
    int deleteData(@Param("id") int id);
}
