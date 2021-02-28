package dev.jpestana.mifitanalyzer.DataImporter.Repositories;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Sleep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SleepRepository extends JpaRepository<Sleep, Integer> {
}
