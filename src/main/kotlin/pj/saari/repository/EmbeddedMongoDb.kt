package pj.saari.repository

import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network

class EmbeddedMongoDb {

    init {
        val ip = "localhost"
        val port = 27017

        val mongodConfig = MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(Net(ip, port, Network.localhostIsIPv6()))
                .build()

        MongodStarter
                .getDefaultInstance()
                .prepare(mongodConfig)
                .start()


    }


}