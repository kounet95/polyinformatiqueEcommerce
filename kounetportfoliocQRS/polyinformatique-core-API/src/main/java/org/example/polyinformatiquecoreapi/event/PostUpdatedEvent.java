package org.example.polyinformatiquecoreapi.event;

import java.util.List;

public class PostUpdatedEvent extends BaseEvent<String> {
    private final String title;
    private final String content;
    private final String urlMedia;
    private final String domainId;
    private final List<String> tagIds;

    public PostUpdatedEvent(
            String id,
            String title,
            String content,
            String urlMedia,
            String domainId,
            List<String> tagIds
    ) {
        super(id);
        this.title = title;
        this.content = content;
        this.urlMedia = urlMedia;
        this.domainId = domainId;
        this.tagIds = tagIds;
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

    public String getDomainId() {
        return domainId;
    }

    public List<String> getTagIds() {
        return tagIds;
    }
}
