package com.example.mk;

import java.util.List;

public class AnimeItem {
   private String name;
   private List<String> genres;
   private String posterUrl;
   private String description;
   private List<String> series;
   private int season;

   public AnimeItem(String name, List<String> genres, String posterUrl, String description, List<String> series, int season) {
      this.name = name;
      this.genres = genres;
      this.posterUrl = posterUrl;
      this.description = description;
      this.series = series;
      this.season = season;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List<String> getGenres() {
      return genres;
   }

   public void setGenres(List<String> genres) {
      this.genres = genres;
   }

   public String getPosterUrl() {
      return posterUrl;
   }

   public void setPosterUrl(String posterUrl) {
      this.posterUrl = posterUrl;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public List<String> getSeries() {
      return series;
   }

   public void setSeries(List<String> series) {
      this.series = series;
   }

   public int getSeason() {
      return season;
   }

   public void setSeason(int season) {
      this.season = season;
   }
}
