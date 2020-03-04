package es.uji.ei1027.elderlypeople;
 
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import es.uji.ei1027.elderlypeople.categoria.Categoria;
import es.uji.ei1027.elderlypeople.categoria.CategoriaFederacio;
import es.uji.ei1027.elderlypeople.categoria.CategoriaWorldCup;

import javax.sql.DataSource;
 
@Configuration
public class ElderlyPeopleConfiguration {
 
   // Configura l'accés a la base de dades (DataSource)
   // a partir de les propietats a src/main/resources/applications.properties
   // que comencen pel prefix spring.datasource
   @Bean
   @ConfigurationProperties(prefix = "spring.datasource")
   public DataSource dataSource() {
       return DataSourceBuilder.create().build();
   }
   
   @Bean
   public Categoria categoria(){        // Convenció de nom
       return new CategoriaWorldCup();
   }

}

