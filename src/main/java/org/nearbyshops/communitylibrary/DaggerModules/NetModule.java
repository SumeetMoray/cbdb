package org.nearbyshops.communitylibrary.DaggerModules;



import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import org.nearbyshops.communitylibrary.DAOs.BookReviewDAO;


/**
 * Created by sumeet on 14/5/16.
 */

        /*
        retrofit = new Retrofit.Builder()
                .baseUrl(UtilityGeneral.getServiceURL(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        */

@Module
public class NetModule {



    // Constructor needs one parameter to instantiate.
    public NetModule() {

    }

    // Dagger will only look for methods annotated with @Provides
    /*
    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    */



    @Provides
    @Singleton
    BookReviewDAO provideBookReviewDAO()
    {
        BookReviewDAO dao = new BookReviewDAO();
        return dao;
    }


}
