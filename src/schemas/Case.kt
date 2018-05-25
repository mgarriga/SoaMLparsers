package schemas

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import compatibilityUtils.InterfacesCompatibilityChecker
import edu.giisco.SoaML.metamodel.Interface
import io.github.cdimascio.dotenv.dotenv
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import parsers.loadOas
import parsers.loadWsdl

val checker = InterfacesCompatibilityChecker()
private val dotenv = dotenv { directory = "./" }


data class Case(val problem: Interface, var solution: String = "") {
    val solutionIsDefined get() = solution != ""

    fun getDistance(anotherCase: Case): Double {
        checker.serviceInterface = anotherCase.problem
        checker.requiredInterface = problem
        checker.run()
        return checker.getAdaptabilityGap()
    }
}


fun getDatabase(name: String): MongoDatabase {
    println("Connecting to Mongo")
    val host = dotenv["DATABASE_HOST"] ?: "192.168.0.1"
    val port = dotenv["DATABASE_PORT"]?.toInt() ?: 27017
    val client = KMongo.createClient(host = host, port = port)
    return client.getDatabase(name)
}


fun getCaseCollection(): MongoCollection<Case> {
    val databaseName = dotenv["DATABASE_NAME"] ?: "KB"
    val database = getDatabase(databaseName)
    val caseCollection = database.getCollection<Case>()
    println("Connection with Mongo established.")
    return caseCollection
}


fun main(args: Array<String>) {
    val dropDB = dotenv["DROP_DB"] == "true"
    if (dropDB) {
        println("Dropping DB")
        val caseCollection = getCaseCollection()
        caseCollection.drop()

    }
    loadWsdl()
    loadOas()
}
