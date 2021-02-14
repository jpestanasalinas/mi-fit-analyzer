package dev.jpestana.mifitanalyzer.DataImporter.Repositories;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.ActivityStage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityStageRepository extends JpaRepository<ActivityStage, Integer> {
}
