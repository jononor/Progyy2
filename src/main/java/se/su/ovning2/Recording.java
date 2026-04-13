package se.su.ovning2;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public class Recording {
  private final int year;
  private final String artist;
  private final String title;
  private final String type;
  private final Set<String> genre;

  public Recording(String title, String artist, int year, String type, Set<String> genre) {
    this.title = title;
    this.year = year;
    this.artist = artist;
    this.type = type;
    this.genre = genre;
  }

  public String getArtist() {
    return artist;
  }

  public Collection<String> getGenre() {
    return genre;
  }

  public String getTitle() {
    return title;
  }

  public String getType() {
    return type;
  }

  public int getYear() {
    return year;
  }

  @Override
  public String toString() {
    return String.format("{ %s | %s | %s | %d | %s }", artist, title, genre, year, type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, year, artist);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this)
      return true;
    if (!(other instanceof Recording))
      return false;
    Recording rec = (Recording) other;
    if (!Objects.equals(title, rec.title))
      return false;
    if (!Objects.equals(year, rec.year))
      return false;
    if (!Objects.equals(artist, rec.artist))
      return false;
    return true;
  }
}
