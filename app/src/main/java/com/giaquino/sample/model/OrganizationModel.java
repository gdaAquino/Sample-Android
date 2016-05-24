package com.giaquino.sample.model;

import com.giaquino.sample.model.api.GithubApi;
import com.giaquino.sample.model.entity.Organization;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/24/16
 */
public class OrganizationModel {

    private GithubApi githubApi;

    private volatile List<Organization> cache = new ArrayList<>();
    private BehaviorSubject<List<Organization>> observableOrganizations = BehaviorSubject.create(
        Collections.unmodifiableList(cache));
    private PublishSubject<Throwable> observableErrors = PublishSubject.create();

    public OrganizationModel(GithubApi githubApi) {
        this.githubApi = githubApi;
    }

    public void loadOrganizations(int since) {
        githubApi.getOrganizations(GithubApi.GITHUB_TOKEN, since).subscribe(organizations -> {
            if (since == 0) {
                cache.clear();
            }
            cache.addAll(organizations);
            observableOrganizations.onNext(Collections.unmodifiableList(cache));
        }, throwable -> observableErrors.onNext(throwable));
    }

    public Observable<List<Organization>> organizations() {
        return observableOrganizations.debounce(1000, TimeUnit.MILLISECONDS);
    }

    public Observable<Throwable> errors() {
        return observableErrors.debounce(1000, TimeUnit.MILLISECONDS);
    }
}
