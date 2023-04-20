package vip.floatationdevice.progmgr.sqlmapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import vip.floatationdevice.progmgr.data.ProgramData;

import java.util.List;

@SuppressWarnings("unused")
@Mapper
public interface ProgramDataMapper
{

// ==================== READ RELATED FUNCTIONS ====================

    /**
     * Get the count of program data stored in the database.
     */
    int getDataCount();

    /**
     * Get a list of all program data (slow).
     */
    List<ProgramData> getAllData();

    /**
     * Get paged program data in groups of up to 5.
     * @param offset The location of the starting data in the database.
     * @return The list of ProgramData (max 5)
     */
    List<ProgramData> getPagedData(@Param("offset") int offset);

    /**
     * Check if a program with specified ID exists.
     * @param id The ID of the program.
     * @return 1 if the program exists.
     */
    int hasData(@Param("id") int id);

    /**
     * Get a single program data with the specified ID
     * @param id The ID of the program.
     * @return The ProgramData object of the requested program.
     */
    ProgramData getData(@Param("id") int id);

    /**
     * Get a list of all program data matching the search criteria.
     * Pass null to the search criteria you don't want to specify.
     * @param typeName The type of the program (full match).
     * @param actorCount The actor count of the program. Before 3 is "==", after is ">=".
     * @param name The name of the program (fuzzy match).
     * @return The list of all program data matching the search criteria.
     */
    List<ProgramData> findData(
            @Param("typeName") String typeName,
            @Param("actorCount") Integer actorCount,
            @Param("name") String name
    );

// ==================== WRITE RELATED FUNCTIONS ====================

    /**
     * Insert a program data into the database.
     * @param p The ProgramData object containing the data you want to insert into.
     *  ID will be ignored.
     * @return 1 if success.
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertData(@Param("p") ProgramData p);

    /**
     * Update a specified program data.
     * @param p The ProgramData object of the program you want to update.
     *  Will replace the data in the original with all the data in this object.
     *  ID must be included in this object.
     * @return 1 if success.
     */
    int updateData(@Param("p") ProgramData p);

    /**
     * Delete a program data with the specified ID.
     * @param id The ID of the program you want to delete.
     * @return 1 if success.
     */
    int deleteData(@Param("id") int id);
}
