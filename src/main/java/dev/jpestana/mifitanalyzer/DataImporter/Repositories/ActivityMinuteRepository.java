package dev.jpestana.mifitanalyzer.DataImporter.Repositories;

import dev.jpestana.mifitanalyzer.DataImporter.Entities.ActivityMinute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityMinuteRepository extends JpaRepository<ActivityMinute, Integer> {
}
