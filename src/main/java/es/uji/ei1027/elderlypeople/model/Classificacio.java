package es.uji.ei1027.elderlypeople.model;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class Classificacio {
       private String nomNadador;
       private String nomProva;
       @DateTimeFormat(pattern = "HH:mm:ss.SSS") 
       private LocalTime temps;
       private int posicio;
       public Classificacio() {
       
       }
		public String getNomNadador() {
			return nomNadador;
		}
		public void setNomNadador(String nomNadador) {
			this.nomNadador = nomNadador;
		}
		public String getNomProva() {
			return nomProva;
		}
		public void setNomProva(String nomProva) {
			this.nomProva = nomProva;
		}
		public LocalTime getTemps() {
			return temps;
		}
		public void setTemps(LocalTime temps) {
			this.temps = temps;
		}
		public int getPosicio() {
			return posicio;
		}
		public void setPosicio(int posicio) {
			this.posicio = posicio;
		}
		@Override
		public String toString() {
			return "Classificacio [nomNadador=" + nomNadador + ", nomProva=" + nomProva + ", temps=" + temps
					+ ", posicio=" + posicio + "]";
		}
		
       
}
