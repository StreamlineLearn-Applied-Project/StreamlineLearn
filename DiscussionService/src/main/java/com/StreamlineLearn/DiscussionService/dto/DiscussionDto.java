package com.StreamlineLearn.DiscussionService.dto;

public class DiscussionDto {
    private String discussionTitle;
    private String discussion;
    private String instructorName;

    public DiscussionDto(String discussionTitle, String discussion, String instructorName) {
        this.discussionTitle = discussionTitle;
        this.discussion = discussion;
        this.instructorName = instructorName;
    }

    public String getDiscussionTitle() {
        return discussionTitle;
    }

    public void setDiscussionTitle(String discussionTitle) {
        this.discussionTitle = discussionTitle;
    }

    public String getDiscussion() {
        return discussion;
    }

    public void setDiscussion(String discussion) {
        this.discussion = discussion;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
}
