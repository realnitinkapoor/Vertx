/**
 * Created by nitinkapoor on 22/09/17.
 */

package examples.vertx

import  io.vertx.core.Vertx
import  io.vertx.core.AbstractVerticle
import  io.vertx.core.Future
import org.codehaus.groovy.transform.tailrec.VariableExpressionReplacer

public class firstVertxAPP {


    static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx()
        vertx.deployVerticle(new myVerticle("V1"),{stringAsyncResult -> println("New Verticle deployed")})
        vertx.deployVerticle(new myVerticle("V2"),{stringAsyncResult -> println("New Verticle deployed")})
        println("hello")
        Thread.sleep(3000)
        vertx.deployVerticle(new myVerticleSender(),{stringAsyncResult -> println("Sender Deployed")})
    }
}


public class myVerticle extends AbstractVerticle {

    private String name

    public myVerticle(String name){
        this.name = name
    }

    @Override
    public void start(Future<Void> startFuture){
        println("A New myVerticle is started")
        vertx.eventBus().consumer("channel1",{message -> println("New Message:  "+message.body()+" on "+message.address())})
    }

    @Override
    public void stop(Future stopFuture) throws Exception {
        println("myVerticle is stoped")
    }
}

public class myVerticleSender extends AbstractVerticle {


    @Override
    public void start(Future<Void> startFuture){
        vertx.eventBus().publish("channel1","Message for everyone")
        vertx.eventBus().send("channel1","Message for some of them")
    }

    @Override
    public void stop(Future stopFuture) throws Exception {
        println("myVerticle is stoped")
    }
}
