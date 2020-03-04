package es.uji.ei1027.elderlypeople;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

@SpringBootApplication
public class ElderlyPeopleApplication {

  private static final Logger log = Logger.getLogger(ElderlyPeopleApplication.class.getName());

  public static void main(String[] args) {
     // Auto-configura l'aplicació
     new SpringApplicationBuilder(ElderlyPeopleApplication.class).run(args);
  }
}
