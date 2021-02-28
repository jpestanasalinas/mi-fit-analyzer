package dev.jpestana.mifitanalyzer.DataImporter.Repositories;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.HeartrateAuto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartrateAutoRepository extends JpaRepository<HeartrateAuto, Integer> {
}
