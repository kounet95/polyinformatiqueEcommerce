package org.example.polyinformatiquecoreapi.event;

import java.time.LocalDateTime;

public class EventUpdatedEvent extends BaseEvent<String> {
    private final String title;
    private final String content;
    private final String urlMedia;
    private final String location;
    private final LocalDateTime begin;
    private final LocalDateTime end;

    public EventUpdatedEvent(
            String id,
            String title,
            String content,
            String urlMedia,
            String location,
            LocalDateTime begin,
            LocalDateTime end
    ) {
        super(id);
        this.title = title;
        this.content = content;
        this.urlMedia = urlMedia;
        this.location = location;
        this.begin = begin;
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUrlMedia() {
        return urlMedia;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}

