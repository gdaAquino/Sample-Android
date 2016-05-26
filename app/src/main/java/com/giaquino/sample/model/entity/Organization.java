package com.giaquino.sample.model.entity;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/24/16
 */
@AutoValue @JsonDeserialize(builder = AutoValue_Organization.Builder.class)
public abstract class Organization implements Parcelable {

    private static final String JSON_PROPERTY_ID = "id";
    private static final String JSON_PROPERTY_LOGIN = "login";
    private static final String JSON_PROPERTY_AVATAR_URL = "avatar_url";
    private static final String JSON_PROPERTY_DESCRIPTION = "description";

    public static Builder builder() {
        return new AutoValue_Organization.Builder();
    }

    @JsonProperty(JSON_PROPERTY_ID) public abstract int id();

    @JsonProperty(JSON_PROPERTY_LOGIN) public abstract String login();

    @JsonProperty(JSON_PROPERTY_AVATAR_URL) public abstract String avatarUrl();

    @Nullable @JsonProperty(JSON_PROPERTY_DESCRIPTION) public abstract String description();

    @AutoValue.Builder public static abstract class Builder {

        @JsonProperty(JSON_PROPERTY_ID) public abstract Builder id(int id);

        @JsonProperty(JSON_PROPERTY_LOGIN) public abstract Builder login(String login);

        @JsonProperty(JSON_PROPERTY_AVATAR_URL) public abstract Builder avatarUrl(String avatarUrl);

        @JsonProperty(JSON_PROPERTY_DESCRIPTION)
        public abstract Builder description(String description);

        public abstract Organization build();
    }
}
