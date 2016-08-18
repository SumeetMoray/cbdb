package org.nearbyshops.communitylibrary;


import org.nearbyshops.communitylibrary.DaggerComponents.DaggerNetComponent;
import org.nearbyshops.communitylibrary.DaggerComponents.NetComponent;

/**
 * Created by sumeet on 14/5/16.
 */
public class DaggerComponentBuilder {


    private static DaggerComponentBuilder instance;

    private NetComponent mNetComponent;


    private DaggerComponentBuilder() {
    }

    public static DaggerComponentBuilder getInstance()
    {
        if(instance == null)
        {
            instance = new DaggerComponentBuilder();
        }

        return instance;
    }


    public NetComponent getNetComponent() {

        // If a Dagger 2 component does not have any constructor arguments for any of its modules,
        // then we can use .create() as a shortcut instead:
        //  mAppComponent = com.codepath.dagger.components.DaggerNetComponent.create();


        if(mNetComponent == null)
        {
            // Dagger%COMPONENT_NAME%
            /*mNetComponent = DaggerNetComponent.builder()
                    // list of modules that are part of this component need to be created here too
                    .netModule(new NetModule())
                    .build();*/

            mNetComponent = DaggerNetComponent.create();
        }

        return mNetComponent;
    }




//    public DataComponent getDataComponent()
//    {
//        if(dataComponent == null)
//        {
//            dataComponent = DaggerDataComponent.create();
//
//        }
//
//        return dataComponent;
//    }

}
