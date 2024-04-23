package com.StreamlineLearn.AnnouncementManagement.dto;

public class AnnouncementDto {
    private String AnnouncementTile;
    private String Announcement;

    public AnnouncementDto(String announcementTile, String announcement) {
        AnnouncementTile = announcementTile;
        Announcement = announcement;
    }

    public String getAnnouncementTile() {
        return AnnouncementTile;
    }

    public void setAnnouncementTile(String announcementTile) {
        AnnouncementTile = announcementTile;
    }

    public String getAnnouncement() {
        return Announcement;
    }

    public void setAnnouncement(String announcement) {
        Announcement = announcement;
    }
}
