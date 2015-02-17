package com.eyeem.chipsedittextdemo;

import android.app.Application;

import com.eyeem.chipsedittextdemo.core.StorageDep;
import com.eyeem.chipsedittextdemo.core.AppDep;
import com.eyeem.chipsedittextdemo.core.AppModule;
import com.eyeem.chipsedittextdemo.core.PausableExecutorModule;
import com.eyeem.chipsedittextdemo.mortarflow.FlowDep;
import com.eyeem.chipsedittextdemo.mortarflow.ScopeSingleton;

import mortar.MortarScope;
import mortar.dagger2support.DaggerService;

import static mortar.dagger2support.DaggerService.createComponent;

/**
 * Created by vishna on 27/01/15.
 */
public class App extends Application {
   private MortarScope rootScope;

   @dagger.Component(modules = {AppModule.class, PausableExecutorModule.class})
   @ScopeSingleton(Component.class)
   public interface Component extends FlowDep, AppDep, StorageDep {}

   @Override public void onCreate() {
      super.onCreate();
   }

   @Override public Object getSystemService(String name) {
      if (rootScope == null) {
         rootScope = MortarScope.buildRootScope()
            .withService(DaggerService.SERVICE_NAME, createComponent(Component.class, new AppModule(this)))
            .build();
      }

      return rootScope.hasService(name) ? rootScope.getService(name) : super.getSystemService(name);
   }
}
