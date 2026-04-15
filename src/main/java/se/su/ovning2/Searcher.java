package se.su.ovning2;

import java.util.*;

public class Searcher implements SearchOperations {
  /*
  private static class RecordingYearComparator implements Comparator<Recording> {
    @Override
    public int compare(Recording a, Recording b) {
      return a.getYear() - b.getYear();
    }

   */


  private Map<String, Set<Recording>> artistToRecording = new HashMap<>();
  private Map<String, Set<Recording>> genreToRecordings = new HashMap<>();
  private Map<String, Recording> getRecordingsByTitle = new HashMap<>();
  private SortedMap<Integer, Set<Recording>> yearToRecordings = new TreeMap<>();
  private Set<Recording> recordings = new HashSet<>();


  public Searcher(Collection<Recording> data) {


    recordings.addAll(data);
    for (Recording recording : recordings) {
      String artist = recording.getArtist();
      Set<Recording> sameArtist = artistToRecording.get(artist);
      if (sameArtist == null) {
        sameArtist = new HashSet<>();
        artistToRecording.put(artist, sameArtist);
      }
      sameArtist.add(recording);
      for (String genre : recording.getGenre()) {
        Set<Recording> sameGenre = genreToRecordings.get(genre);
        if (sameGenre == null) {
          sameGenre = new HashSet<>();
          genreToRecordings.put(genre, sameGenre);

        }
        sameGenre.add(recording);
      }
      getRecordingsByTitle.put(recording.getTitle(), recording);

      int year = recording.getYear();
      Set<Recording> sameYear = yearToRecordings.get(year);
      if (sameYear == null) {
        sameYear = new HashSet<>();
        yearToRecordings.put(year, sameYear);
      }
      sameYear.add(recording);
    }

  }

  @Override
  public long numberOfArtists() {
    return artistToRecording.size();
  }

  @Override
  public long numberOfGenres() {
    return genreToRecordings.size();
  }

  @Override
  public long numberOfTitles() {
    return getRecordingsByTitle.size();
  }

  @Override
  public boolean doesArtistExist(String name) {
    return artistToRecording.containsKey(name);
  }

  @Override
  public Collection<String> getGenres() {
    return Collections.unmodifiableCollection(.keySet());
  }

  @Override
  public Recording getRecordingByName(String title) {
    return getRecordingsByTitle.get(title)
  }

  @Override
  public Collection<Recording> getRecordingsAfter(int year) {
    Set<Recording> holder = new HashSet<>();
    for ( Set<Recording> recordings : yearToRecordings.tailMap(year).values() ) {
      holder.addAll(recordings);
    }
    return Collections.unmodifiableCollection(holder);
  }

  @Override
  public SortedSet<Recording> getRecordingsByArtistOrderedByYearAsc(String artist) {
    Set<Recording> sameArtist = artistToRecording.get(artist);
    if (sameArtist==null){
      return Collections.emptySortedSet();
    }
    SortedSet<Recording> sortedRecording = new TreeSet<>(RECORDING_BY_YEAR);
    sortedRecording.addAll(sameArtist);
    return Collections.unmodifiableSortedSet(sortedRecording);
  }

  @Override
  public Collection<Recording> getRecordingsByGenre(String genre) {
    Set<Recording> holder = genreToRecordings.get(genre);
    if (holder == null) {
      return Collections.emptyList();
    }
    return Collections.unmodifiableCollection(holder);
  }

  @Override
  public Collection<Recording> getRecordingsByGenreAndYear(String genre, int yearFrom, int yearTo) {
    Set<Recording> toReturn = new HashSet<>();
    SortedMap<Integer, Set<Recording>> betweenYear = yearToRecordings.subMap(yearFrom, yearTo + 1);
    for(Set<Recording> recordings : betweenYear.values()) {
      for (Recording r: recordings) {
        for(String g : r.getGenre()) {
          if (g.equals(genre)) {
            toReturn.add(r);
          }
        }
      }
    }
    return Collections.unmodifiableSet(toReturn);
  }

  @Override
  public Collection<Recording> offerHasNewRecordings(Collection<Recording> offered) {
    Set<Recording> missing = new HashSet<>(offered);
    missing.removeAll(recordings);
    return Collections.unmodifiableSet(missing);
  }
}
