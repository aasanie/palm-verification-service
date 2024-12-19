package com.palm.repository;

import com.palm.model.PalmData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PalmDataRepository extends JpaRepository<PalmData, String> {

    /**
     * Find all PalmData records by a specific school ID.
     *
     * @param schoolId The school ID.
     * @return A list of PalmData records associated with the given school ID.
     */
    List<PalmData> findBySchoolId(String schoolId);

    /**
     * Delete all PalmData records associated with a specific school ID.
     *
     * @param schoolId The school ID.
     */
    void deleteBySchoolId(String schoolId);
}

