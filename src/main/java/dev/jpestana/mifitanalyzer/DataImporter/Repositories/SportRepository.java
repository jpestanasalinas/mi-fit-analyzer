package dev.jpestana.mifitanalyzer.DataImporter.Repositories;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository extends JpaRepository<Sport, Integer> {
}
