package com.giaquino.sample.model.entity;

import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

/**
 * @author Gian Darren Azriel Aquino.
 */
@AutoValue
@JsonDeserialize(builder = AutoValue_User.Builder.class)
public abstract class User implements Parcelable {

    private static final String JSON_PROPERTY_ID = "id";
    private static final String JSON_PROPERTY_LOGIN = "login";
    private static final String JSON_PROPERTY_AVATAR_URL = "avatar_url";

    public static Builder builder() {
        return new AutoValue_User.Builder();
    }

    @JsonProperty(JSON_PROPERTY_ID)
    public abstract int id();

    @JsonProperty(JSON_PROPERTY_LOGIN)
    public abstract String login();

    @JsonProperty(JSON_PROPERTY_AVATAR_URL)
    public abstract String avatarUrl();

    @AutoValue.Builder
    public static abstract class Builder {

        @JsonProperty(JSON_PROPERTY_ID)
        public abstract Builder id(int id);

        @JsonProperty(JSON_PROPERTY_LOGIN)
        public abstract Builder login(String login);

        @JsonProperty(JSON_PROPERTY_AVATAR_URL)
        public abstract Builder avatarUrl(String avatarUrl);

        public abstract User build();
    }
}
