package org.example.polyinformatiquecoreapi.event;

public class NewsUpdatedEvent extends BaseEvent<String> {
    private final String title;
    private final String content;
    private final String urlMedia;
    private final String summary;

    public NewsUpdatedEvent(
            String id,
            String title,
            String content,
            String urlMedia,
            String summary
    ) {
        super(id);
        this.title = title;
        this.content = content;
        this.urlMedia = urlMedia;
        this.summary = summary;
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

    public String getSummary() {
        return summary;
    }
}
