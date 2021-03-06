package de.unikoblenz.west.lkastler.eventfinder.events;

/**
 * describes a temporal-spatial event.
 *
 * Created by lkastler on 9/5/13.
 */
public class Event {

    private int id;
    private String title;
    private String artists;
    private String venue;
    private long start;
    private long end;
    private long latitude;
    private long longitude;

    /**
     * constructor
     *
     * @param id - identifier of event.
     * @param title - title of event.
     * @param artists - comma separated list of artists.
     * @param venue - name of the venue.
     * @param start - starting time of the event.
     * @param end - end time of the event.
     * @param latitude - latitude of event.
     * @param longitude - longitude of events.
     */
    public Event(int id, String title, String artists, String venue, long start, long end, long latitude, long longitude) {
        this.id = id;
        this.title = title;
        this.artists = artists;
        this.venue = venue;
        this.start = start;
        this.end = end;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * returns the id of this Event.
     * @return the id of this Event.
     */
    public int getId() {
        return id;
    }

    /**
     * returns the title of this Event.
     * @return the title of this Event.
     */
    public String getTitle() {
        return title;
    }

    /**
     * returns artists of this Event.
     * @return artists of this Event.
     */
    public String getArtists() {
        return artists;
    }

    /**
     * returns the name of the venue of this Event.
     * @return the name of the venue of this Event.
     */
    public String getVenue() {
        return venue;
    }

    /**
     * returns the starting time of this Event.
     * @return the starting time of this Event.
     */
    public long getStart() {
        return start;
    }

    /**
     * returns the end time of this Event.
     * @return the end time of this Event.
     */
    public long getEnd() {
        return end;
    }

    /**
     * returns the latitude of this Event.
     * @return the latitude of this Event.
     */
    public long getLatitude() {
        return latitude;
    }

    /**
     * returns the longitude of this Event.
     * @return the longitude of this Event.
     */
    public long getLongitude() {
        return longitude;
    }
}
