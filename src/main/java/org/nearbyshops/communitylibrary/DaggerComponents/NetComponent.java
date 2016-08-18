package org.nearbyshops.communitylibrary.DaggerComponents;


import javax.inject.Singleton;

import dagger.Component;
import org.nearbyshops.communitylibrary.DaggerModules.NetModule;
import org.nearbyshops.communitylibrary.RESTEndpoint.BookReviewResource;

/**
 * Created by sumeet on 14/5/16.
 */

@Singleton
@Component(modules={NetModule.class})
public interface NetComponent {

    void Inject(BookReviewResource bookReviewResource);
}
