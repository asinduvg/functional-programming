package actors

import akka.actor.{Actor, ActorSystem, Props}

object ActorsIntro extends App {

  // part1 - Actor systems
  val actorSystem = ActorSystem("firstActorSystem")
  println(actorSystem.name)

  // part2 - create actors
  /*
    1) actors are uniquely identified within an actor system (as humans)
    2) messages are passed asynchronously
    3) each actor may respond differently
    4) actors are encapsulated (cannot invade)
   */

  class WordCountActor extends Actor {

    // internal data
    var totalWords = 0

    // behaviour
    override def receive: PartialFunction[Any, Unit] = {
      case message: String =>
        totalWords += message.split(" ").length
        println(s"[Word Counter]: I received the message \'$message\' and it has $totalWords words")

      case msg => println(s"I cannot understand ${msg.toString}")
    }

  }

  // part3 - instantiate actors
  /*
    1) actors cannot be instantiated using new keyword. instead it has a special way to create instances
   */

  val wordCounter = actorSystem.actorOf(Props[WordCountActor], "wordCounter") // this is the instantiation of an actor
  val anotherWordCounter = actorSystem.actorOf(Props[WordCountActor], "anotherWordCounter")
  //  val wc = new WordCountActor  // this is wrong

  // part4 - communicate with the actor
  wordCounter ! "I am learning akka and it's pretty damn cool!!"
  anotherWordCounter ! "A different message"
  // sometimes anotherWordCounter receives the message before wordCounter
  // ! means tell

}
