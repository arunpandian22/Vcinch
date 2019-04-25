package me.arun.vcinch.utils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * A Utill class is created to implement the event bus across the app to communicate between any class to any activity
 */
public class RxBusUtil
{
    PublishSubject<Object> bus;
    public RxBusUtil()
    {
        bus = PublishSubject.create();
    }

     /**
     * Subscribe to this Observable. On event, do something
     *
     */

    public Observable<Object> toObservable()
    {
        return bus;
    }

    /**
     * A  method to send the event
     * @param o a object which is supposed to be sent
     */
    public void send(Object o)
    {
        bus.onNext(o);
    }

}