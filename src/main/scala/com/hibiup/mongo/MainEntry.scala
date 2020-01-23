package com.hibiup.mongo

import cats.effect.{ExitCode, IO, IOApp, Resource}
import com.typesafe.config.ConfigFactory
import monix.eval.Task
import com.mongodb.{MongoClientURI, MongoClient}
import monix.execution.Scheduler.Implicits.global

object MainEntry extends IOApp{
  override def run(args: List[String]): IO[ExitCode] = IO {
    mongodb.use {
      db => {
        Task(println(db.getName))
      }
    }.guarantee(Task(println("Application stopped"))).map(_ => ExitCode.Success).runSyncUnsafe()
  }

  def mongodb = for {
      conf <- Resource.make {
        Task(ConfigFactory.load())
      } { _ => Task() }

      mongo <- Resource.make{
        Task{
          val connectionURI = new MongoClientURI(conf.getString("mongodb.connection"))
          new MongoClient(connectionURI)
        }
      }{ client => Task(client.close()) }
    } yield mongo.getDatabase(conf.getString("mongodb.database"))
}
